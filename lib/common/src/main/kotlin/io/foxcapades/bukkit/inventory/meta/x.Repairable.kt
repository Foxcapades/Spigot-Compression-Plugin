@file:JvmName("RepairableExtensions")
package io.foxcapades.bukkit.inventory.meta

import org.bukkit.inventory.meta.Repairable
import org.bukkit.inventory.meta.ItemMeta


private inline val Repairable.fieldCount
  get() = (this as ItemMeta).fieldCount + 1

fun Repairable.toJsonSerializable(): Map<String, Any> =
  typedMetaMap(fieldCount, MetaType.Repairable).also(::serializeInto)

@JvmOverloads
fun Repairable.serializeInto(into: MutableMap<String, Any>, includeBaseMeta: Boolean = true) {
  if (hasRepairCost())
    into[MetaKey.RepairCost] = repairCost

  if (includeBaseMeta)
    internalSerializeInto(into)
}
