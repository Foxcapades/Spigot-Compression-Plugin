@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.spigot.zip.block

import org.bukkit.Material.AIR
import org.bukkit.block.Block
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

inline fun Block?.isEmpty() =
  this == null || blockData.material == AIR

@OptIn(ExperimentalContracts::class)
inline fun Block?.isNotEmpty(): Boolean {
  contract {
    returns(true) implies(this@isNotEmpty != null)
  }

  return this != null && blockData.material != AIR
}