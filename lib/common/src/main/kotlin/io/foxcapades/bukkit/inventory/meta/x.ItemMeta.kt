@file:JvmName("ItemMetaExtensions")
package io.foxcapades.bukkit.inventory.meta

import com.google.common.collect.Multimap
import io.foxcapades.bukkit.attribute.toSlimSerializable
import io.foxcapades.bukkit.enchantments.toSlimSerializable
import io.foxcapades.bukkit.inventory.meta.components.toJsonSerializable
import io.foxcapades.bukkit.utils.toSlimArray
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.meta.ItemMeta

internal inline val ItemMeta.fieldCount
  get() = 18

fun ItemMeta.toJsonSerializable(): Map<String, Any> =
  HashMap<String, Any>(fieldCount).also(::internalSerializeInto)

fun ItemMeta.serializeInto(into: MutableMap<String, Any>) {
  internalSerializeInto(into)
}

private const val CurrentVersion = 1

internal fun ItemMeta.internalSerialize(): Array<Any?> {
  return arrayOf(
    CurrentVersion,

    if (hasDisplayName()) displayName else null,

    if (hasItemName()) itemName else null,

    if (hasLore()) lore?.takeUnless { it.isEmpty() } ?.also { it.toTypedArray() } else null,

    if (hasCustomModelData()) customModelData else null,

    if (hasEnchants())
      (enchants as Map<Enchantment, Int>).takeUnless { it.isEmpty() }
        ?.also { it.toSlimArray(
          { _, lvl -> lvl > 0 },
          { en, lvl -> arrayOf(en.toSlimSerializable(), lvl) }
        ) } ?: emptyArray<Any>()
    else
      emptyArray<Any>(),

    itemFlags.takeUnless { it.isEmpty() }
      ?.toSlimArray(convert = ItemFlag::name)
      ?: emptyArray<Any>(),

    isHideTooltip,

    isUnbreakable,

    hasEnchantmentGlintOverride(),

    isFireResistant,

    if (hasMaxStackSize()) maxStackSize else -1,

    if (hasRarity()) rarity.name else null,

    if (hasFood()) food.toJsonSerializable() else null,

    if (hasTool()) tool.toJsonSerializable() else null,

    jukeboxPlayable?.toJsonSerializable(),

    (attributeModifiers as Multimap<Attribute, AttributeModifier>?)?.takeUnless { it.isEmpty }
      ?.toSlimArray(
        { _, mods -> mods.isNotEmpty() },
        { attr, mods -> arrayOf(attr.key.toString(), mods.toSlimArray { mod -> mod.toSlimSerializable() }) }
      ) ?: emptyArray<Any>(),

    persistentDataContainer.takeUnless { it.isEmpty }
      ?.
  )
}
