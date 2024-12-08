@file:JvmName("SkullMetaExtensions")
package io.foxcapades.bukkit.inventory.meta

import io.foxcapades.bukkit.profile.toJsonSerializable
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.inventory.meta.ItemMeta


private inline val SkullMeta.fieldCount
  get() = (this as ItemMeta).fieldCount + 3

fun SkullMeta.toJsonSerializable(): Map<String, Any> =
  typedMetaMap(fieldCount, MetaType.Skull).also(::serializeInto)

@JvmOverloads
fun SkullMeta.serializeInto(into: MutableMap<String, Any>, includeBaseMeta: Boolean = true) {
  owningPlayer?.also { into[MetaKey.Owner] = it.uniqueId.toString() }
  ownerProfile?.also { into[MetaKey.Profile] = it.toJsonSerializable() }
  noteBlockSound?.also { into[MetaKey.Sound] = it.toString() }

  if (includeBaseMeta)
    internalSerializeInto(into)
}
