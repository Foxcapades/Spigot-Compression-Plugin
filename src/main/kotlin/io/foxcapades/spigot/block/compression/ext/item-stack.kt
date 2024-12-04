@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.spigot.block.compression.ext

import io.foxcapades.spigot.block.compression.item.Air
import org.bukkit.Material.AIR
import org.bukkit.inventory.ItemStack
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

internal inline val ItemStack.logName
  get() = itemMeta?.takeIf { it.hasDisplayName() }?.displayName ?: type.name

@OptIn(ExperimentalContracts::class)
internal inline fun ItemStack?.isEmpty(): Boolean {
  contract {
    returns(false) implies (this@isEmpty != null)
  }

  return this == null || type == AIR
}

internal inline fun ItemStack.ifEmpty(action: () -> Unit) {
  if (type == AIR)
    action()
}

@OptIn(ExperimentalContracts::class)
internal inline fun ItemStack?.isNotEmpty(): Boolean {
  contract {
    returns(true) implies (this@isNotEmpty != null)
  }

  return this != null && type != AIR
}

internal inline fun ItemStack.ifNotEmpty(action: () -> Unit) {
  if (type != AIR)
    action()
}

internal inline fun ItemStack?.hasSpace() = this != null && amount < maxStackSize

internal inline fun ItemStack.ifHasSpace(action: (space: Int) -> Unit) {
  val space = maxStackSize - amount
  if (space > 0)
    action(space)
}

internal inline fun ItemStack?.freeSpace() = if (this == null) 0 else maxStackSize - amount

internal var ItemStack?.size: Int
  get()  = this?.amount ?: 0
  set(v) { this?.apply { amount = v } }

internal inline fun ItemStack?.clone(size: Int) = if (this == null) Air else ItemStack(this).apply { amount = size }


internal inline fun ItemStack.ifSimilar(other: ItemStack?, action: (other: ItemStack) -> Unit) {
  if (isSimilar(other))
    action(other!!)
}
