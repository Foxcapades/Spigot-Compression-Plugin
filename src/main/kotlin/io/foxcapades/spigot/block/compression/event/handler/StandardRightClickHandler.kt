package io.foxcapades.spigot.block.compression.event.handler

import io.foxcapades.spigot.block.compression.event.BCInvClickEvent
import io.foxcapades.spigot.block.compression.item.ifNotEmpty

internal object StandardRightClickHandler : ClickHandler {
  override fun handle(event: BCInvClickEvent) {
    event.cursor.ifNotEmpty {
      StandardLeftClickHandler.handleStandardItemPlace(event)
    }
  }
}