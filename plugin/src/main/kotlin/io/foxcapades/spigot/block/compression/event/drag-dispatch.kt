package io.foxcapades.spigot.block.compression.event

import io.foxcapades.spigot.block.compression.item.compressionLevel
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryType.*

internal fun handleDragEvent(event: InventoryDragEvent) {
  // Do nothing for these view types, the player is allowed to put compressed
  // blocks in these.
  when (event.view.topInventory.type) {
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

  if (event.newItems.values.first().compressionLevel().value == 0) {
    return
  }

  val min = event.view.topInventory.size

  for (i in event.rawSlots) {
    if (i < min) {
      event.isCancelled = true
      return
    }
  }
}
