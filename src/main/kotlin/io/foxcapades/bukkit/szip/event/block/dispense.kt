package io.foxcapades.bukkit.szip.event.block

import io.foxcapades.bukkit.szip.Logger
import io.foxcapades.bukkit.szip.item.ZipTool.isZipTool
import io.foxcapades.bukkit.szip.zip.isCompressed
import org.bukkit.event.block.BlockDispenseEvent

internal fun BlockDispenseEvent.handle() {
  if (item.isCompressed || item.isZipTool) {
    Logger.trace { "cancelling block dispense event for (block=(%d, %d, %d), item=%s)".format(block.x, block.y, block.z, item.itemMeta?.displayName) }
    isCancelled = true
  }
}
