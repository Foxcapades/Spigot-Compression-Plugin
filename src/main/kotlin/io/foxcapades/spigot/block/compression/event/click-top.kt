@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.spigot.block.compression.event

import io.foxcapades.spigot.block.compression.facades.Facade
import io.foxcapades.spigot.block.compression.inventory.decrementCraftingGrid
import io.foxcapades.spigot.block.compression.item.*
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType.SlotType.RESULT
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

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
    handleGridClick()
}


// ╔═════════════════════════════════════════════════════════════════════════╗//
// ║                                                                         ║//
// ║    Result Slot Click Event                                              ║//
// ║                                                                         ║//
// ╚═════════════════════════════════════════════════════════════════════════╝//


private inline fun InventoryClickEvent.handleResultClick() {
  when {
    click.isShiftClick -> handleResultShiftClick()
    click.isRightClick -> handleResultRightClick()
    click.isLeftClick  -> handleResultLeftClick()
  }
}

private inline fun InventoryClickEvent.handleResultLeftClick() {
  // If the result slot is empty:
  if (currentItem.isEmpty()) {
    // There's nothing to do.
    return
  }


  // If the player's cursor is non-empty:
  if (cursor.isEmpty())
    handleResultLeftClickEmptyCursor()
  else
    handleResultLeftClickFullCursor(cursor!!, currentItem!!)

}

private inline fun InventoryClickEvent.handleResultLeftClickEmptyCursor() {
  // We know:
  // - The result slot has an ItemStack in it.
  // - The cursor is empty.

  // Set the cursor item to the item that is currently in the result slot.
  view.cursor = currentItem

  // Clear out the result slot.
  currentItem = null

  // Decrement the stacks in the crafting grid by 1.
  view.topInventory.decrementCraftingGrid()

  // Recalculate the result.
  calculateResult()
}

private inline fun InventoryClickEvent.handleResultLeftClickFullCursor(cursor: ItemStack, slot: ItemStack) {
  Facade.logTrace("InventoryClickEvent#handleResultLeftClickFullCursor()")

  // We know:
  // - The result slot has an ItemStack in it.
  // - The cursor is not empty

  // If the held item and the result item are incompatible:
  if (cursor isNotSimilarTo slot) {
    // End here.
    return
  }

  // If the stack currently in the cursor is already full:
  if (cursor.isFull()) {
    // End here.
    return
  }

  // Calculate the new amount that will be held in the cursor after this
  // action.
  val newAmount = cursor.amount + slot.amount

  // If the new amount would push the held stack over the maximum:
  if (newAmount > cursor.maxStackSize)
  // End here.
    return

  // Update the amount on the cursor stack.
  view.cursor!!.amount = newAmount

  // Clear out the result slot.
  currentItem = null

  // Decrement the stacks in the crafting grid by 1.
  view.topInventory.decrementCraftingGrid()

  // Recalculate the recipe.
  calculateResult()
}

// TODO: This should materialize the stack and consume half.  Waiting on Tap
//       library to implement this feature.
//       This will also mean updating the code in handleResultShiftClick to
//       to handle a materialized partial stack already in the slot.
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
  val resultSlot = view.topInventory.getItem(0) ?: return
  val reduceBy   = view.topInventory.maxAllStackSize()
  val remains    = view.bottomInventory.addItem(ItemStack(resultSlot).apply { amount = resultSlot.amount * reduceBy })

  view.topInventory.reduceAllBy(reduceBy)
  calculateResult()
  whoClicked.world.dropItem(whoClicked.location, remains[0] ?: return)
}


// ╔═════════════════════════════════════════════════════════════════════════╗//
// ║                                                                         ║//
// ║    Crafting Grid Click Event                                            ║//
// ║                                                                         ║//
// ╚═════════════════════════════════════════════════════════════════════════╝//


private inline fun InventoryClickEvent.handleGridClick() {
  when {
    click.isShiftClick    -> handleGridShiftClick()
    currentItem.isEmpty() -> handleEmptySlotGridClick()
    else                  -> handleFullSlotGridClick()
  }
}

private inline fun InventoryClickEvent.handleGridShiftClick() {
  Facade.runTask {
    val clicked = view.topInventory.getItem(slot) ?: return@runTask
    val remains = view.bottomInventory.addItem(clicked)

    view.topInventory.setItem(slot, remains[0])
  }
}

private inline fun InventoryClickEvent.handleEmptySlotGridClick() {
  // If there is nothing on the user's cursor:
  if (cursor.isEmpty())
    // There's nothing to do here.
    return

  // There is something on the cursor and the slot is empty, so we should now
  // put the item into the crafting grid slot.

  // Set the slot item to the cursor item.
  view.topInventory.setItem(slot, cursor)

  // Clear out the cursor.
  view.cursor = null

  // Recalculate the result.
  calculateResult()
}

private inline fun InventoryClickEvent.handleFullSlotGridClick() {
  // If there is nothing on the user's cursor:
  if (cursor.isEmpty()) {

    // Cursor is empty, slot is not, pick up the slot item.

    // Set the cursor to the slotted item.
    view.cursor = currentItem

    // Clear the slot.
    view.topInventory.setItem(slot, null)

    // Recalculate the recipe
    calculateResult()

    return
  }

  // Cursor is full, slot is full.

  // If the slotted item and cursor item are compatible:
  if (cursor isCompatibleWith currentItem) {

    // If the slotted item has available space for more items:
    if (currentItem.hasSpace()) {
      Facade.runTask {
        view.cursor = currentItem.take(cursor)
      }
    } else {
      // The slotted stack does not have room for more items.

      // Nothing we can do, end here.
      return
    }
  }
}


// ╔═════════════════════════════════════════════════════════════════════════╗//
// ║                                                                         ║//
// ║    Inventory Mixins                                                     ║//
// ║                                                                         ║//
// ╚═════════════════════════════════════════════════════════════════════════╝//


private inline fun Inventory.maxAllStackSize(): Int {
  var max = getItem(1)?.amount  ?: -1

  for (i in 2..9) {
    val c = getItem(i) ?: continue

    when {
      max == -1      -> max = c.amount
      c.amount < max -> max = c.amount
    }
  }

  return max
}

private inline fun Inventory.reduceAllBy(qty: Int) {
  for (i in 1..9) {
    val c = getItem(i) ?: continue

    when {
      c.amount  > qty -> c.amount -= qty
      c.amount == qty -> setItem(i, null)
      else            -> throw IllegalStateException("Attempted to reduce a stack of size ${c.amount} by $qty.")
    }
  }
}
