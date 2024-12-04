package io.foxcapades.spigot.bcp.event.player

import io.foxcapades.spigot.bcp.consts.Permission
import io.foxcapades.spigot.bcp.item.ZipTool
import org.bukkit.event.player.PlayerJoinEvent

internal fun PlayerJoinEvent.handle() {
  if (player.hasPermission(Permission.UseTool)) {
    player.discoverRecipe(ZipTool.key)
  } else {
    player.undiscoverRecipe(ZipTool.key)
  }
}
