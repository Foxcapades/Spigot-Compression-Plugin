@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.spigot.zip.event

import io.foxcapades.spigot.block.compression.consts.CompressorTitle
import io.foxcapades.spigot.block.compression.event.handler.world.BlockPlaceHandler
import io.foxcapades.spigot.block.compression.event.handler.world.InteractHandler
import org.bukkit.Material.AIR
import org.bukkit.event.*
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.inventory.*
import org.bukkit.event.inventory.InventoryType.*
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

object EventDispatch : Listener {

  @EventHandler
  fun InventoryClickEvent.onInventoryClick() = handleClick()

  @EventHandler
  fun InventoryDragEvent.onInventoryDrag() {
    if (view.title == CompressorTitle)
      handleCustomDrag()
    else
      handleDragEvent(this)
  }

  @EventHandler
  fun InventoryCloseEvent.onInventoryClose() {

    if (view.topInventory.type != WORKBENCH) {
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

  @EventHandler
  fun onBlockPlace(event: BlockPlaceEvent) =
    BlockPlaceHandler.handle(BCBlockPlaceEvent(event))

  @EventHandler
  fun onPlayerInteract(event: PlayerInteractEvent) =
    InteractHandler.handle(BCPlayerInteractEvent(event))
}
