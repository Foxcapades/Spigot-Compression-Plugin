package io.foxcapades.spigot.block.compression.event

import io.foxcapades.spigot.block.compression.item.*
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType.SlotType.RESULT
import org.bukkit.inventory.ItemStack


internal fun InventoryClickEvent.handleCustomRightClick() {
  // If the user is attempting to place or pull from the result slot, reject the
  // action.
  if (slotType == RESULT)
    return

  if (cursor.isEmpty() && currentItem.isNotEmpty()) {
    handlePickupHalf(currentItem!!)
  } else {
    handlePlaceOne(cursor!!)
  }
}

private fun InventoryClickEvent.handlePickupHalf(slot: ItemStack) {
  when (slot.amount) {
    // If the clicked stack has only 1 item:
    1 -> {

      // Move the item to the cursor.
      view.cursor = slot

      // Clear the slot.
      clickedInventory!!.clear(this.slot)
    }

    // If the clicked stack has more than 1 item:
    else -> {

      // Calculate the new stack size for both the cursor and slotted item.
      val newSize = slot.amount / 2

      // Copy the slotted ItemStack at the new size to the cursor.
      view.cursor = slot.clone(newSize + slot.amount % 2)

      // Update the slotted ItemStack size.
      slot.amount = newSize
    }
  }
}

private fun InventoryClickEvent.handlePlaceOne(cursor: ItemStack) {
  // Disallow all actions on the result slot.
  // TODO: Splitting should be allowed.
  if (slotType == RESULT)
    return

  if (currentItem.isNotEmpty()) {
    if (currentItem.isFull())
      return

    currentItem.size++
  } else {
    view.setItem(rawSlot, cursor.clone(1))
  }

  if (cursor.amount == 1) {
    view.cursor = Air
  } else {
    view.cursor.size--
  }
}