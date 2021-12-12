package io.foxcapades.spigot.block.compression.event

import io.foxcapades.spigot.block.compression.item.Air
import io.foxcapades.spigot.block.compression.wrap.BCCraftingInv
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.inventory.InventoryType.*
import org.bukkit.inventory.ItemStack

@Suppress("NOTHING_TO_INLINE")
internal class BCInvClickEvent(val raw: InventoryClickEvent) {

  val top: BCCraftingInv by lazy { BCCraftingInv(raw.view.topInventory) }

  inline var inventorySlot: ItemStack
    get() = raw.currentItem ?: Air
    set(v) {
      raw.currentItem = v
    }

  inline var cursor
    get() = raw.cursor ?: Air
    set(v) {
      raw.view.cursor = v
    }

  inline val slotType: SlotType
    get() = raw.slotType

  inline val slotIndex: Int
    get() = raw.slot

  inline val rawSlotIndex: Int
    get() = raw.rawSlot

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

  inline val action
    get() = raw.action

  inline var cancelled
    get() = raw.isCancelled
    set(v) {
      raw.isCancelled = v
    }

  inline val topInventory
    get() = raw.view.topInventory

  inline val bottomInventory
    get() = raw.view.bottomInventory

  inline val clickedInventory
    get() = raw.clickedInventory!!

  inline val view
    get() = raw.view

  inline fun cancel() {
    raw.isCancelled = true
  }

  inline fun ifTopInvIsNotCompressedItemSafe(action: BCInvClickEvent.(BCCraftingInv) -> Unit) {
    if (!topInvIsCompressedItemSafe)
      action(top)
  }

  inline fun ifUserClickedTopInv(action: BCInvClickEvent.(BCCraftingInv) -> Unit) {
    if (userClickedTopInv)
      action(top)
  }

  inline fun ifUserClickedBottomInv(action: BCInvClickEvent.(BCCraftingInv) -> Unit) {
    if (userClickedBottom)
      action(top)
  }

  inline fun ifSlotTypeIsNot(type: SlotType, action: BCInvClickEvent.(BCCraftingInv) -> Unit) {
    if (slotType !== type)
      action(top)
  }

  inline fun ifSlotTypeIs(type: SlotType, action: BCInvClickEvent.(BCCraftingInv) -> Unit) {
    if (slotType === type)
      action(top)
  }
}