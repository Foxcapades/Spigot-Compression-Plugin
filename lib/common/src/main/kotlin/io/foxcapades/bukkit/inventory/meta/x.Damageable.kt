@file:JvmName("DamageableExtensions")
package io.foxcapades.bukkit.inventory.meta

import org.bukkit.inventory.meta.Damageable
import org.bukkit.inventory.meta.ItemMeta


private inline val Damageable.fieldCount
  get() = (this as ItemMeta).fieldCount + 2

fun Damageable.toJsonSerializable(): Map<String, Any> =
  typedMetaMap(fieldCount, MetaType.Damageable).also(::serializeInto)

@JvmOverloads
fun Damageable.serializeInto(into: MutableMap<String, Any>, includeBaseMeta: Boolean = true) {
  if (hasDamage())
    into[MetaKey.Damage] = damage

  if (hasMaxDamage())
    into[MetaKey.MaxDamage] = maxDamage

  if (includeBaseMeta)
    internalSerializeInto(into)
}
