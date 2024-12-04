package io.foxcapades.spigot.bcp.event.block

import io.foxcapades.spigot.bcp.compress.ifCompressed
import org.bukkit.event.block.BlockPlaceEvent

internal fun BlockPlaceEvent.handle() =
  itemInHand.ifCompressed { isCancelled = true }
