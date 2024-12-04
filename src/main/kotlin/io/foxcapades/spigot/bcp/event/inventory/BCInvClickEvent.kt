package io.foxcapades.spigot.bcp.event.inventory

import io.foxcapades.spigot.bcp.inv.CraftInventory
import io.foxcapades.spigot.bcp.item.Air
import org.bukkit.entity.Player
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

  inline val player: Player
    get() = raw.whoClicked as Player

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

  inline val userClickedTopInventory: Boolean
    get() = raw.clickedInventory === raw.view.topInventory

  inline val userClickedBottomInventory: Boolean
    get() = raw.clickedInventory === raw.view.bottomInventory

  inline fun cancel() { raw.isCancelled = true }

  inline fun uncancel() { raw.isCancelled = false }
}
