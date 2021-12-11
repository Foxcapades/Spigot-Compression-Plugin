@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.spigot.block.compression.event

import io.foxcapades.spigot.block.compression.wrap.BCCraftingInv
import org.bukkit.event.inventory.ClickType.*
import org.bukkit.event.inventory.InventoryClickEvent

internal fun InventoryClickEvent.handleCustomClick() {
  // Cancel the default handling, we handle everything manually from here on.
  isCancelled = true

  val top = BCCraftingInv(view.topInventory)

  when (click) {
    LEFT         -> handleCustomLeftClick(top)
    SHIFT_LEFT   -> handleCustomShiftLeftClick(top)
    RIGHT        -> handleCustomRightClick()
    SHIFT_RIGHT  -> handleShiftRightClick(top)
    else         -> {}
  }
}


private fun InventoryClickEvent.handleShiftRightClick(top: BCCraftingInv) = handleCustomShiftLeftClick(top)