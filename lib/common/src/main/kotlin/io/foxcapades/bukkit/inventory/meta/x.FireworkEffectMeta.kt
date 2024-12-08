@file:JvmName("FireworkEffectMetaExtensions")
package io.foxcapades.bukkit.inventory.meta

import io.foxcapades.bukkit.toJsonSerializable
import org.bukkit.inventory.meta.FireworkEffectMeta
import org.bukkit.inventory.meta.ItemMeta


private inline val FireworkEffectMeta.fieldCount
  get() = (this as ItemMeta).fieldCount + 1

fun FireworkEffectMeta.toJsonSerializable(): Map<String, Any> =
  typedMetaMap(fieldCount, MetaType.FireworkEffect).also(::serializeInto)

@JvmOverloads
fun FireworkEffectMeta.serializeInto(into: MutableMap<String, Any>, includeBaseMeta: Boolean = true) {
  effect?.also { into[MetaKey.Effect] = it.toJsonSerializable() }

  if (includeBaseMeta)
    internalSerializeInto(into)
}
