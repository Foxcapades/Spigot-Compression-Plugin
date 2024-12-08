package io.foxcapades.bukkit.inventory

import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

/**
 * Inventory slot reference.
 *
 * Pseudo-data-class representing a single slot in a target inventory.
 *
 * This type may be destructured like a data class to get the following values:
 * 1. Slot index
 * 2. Item stack (or null)
 * 3. Inventory instance
 *
 * Example:
 * ```kotlin
 * // Destructuring
 * val (index, stack) = slotRef
 * ```
 *
 * @constructor
 *
 * @param inventory Parent inventory of the slot being referenced.
 *
 * @param index Slot index.
 */
class SlotReference(val inventory: Inventory, val index: Int) {
  /**
   * The `ItemStack` at the referenced slot, or `null`.
   */
  inline val stack: ItemStack?
    get() = inventory[index]

  /**
   * The `ItemStack` at the referenced slot, or [Air].
   */
  inline val stackOrAir: ItemStack
    get() = stack ?: Air

  /**
   * Destructuring item 1: [index].
   */
  @Suppress("NOTHING_TO_INLINE")
  inline operator fun component1() = index

  /**
   * Destructuring item 2: [stack].
   */
  @Suppress("NOTHING_TO_INLINE")
  inline operator fun component2() = stack

  /**
   * Destructuring item 3: [inventory].
   */
  @Suppress("NOTHING_TO_INLINE")
  inline operator fun component3() = inventory
}
