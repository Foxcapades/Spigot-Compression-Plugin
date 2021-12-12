package io.foxcapades.spigot.block.compression.event.handler

import io.foxcapades.spigot.block.compression.event.BCInvClickEvent

internal object CustomShiftRightClickHandler : ClickHandler {
  override fun handle(event: BCInvClickEvent) {
    CustomShiftLeftClickHandler.handle(event)
  }
}