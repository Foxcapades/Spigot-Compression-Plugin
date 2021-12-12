package io.foxcapades.spigot.block.compression.event.handler

import io.foxcapades.spigot.block.compression.event.BCInvClickEvent
import io.foxcapades.spigot.block.compression.event.calculateResult
import io.foxcapades.spigot.block.compression.item.*
import org.bukkit.event.inventory.InventoryType.SlotType.CRAFTING
import org.bukkit.event.inventory.InventoryType.SlotType.RESULT

internal object CustomLeftClickHandler : ClickHandler {
  override fun handle(event: BCInvClickEvent) {
    val cursor  = event.cursor
    val slotted = event.inventorySlot

    cursor.ifEmpty {

      slotted.ifNotEmpty {
        event.cursor = this
        event.clickedInventory.clear(event.slotIndex)

        event.ifSlotTypeIs(RESULT) {
          it.dec()
          raw.calculateResult()
          return
        }

        event.ifSlotTypeIs(CRAFTING) {
          raw.calculateResult()
          return
        }

      }

      return
    }

    // Cursor is not empty.

    event.ifSlotTypeIsNot(RESULT) { return handleCustomItemClick(this) }

    // Cursor is not empty
    // Slot type is RESULT

    // If the slot is empty, bail here as there is nothing for us to do.
    slotted.ifEmpty { return }

    // Cursor is not empty.
    // Slot type is RESULT.
    // Slot is not empty.

    if (cursor.isCompatibleWith(slotted) && cursor.freeSpace() >= slotted.size) {
      cursor.amount += slotted.amount
      event.top.dec()
      event.raw.calculateResult()
    }
  }

  internal fun handleCustomItemClick(event: BCInvClickEvent) =
    event.ifSlotTypeIsNot(RESULT) {
      event.inventorySlot.ifEmpty {
        view.setItem(event.rawSlotIndex, event.cursor)
        event.cursor = Air

        event.ifSlotTypeIs(CRAFTING) { event.raw.calculateResult() }

        return
      }

      event.cursor.ifSimilar(event.inventorySlot) {
        event.inventorySlot.ifHasSpace {
          event.cursor = event.inventorySlot.fillWith(event.cursor)
          event.ifSlotTypeIs(CRAFTING) { event.raw.calculateResult() }
        }

        return
      }

      val tmp = event.cursor
      event.cursor = event.inventorySlot
      event.inventorySlot = tmp

      event.ifSlotTypeIs(CRAFTING) { event.raw.calculateResult() }
    }
}