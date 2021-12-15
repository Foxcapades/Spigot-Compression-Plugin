package io.foxcapades.spigot.block.compression.event.handler.inventory

import io.foxcapades.spigot.block.compression.compressible.CompressionLevel.None
import io.foxcapades.spigot.block.compression.event.BCInvClickEvent
import io.foxcapades.spigot.block.compression.item.ifCompressionLevelNot
import io.foxcapades.spigot.block.compression.item.isNotEmpty

internal object StandardLeftClickHandler : ClickHandler {
  override fun handle(event: BCInvClickEvent) {
    if (event.cursor.isNotEmpty())
      handleStandardItemPlace(event)
  }

  fun handleStandardItemPlace(event: BCInvClickEvent) =
    with(event) {
      ifUserClickedTopInv {
        cursor.ifCompressionLevelNot(None) {
          ifTopInvIsNotCompressedItemSafe { event.cancel() }
        }
      }
    }
}