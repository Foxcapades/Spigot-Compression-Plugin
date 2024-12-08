@file:JvmName("ArmorMetaExtensions")
package io.foxcapades.bukkit.inventory.meta

import org.bukkit.inventory.meta.ArmorMeta
import org.bukkit.inventory.meta.ItemMeta


internal inline val ArmorMeta.fieldCount
  get() = (this as ItemMeta).fieldCount + 1

fun ArmorMeta.toJsonSerializable(): Map<String, Any> =
  typedMetaMap(fieldCount, MetaType.Armor).also(::serializeInto)

@JvmOverloads
fun ArmorMeta.serializeInto(into: MutableMap<String, Any>, includeBaseMeta: Boolean = true) {
  trim?.also { into[MetaKey.Trim] = listOf(it.material.key, it.pattern.key) }

  if (includeBaseMeta)
    internalSerializeInto(into)
}
