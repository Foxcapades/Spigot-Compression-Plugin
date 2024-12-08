@file:JvmName("BlockDataMetaExtensions")
package io.foxcapades.bukkit.inventory.meta

import org.bukkit.inventory.meta.BlockDataMeta
import org.bukkit.inventory.meta.ItemMeta


private inline val BlockDataMeta.fieldCount
  get() = (this as ItemMeta).fieldCount

fun BlockDataMeta.toJsonSerializable(): Map<String, Any> =
  typedMetaMap(fieldCount, MetaType.BlockData).also(::serializeInto)

@JvmOverloads
fun BlockDataMeta.serializeInto(into: MutableMap<String, Any>, includeBaseMeta: Boolean = true) {
  // TODO: should we store block data?  Is it even customizable?
  if (includeBaseMeta)
    internalSerializeInto(into)
}
