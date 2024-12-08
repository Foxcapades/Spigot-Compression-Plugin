package io.foxcapades.bukkit.szip.event.player

import io.foxcapades.bukkit.inventory.SlotReference
import io.foxcapades.bukkit.inventory.itemSlotSequence
import io.foxcapades.bukkit.inventory.set
import io.foxcapades.bukkit.szip.Logger
import io.foxcapades.bukkit.szip.Plugin
import io.foxcapades.bukkit.szip.Server
import io.foxcapades.bukkit.szip.zip.isCompressed
import io.foxcapades.bukkit.szip.consts.Permission
import io.foxcapades.bukkit.szip.event.inventory.handle
import io.foxcapades.bukkit.szip.ext.*
import io.foxcapades.bukkit.szip.item.ZipTool.isZipTool
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.block.Action
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack

internal fun PlayerInteractEvent.handle() {
  when {
    item.isZipTool             -> handleZipTool()
    item?.isCompressed == true -> handleCompressed()
    item.isProjectileWeapon    -> handleProjectileWeapon()
  }
}

private fun PlayerInteractEvent.handleZipTool() {
  if (player.hasPermission(Permission.ToolUse)) {
    isCancelled = true

    // see method docs for more info
    if (player.openInventory.isCompressionTool()) {
      player.openInventory.handleBukkitBugForBlocklessCraftingTable()
    } else {
      Logger.trace { "opening compression GUI for player %s".format(player.name) }
      player.openCompressionGUI()
    }
  }
}

private fun PlayerInteractEvent.handleCompressed() {
  isCancelled = when (action) {
    Action.RIGHT_CLICK_BLOCK -> !clickedBlock!!.type.isInteractable
    else                     -> true
  }

  if (useItemInHand() == Event.Result.DENY)
    Logger.trace { "cancelling interact event for (player=%s, item=%s)".format(player.name, item?.itemMeta?.displayName) }
}

private fun PlayerInteractEvent.handleProjectileWeapon() {
  val slots = ArrayList<SlotReference>(35)

  val hasOtherAmmo = player.inventory
    .itemSlotSequence(reverseSections = true)
    .filter {
      if (it.stack.isAmmo && it.stack?.isCompressed == true) {
        slots.add(it)
        false
      } else {
        true
      }
    }
    .any { (_, v) -> v.isAmmo }

  if (hasOtherAmmo) {
    Logger.trace { "hiding compressed ammo for %s for one tick".format(player.name) }
    slots.forEach { (slot, _) -> player.inventory[slot] = null }
    Server.scheduler.runTask(Plugin, Runnable { slots.forEach { (slot, stack) -> player.inventory[slot] = stack } })
  } else {
    Logger.trace { "cancelling bow usage for player %s as have no usable ammo".format(player.name) }
    setUseItemInHand(Event.Result.DENY)
  }
}

/**
 * Handles a weird edge case (bug?) in bukkit where when a player opens the
 * compression GUI by clicking open air, then tries to shift + right-click the
 * compression result into their inventory, Bukkit fires a bunch of player
 * interact events instead of an inventory click event.
 *
 * Handling checks if the player already has the compression GUI open, and if
 * so, processes a fake inventory click event instead of trying to open the GUI
 * again.
 */
private fun InventoryView.handleBukkitBugForBlocklessCraftingTable() {
  Logger.trace { "handling bukkit bug with incorrect PlayerInteractEvent for player %s".format(player.name) }
  player.openInventory
    .let { InventoryClickEvent(it, it.getSlotType(0), 0, ClickType.SHIFT_LEFT, InventoryAction.PICKUP_ALL) }
    .handle()
}

private fun Player.getClosestBlock(): Block {
  for (y in location.blockY - 1 downTo location.blockY - 10)
    world.getBlockAt(location.blockX, y, location.blockZ).takeUnless { it.isEmpty }?.also { return it }

  for (x in location.blockX - 10 .. location.blockX + 10)
    world.getBlockAt(x, location.blockY, location.blockZ).takeUnless { it.isEmpty }?.also { return it }

  for (z in location.blockZ - 10 .. location.blockZ + 10)
    world.getBlockAt(location.blockX, location.blockY, z).takeUnless { it.isEmpty }?.also { return it }

  return world.getBlockAt(location)
}
