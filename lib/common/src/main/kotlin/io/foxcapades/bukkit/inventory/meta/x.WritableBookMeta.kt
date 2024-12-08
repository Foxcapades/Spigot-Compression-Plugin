@file:JvmName("WritableBookMetaExtensions")
package io.foxcapades.bukkit.inventory.meta

import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.WritableBookMeta


internal inline val WritableBookMeta.fieldCount
  get() = (this as ItemMeta).fieldCount + 1

fun WritableBookMeta.toJsonSerializable(): Map<String, Any> =
  typedMetaMap(fieldCount, MetaType.WritableBook)

@JvmOverloads
fun WritableBookMeta.serializeInto(into: MutableMap<String, Any>, includeBaseMeta: Boolean = true) {
  pages.takeUnless { it.isEmpty() }?.also { into[MetaKey.Pages] = it }
  if (includeBaseMeta)
    internalSerializeInto(into)
}
