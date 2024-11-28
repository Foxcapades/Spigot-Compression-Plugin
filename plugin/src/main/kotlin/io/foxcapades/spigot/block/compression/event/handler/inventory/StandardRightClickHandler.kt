package io.foxcapades.spigot.block.compression.event.handler.inventory

import io.foxcapades.spigot.block.compression.event.BCInvClickEvent
import io.foxcapades.spigot.block.compression.item.ifNotEmpty

internal data object StandardRightClickHandler : ClickHandler {
  override fun handle(event: BCInvClickEvent) {
    event.cursor.ifNotEmpty {
      StandardLeftClickHandler.handleStandardItemPlace(event)
    }
  }
}