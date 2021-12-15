package io.foxcapades.spigot.block.compression.event.handler.inventory

import io.foxcapades.spigot.block.compression.event.BCInvClickEvent
import io.foxcapades.spigot.block.compression.item.ifEmpty
import io.foxcapades.spigot.block.compression.item.ifNotEmpty
import org.bukkit.event.inventory.InventoryType.SlotType.CRAFTING
import org.bukkit.event.inventory.InventoryType.SlotType.RESULT

internal object CustomLeftClickHandler : ClickHandler {

  override fun handle(event: BCInvClickEvent) {
    event.ifUserClickedTopInv { return topClick() }
    event.ifUserClickedBottomInv { return bottomClick() }
    // The user clicked outside or something?
    event.uncancel()
  }

  private fun BCInvClickEvent.topClick() {
    cursor.ifEmpty { return topTake() }
    topPut()
  }

  private fun BCInvClickEvent.topTake() =
    when (slotType) {
      RESULT   -> topTakeResult()
      CRAFTING -> topTakeIngredient()
      else     -> throw IllegalStateException("Called topTake with the invalid slot type: $slotType")
    }

  private fun BCInvClickEvent.topTakeResult() =
    inventorySlot.ifNotEmpty { cursor = top.popResult() }

  private fun BCInvClickEvent.topTakeIngredient() =
    inventorySlot.ifNotEmpty { cursor = top.take(slotIndex) }

  private fun BCInvClickEvent.topPut() =
    when (slotType) {
      CRAFTING -> topPutIngredient()
      RESULT   -> topMergeToCursor()
      else     -> throw IllegalStateException("Called topPut with the invalid slot type: $slotType")
    }

  private fun BCInvClickEvent.topPutIngredient() {
    cursor = top.putItem(slotIndex, cursor)
  }

  private fun BCInvClickEvent.topMergeToCursor() {
    // If the stacks are incompatible, do nothing.
    if (inventorySlot.type != cursor.type)
      return

    val newSize = inventorySlot.amount + cursor.amount

    // If the full slotted stack can't fit in the cursor stack, do nothing.
    if (newSize > cursor.maxStackSize)
      return

    // Here we know the slot stack is compatible with the cursor stack and there
    // is enough room in the cursor stack to hold the entire slotted stack.

    top.popResult()
    cursor.amount = newSize
  }

  private fun BCInvClickEvent.bottomClick() = uncancel()
}