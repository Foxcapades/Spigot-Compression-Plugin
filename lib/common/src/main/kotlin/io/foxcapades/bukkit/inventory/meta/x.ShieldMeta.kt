@file:JvmName("ShieldMetaExtensions")
package io.foxcapades.bukkit.inventory.meta

import org.bukkit.inventory.meta.BannerMeta
import org.bukkit.inventory.meta.ShieldMeta


private inline val ShieldMeta.fieldCount
  get() = (this as BannerMeta).fieldCount + 1

fun ShieldMeta.toJsonSerializable(): Map<String, Any> =
  typedMetaMap(fieldCount, MetaType.Shield).also(::serializeInto)

@JvmOverloads
fun ShieldMeta.serializeInto(into: MutableMap<String, Any>, includeBaseMeta: Boolean = true) {
  baseColor?.also { into[MetaKey.RepairCost] = it.color.asARGB() }
  (this as BannerMeta).serializeInto(into, includeBaseMeta)
}
