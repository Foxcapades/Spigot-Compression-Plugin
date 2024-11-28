package io.foxcapades.spigot.block.compression.event.handler.world

import io.foxcapades.spigot.block.compression.event.BCBlockPlaceEvent
import io.foxcapades.spigot.block.compression.item.compressionLevel

internal object BlockPlaceHandler {
  fun handle(event: BCBlockPlaceEvent) {
    if (event.itemInHand.compressionLevel().value > 0)
      event.cancel()
  }
}