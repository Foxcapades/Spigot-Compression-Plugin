package io.foxcapades.spigot.block.compression.event.handler

import io.foxcapades.spigot.block.compression.event.BCInvClickEvent
import io.foxcapades.spigot.block.compression.event.calculateResult
import io.foxcapades.spigot.block.compression.item.ifCompressible
import io.foxcapades.spigot.block.compression.item.ifNotEmpty
import org.bukkit.event.inventory.InventoryType.SlotType.RESULT
import org.bukkit.inventory.ItemStack

internal object CustomShiftLeftClickHandler : ClickHandler {
  override fun handle(event: BCInvClickEvent) =
    event.ifSlotTypeIsNot(RESULT) {
      event.inventorySlot.ifNotEmpty {
        return handleItemMove(event, event.inventorySlot)
      }

      event.cursor.ifNotEmpty {
        return CustomLeftClickHandler.handleCustomItemClick(event)
      }
    }

  private fun handleItemMove(event: BCInvClickEvent, slot: ItemStack) =
    if (event.userClickedTopInv)
      handleItemMoveFromTop(event, slot)
    else
      handleItemMoveFromBottom(event, slot)

  private fun handleItemMoveFromTop(event: BCInvClickEvent, slot: ItemStack) {
    event.topInventory.clear(event.slotIndex)
    event.bottomInventory.addItem(slot)
    event.raw.calculateResult()
  }

  /**
   * Handles the action of a user attempting to bulk move an item from a slot in
   * their backpack to the crafting grid.
   *
   * The stack will be spread across the crafting grid to enable easier
   * compression.
   *
   * TODO: This should really only happen on ctrl click or middle click or
   *       something, the default behavior should just be to put one stack into
   *       the crafting grid.
   */
  private fun handleItemMoveFromBottom(event: BCInvClickEvent, slot: ItemStack) =
    slot.ifCompressible {
      event.bottomInventory.clear(event.slotIndex)
      event.bottomInventory.addItem(event.top.spread(slot))
      event.raw.calculateResult()
    }

}