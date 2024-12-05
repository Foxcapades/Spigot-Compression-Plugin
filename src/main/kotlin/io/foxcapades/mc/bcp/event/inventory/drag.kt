package io.foxcapades.mc.bcp.event.inventory

import io.foxcapades.mc.bcp.Logger
import io.foxcapades.mc.bcp.Plugin
import io.foxcapades.mc.bcp.Server
import io.foxcapades.mc.bcp.zip.isCompressed
import io.foxcapades.mc.bcp.ext.isCompressionTool
import io.foxcapades.mc.bcp.ext.isEmpty
import io.foxcapades.mc.bcp.ext.logName
import io.foxcapades.mc.bcp.inv.CraftInventory
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.DragType
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryType.*

internal fun InventoryDragEvent.handle() = if (view.isCompressionTool()) handleCustom() else handleStandard()

private fun InventoryDragEvent.handleStandard() {
  // Do nothing for these view types, the player is allowed to put compressed
  // blocks in these.
  when (view.topInventory.type) {
    CHEST       -> return
    DISPENSER   -> return
    DROPPER     -> return
    PLAYER      -> return
    ENDER_CHEST -> return
    SHULKER_BOX -> return
    BARREL      -> return
    CREATIVE    -> return
    HOPPER      -> return
    else        -> { /* continue */ }
  }

  if (!newItems.values.first().isCompressed)
    return

  val min = view.topInventory.size

  for (i in rawSlots) {
    if (i < min) {
      Logger.trace { "cancelling drag event for item %s".format(newItems.values.first().logName) }
      isCancelled = true
      return
    }
  }
}

private fun InventoryDragEvent.handleCustom() {
  Logger.trace("cursor = %s", oldCursor)
  if (rawSlots.size == 0 && false) {
    val slot = rawSlots.first()
    val click = when (type) {
      DragType.SINGLE -> ClickType.SHIFT_RIGHT
      DragType.EVEN -> ClickType.SHIFT_LEFT
    }

    InventoryClickEvent(view, view.getSlotType(slot), slot, click, InventoryAction.PICKUP_ALL).handle()

    return
  }

  // For each slot in the slots the user dragged across:
  for (index in rawSlots) {

    if (index == 0) {
      isCancelled = true
      if (cursor.isEmpty())

      return
    }

    // indices 1..9 are top inventory slots
    if (index in 1..9) {
      Server.scheduler.runTask(Plugin, Runnable { CraftInventory(view).calculateResult() })
      break
    }
  }
}
