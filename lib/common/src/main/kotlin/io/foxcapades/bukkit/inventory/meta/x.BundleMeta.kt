@file:JvmName("BundleMetaExtensions")
package io.foxcapades.bukkit.inventory.meta

import org.bukkit.inventory.meta.BundleMeta
import org.bukkit.inventory.meta.ItemMeta


private inline val BundleMeta.fieldCount
  get() = (this as ItemMeta).fieldCount + 1

fun BundleMeta.toJsonSerializable(): Map<String, Any> =
  typedMetaMap(fieldCount, MetaType.Bundle)

@JvmOverloads
fun BundleMeta.serializeInto(into: MutableMap<String, Any>, includeBaseMeta: Boolean = true) {
  items.takeUnless { it.isEmpty() }
    ?.also { into[MetaKey.Items] = it.map { stack -> stack.toJsonSerializable() } }

  if (includeBaseMeta)
    internalSerializeInto(into)
}


