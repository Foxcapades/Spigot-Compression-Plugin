package io.foxcapades.mc.bcp.event.block

import io.foxcapades.mc.bcp.zip.isCompressed
import io.foxcapades.mc.bcp.item.ZipTool.isZipTool
import org.bukkit.event.block.BlockPlaceEvent

internal fun BlockPlaceEvent.handle() {
  if (itemInHand.isCompressed || itemInHand.isZipTool)
    isCancelled = true
}
