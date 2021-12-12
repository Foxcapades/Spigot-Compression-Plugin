package io.foxcapades.spigot.block.compression.event.handler

import io.foxcapades.spigot.block.compression.event.BCInvClickEvent

internal object StandardShiftRightClickHandler : ClickHandler {
  override fun handle(event: BCInvClickEvent) {
    StandardShiftLeftClickHandler.handle(event)
  }
}