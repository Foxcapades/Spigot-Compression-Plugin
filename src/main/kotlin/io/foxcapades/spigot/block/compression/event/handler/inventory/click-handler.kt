package io.foxcapades.spigot.block.compression.event.handler.inventory

import io.foxcapades.spigot.block.compression.event.BCInvClickEvent
import io.foxcapades.spigot.block.compression.i18n.I18N
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.ClickType.*
import org.bukkit.event.inventory.InventoryClickEvent


internal fun InventoryClickEvent.handleClick() =
  BCInvClickEvent(this).run {
    if (view.title == I18N.workbenchName())
      handleCustomClick(click)
    else
      handleStandardClick(click)
  }

internal fun BCInvClickEvent.handleStandardClick(click: ClickType) =
  when (click) {
    LEFT         -> handleStandardLeftClick()
    SHIFT_LEFT   -> handleStandardShiftLeftClick()
    RIGHT        -> handleStandardRightClick()
    SHIFT_RIGHT  -> handleStandardShiftRightClick()
    else         -> Unit
  }

internal fun BCInvClickEvent.handleCustomClick(click: ClickType) = run {
  when (click) {
    LEFT         -> handleCustomLeftClick()
    SHIFT_LEFT   -> handleCustomShiftLeftClick()
    RIGHT        -> handleCustomRightClick()
    SHIFT_RIGHT  -> handleCustomShiftRightClick()
    else         -> Unit
  }
}
