package io.foxcapades.spigot.zip.event

import io.foxcapades.spigot.block.compression.block.isNotEmpty
import io.foxcapades.spigot.block.compression.item.isNotEmpty
import org.bukkit.event.player.PlayerInteractEvent

@JvmInline
internal value class BCPlayerInteractEvent(val raw: PlayerInteractEvent) {
  inline val player
    get() = raw.player

  inline val action
    get() = raw.action

  inline var cancelled: Boolean
    get()  = raw.isCancelled
    set(v) { raw.isCancelled = v }

  inline val itemUsed
    get() = raw.item

  inline val itemType
    get() = raw.item?.type

  inline var itemResult
    get()  = raw.useItemInHand()
    set(v) = raw.setUseItemInHand(v)

  inline val blockClicked
    get() = raw.clickedBlock

  inline val blockType
    get() = raw.clickedBlock?.type

  inline var blockResult
    get()  = raw.useInteractedBlock()
    set(v) = raw.setUseInteractedBlock(v)

  inline val hand
    get() = raw.hand

  inline val blockFace
    get() = raw.blockFace

  inline val userUsedItemOnBlock
    get() = itemUsed.isNotEmpty() && blockClicked.isNotEmpty()

}
