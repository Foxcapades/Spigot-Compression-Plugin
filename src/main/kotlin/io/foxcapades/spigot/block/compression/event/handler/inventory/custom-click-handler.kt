package io.foxcapades.spigot.block.compression.event.handler.inventory

import io.foxcapades.spigot.block.compression.event.BCInvClickEvent
import io.foxcapades.spigot.block.compression.ext.*
import io.foxcapades.spigot.block.compression.log.Logger
import org.bukkit.event.inventory.InventoryType.SlotType.CRAFTING
import org.bukkit.event.inventory.InventoryType.SlotType.RESULT


internal fun BCInvClickEvent.handleCustomLeftClick() =
  ifUserClickedTopInv {
    cancel()
    topClickLeft()
  }

internal fun BCInvClickEvent.handleCustomRightClick() =
  ifUserClickedTopInv {
    cancel()
    topClickRight()
  }

internal fun BCInvClickEvent.handleCustomShiftLeftClick() {
//    event.ifUserClickedTopInv { return topClickShiftLeft() }
  ifUserClickedBottomInv {
    cancel()
    return bottomClickShiftLeft()
  }
}

internal fun BCInvClickEvent.handleCustomShiftRightClick() {
  ifUserClickedBottomInv {
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
