package io.foxcapades.spigot.bcp.event.block

import io.foxcapades.spigot.bcp.compress.isCompressed
import io.foxcapades.spigot.bcp.item.ZipTool.isZipTool
import org.bukkit.event.block.BlockPlaceEvent

internal fun BlockPlaceEvent.handle() {
  if (itemInHand.isCompressed || itemInHand.isZipTool)
    isCancelled = true
}
