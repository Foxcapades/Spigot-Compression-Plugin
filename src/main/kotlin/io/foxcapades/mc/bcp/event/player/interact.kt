package io.foxcapades.mc.bcp.event.player

import io.foxcapades.mc.bcp.zip.isCompressed
import io.foxcapades.mc.bcp.consts.Permission
import io.foxcapades.mc.bcp.event.inventory.handle
import io.foxcapades.mc.bcp.ext.isCompressionTool
import io.foxcapades.mc.bcp.ext.openCompressionGUI
import io.foxcapades.mc.bcp.item.ZipTool.isZipTool
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerInteractEvent

internal fun PlayerInteractEvent.handle() {
  if (item.isZipTool && player.hasPermission(Permission.ToolUse)) {
    isCancelled = true

    player.openInventory.takeIf { it.isCompressionTool() }
      ?.let { InventoryClickEvent(it, it.getSlotType(0), 0, ClickType.SHIFT_LEFT, InventoryAction.PICKUP_ALL) }
      ?.handle()
      ?: player.openCompressionGUI()
  } else if (item.isCompressed) {
    isCancelled = true
  }
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
