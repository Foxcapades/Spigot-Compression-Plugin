package io.foxcapades.spigot.bcp.event.item

import io.foxcapades.spigot.bcp.consts.Permission
import io.foxcapades.spigot.bcp.item.ZipTool.isZipTool
import org.bukkit.event.inventory.PrepareItemCraftEvent

internal fun PrepareItemCraftEvent.handle() {
  if (recipe?.result.isZipTool && !view.player.hasPermission(Permission.ToolCraft))
    inventory.result = null
}
