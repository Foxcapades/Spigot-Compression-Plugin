package io.foxcapades.spigot.block.compression.event.handler.inventory

import io.foxcapades.spigot.block.compression.Plugin
import io.foxcapades.spigot.block.compression.Server
import io.foxcapades.spigot.block.compression.compress.isCompressed
import io.foxcapades.spigot.block.compression.inv.CraftInventory
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryType.*

internal fun InventoryDragEvent.handleStandard() {
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

  if (newItems.values.first().isCompressed())
    return

  val min = view.topInventory.size

  for (i in rawSlots) {
    if (i < min) {
      isCancelled = true
      return
    }
  }
}

internal fun InventoryDragEvent.handleCustom() {
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
