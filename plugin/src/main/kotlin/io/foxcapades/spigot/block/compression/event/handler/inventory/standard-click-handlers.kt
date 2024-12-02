package io.foxcapades.spigot.block.compression.event.handler.inventory

import io.foxcapades.spigot.block.compression.compress.ifCompressed
import io.foxcapades.spigot.block.compression.event.BCInvClickEvent
import io.foxcapades.spigot.block.compression.ext.ifNotEmpty

internal fun BCInvClickEvent.handleStandardLeftClick() =
  cursor.ifNotEmpty(::handleStandardItemPlace)

internal fun BCInvClickEvent.handleStandardRightClick() =
  cursor.ifNotEmpty(::handleStandardItemPlace)

/**
 * Prevent placing compressed blocks in non-compression-table inventories.
 */
internal fun BCInvClickEvent.handleStandardItemPlace() =
  ifUserClickedTopInv { cursor.ifCompressed { ifTopInvIsNotCompressedItemSafe { cancel() } } }

internal fun BCInvClickEvent.handleStandardShiftLeftClick() {
  inventorySlot.ifNotEmpty {
    return handleItemMove()
  }

  cursor.ifNotEmpty(::handleStandardItemPlace)
}

internal fun BCInvClickEvent.handleStandardShiftRightClick() =
  handleStandardShiftLeftClick()

private fun BCInvClickEvent.handleItemMove() =
  ifUserClickedBottomInv { inventorySlot.ifCompressed { ifTopInvIsNotCompressedItemSafe { cancel() } } }
