package io.foxcapades.bukkit.szip.util

import org.bukkit.Bukkit
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

internal fun ItemStack.requireMeta(): ItemMeta =
  itemMeta ?: Bukkit.getItemFactory().getItemMeta(type)!!
