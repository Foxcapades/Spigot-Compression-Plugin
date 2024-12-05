package io.foxcapades.mc.bcp.event

import io.foxcapades.mc.bcp.Logger
import io.foxcapades.mc.bcp.event.block.handle
import io.foxcapades.mc.bcp.event.inventory.handle
import io.foxcapades.mc.bcp.event.item.handle
import io.foxcapades.mc.bcp.event.player.handle
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.PrepareItemCraftEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent

internal object EventDispatch : Listener {
  @EventHandler
  fun onBlockPlace(event: BlockPlaceEvent) {
    Logger.trace("received event %s", event)
    event.handle()
  }

  @EventHandler
  fun onInventoryClick(event: InventoryClickEvent) {
    Logger.trace("received event %s", event)
    event.handle()
  }

  @EventHandler
  fun onInventoryDrag(event: InventoryDragEvent) {
    Logger.trace("received event %s", event)
    event.handle()
  }

  @EventHandler
  fun onInventoryClose(event: InventoryCloseEvent) {
    Logger.trace("received event %s", event)
    event.handle()
  }

  @EventHandler
  fun onPlayerInteract(event: PlayerInteractEvent) {
    Logger.trace("received event %s", event)
    event.handle()
  }

  @EventHandler
  fun onPlayerJoin(event: PlayerJoinEvent) {
    Logger.trace("received event %s", event)
    event.handle()
  }

  @EventHandler
  fun onPrepareItemCraft(event: PrepareItemCraftEvent) {
    Logger.trace("received event %s", event)
    event.handle()
  }
}
