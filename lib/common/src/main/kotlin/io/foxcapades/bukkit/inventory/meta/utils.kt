package io.foxcapades.bukkit.inventory.meta

internal fun typedMetaMap(size: Int, type: MetaType): MutableMap<String, Any> =
  HashMap<String, Any>(size + 1).apply { put(MetaKey.MetaType, type.value) }
