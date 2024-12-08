package io.foxcapades.bukkit.szip.event.player

import io.foxcapades.bukkit.szip.Logger
import io.foxcapades.bukkit.szip.consts.Permission
import io.foxcapades.bukkit.szip.item.ZipTool
import org.bukkit.event.player.PlayerJoinEvent

internal fun PlayerJoinEvent.handle() {
  if (player.hasPermission(Permission.ToolCraft)) {
    if (player.discoverRecipe(ZipTool.key))
      Logger.trace { "added compression tool to recipes for player %s".format(player.name) }
  } else {
    if (player.undiscoverRecipe(ZipTool.key))
      Logger.trace { "removed compression tool from recipes for player %s".format(player.name) }
  }
}
