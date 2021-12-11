@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.spigot.block.compression.event

import io.foxcapades.spigot.block.compression.compressible.CompressionLevel.None
import io.foxcapades.spigot.block.compression.item.compressionLevel
import org.bukkit.event.inventory.ClickType.*
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType.*
import org.bukkit.event.inventory.InventoryType.CREATIVE
import org.bukkit.inventory.ItemStack

internal fun InventoryClickEvent.handleStandardClick() {
  when (click) {
    LEFT         -> handleStandardLeftClick()
    SHIFT_LEFT   -> handleShiftLeftClick()
    RIGHT        -> handleRightClick()
    SHIFT_RIGHT  -> handleShiftRightClick()
    else         -> {}
  }
}

private fun InventoryClickEvent.handleShiftLeftClick() {
  if (currentItem != null)
    return handleItemMove(currentItem!!)

  // If the cursor has an item, and the slot is empty, this behaves as a normal
  // item place event.
  if (cursor != null)
    handleStandardItemPlace(cursor!!)
}

private fun InventoryClickEvent.handleItemMove(slot: ItemStack) {
  // If the user is trying to move an item from the top inventory to the bottom,
  // do not prevent.
  if (clickedInventory === view.topInventory)
    return

  // We don't care about non-compressed item moves.
  if (slot.compressionLevel() == None)
    return

  // We allow freely moving into these inventory types
  when (view.topInventory.type) {
    CHEST         -> return
    DISPENSER     -> return
    DROPPER       -> return
    PLAYER        -> return
    CREATIVE      -> return
    ENDER_CHEST   -> return
    HOPPER        -> return
    SHULKER_BOX   -> return
    BARREL        -> return
    // Since they are trying to move the item to the top inventory and it is not
    // an allowed type, cancel the event.
    else          -> isCancelled = true
  }
}

private fun InventoryClickEvent.handleRightClick() {
  if (cursor != null)
    handleStandardItemPlace(cursor!!)
}

private fun InventoryClickEvent.handleShiftRightClick() = handleShiftLeftClick()