package io.foxcapades.spigot.zip.event.handler.inventory

import io.foxcapades.spigot.block.compression.event.BCInvClickEvent

internal data object StandardShiftRightClickHandler : ClickHandler {
  override fun handle(event: BCInvClickEvent) {
    StandardShiftLeftClickHandler.handle(event)
  }
}