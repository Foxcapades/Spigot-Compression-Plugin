package io.foxcapades.spigot.zip.event.handler.inventory

import io.foxcapades.spigot.block.compression.event.BCInvClickEvent
import io.foxcapades.spigot.block.compression.item.ifEmpty
import io.foxcapades.spigot.block.compression.item.ifNotEmpty
import org.bukkit.event.inventory.InventoryType.SlotType.CRAFTING
import org.bukkit.event.inventory.InventoryType.SlotType.RESULT

internal data object CustomRightClickHandler : ClickHandler {
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

  private fun BCInvClickEvent.topTake() {
    when (slotType) {
      RESULT   -> topTakeResult()
      CRAFTING -> topTakeIngredient()
      else     -> throw IllegalStateException("Called topTake with the invalid slot type: $slotType")
    }
  }

  private fun BCInvClickEvent.topTakeResult() {
    inventorySlot.ifNotEmpty { cursor = top.splitResult() }
  }

  private fun BCInvClickEvent.topTakeIngredient() = uncancel()

  private fun BCInvClickEvent.topPut() {
    when (slotType) {
      RESULT   -> return // Just reject it altogether.
      CRAFTING -> uncancel()
      else     -> throw IllegalStateException("Called topPut with the invalid slot type: $slotType")
    }
  }

  private fun BCInvClickEvent.bottomClick() = uncancel()
}