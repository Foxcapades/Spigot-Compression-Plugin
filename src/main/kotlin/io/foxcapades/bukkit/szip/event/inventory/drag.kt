package io.foxcapades.bukkit.szip.event.inventory

import io.foxcapades.bukkit.szip.Logger
import io.foxcapades.bukkit.szip.Plugin
import io.foxcapades.bukkit.szip.Server
import io.foxcapades.bukkit.szip.zip.isCompressed
import io.foxcapades.bukkit.szip.ext.isCompressionTool
import io.foxcapades.bukkit.szip.ext.logName
import io.foxcapades.bukkit.szip.inv.CraftInventory
import org.bukkit.event.inventory.*
import org.bukkit.event.inventory.InventoryType.*

internal fun InventoryDragEvent.handle() = if (view.isCompressionTool()) handleCustom() else handleStandard()

private fun InventoryDragEvent.handleStandard() {
  // Do nothing for these view types, the player is allowed to put compressed
  // blocks in these.
  when (view.topInventory.type) {
    CHEST       -> return
    // DISPENSER   -> return // TODO: re-enable when better item usage prevention is figured out.  Dispenser performs actions with some items (such as shooting arrows or dumping water buckets) that must be prevented.
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
      Logger.trace { "cancelling drag event for (player=%s, item=%s)".format(view.player.name, newItems.values.first().logName) }
      isCancelled = true
      return
    }
  }
}

private fun InventoryDragEvent.handleCustom() {
  // For each slot in the slots the user dragged across:
  for (index in rawSlots) {
    if (index == 0) {
      Logger.trace {
        "preventing drag action across result slot in compression gui for player %s"
          .format(view.player.name)
      }

      isCancelled = true
      break
    }

    // indices 1..9 are top inventory slots
    if (index in 1..9) {
      Logger.trace { "scheduling compression recipe refresh for player %s".format(view.player.name) }
      Server.scheduler.runTask(Plugin, Runnable { CraftInventory(view).calculateResult() })
      break
    }
  }
}
