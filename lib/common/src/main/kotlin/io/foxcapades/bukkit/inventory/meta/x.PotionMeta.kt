@file:JvmName("PotionMetaExtensions")
package io.foxcapades.bukkit.inventory.meta

import io.foxcapades.bukkit.potion.toJsonSerializable
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.potion.PotionEffect


private inline val PotionMeta.fieldCount
  get() = (this as ItemMeta).fieldCount + 3

fun PotionMeta.toJsonSerializable(): Map<String, Any> =
  typedMetaMap(fieldCount, MetaType.Potion).also(::serializeInto)

@JvmOverloads
fun PotionMeta.serializeInto(into: MutableMap<String, Any>, includeBaseMeta: Boolean = true) {
  if (hasBasePotionType())
    into[MetaKey.BaseType] = basePotionType!!.key.toString()

  if (hasCustomEffects())
    customEffects.takeUnless { it.isEmpty() }
      ?.also { effects -> into[MetaKey.Effects] = effects.map(PotionEffect::toJsonSerializable) }

  if (hasColor())
    into[MetaKey.Color] = color!!.asARGB()

  if (includeBaseMeta)
    internalSerializeInto(into)
}
