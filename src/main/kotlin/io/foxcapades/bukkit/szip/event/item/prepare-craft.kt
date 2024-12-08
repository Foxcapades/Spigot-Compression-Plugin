package io.foxcapades.bukkit.szip.event.item

import io.foxcapades.bukkit.szip.Logger
import io.foxcapades.bukkit.szip.consts.Permission
import io.foxcapades.bukkit.szip.item.ZipTool.isZipTool
import org.bukkit.event.inventory.PrepareItemCraftEvent

internal fun PrepareItemCraftEvent.handle() {
  if (recipe?.result.isZipTool && !view.player.hasPermission(Permission.ToolCraft)) {
    Logger.trace {
      "cancelling item craft event for (player=%s, recipe=%s)"
        .format(view.player.name, recipe?.result?.itemMeta?.displayName)
    }

    inventory.result = null
  }
}
