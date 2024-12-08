package io.foxcapades.bukkit.szip.event

import io.foxcapades.bukkit.szip.event.block.handle
import io.foxcapades.bukkit.szip.event.entity.handle
import io.foxcapades.bukkit.szip.event.inventory.handle
import io.foxcapades.bukkit.szip.event.item.handle
import io.foxcapades.bukkit.szip.event.player.handle
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockDispenseEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.PrepareItemCraftEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.event.player.PlayerItemDamageEvent
import org.bukkit.event.player.PlayerJoinEvent

internal object EventDispatch : Listener {
  @EventHandler
  fun onBlockDispense(event: BlockDispenseEvent) = event.handle()

  @EventHandler
  fun onBlockPlace(event: BlockPlaceEvent) = event.handle()

  @EventHandler
  fun onEntityShootBow(event: EntityShootBowEvent) = event.handle()

  @EventHandler
  fun onInventoryClick(event: InventoryClickEvent) = event.handle()

  @EventHandler
  fun onInventoryDrag(event: InventoryDragEvent) = event.handle()

  @EventHandler
  fun onInventoryClose(event: InventoryCloseEvent) = event.handle()

  @EventHandler
  fun onPlayerInteract(event: PlayerInteractEvent) = event.handle()

  @EventHandler
  fun onPlayerItemConsume(event: PlayerItemConsumeEvent) = event.handle()

  @EventHandler
  fun onPlayerItemDamage(event: PlayerItemDamageEvent) = event.handle()

  @EventHandler
  fun onPlayerJoin(event: PlayerJoinEvent) = event.handle()

  @EventHandler
  fun onPrepareItemCraft(event: PrepareItemCraftEvent) = event.handle()
}
