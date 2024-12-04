package io.foxcapades.spigot.bcp.event.inventory

import io.foxcapades.spigot.bcp.Plugin
import io.foxcapades.spigot.bcp.Server
import io.foxcapades.spigot.bcp.compress.isCompressed
import io.foxcapades.spigot.bcp.ext.isCompressionTool
import io.foxcapades.spigot.bcp.ext.logName
import io.foxcapades.spigot.bcp.inv.CraftInventory
import io.foxcapades.spigot.bcp.Logger
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryType.*

internal fun InventoryDragEvent.handle() = if (view.isCompressionTool()) handleCustom() else handleStandard()

private fun InventoryDragEvent.handleStandard() {
  // Do nothing for these view types, the player is allowed to put compressed
  // blocks in these.
  when (view.topInventory.type) {
    CHEST       -> return
    DISPENSER   -> return
    DROPPER     -> return
    PLAYER      -> return
    ENDER_CHEST -> return
    SHULKER_BOX -> return
    BARREL      -> return
    CREATIVE    -> return
    HOPPER      -> return
    else        -> { /* continue */ }
  }

  if (!newItems.values.first().isCompressed)
    return

  val min = view.topInventory.size

  for (i in rawSlots) {
    if (i < min) {
      Logger.trace { "cancelling drag event for item %s".format(newItems.values.first().logName) }
      isCancelled = true
      return
    }
  }
}

private fun InventoryDragEvent.handleCustom() {
  // For each slot in the slots the user dragged across:
  for (index in rawSlots) {

    if (index == 0) {
      isCancelled = true
      return
    }

    // indices 0..9 are top inventory slots
    if (index in 0..9) {
      Server.scheduler.runTask(Plugin, Runnable { CraftInventory(view.topInventory).calculateResult() })
      break
    }
  }
}
