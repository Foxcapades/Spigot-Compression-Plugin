package io.foxcapades.mc.bcp.ext

import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

@Suppress("NOTHING_TO_INLINE")
internal inline operator fun Inventory.get(index: Int) = getItem(index)

@Suppress("NOTHING_TO_INLINE")
internal inline operator fun Inventory.set(index: Int, stack: ItemStack?) = setItem(index, stack)
