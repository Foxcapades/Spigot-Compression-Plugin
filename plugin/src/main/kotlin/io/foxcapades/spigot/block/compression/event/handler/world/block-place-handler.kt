package io.foxcapades.spigot.block.compression.event.handler.world

import io.foxcapades.spigot.block.compression.compress.ifCompressed
import org.bukkit.event.block.BlockPlaceEvent

internal fun BlockPlaceEvent.handle() =
  itemInHand.ifCompressed { isCancelled = true }
