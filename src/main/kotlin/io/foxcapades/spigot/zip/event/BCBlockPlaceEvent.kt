package io.foxcapades.spigot.zip.event

import org.bukkit.event.block.BlockPlaceEvent

class BCBlockPlaceEvent(val raw: BlockPlaceEvent) {

  inline val player
    get() = raw.player

  inline val placedBlock
    get() = raw.blockPlaced

  inline val itemInHand
    get() = raw.itemInHand

  inline val cancelled
    get() = raw.isCancelled

  inline val hand
    get() = raw.hand

  fun cancel() {
    raw.isCancelled = true
  }

  fun uncancel() {
    raw.isCancelled = false
  }
}