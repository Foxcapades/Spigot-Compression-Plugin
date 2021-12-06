package io.foxcapades.spigot.block.compression.event

import org.bukkit.event.inventory.InventoryDragEvent

// ╔═════════════════════════════════════════════════════════════════════════╗//
// ║                                                                         ║//
// ║    Any Inventory Drag Event                                             ║//
// ║                                                                         ║//
// ╚═════════════════════════════════════════════════════════════════════════╝//

internal fun InventoryDragEvent.handleDrag() {
  var relevant = false

  // For each slot in the slots the user dragged across:
  for (i in rawSlots) {

    // If the slot is the result slot:
    if (i == 0) {

      // Cancel the event as we don't allow dragging into that slot.
      isCancelled = true
      return
    }

    // If the slot is within the crafting grid:
    if (i in 1..9) {

      // We know that this drag event is relevant to us.
      relevant = true

      // No need to keep searching.
      break
    }
  }

  // If this event is not relevant to us:
  if (!relevant)
    // Ignore it.
    return

  // Update the result slot asynchronously.
  calculateResult(true)
}