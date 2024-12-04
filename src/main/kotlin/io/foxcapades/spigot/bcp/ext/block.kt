@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.spigot.bcp.ext

import org.bukkit.Material.AIR
import org.bukkit.block.Block

internal inline fun Block?.isEmpty() =
  this == null || blockData.material == AIR
