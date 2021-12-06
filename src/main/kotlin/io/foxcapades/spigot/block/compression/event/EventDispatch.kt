@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.spigot.block.compression.event

import io.foxcapades.spigot.block.compression.compressible.*
import io.foxcapades.spigot.block.compression.compressible.CompressionLevel.None
import io.foxcapades.spigot.block.compression.consts.CompressorTitle
import io.foxcapades.spigot.block.compression.facades.Facade
import io.foxcapades.spigot.block.compression.facades.I18N
import org.bukkit.Material.AIR

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.*
import org.bukkit.event.inventory.InventoryType.SlotType.RESULT
import org.bukkit.event.inventory.InventoryType.WORKBENCH
import org.bukkit.inventory.*
import org.bukkit.persistence.PersistentDataType

// FIXME: Shift click consumes without granting a new item
// FIXME: Click with held places block in result slot.

object EventDispatch : Listener {
  @EventHandler
  fun InventoryClickEvent.onInventoryClick() {
    val top = view.topInventory

    clickedInventory == top       || return
    top.type == WORKBENCH         || return
    view.title == CompressorTitle || return

    // FIXME
    if (slotType == RESULT) {
      handleResultClick()
      return
    }

    // Placing a stack in the crafting grid
    Facade.runTaskLater(2) { calculateResult() }
  }

  @EventHandler
  fun InventoryDragEvent.onInventoryDrag() {

    val top = view.topInventory

    if (top.type != WORKBENCH) {
      return
    }

    if (view.title != CompressorTitle) {
      return
    }

    var relevant = false

    // validate/check slots
    for (i in inventorySlots) {
      // Reject drags into the result slot
      if (i == 0) {
        isCancelled = true
        return
      }

      if (i in 1..9) {
        relevant = true
        break
      }
    }

    if (!relevant)
      return

    Facade.runTaskLater(2) { calculateResult() }
  }

  @EventHandler
  fun InventoryCloseEvent.onInventoryClose() {

    if (view.topInventory.type != WORKBENCH) {
      return
    }

    if (view.title != CompressorTitle) {
      return
    }

    var count = 0

    for (i in 1..9) {
      val current = view.topInventory.getItem(i)

      if (current != null && current.type != AIR)
        count++
    }

    if (count == 0)
      return


    val tmp = arrayOfNulls<ItemStack>(count)

    for (i in 1..9) {
      val current = view.topInventory.getItem(i)

      if (current != null && current.type != AIR)
        tmp[--count] = current
    }

    val remain = view.bottomInventory.addItem(*tmp)

    for (v in remain.values)
      player.world.dropItem(player.location, v)
  }
}

internal inline fun InventoryEvent.calculateResult() {
  val top = view.topInventory

  var stack = top.singularStack()


  if (stack != null && stack in Compressibles) {
    val lvl = stack.compressionLevel()
    if (lvl.hasPrevious)
      top.setItem(0, stack.compressionLevel(lvl.previous, 9))

    return
  }

  stack = top.allTheSame()

  if (stack == null) {
    top.setItem(0, null)
    return
  }


  if (stack !in Compressibles)
    return

  val lvl = stack.compressionLevel()

  if (lvl.hasNext) {
    top.setItem(0, stack.compressionLevel(lvl.next, 1))
  }

}

fun Inventory.decrementCraftingGrid() {

  for (i in 1..9) {
    val current = getItem(i) ?: continue

    if (current.amount == 1) {
      setItem(i, null)
    } else {
      current.amount--
    }
  }
}

fun Inventory.allTheSame(): ItemStack? {

  val first = getItem(2) ?: return null

  for (i in 2..9)
    if (!first.isSimilar(getItem(i)))
      return null

  return first
}

fun Inventory.singularStack(): ItemStack? {

  var found: ItemStack? = null

  for (i in 1..9)
    when (val v = getItem(i)) {
      null -> continue
      else -> if (found != null) return null else found = v
    }

  return found
}

const val metaKey = "bcp_lvl"

fun ItemStack.compressionLevel(): CompressionLevel {
  return CompressionLevel.from(itemMeta?.persistentDataContainer?.get(Facade.key(metaKey), PersistentDataType.BYTE) ?: return None)
}

fun ItemStack.compressionLevel(lvl: CompressionLevel, qty: Int): ItemStack {
  val out = ItemStack(this)
  out.amount = qty

  if (lvl == None) {
    out.itemMeta = null
  } else {
    val meta = out.itemMeta ?: throw IllegalStateException()
    meta.persistentDataContainer.set(Facade.key(metaKey), PersistentDataType.BYTE, lvl.ordinal.toByte())

    val name = if (type.isBlock) I18N.blockName(type) else I18N.itemName(type)
    meta.setDisplayName(I18N.fillNameFor(lvl, name))
    meta.lore = listOf(I18N.fillLoreFor(lvl, name))

    out.itemMeta = meta
  }

  return out
}
