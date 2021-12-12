package io.foxcapades.spigot.block.compression.event

import io.foxcapades.spigot.block.compression.item.Air
import io.foxcapades.spigot.block.compression.wrap.CraftInventory
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.inventory.InventoryType.*
import org.bukkit.inventory.ItemStack

@Suppress("NOTHING_TO_INLINE")
internal class BCInvClickEvent(private val raw: InventoryClickEvent) {

  val top by lazy { CraftInventory(raw.view.topInventory) }

  val bottom
    get() = raw.view.bottomInventory

  inline val inventorySlot: ItemStack
    get() = raw.currentItem ?: Air

  inline var cursor
    get() = raw.cursor ?: Air
    set(v) {
      raw.view.cursor = v
    }

  inline val slotType: SlotType
    get() = raw.slotType

  inline val slotIndex: Int
    get() = raw.slot

  inline val invType: InventoryType
    get() = raw.view.topInventory.type

  inline val topInvIsCompressedItemSafe: Boolean
    get() = when (invType) {
      CHEST       -> true
      DISPENSER   -> true
      DROPPER     -> true
      PLAYER      -> true
      CREATIVE    -> true
      ENDER_CHEST -> true
      HOPPER      -> true
      SHULKER_BOX -> true
      BARREL      -> true
      else        -> false
    }

  inline val userClickedTopInv: Boolean
    get() = raw.clickedInventory === raw.view.topInventory

  inline val userClickedBottom: Boolean
    get() = raw.clickedInventory === raw.view.bottomInventory

  inline val clickType
    get() = raw.click

  inline fun cancel() { raw.isCancelled = true }
  inline fun uncancel() { raw.isCancelled = false }

  inline fun ifTopInvIsNotCompressedItemSafe(action: BCInvClickEvent.(CraftInventory) -> Unit) {
    if (!topInvIsCompressedItemSafe)
      action(top)
  }

  inline fun ifUserClickedTopInv(action: BCInvClickEvent.(CraftInventory) -> Unit) {
    if (userClickedTopInv)
      action(top)
  }

  inline fun ifUserClickedBottomInv(action: BCInvClickEvent.(CraftInventory) -> Unit) {
    if (userClickedBottom)
      action(top)
  }

  inline fun ifSlotTypeIsNot(type: SlotType, action: BCInvClickEvent.(CraftInventory) -> Unit) {
    if (slotType !== type)
      action(top)
  }

  inline fun ifSlotTypeIs(type: SlotType, action: BCInvClickEvent.(CraftInventory) -> Unit) {
    if (slotType === type)
      action(top)
  }
}