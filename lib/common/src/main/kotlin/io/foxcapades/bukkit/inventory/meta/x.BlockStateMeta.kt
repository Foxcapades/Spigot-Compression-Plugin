@file:JvmName("BlockStateMetaExtensions")
package io.foxcapades.bukkit.inventory.meta

import org.bukkit.inventory.meta.BlockStateMeta
import org.bukkit.inventory.meta.ItemMeta


private inline val BlockStateMeta.fieldCount
  get() = (this as ItemMeta).fieldCount

fun BlockStateMeta.toJsonSerializable(): Map<String, Any> =
  typedMetaMap(fieldCount, MetaType.BlockState).also(::serializeInto)

@JvmOverloads
fun BlockStateMeta.serializeInto(into: MutableMap<String, Any>, includeBaseMeta: Boolean = true) {
  // TODO: should we store block data?  Is it even customizable?
  if (includeBaseMeta)
    internalSerializeInto(into)
}
