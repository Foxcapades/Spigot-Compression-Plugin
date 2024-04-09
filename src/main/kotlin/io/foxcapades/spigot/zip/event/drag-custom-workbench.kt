package io.foxcapades.spigot.zip.event

import io.foxcapades.spigot.block.compression.facades.Facade
import io.foxcapades.spigot.block.compression.wrap.CraftInventory
import org.bukkit.event.inventory.InventoryDragEvent

// ╔═════════════════════════════════════════════════════════════════════════╗//
// ║                                                                         ║//
// ║    Any Inventory Drag Event                                             ║//
// ║                                                                         ║//
// ╚═════════════════════════════════════════════════════════════════════════╝//

internal fun InventoryDragEvent.handleCustomDrag() {
  // For each slot in the slots the user dragged across:
  for (index in rawSlots) {

    if (index == 0) {
      isCancelled = true
      return
    }

    if (index.isTopInventorySlot()) {
      Facade.runTask { CraftInventory(view.topInventory).calculateResult() }
      break
    }
  }
}