@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.spigot.block.compression.event

import io.foxcapades.spigot.block.compression.consts.CompressorTitle
import org.bukkit.Material.AIR
import org.bukkit.event.*
import org.bukkit.event.inventory.*
import org.bukkit.inventory.ItemStack

// FIXME: Click on result with a stack in hand doesn't work.

object EventDispatch : Listener {
  @EventHandler
  fun InventoryClickEvent.onInventoryClick() {
    view.topInventory.type == InventoryType.WORKBENCH       || return
    view.title             == CompressorTitle || return

    when {
      clickedInventory === view.bottomInventory -> handleBottomClick()
      clickedInventory === view.topInventory    -> handleTopClick()
    }
  }

  @EventHandler
  fun InventoryDragEvent.onInventoryDrag() {
    view.topInventory.type == InventoryType.WORKBENCH       || return
    view.title             == CompressorTitle || return

    handleDrag()
  }

  @EventHandler
  fun InventoryCloseEvent.onInventoryClose() {

    if (view.topInventory.type != InventoryType.WORKBENCH) {
      return
    }

    if (view.title != CompressorTitle) {
      return
    }

    var count = 0

    for (i in 1..9) {
      val current = view.topInventory.getItem(i)

      if (current != null && current.type != AIR)
        count++
    }

    if (count == 0)
      return


    val tmp = arrayOfNulls<ItemStack>(count)

    for (i in 1..9) {
      val current = view.topInventory.getItem(i)

      if (current != null && current.type != AIR)
        tmp[--count] = current
    }

    val remain = view.bottomInventory.addItem(*tmp)

    for (v in remain.values)
      player.world.dropItem(player.location, v)
  }
}
