@file:Suppress("NOTHING_TO_INLINE")
@file:JvmName("BlockExtensions")
package io.foxcapades.bukkit.inventory

import org.bukkit.Material.AIR
import org.bukkit.block.Block
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * Tests whether the target `Block` is `null` or `AIR`.
 *
 * @return `true` if the target `Block` is null or has a type of `AIR`,
 * otherwise `false`.
 */
@OptIn(ExperimentalContracts::class)
inline fun Block?.isEmpty(): Boolean {
  contract {
    returns(false) implies (this@isEmpty != null)
  }

  return this == null || blockData.material == AIR
}
