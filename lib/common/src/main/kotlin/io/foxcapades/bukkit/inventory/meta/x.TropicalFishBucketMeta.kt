@file:JvmName("TropicalFishBucketMetaExtensions")
package io.foxcapades.bukkit.inventory.meta

import org.bukkit.inventory.meta.TropicalFishBucketMeta
import org.bukkit.inventory.meta.ItemMeta


private inline val TropicalFishBucketMeta.fieldCount
  get() = (this as ItemMeta).fieldCount + 2

fun TropicalFishBucketMeta.toJsonSerializable(): Map<String, Any> =
  typedMetaMap(fieldCount, MetaType.TropicalFish).also(::serializeInto)

@JvmOverloads
fun TropicalFishBucketMeta.serializeInto(into: MutableMap<String, Any>, includeBaseMeta: Boolean = true) {
  if (hasVariant()) {
    into[MetaKey.Color] = listOf(bodyColor.color.asARGB(), patternColor.color.asARGB())
    into[MetaKey.Variant] = pattern.name
  }

  if (includeBaseMeta)
    internalSerializeInto(into)
}
