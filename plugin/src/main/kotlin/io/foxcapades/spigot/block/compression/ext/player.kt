package io.foxcapades.spigot.block.compression.ext

import io.foxcapades.spigot.block.compression.Server
import io.foxcapades.spigot.block.compression.i18n.I18N
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType.WORKBENCH
import org.bukkit.inventory.CraftingInventory

@Suppress("NOTHING_TO_INLINE")
internal inline fun Player.openCompressionWorkbench() =
  openInventory(Server.createInventory(this, WORKBENCH, I18N.workbenchName())) as CraftingInventory

@Suppress("NOTHING_TO_INLINE")
internal inline fun Player.hasAnyPerm(vararg perms: String) =
  perms.any(::hasPermission)
