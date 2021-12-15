package io.foxcapades.spigot.block.compression.event

import io.foxcapades.spigot.block.compression.block.isNotEmpty
import io.foxcapades.spigot.block.compression.item.isNotEmpty
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.event.Event.Result
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack

@JvmInline
internal value class BCPlayerInteractEvent(val raw: PlayerInteractEvent) {
  inline val player: Player
    get() = raw.player

  inline val action: Action
    get() = raw.action

  inline var cancelled: Boolean
    get()  = raw.isCancelled
    set(v) { raw.isCancelled = v }

  inline val itemUsed: ItemStack?
    get() = raw.item

  inline var itemResult: Result
    get()  = raw.useItemInHand()
    set(v) = raw.setUseItemInHand(v)

  inline val blockClicked: Block?
    get() = raw.clickedBlock

  inline var blockResult: Result
    get()  = raw.useInteractedBlock()
    set(v) = raw.setUseInteractedBlock(v)

  inline val hand: EquipmentSlot?
    get() = raw.hand

  inline val blockFace: BlockFace
    get() = raw.blockFace

  inline val userUsedItemOnBlock: Boolean
    get() = itemUsed.isNotEmpty() && blockClicked.isNotEmpty()

}
