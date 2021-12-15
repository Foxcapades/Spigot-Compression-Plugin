package io.foxcapades.spigot.block.compression.event.handler.inventory

import io.foxcapades.spigot.block.compression.event.BCInvClickEvent
import io.foxcapades.spigot.block.compression.item.ifNotEmpty

internal object CustomShiftRightClickHandler : ClickHandler {
  override fun handle(event: BCInvClickEvent) {
    event.ifUserClickedBottomInv {
      inventorySlot.ifNotEmpty {
        val result = top.fillGrid(inventorySlot)
        bottom.clear(slotIndex)
        bottom.addItem(result)
      }
    }
  }
}