@file:JvmName("LeatherArmorMetaExtensions")
package io.foxcapades.bukkit.inventory.meta

import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.LeatherArmorMeta


internal inline val LeatherArmorMeta.fieldCount
  get() = (this as ItemMeta).fieldCount + 1

fun LeatherArmorMeta.toJsonSerializable(): Map<String, Any> =
  typedMetaMap(fieldCount, MetaType.LeatherArmor).also(::serializeInto)

@JvmOverloads
fun LeatherArmorMeta.serializeInto(into: MutableMap<String, Any>, includeBaseMeta: Boolean = true) {
  into[MetaKey.Color] = color.asARGB()

  if (includeBaseMeta)
    internalSerializeInto(into)
}
