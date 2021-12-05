package io.foxcapades.spigot.block.compression.item

import org.bukkit.inventory.ItemStack
import java.io.BufferedInputStream
import java.util.*

object ItemID {
  val itemIDs: ResourceBundle

  init {
    BufferedInputStream(ItemID::class.java.getResourceAsStream("/mc-item-ids.properties")!!).use {
      itemIDs = PropertyResourceBundle(it)
    }
  }

  @Suppress("NOTHING_TO_INLINE")
  inline operator fun get(item: ItemStack): String {
    val safeKey = item.type.key.namespace + "." + item.type.key.key

    return if (itemIDs.containsKey(safeKey))
      itemIDs.getString(safeKey)
    else
      item.type.key.toString()
  }
}