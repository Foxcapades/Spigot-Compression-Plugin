package io.foxcapades.spigot.block.compression.event.handler

import io.foxcapades.spigot.block.compression.event.BCInvClickEvent
import io.foxcapades.spigot.block.compression.item.*
import io.foxcapades.spigot.block.compression.item.clone
import io.foxcapades.spigot.block.compression.item.isEmpty
import io.foxcapades.spigot.block.compression.item.isFull
import io.foxcapades.spigot.block.compression.item.isNotEmpty
import org.bukkit.event.inventory.InventoryType.SlotType.RESULT

internal object CustomRightClickHandler : ClickHandler {
  override fun handle(event: BCInvClickEvent) =
    event.ifSlotTypeIsNot(RESULT) {
      if (event.cursor.isEmpty() && event.inventorySlot.isNotEmpty()) {
        handlePickupHalf(event)
      } else {
        handlePlaceOne(event)
      }
    }

  private fun handlePickupHalf(event: BCInvClickEvent) {
    val slot       = event.inventorySlot
    val slotAmount = slot.amount

    when (slotAmount) {
      // If the clicked stack has only 1 item:
      1 -> {

        // Move the item to the cursor.
        event.cursor = slot

        // Clear the slot.
        event.clickedInventory.clear(event.slotIndex)
      }

      // If the clicked stack has more than 1 item:
      else -> {

        // Calculate the new stack size for both the cursor and slotted item.
        val newSize = slotAmount / 2

        // Copy the slotted ItemStack at the new size to the cursor.
        event.cursor = slot.clone(newSize + slot.amount % 2)

        // Update the slotted ItemStack size.
        slot.amount = newSize
      }
    }
  }

  private fun handlePlaceOne(event: BCInvClickEvent) =
    // Disallow all actions on the result slot.
    // TODO: Splitting should be allowed.
    event.ifSlotTypeIsNot(RESULT) {
      if (event.inventorySlot.isNotEmpty()) {
        if (event.inventorySlot.isFull())
          return

        event.inventorySlot.amount++
      } else {
        event.inventorySlot = event.cursor.clone(1)
      }

      if (event.cursor.amount == 1) {
        event.cursor = Air
      } else {
        event.cursor.size--
      }
    }
}