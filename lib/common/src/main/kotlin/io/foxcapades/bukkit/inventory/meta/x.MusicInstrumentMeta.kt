@file:JvmName("MusicInstrumentMetaExtensions")
package io.foxcapades.bukkit.inventory.meta

import org.bukkit.inventory.meta.MusicInstrumentMeta
import org.bukkit.inventory.meta.ItemMeta


private inline val MusicInstrumentMeta.fieldCount
  get() = (this as ItemMeta).fieldCount + 1

fun MusicInstrumentMeta.toJsonSerializable(): Map<String, Any> =
  typedMetaMap(fieldCount, MetaType.MusicInstrument).also(::serializeInto)

@JvmOverloads
fun MusicInstrumentMeta.serializeInto(into: MutableMap<String, Any>, includeBaseMeta: Boolean = true) {
  instrument?.also { into[MetaKey.Instrument] = it.key.toString() }

  if (includeBaseMeta)
    internalSerializeInto(into)
}
