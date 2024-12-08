@file:JvmName("SuspiciousStewMetaExtensions")
package io.foxcapades.bukkit.inventory.meta

import io.foxcapades.bukkit.potion.toJsonSerializable
import org.bukkit.inventory.meta.SuspiciousStewMeta
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.potion.PotionEffect


private inline val SuspiciousStewMeta.fieldCount
  get() = (this as ItemMeta).fieldCount + 1

fun SuspiciousStewMeta.toJsonSerializable(): Map<String, Any> =
  typedMetaMap(fieldCount, MetaType.SuspiciousStew).also(::serializeInto)

@JvmOverloads
fun SuspiciousStewMeta.serializeInto(into: MutableMap<String, Any>, includeBaseMeta: Boolean = true) {
  customEffects.takeUnless { it.isEmpty() }
    ?.also { into[MetaKey.Effects] = it.map(PotionEffect::toJsonSerializable) }

  if (includeBaseMeta)
    internalSerializeInto(into)
}
