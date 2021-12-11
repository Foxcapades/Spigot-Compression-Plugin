@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.spigot.block.compression.item

import io.foxcapades.spigot.block.compression.compressible.CompressionLevel
import io.foxcapades.spigot.block.compression.compressible.CompressionLevel.None
import io.foxcapades.spigot.block.compression.facades.Facade
import io.foxcapades.spigot.block.compression.facades.I18N
import org.bukkit.Material.AIR
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract


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
    meta.persistentDataContainer.set(Facade.key(metaKey), PersistentDataType.BYTE, lvl.value.toByte())

    val name = if (type.isBlock) I18N.blockName(type) else I18N.itemName(type)
    meta.setDisplayName(I18N.fillNameFor(lvl, name))
    meta.lore = listOf(I18N.fillLoreFor(lvl, name))

    out.itemMeta = meta
  }

  return out
}

@OptIn(ExperimentalContracts::class)
internal inline fun ItemStack?.isEmpty(): Boolean {
  contract {
    returns(false) implies (this@isEmpty != null)
  }

  return this == null || type == AIR
}

@OptIn(ExperimentalContracts::class)
internal inline fun ItemStack?.isNotEmpty(): Boolean {
  contract {
    returns(true) implies (this@isNotEmpty != null)
  }

  return this != null && type != AIR
}

internal inline fun ItemStack?.isFull() = this == null || amount >= maxStackSize

internal inline fun ItemStack?.hasSpace() = this != null && amount < maxStackSize

internal inline fun ItemStack?.freeSpace() = if (this == null) 0 else maxStackSize - amount

internal infix fun ItemStack?.isCompatibleWith(other: ItemStack?) = this != null && this.isSimilar(other)

internal infix fun ItemStack.isNotSimilarTo(other: ItemStack?) = !isSimilar(other)

internal var ItemStack?.size: Int
  get()  = this?.amount ?: 0
  set(v) {
    this?.apply { amount = v }
  }

internal inline fun ItemStack?.clone(size: Int): ItemStack {
  return if (this == null) Air else ItemStack(this).apply { amount = size }
}

internal inline infix fun ItemStack?.fillWith(other: ItemStack?): ItemStack? {
  // Calculate the amount of space available in the slotted stack.
  val space = this!!.freeSpace()

  // If there is enough room in the slotted stack to contain the entire
  // cursor stack:
  if (other!!.size <= space) {
    // Put the entire cursor stack into the slot.

    // Update the slotted stack quantity.
    amount += other.size

    // Clear out the cursor slot.
    return null
  } else {
    // Put the amount that we can fit into the slotted stack.

    // Max out the slotted stack.
    amount = maxStackSize

    // Reduce the cursor stack quantity.
    other.amount -= space

    return other
  }
}