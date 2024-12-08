@file:Suppress("NOTHING_TO_INLINE")
@file:JvmName("ItemStackExtensions")
package io.foxcapades.bukkit.inventory

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.math.max

/**
 * Tests whether the target `ItemStack` is `null` or `AIR`.
 *
 * @return `true` if the target `ItemStack` is null or has a type of `AIR`,
 * otherwise `false`.
 */
@OptIn(ExperimentalContracts::class)
inline fun ItemStack?.isEmpty(): Boolean {
  contract {
    returns(false) implies (this@isEmpty != null)
  }

  return this == null || type == Material.AIR
}

/**
 * Executes the given [action] if the target `ItemStack` [is empty][isEmpty].
 *
 * @param action Action to execute if the target `ItemStack` is empty.
 *
 * @return Any value returned by [action] if this `ItemStack` is empty,
 * otherwise `null`.
 */
@OptIn(ExperimentalContracts::class)
inline fun <T> ItemStack?.ifEmpty(action: () -> T): T? {
  contract {
    returnsNotNull() implies (this@ifEmpty != null)
    callsInPlace(action, InvocationKind.AT_MOST_ONCE)
  }

  return if (isEmpty())
    action()
  else
    null
}

/**
 * Tests whether the target `ItemStack` is something other than `null` or `AIR`.
 *
 * @return `true` if the target `ItemStack` is not null or has a type other than
 * `AIR`. `false` if the target item [is empty][isEmpty].
 */
@OptIn(ExperimentalContracts::class)
inline fun ItemStack?.isNotEmpty(): Boolean {
  contract {
    returns(true) implies (this@isNotEmpty != null)
  }

  return this != null && type != Material.AIR
}

/**
 * Executes the given [action] if the target `ItemStack`
 * [is not empty][isNotEmpty].
 *
 * @param action Action to execute if the target `ItemStack` is not empty.
 *
 * @return Any value returned by [action] if this `ItemStack` is not empty,
 * otherwise `null`.
 */
@OptIn(ExperimentalContracts::class)
inline fun <T> ItemStack?.ifNotEmpty(action: () -> T): T? {
  contract {
    returnsNotNull() implies (this@ifNotEmpty != null)
    callsInPlace(action, InvocationKind.AT_MOST_ONCE)
  }

  return if (isNotEmpty())
    action()
  else
    null
}

/**
 * Tests whether the target `ItemStack` has a stack size that is less than the
 * allowed [max stack size][ItemStack.getMaxStackSize].
 */
inline fun ItemStack.hasSpace() = amount < maxStackSize

/**
 * Executes the given [action] on the target `ItemStack` if the stack
 * [has space available][hasSpace].
 *
 * @param action Action to execute if the `ItemStack` has space.  The given
 * action will be called with the same receiver instance as this function along
 * with an argument that is the [free space][freeSpace] available in the stack.
 *
 * @return Any value returned by [action] if this `ItemStack` has free space,
 * otherwise `null`.
 */
@OptIn(ExperimentalContracts::class)
inline fun <T> ItemStack.ifHasSpace(action: ItemStack.(space: Int) -> T): T? {
  contract {
    callsInPlace(action, InvocationKind.AT_MOST_ONCE)
  }

  return when (val space = freeSpace) {
    0    -> null
    else -> action(space)
  }
}

/**
 * The amount of free space left in this stack for more items before it reaches
 * its [max stack size][ItemStack.getMaxStackSize].
 */
inline val ItemStack.freeSpace
  get() = max(0, maxStackSize - amount)

/**
 * Clones the target `ItemStack` with the given [newAmount] as the new stack's
 * stack size.
 *
 * @param newAmount Stack size to set on the new, cloned `ItemStack` instance.
 *
 * @return A new `ItemStack` instance with the given stack size.
 */
inline fun ItemStack.clone(newAmount: Int) = ItemStack(this).apply { amount = newAmount }
