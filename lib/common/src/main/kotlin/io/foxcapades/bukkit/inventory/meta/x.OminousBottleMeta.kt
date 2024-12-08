@file:JvmName("OminousBottleMetaExtensions")
package io.foxcapades.bukkit.inventory.meta

import org.bukkit.inventory.meta.OminousBottleMeta
import org.bukkit.inventory.meta.ItemMeta


private inline val OminousBottleMeta.fieldCount
  get() = (this as ItemMeta).fieldCount + 1

fun OminousBottleMeta.toJsonSerializable(): Map<String, Any> =
  typedMetaMap(fieldCount, MetaType.OminousBottle).also(::serializeInto)

@JvmOverloads
fun OminousBottleMeta.serializeInto(into: MutableMap<String, Any>, includeBaseMeta: Boolean = true) {
  if (hasAmplifier())
    into[MetaKey.Amplifier] = amplifier

  if (includeBaseMeta)
    internalSerializeInto(into)
}
