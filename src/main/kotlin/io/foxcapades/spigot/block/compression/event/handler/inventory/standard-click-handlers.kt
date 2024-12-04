package io.foxcapades.spigot.block.compression.event.handler.inventory

import io.foxcapades.spigot.block.compression.compress.ifCompressed
import io.foxcapades.spigot.block.compression.event.BCInvClickEvent
import io.foxcapades.spigot.block.compression.ext.ifNotEmpty
import io.foxcapades.spigot.block.compression.log.Logger

internal fun BCInvClickEvent.handleStandardLeftClick() =
  cursor.ifNotEmpty(::handleStandardItemPlace)

internal fun BCInvClickEvent.handleStandardRightClick() =
  cursor.ifNotEmpty(::handleStandardItemPlace)

/**
 * Prevent placing compressed blocks in non-compression-table inventories.
 */
internal fun BCInvClickEvent.handleStandardItemPlace() =
  ifUserClickedTopInv {
    cursor.ifCompressed {
      ifTopInvIsNotCompressedItemSafe {
        Logger.trace("canceling top inventory click event for %s", cursor.type.name)
        cancel()
      }
    }
  }

internal fun BCInvClickEvent.handleStandardShiftLeftClick() {
  inventorySlot.ifNotEmpty {
    return ifUserClickedBottomInv {
      inventorySlot.ifCompressed {
        ifTopInvIsNotCompressedItemSafe {
          Logger.trace("canceling moving item %s to top inventory", inventorySlot.type.name)
          cancel()
        }
      }
    }
  }

  cursor.ifNotEmpty(::handleStandardItemPlace)
}

internal fun BCInvClickEvent.handleStandardShiftRightClick() =
  handleStandardShiftLeftClick()
