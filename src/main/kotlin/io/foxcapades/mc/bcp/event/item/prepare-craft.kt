package io.foxcapades.mc.bcp.event.item

import io.foxcapades.mc.bcp.consts.Permission
import io.foxcapades.mc.bcp.item.ZipTool.isZipTool
import org.bukkit.event.inventory.PrepareItemCraftEvent

internal fun PrepareItemCraftEvent.handle() {
  if (recipe?.result.isZipTool && !view.player.hasPermission(Permission.ToolCraft))
    inventory.result = null
}
