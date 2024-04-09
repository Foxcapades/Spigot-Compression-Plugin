package io.foxcapades.spigot.zip.event.handler.inventory

import io.foxcapades.spigot.block.compression.event.BCInvClickEvent

internal sealed interface ClickHandler {
  fun handle(event: BCInvClickEvent)
}