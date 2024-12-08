@file:JvmName("ColorableArmorMetaExtensions")
package io.foxcapades.bukkit.inventory.meta

import org.bukkit.inventory.meta.ArmorMeta
import org.bukkit.inventory.meta.ColorableArmorMeta
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.LeatherArmorMeta


private inline val ColorableArmorMeta.fieldCount: Int
  get() {
    val f = (this as ItemMeta).fieldCount
    return f + ((this as LeatherArmorMeta).fieldCount - f) + ((this as ArmorMeta).fieldCount - f)
  }

fun ColorableArmorMeta.toJsonSerializable(): Map<String, Any> =
  typedMetaMap(fieldCount, MetaType.ColorArmor).also(::serializeInto)

@JvmOverloads
fun ColorableArmorMeta.serializeInto(into: MutableMap<String, Any>, includeBaseMeta: Boolean = true) {
  (this as LeatherArmorMeta).serializeInto(into, false)
  (this as ArmorMeta).serializeInto(into, includeBaseMeta)
}
