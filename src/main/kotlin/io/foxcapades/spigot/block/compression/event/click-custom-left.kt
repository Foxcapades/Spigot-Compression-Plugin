package io.foxcapades.spigot.block.compression.event

import io.foxcapades.spigot.block.compression.item.*
import io.foxcapades.spigot.block.compression.item.hasSpace
import io.foxcapades.spigot.block.compression.item.isCompatibleWith
import io.foxcapades.spigot.block.compression.item.isEmpty
import io.foxcapades.spigot.block.compression.item.isNotEmpty
import io.foxcapades.spigot.block.compression.wrap.BCCraftingInv
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType.SlotType.*
import org.bukkit.inventory.ItemStack

internal fun InventoryClickEvent.handleCustomLeftClick(top: BCCraftingInv) {
  if (cursor.isEmpty()) {
    if (currentItem.isNotEmpty()) {
      view.cursor = currentItem
      clickedInventory!!.clear(slot)

      if (slotType == RESULT) {
        top.dec()
        calculateResult()
      }

      if (slotType == CRAFTING)
        calculateResult()
    }

    return
  }

  // Cursor is not empty.

  if (slotType != RESULT)
    return handleCustomItemClick(top, cursor!!)

  // Cursor is not empty
  // Slot type is RESULT

  // If the slot is empty, bail here as there is nothing for us to do.
  if (currentItem.isEmpty())
    return

  // Cursor is not empty.
  // Slot type is RESULT.
  // Slot is not empty.

  if (cursor.isCompatibleWith(currentItem) && cursor.freeSpace() >= currentItem.size) {
    cursor.size += currentItem.size
    top.dec()
    calculateResult()
  }
}

internal fun InventoryClickEvent.handleCustomItemClick(top: BCCraftingInv, cursor: ItemStack) {
  if (slotType == RESULT)
    return

  if (currentItem.isEmpty()) {
    view.setItem(rawSlot, cursor)
    view.cursor = Air

  } else if (cursor.isSimilar(currentItem)) {
    if (currentItem.hasSpace())
      view.cursor = currentItem.fillWith(cursor)

  } else {
    view.cursor = currentItem
    currentItem = cursor
  }

  if (slotType == CRAFTING)
    calculateResult()
}
