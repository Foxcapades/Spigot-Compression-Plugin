@file:JvmName("BannerMetaExtensions")
package io.foxcapades.bukkit.inventory.meta

import org.bukkit.inventory.meta.BannerMeta
import org.bukkit.inventory.meta.ItemMeta


internal inline val BannerMeta.fieldCount
  get() = (this as ItemMeta).fieldCount + 1

fun BannerMeta.toJsonSerializable(): Map<String, Any> =
  typedMetaMap(fieldCount, MetaType.Banner).also(::serializeInto)

@JvmOverloads
fun BannerMeta.serializeInto(into: MutableMap<String, Any>, includeBaseMeta: Boolean = true) {
  patterns.takeUnless { it.isEmpty() }
    ?.also { into["patterns"] = it.map { pattern -> listOf(
      pattern.pattern.key,
      pattern.color.color.asARGB()
    ) } }

  if (includeBaseMeta)
    internalSerializeInto(into)
}
