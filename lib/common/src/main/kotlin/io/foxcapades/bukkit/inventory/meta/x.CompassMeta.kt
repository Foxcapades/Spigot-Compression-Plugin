@file:JvmName("CompassMetaExtensions")
package io.foxcapades.bukkit.inventory.meta

import org.bukkit.inventory.meta.CompassMeta
import org.bukkit.inventory.meta.ItemMeta


private inline val CompassMeta.fieldCount
  get() = (this as ItemMeta).fieldCount + 1

fun CompassMeta.toJsonSerializable(): Map<String, Any> =
  typedMetaMap(fieldCount, MetaType.Compass).also(::serializeInto)

@JvmOverloads
fun CompassMeta.serializeInto(into: MutableMap<String, Any>, includeBaseMeta: Boolean = true) {
  lodestone?.also { into[MetaKey.Lodestone] = listOf(it.world?.key, it.x, it.y, it.z, it.pitch, it.yaw) }

  if (includeBaseMeta)
    internalSerializeInto(into)
}
