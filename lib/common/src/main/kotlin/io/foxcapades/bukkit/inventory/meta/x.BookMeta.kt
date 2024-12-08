@file:JvmName("BookMetaExtensions")
package io.foxcapades.bukkit.inventory.meta

import org.bukkit.inventory.meta.BookMeta
import org.bukkit.inventory.meta.WritableBookMeta


private inline val BookMeta.fieldCount
  get() = (this as WritableBookMeta).fieldCount + 3

fun BookMeta.toJsonSerializable(): Map<String, Any> =
  typedMetaMap(fieldCount, MetaType.Book)

@JvmOverloads
fun BookMeta.serializeInto(into: MutableMap<String, Any>, includeBaseMeta: Boolean = true) {
  title?.also { into[MetaKey.Title] = it }
  author?.also { into[MetaKey.Author] = it }
  generation?.also { into[MetaKey.Generation] = it.name }

  (this as WritableBookMeta).serializeInto(into, includeBaseMeta)
}
