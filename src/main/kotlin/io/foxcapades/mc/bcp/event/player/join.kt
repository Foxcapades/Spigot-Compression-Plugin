package io.foxcapades.mc.bcp.event.player

import io.foxcapades.mc.bcp.consts.Permission
import io.foxcapades.mc.bcp.item.ZipTool
import org.bukkit.event.player.PlayerJoinEvent

internal fun PlayerJoinEvent.handle() {
  if (player.hasPermission(Permission.ToolCraft)) {
    player.discoverRecipe(ZipTool.key)
  } else {
    player.undiscoverRecipe(ZipTool.key)
  }
}
