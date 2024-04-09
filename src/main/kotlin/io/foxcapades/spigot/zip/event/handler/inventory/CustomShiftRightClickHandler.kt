package io.foxcapades.spigot.zip.event.handler.inventory

import io.foxcapades.spigot.block.compression.event.BCInvClickEvent
import io.foxcapades.spigot.block.compression.item.ifNotEmpty

internal data object CustomShiftRightClickHandler : ClickHandler {
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