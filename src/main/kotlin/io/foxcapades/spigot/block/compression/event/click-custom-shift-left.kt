package io.foxcapades.spigot.block.compression.event

import io.foxcapades.spigot.block.compression.compressible.Compressibles
import io.foxcapades.spigot.block.compression.facades.Facade
import io.foxcapades.spigot.block.compression.wrap.BCCraftingInv
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType.SlotType.RESULT
import org.bukkit.inventory.ItemStack

internal fun InventoryClickEvent.handleCustomShiftLeftClick(top: BCCraftingInv) {
  // TODO: Handle shift-click on result slot.
  if (slotType == RESULT)
    return

  if (currentItem != null)
    return handleItemMove(currentItem!!)

  if (cursor != null)
    handleCustomItemClick(top, cursor!!)
}

private fun InventoryClickEvent.handleItemMove(slot: ItemStack) {
  if (clickedInventory === view.topInventory)
    handleItemMoveFromTop(slot, BCCraftingInv(view.topInventory))
  else
    handleItemMoveFromBottom(slot, BCCraftingInv(view.topInventory))
}

private fun InventoryClickEvent.handleItemMoveFromTop(slot: ItemStack, inv: BCCraftingInv) {
  view.topInventory.clear(this.slot)
  view.bottomInventory.addItem(slot)
  calculateResult()
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
private fun InventoryClickEvent.handleItemMoveFromBottom(slot: ItemStack, inv: BCCraftingInv) {
  isCancelled = true

  // If the item the user is trying to move to the crafting grid is not
  // compressible, reject the action.
  if (slot !in Compressibles) {
    return
  }

  view.bottomInventory.clear(this.slot)
  view.bottomInventory.addItem(inv.spread(slot))
  calculateResult()
}
