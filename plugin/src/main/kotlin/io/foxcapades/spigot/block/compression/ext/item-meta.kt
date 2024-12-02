package io.foxcapades.spigot.block.compression.ext

import io.foxcapades.spigot.block.compression.Plugin
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType

@Suppress("NOTHING_TO_INLINE")
internal inline operator fun <P : Any, C : Any> ItemMeta.get(key: String, type: PersistentDataType<P, C>) =
  persistentDataContainer[Plugin.newKey(key), type]

@Suppress("NOTHING_TO_INLINE")
internal inline operator fun <P : Any, C : Any> ItemMeta.set(key: String, type: PersistentDataType<P, C>, data: C) {
  persistentDataContainer[Plugin.newKey(key), type] = data
}
