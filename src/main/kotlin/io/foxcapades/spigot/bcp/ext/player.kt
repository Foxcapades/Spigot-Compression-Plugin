package io.foxcapades.spigot.bcp.ext

import io.foxcapades.spigot.bcp.Server
import io.foxcapades.spigot.bcp.i18n.I18N
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType.WORKBENCH
import org.bukkit.inventory.CraftingInventory

@Suppress("NOTHING_TO_INLINE")
internal inline fun Player.openCompressionGUI() =
  openInventory(Server.createInventory(this, WORKBENCH, I18N.workbenchName()))

@Suppress("NOTHING_TO_INLINE")
internal inline fun Player.hasAnyPerm(vararg perms: String) =
  perms.any(::hasPermission)
