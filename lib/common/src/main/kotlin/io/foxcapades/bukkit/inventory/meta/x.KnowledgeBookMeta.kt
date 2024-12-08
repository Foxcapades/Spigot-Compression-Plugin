@file:JvmName("KnowledgeBookMetaExtensions")
package io.foxcapades.bukkit.inventory.meta

import org.bukkit.inventory.meta.KnowledgeBookMeta
import org.bukkit.inventory.meta.ItemMeta


private inline val KnowledgeBookMeta.fieldCount
  get() = (this as ItemMeta).fieldCount + 1

fun KnowledgeBookMeta.toJsonSerializable(): Map<String, Any> =
  typedMetaMap(fieldCount, MetaType.KnowledgeBook).also(::serializeInto)

@JvmOverloads
fun KnowledgeBookMeta.serializeInto(into: MutableMap<String, Any>, includeBaseMeta: Boolean = true) {
  recipes.takeUnless { it.isEmpty() }?.also { into[MetaKey.Recipes] = it.map(Any::toString) }

  if (includeBaseMeta)
    internalSerializeInto(into)
}
