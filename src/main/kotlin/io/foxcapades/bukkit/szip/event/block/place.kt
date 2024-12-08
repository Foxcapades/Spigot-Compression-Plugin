package io.foxcapades.bukkit.szip.event.block

import io.foxcapades.bukkit.szip.Logger
import io.foxcapades.bukkit.szip.zip.isCompressed
import io.foxcapades.bukkit.szip.item.ZipTool.isZipTool
import org.bukkit.event.block.BlockPlaceEvent

internal fun BlockPlaceEvent.handle() {
  if (itemInHand.isCompressed || itemInHand.isZipTool) {
    Logger.trace { "cancelling block place event for (player=%s, block=%s)".format(player.name, itemInHand.itemMeta?.displayName) }
    isCancelled = true
  }
}
