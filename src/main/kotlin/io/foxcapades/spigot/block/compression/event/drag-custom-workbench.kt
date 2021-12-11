package io.foxcapades.spigot.block.compression.event

import io.foxcapades.spigot.block.compression.facades.Facade
import org.bukkit.event.inventory.InventoryDragEvent

// ╔═════════════════════════════════════════════════════════════════════════╗//
// ║                                                                         ║//
// ║    Any Inventory Drag Event                                             ║//
// ║                                                                         ║//
// ╚═════════════════════════════════════════════════════════════════════════╝//

internal fun InventoryDragEvent.handleCustomDrag() {
  // For each slot in the slots the user dragged across:
  for (index in rawSlots) {

    // If the slot is the result slot:
    if (index == 0) {
      // Cancel the event as we don't allow dragging into that slot.
      isCancelled = true
      return
    }

    // If the slot is within the crafting grid:
    if (index.isTopInventorySlot()) {

      // We know that this drag event is relevant to us.
      Facade.runTask { calculateResult() }

      // No need to keep searching.
      break
    }
  }
}