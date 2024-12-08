@file:JvmName("MapMetaExtensions")
package io.foxcapades.bukkit.inventory.meta

import io.foxcapades.bukkit.map.toJsonSerializable
import org.bukkit.inventory.meta.MapMeta
import org.bukkit.inventory.meta.ItemMeta


private inline val MapMeta.fieldCount
  get() = (this as ItemMeta).fieldCount + 2

fun MapMeta.toJsonSerializable(): Map<String, Any> =
  typedMetaMap(fieldCount, MetaType.Map).also(::serializeInto)

@JvmOverloads
fun MapMeta.serializeInto(into: MutableMap<String, Any>, includeBaseMeta: Boolean = true) {
  if (hasMapView())
    into[MetaKey.MapView] = mapView!!.toJsonSerializable()
  if (hasColor())
    into[MetaKey.Color] = color!!.asARGB()

  if (includeBaseMeta)
    internalSerializeInto(into)
}
