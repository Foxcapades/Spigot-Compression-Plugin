@file:JvmName("FireworkMetaExtensions")
package io.foxcapades.bukkit.inventory.meta

import io.foxcapades.bukkit.toJsonSerializable
import org.bukkit.FireworkEffect
import org.bukkit.inventory.meta.FireworkMeta
import org.bukkit.inventory.meta.ItemMeta


private inline val FireworkMeta.fieldCount
  get() = (this as ItemMeta).fieldCount + 2

fun FireworkMeta.toJsonSerializable(): Map<String, Any> =
  typedMetaMap(fieldCount, MetaType.Firework).also(::serializeInto)

@JvmOverloads
fun FireworkMeta.serializeInto(into: MutableMap<String, Any>, includeBaseMeta: Boolean = true) {
  effects.takeUnless { it.isEmpty() }?.also { into[MetaKey.Effects] = it.map(FireworkEffect::toJsonSerializable) }

  if (hasPower())
    into[MetaKey.Power] = power

  if (includeBaseMeta)
    internalSerializeInto(into)
}
