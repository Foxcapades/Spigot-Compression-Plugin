@file:JvmName("AxolotlBucketMetaExtensions")
package io.foxcapades.bukkit.inventory.meta

import org.bukkit.inventory.meta.AxolotlBucketMeta
import org.bukkit.inventory.meta.ItemMeta


private inline val AxolotlBucketMeta.fieldCount
  get() = (this as ItemMeta).fieldCount + 1

fun AxolotlBucketMeta.toJsonSerializable(): Map<String, Any> =
  typedMetaMap(fieldCount, MetaType.Axolotl).also(::serializeInto)

@JvmOverloads
fun AxolotlBucketMeta.serializeInto(into: MutableMap<String, Any>, includeBaseMeta: Boolean = true) {
  if (hasVariant())
    into[MetaKey.Variant] = variant.name

  if (includeBaseMeta)
    internalSerializeInto(into)
}
