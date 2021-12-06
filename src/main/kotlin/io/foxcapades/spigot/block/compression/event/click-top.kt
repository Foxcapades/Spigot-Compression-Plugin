@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.spigot.block.compression.event

import io.foxcapades.spigot.block.compression.facades.Facade
import io.foxcapades.spigot.block.compression.inventory.decrementCraftingGrid
import io.foxcapades.spigot.block.compression.item.*
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType.SlotType.RESULT

// ╔═════════════════════════════════════════════════════════════════════════╗//
// ║                                                                         ║//
// ║    Compression Workbench Click Event                                    ║//
// ║                                                                         ║//
// ╚═════════════════════════════════════════════════════════════════════════╝//

internal fun InventoryClickEvent.handleTopClick() {
  Facade.logTrace("InventoryClickEvent#handleTopClick()")

  // Proactively cancel the event as we handle all conditions ourselves.
  isCancelled = true

  if (slotType == RESULT)
    handleResultClick()
  else
    calculateResult(true)
}

private inline fun InventoryClickEvent.handleResultClick() {
  when {
    click.isShiftClick -> handleResultShiftClick()
    click.isRightClick -> handleResultRightClick()
    click.isLeftClick  -> handleResultLeftClick()
  }
}

private inline fun InventoryClickEvent.handleResultLeftClick() {

  // If the result slot is empty:
  if (currentItem.isEmpty())
    // There's nothing to do.
    return

  // We know:
  // - The result slot has an ItemStack in it.

  // If the player's cursor is non-empty:
  if (cursor.isNotEmpty()) {

    // If the held item and the result item are incompatible:
    if (cursor isNotSimilarTo currentItem)
      // End here.
      return

    // If the stack currently in the cursor is already full:
    if (!cursor.isFull())
      // End here.
      return

    // Calculate the new amount that will be held in the cursor after this
    // action.
    val newAmount = cursor!!.amount + currentItem!!.amount

    // If the new amount would push the held stack over the maximum:
    if (newAmount > cursor!!.maxStackSize)
      // End here.
      return

    Facade.runTask {
      // Update the amount on the cursor stack.
      view.cursor!!.amount = newAmount

      // Clear out the result slot.
      currentItem = null

      // Decrement the stacks in the crafting grid by 1.
      view.topInventory.decrementCraftingGrid()

      // Recalculate the recipe.
      calculateResult(false)
    }
  }

  // We know:
  // - The result slot is not empty
  // - The cursor is empty.

  Facade.runTask {
    // Set the cursor item to the item that is currently in the result slot.
    view.cursor = currentItem

    // Clear out the result slot.
    currentItem = null

    // Decrement the stacks in the crafting grid by 1.
    view.topInventory.decrementCraftingGrid()

    // Recalculate the result.
    calculateResult(false)
  }
}

// TODO: This should materialize the stack and consume half.  Waiting on Tap
//       library to implement this feature.
private inline fun InventoryClickEvent.handleResultRightClick() {

  // If the user's cursor has a held item on it:
  if (cursor.isNotEmpty()) {

    // Cancel the event as we don't allow placing items into the result slot.
    isCancelled = true

    // End here
    return
  }

  // Temporarily cancel the event until we can implement the materialization
  // feature.
  isCancelled = true
}

private inline fun InventoryClickEvent.handleResultShiftClick() {
  // TODO: repeatedly consume all ingredients and push to inventory
  isCancelled = true
  return
}