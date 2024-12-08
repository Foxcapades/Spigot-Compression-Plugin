@file:JvmName("EnchantmentStorageMetaExtensions")
package io.foxcapades.bukkit.inventory.meta

import org.bukkit.inventory.meta.EnchantmentStorageMeta
import org.bukkit.inventory.meta.ItemMeta


private inline val EnchantmentStorageMeta.fieldCount
  get() = (this as ItemMeta).fieldCount + 1

fun EnchantmentStorageMeta.toJsonSerializable(): Map<String, Any> =
  typedMetaMap(fieldCount, MetaType.EnchantmentStorage).also(::serializeInto)

@JvmOverloads
fun EnchantmentStorageMeta.serializeInto(into: MutableMap<String, Any>, includeBaseMeta: Boolean = true) {
  storedEnchants.takeUnless { it.isEmpty() }
    ?.also { into[MetaKey.Enchantments] = it.map { (en, lvl) -> listOf(en.key.toString(), lvl) } }

  if (includeBaseMeta)
    internalSerializeInto(into)
}
