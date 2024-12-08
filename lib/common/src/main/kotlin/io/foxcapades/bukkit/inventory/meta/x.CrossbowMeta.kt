@file:JvmName("CrossbowMetaExtensions")
package io.foxcapades.bukkit.inventory.meta

import org.bukkit.inventory.meta.CrossbowMeta
import org.bukkit.inventory.meta.ItemMeta


private inline val CrossbowMeta.fieldCount
  get() = (this as ItemMeta).fieldCount + 1

fun CrossbowMeta.toJsonSerializable(): Map<String, Any> =
  typedMetaMap(fieldCount, MetaType.Crossbow).also(::serializeInto)

@JvmOverloads
fun CrossbowMeta.serializeInto(into: MutableMap<String, Any>, includeBaseMeta: Boolean = true) {
  chargedProjectiles.takeUnless { it.isEmpty() }
    ?.also { into[MetaKey.Projectiles] = it.map { stack -> stack.toJsonSerializable() } }

  if (includeBaseMeta)
    internalSerializeInto(into)
}
