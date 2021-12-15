package io.foxcapades.spigot.block.compression.event.handler.inventory

import io.foxcapades.spigot.block.compression.event.BCInvClickEvent

internal object StandardShiftRightClickHandler : ClickHandler {
  override fun handle(event: BCInvClickEvent) {
    StandardShiftLeftClickHandler.handle(event)
  }
}