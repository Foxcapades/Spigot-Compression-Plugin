package io.foxcapades.spigot.bcp.event.player

import io.foxcapades.spigot.bcp.consts.Permission
import io.foxcapades.spigot.bcp.ext.openCompressionGUI
import io.foxcapades.spigot.bcp.item.ZipTool.isZipTool
import org.bukkit.event.player.PlayerInteractEvent

internal fun PlayerInteractEvent.handle() {
  if (item.isZipTool) {
    isCancelled = true
    if (player.hasPermission(Permission.UseTool))
      player.openCompressionGUI()
  }
}
