@file:JvmName("SpawnEggMetaExtensions")
package io.foxcapades.bukkit.inventory.meta

import io.foxcapades.bukkit.entity.toJsonSerializable
import org.bukkit.inventory.meta.SpawnEggMeta
import org.bukkit.inventory.meta.ItemMeta


private inline val SpawnEggMeta.fieldCount
  get() = (this as ItemMeta).fieldCount + 1

fun SpawnEggMeta.toJsonSerializable(): Map<String, Any> =
  typedMetaMap(fieldCount, MetaType.SpawnEgg).also(::serializeInto)

@JvmOverloads
fun SpawnEggMeta.serializeInto(into: MutableMap<String, Any>, includeBaseMeta: Boolean = true) {
  spawnedEntity?.also { into[MetaKey.Entity] = it.toJsonSerializable() }

  if (includeBaseMeta)
    internalSerializeInto(into)
}
