@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.spigot.zip.entity

import io.foxcapades.spigot.block.compression.consts.CompressorTitle
import io.foxcapades.spigot.block.compression.facades.Facade
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType.WORKBENCH

internal inline fun Player.openCompressionWorkbench() {
  openInventory(Facade.server.createInventory(this, WORKBENCH, CompressorTitle))
}

internal inline fun Player.hasAnyPerm(vararg perms: String): Boolean {
  for (perm in perms)
    if (hasPermission(perm))
      return true

  return false
}