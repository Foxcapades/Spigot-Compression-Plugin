package io.foxcapades.spigot.block.compression.event.handler

import io.foxcapades.spigot.block.compression.event.BCInvClickEvent
import io.foxcapades.spigot.block.compression.facades.Facade
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
      RESULT   -> { /* Not permitted, do nothing. */ }
      else     -> throw IllegalStateException("Called topPut with the invalid slot type: $slotType")
    }

  private fun BCInvClickEvent.topPutIngredient() {
    Facade.logTrace("CustomLeftClickHandler#topPutIngredient()")
    cursor = top.putItem(slotIndex, cursor)
  }

  private fun BCInvClickEvent.bottomClick() = uncancel()
}