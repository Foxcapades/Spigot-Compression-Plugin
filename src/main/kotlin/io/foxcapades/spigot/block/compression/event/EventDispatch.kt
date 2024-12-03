package io.foxcapades.spigot.block.compression.event

import io.foxcapades.spigot.block.compression.config.PluginConfig
import io.foxcapades.spigot.block.compression.consts.Permission
import io.foxcapades.spigot.block.compression.event.handler.inventory.handleClick
import io.foxcapades.spigot.block.compression.event.handler.inventory.handleCustom
import io.foxcapades.spigot.block.compression.event.handler.inventory.handleStandard
import io.foxcapades.spigot.block.compression.event.handler.world.handle
import io.foxcapades.spigot.block.compression.ext.isCompressionTool
import io.foxcapades.spigot.block.compression.ext.openCompressionGUI
import io.foxcapades.spigot.block.compression.item.ZipTool.isZipTool
import org.bukkit.Material.AIR
import org.bukkit.event.*
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.inventory.*
import org.bukkit.event.inventory.InventoryType.*
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

internal object EventDispatch : Listener {
  @EventHandler
  fun BlockPlaceEvent.onBlockPlace() = handle()

  @EventHandler
  fun InventoryClickEvent.onInventoryClick() = handleClick()

  @EventHandler
  fun InventoryDragEvent.onInventoryDrag() = if (view.isCompressionTool()) handleCustom() else handleStandard()

  @EventHandler
  fun InventoryCloseEvent.onInventoryClose() {
    if (view.topInventory.type != WORKBENCH) {
      return
    }

    if (view.isCompressionTool()) {
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
  fun PlayerInteractEvent.onPlayerInteract() {
    if (item?.isZipTool == true && player.hasPermission(Permission.UseTool)) {
      isCancelled = true
      player.openCompressionGUI()
    }
  }

  @EventHandler
  fun PrepareItemCraftEvent.onItemCraft() {
    if (recipe == PluginConfig.ZipToolRecipe && !view.player.hasPermission(Permission.UseTool))
      inventory.result = null
  }
}
