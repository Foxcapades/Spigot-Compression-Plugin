@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.spigot.block.compression.event

import io.foxcapades.spigot.block.compression.facades.Facade
import io.foxcapades.spigot.block.compression.inventory.spreadCraftingTable
import io.foxcapades.spigot.block.compression.item.isEmpty
import io.foxcapades.spigot.block.compression.item.isNotEmpty
import org.bukkit.Material.AIR
import org.bukkit.event.inventory.InventoryClickEvent

internal inline fun InventoryClickEvent.handleBottomShiftClick() {
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
  }

}

internal inline fun InventoryClickEvent.handleResultClick() {

  if (click.isShiftClick) {
    // TODO: repeatedly consume all ingredients and push to inventory
    isCancelled = true
    return
  }

  if (click.isRightClick) {
    handleResultRightClick()
    return
  }

  // is left click (or middle click?)

  if (currentItem == null || currentItem!!.type == AIR) {
    isCancelled = true
    return
  }

  // left click with a held item
  if (cursor != null && cursor!!.type != AIR) {

    if (cursor!!.isSimilar(currentItem) && cursor!!.amount < cursor!!.maxStackSize) {
      Facade.runTask {
        view.cursor!!.amount += currentItem!!.amount
        view.topInventory.decrementCraftingGrid()
        calculateResult()
      }
    }

    // Cancel intentionally to stop default behavior.
    isCancelled = true
    return
  }


  Facade.runTask {
    view.topInventory.decrementCraftingGrid()
    calculateResult()
  }

}

internal inline fun InventoryClickEvent.handleResultRightClick() {
  // Right click into the slot is rejected.
  if (cursor != null) {
    isCancelled = true
    return
  }

  // TODO: Should materialize stack and consume half.
  isCancelled = true
}