@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.spigot.block.compression.event

import io.foxcapades.spigot.block.compression.consts.CompressorTitle
import io.foxcapades.spigot.block.compression.event.handler.inventory.*
import io.foxcapades.spigot.block.compression.event.handler.inventory.CustomLeftClickHandler
import io.foxcapades.spigot.block.compression.event.handler.inventory.CustomRightClickHandler
import io.foxcapades.spigot.block.compression.event.handler.inventory.CustomShiftLeftClickHandler
import io.foxcapades.spigot.block.compression.event.handler.inventory.CustomShiftRightClickHandler
import io.foxcapades.spigot.block.compression.event.handler.inventory.StandardShiftLeftClickHandler
import org.bukkit.event.inventory.ClickType.*
import org.bukkit.event.inventory.InventoryClickEvent

internal fun InventoryClickEvent.handleClick() =
  if (view.title == CompressorTitle)
    handleCustomClick()
  else
    handleStandardClick()

internal fun InventoryClickEvent.handleStandardClick() {
  val event = BCInvClickEvent(this)

  when (click) {
    LEFT         -> StandardLeftClickHandler.handle(event)
    SHIFT_LEFT   -> StandardShiftLeftClickHandler.handle(event)
    RIGHT        -> StandardRightClickHandler.handle(event)
    SHIFT_RIGHT  -> StandardShiftRightClickHandler.handle(event)
    else         -> {}
  }
}

internal fun InventoryClickEvent.handleCustomClick() {
  val event = BCInvClickEvent(this)

  // Cancel the default handling, we handle everything manually from here on.
  event.cancel()

  when (click) {
    LEFT         -> CustomLeftClickHandler.handle(event)
    SHIFT_LEFT   -> CustomShiftLeftClickHandler.handle(event)
    RIGHT        -> CustomRightClickHandler.handle(event)
    SHIFT_RIGHT  -> CustomShiftRightClickHandler.handle(event)
    else         -> {}
  }
}
