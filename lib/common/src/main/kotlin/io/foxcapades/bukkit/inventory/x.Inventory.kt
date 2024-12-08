@file:JvmName("InventoryExtensions")
package io.foxcapades.bukkit.inventory

import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

/**
 * Operator overload shortcut for [Inventory.getItem].
 *
 * @see Inventory.getItem
 */
@Suppress("NOTHING_TO_INLINE")
inline operator fun Inventory.get(index: Int) = getItem(index)

/**
 * Operator overload shortcut for [Inventory.setItem].
 *
 * @see Inventory.setItem
 */
@Suppress("NOTHING_TO_INLINE")
inline operator fun Inventory.set(index: Int, stack: ItemStack?) = setItem(index, stack)

/**
 * Highest valid slot index for the target inventory.
 */
inline val Inventory.lastIndex
  get() = size - 1
