package io.foxcapades.spigot.bcp.event

import io.foxcapades.spigot.bcp.event.block.handle
import io.foxcapades.spigot.bcp.event.inventory.handle
import io.foxcapades.spigot.bcp.event.player.handle
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent

internal object EventDispatch : Listener {
  @EventHandler
  fun onPlayerJoin(event: PlayerJoinEvent) = event.handle()

  @EventHandler
  fun onBlockPlace(event: BlockPlaceEvent) = event.handle()

  @EventHandler
  fun onInventoryClick(event: InventoryClickEvent) = event.handle()

  @EventHandler
  fun onInventoryDrag(event: InventoryDragEvent) = event.handle()

  @EventHandler
  fun onInventoryClose(event: InventoryCloseEvent) = event.handle()

  @EventHandler
  fun onPlayerInteract(event: PlayerInteractEvent) = event.handle()
}
