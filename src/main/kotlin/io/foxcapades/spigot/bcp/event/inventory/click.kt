package io.foxcapades.spigot.bcp.event.inventory

import io.foxcapades.spigot.bcp.ext.*
import io.foxcapades.spigot.bcp.ext.ifEmpty
import io.foxcapades.spigot.bcp.ext.ifNotEmpty
import io.foxcapades.spigot.bcp.ext.isEmpty
import io.foxcapades.spigot.bcp.ext.logName
import io.foxcapades.spigot.bcp.i18n.I18N
import io.foxcapades.spigot.bcp.Logger
import io.foxcapades.spigot.bcp.compress.isCompressed
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.ClickType.*
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType.SlotType.CRAFTING
import org.bukkit.event.inventory.InventoryType.SlotType.RESULT


internal fun InventoryClickEvent.handle() =
  with(BCInvClickEvent(this)) {
    if (view.title == I18N.workbenchName())
      handleCustomClick(click)
    else
      handleStandardClick(click)
  }

private fun BCInvClickEvent.handleStandardClick(click: ClickType) =
  when (click) {
    LEFT         -> handleStandardLeftClick()
    SHIFT_LEFT   -> handleStandardShiftLeftClick()
    RIGHT        -> handleStandardRightClick()
    SHIFT_RIGHT  -> handleStandardShiftRightClick()
    else         -> Unit
  }

private fun BCInvClickEvent.handleCustomClick(click: ClickType) = run {
  when (click) {
    LEFT         -> handleCustomLeftClick()
    SHIFT_LEFT   -> handleCustomShiftLeftClick()
    RIGHT        -> handleCustomRightClick()
    SHIFT_RIGHT  -> handleCustomShiftRightClick()
    else         -> Unit
  }
}

// region Standard GUI Click Handling

private fun BCInvClickEvent.handleStandardLeftClick() =
  cursor.ifNotEmpty(::handleStandardItemPlace)

private fun BCInvClickEvent.handleStandardRightClick() =
  cursor.ifNotEmpty(::handleStandardItemPlace)

/**
 * Prevent placing compressed blocks in non-compression-table inventories.
 */
private fun BCInvClickEvent.handleStandardItemPlace() {
  if (userClickedTopInventory && cursor.isCompressed && !topInvIsCompressedItemSafe) {
    Logger.trace("canceling top inventory click event for %s", cursor.type.name)
    cancel()
  }
}

private fun BCInvClickEvent.handleStandardShiftLeftClick() {
  when {
    inventorySlot.isNotEmpty() -> {
      if (userClickedBottomInventory && inventorySlot.isCompressed && !topInvIsCompressedItemSafe) {
        Logger.trace("canceling moving item %s to top inventory", inventorySlot.type.name)
        cancel()
      }
    }

    cursor.isNotEmpty() -> handleStandardItemPlace()
  }
}

private fun BCInvClickEvent.handleStandardShiftRightClick() =
  handleStandardShiftLeftClick()

// endregion Standard GUI Click Handling

// region Compression Workbench Click Handling

private fun BCInvClickEvent.handleCustomLeftClick() {
  if (userClickedTopInventory) {
    cancel()
    topClickLeft()
  }
}

private fun BCInvClickEvent.handleCustomRightClick() {
  if (userClickedTopInventory) {
    cancel()
    topClickRight()
  }
}

private fun BCInvClickEvent.handleCustomShiftLeftClick() {
  cancel()

  when {
    userClickedTopInventory    -> topClickShiftLeft()
    userClickedBottomInventory -> bottomClickShiftLeft()
  }
}

private fun BCInvClickEvent.handleCustomShiftRightClick() {
  if (userClickedBottomInventory) {
    cancel()

    inventorySlot.ifNotEmpty {
      val result = top.fillGrid(inventorySlot)
      bottom.clear(slotIndex)
      bottom.setItem(slotIndex, result)
    }
  }
}

// Shift Left Click

private fun BCInvClickEvent.topClickShiftLeft() {
  val rem = if (slotType == RESULT)
    bottom.addItem(*top.popAll().toTypedArray())
  else
    bottom.addItem(top.take(slotIndex))

  for (item in rem.values)
    player.world.dropItem(player.location, item!!)
}

private fun BCInvClickEvent.bottomClickShiftLeft() =
  inventorySlot.ifNotEmpty { bottom.setItem(slotIndex, top.addItem(inventorySlot)) }

// Right Click

private fun BCInvClickEvent.topClickRight() {
  cursor.ifEmpty { return topTakeRight() }
  topPutRight()
}

private fun BCInvClickEvent.topTakeRight() {
  when (slotType) {
    RESULT   -> topTakeResultRight()
    CRAFTING -> topTakeIngredientRight()
    else     -> throw IllegalStateException("Called topTake with the invalid slot type: $slotType")
  }
}

private fun BCInvClickEvent.topTakeResultRight() {
  inventorySlot.ifNotEmpty { cursor = top.splitResult() }
}

private fun BCInvClickEvent.topTakeIngredientRight() = uncancel()

private fun BCInvClickEvent.topPutRight() {
  when (slotType) {
    RESULT   -> return // Just reject it altogether.
    CRAFTING -> uncancel()
    else     -> throw IllegalStateException("Called topPut with the invalid slot type: $slotType")
  }
}

// Left Click

private fun BCInvClickEvent.topClickLeft() {
  if (cursor.isEmpty())
    topTakeLeft()
  else
    topPutLeft()
}

private fun BCInvClickEvent.topTakeLeft() =
  when (slotType) {
    RESULT   -> topTakeResultLeft()
    CRAFTING -> topTakeIngredientLeft()
    else     -> throw IllegalStateException("Called topTake with the invalid slot type: $slotType")
  }

private fun BCInvClickEvent.topTakeResultLeft() =
  inventorySlot.ifNotEmpty {
    cursor = top.popResult()
    Logger.trace { "popped result %s".format(cursor.logName) }
  }

private fun BCInvClickEvent.topTakeIngredientLeft() =
  inventorySlot.ifNotEmpty {
    cursor = top.take(slotIndex)
    Logger.trace { "removed ingredient %s".format(cursor.logName) }
  }

private fun BCInvClickEvent.topPutLeft() =
  when (slotType) {
    CRAFTING -> topPutIngredient()
    RESULT   -> topMergeToCursor()
    else     -> throw IllegalStateException("Called topPut with the invalid slot type: $slotType")
  }

private fun BCInvClickEvent.topPutIngredient() {
  Logger.trace { "attempting to put ingredient %s (%d)".format(cursor.logName, cursor.size) }
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
  Logger.trace { "merged result %s to cursor stack".format(cursor.logName) }
}

// endregion Compression Workbench Click Handling
