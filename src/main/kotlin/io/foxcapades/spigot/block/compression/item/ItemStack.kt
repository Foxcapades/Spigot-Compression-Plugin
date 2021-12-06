@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.spigot.block.compression.item

import org.bukkit.Material.AIR
import org.bukkit.inventory.ItemStack
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

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

internal inline fun ItemStack.hasSpace() = amount < maxStackSize

internal inline fun ItemStack.freeSpace() = maxStackSize - amount