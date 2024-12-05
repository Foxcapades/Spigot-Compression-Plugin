package io.foxcapades.mc.bcp.ext

import io.foxcapades.mc.bcp.Server
import io.foxcapades.mc.bcp.i18n.I18N
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType.WORKBENCH

@Suppress("NOTHING_TO_INLINE")
internal inline fun Player.openCompressionGUI() =
  openInventory(Server.createInventory(this, WORKBENCH, I18N.workbenchName()))
