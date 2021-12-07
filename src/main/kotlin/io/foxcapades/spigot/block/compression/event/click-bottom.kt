@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.spigot.block.compression.event

import io.foxcapades.spigot.block.compression.facades.Facade
import io.foxcapades.spigot.block.compression.inventory.spreadCraftingTable
import io.foxcapades.spigot.block.compression.item.isEmpty
import org.bukkit.event.inventory.InventoryClickEvent

// ╔═════════════════════════════════════════════════════════════════════════╗//
// ║                                                                         ║//
// ║    Player Inventory Click Event                                         ║//
// ║                                                                         ║//
// ╚═════════════════════════════════════════════════════════════════════════╝//

internal fun InventoryClickEvent.handleBottomClick() {
  Facade.logTrace("InventoryClickEvent#handleBottomClick()")

  if (click.isShiftClick)
    handleBottomShiftClick()
}

private inline fun InventoryClickEvent.handleBottomShiftClick() {
  // Disable the default behavior of this action no matter what.
  isCancelled = true

  // If the slot that was clicked on is empty:
  if (currentItem.isEmpty())
    // Do nothing.
    return

  // Convenience holder to avoid the null-checks since we already know it's not
  // null.
  val current = currentItem!!

  Facade.runTask {
    val remains = view.topInventory.spreadCraftingTable(current)

    // Nothing left to keep in the old stack
    if (remains == 0) {
      view.bottomInventory.setItem(slot, null)
    }

    // The full clicked stack wasn't able to be fully consumed by the
    // crafting grid.
    else {
      current.amount = remains
    }

    calculateResult()
  }
}

