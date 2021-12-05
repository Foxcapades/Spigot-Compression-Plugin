@file:Suppress("unused", "UNCHECKED_CAST")

package io.foxcapades.spigot.block.compression.test_utils

import com.google.common.collect.Multimap
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.tags.CustomItemTagContainer
import org.bukkit.persistence.PersistentDataContainer

open class MockItemMeta : ItemMeta {

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var _clone: () -> ItemMeta = { TODO("Mock me!") }
  open fun mockItemMetaClone(fn: () -> ItemMeta) { _clone = fn }
  override fun clone() = _clone()

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var _serialize: () -> MutableMap<String, Any> = { TODO("Mock me!") }
  fun mockSerialize(fn: () -> MutableMap<String, Any>) { _serialize = fn }
  override fun serialize() = _serialize()

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var _getPersistentDataContainer: () -> PersistentDataContainer = { TODO("Mock me!") }
  fun mockGetPersistentDataContainer(fn: () -> PersistentDataContainer) { _getPersistentDataContainer = fn }
  override fun getPersistentDataContainer() = _getPersistentDataContainer()

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var _hasDisplayName: () -> Boolean = { TODO("Mock me!") }
  fun mockHasDisplayName(fn: () -> Boolean) { _hasDisplayName = fn }
  override fun hasDisplayName() = _hasDisplayName()

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var _getDisplayName: () -> String = { TODO("Mock me!") }
  fun mockGetDisplayName(fn: () -> String) { _getDisplayName = fn }
  override fun getDisplayName() = _getDisplayName()

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var  _setDisplayName: (String?) -> Unit = { TODO("Mock me!") }
  fun mockSetDisplayName(fn: (name: String?) -> Unit) { _setDisplayName = fn }
  override fun setDisplayName(name: String?) = _setDisplayName(name)

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var _hasLocalizedName: () -> Boolean = { TODO("Mock me!") }
  fun mockHasLocalizedName(fn: () -> Boolean) { _hasLocalizedName = fn }
  override fun hasLocalizedName() = _hasLocalizedName()

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var _getLocalizedName: () -> String = { TODO("Mock me!") }
  fun mockGetLocalizedName(fn: () -> String) { _getLocalizedName = fn }
  override fun getLocalizedName() = _getLocalizedName()

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var  _setLocalizedName: (String?) -> Unit = { TODO("Mock me!") }
  fun mockSetLocalizedName(fn: (name: String?) -> Unit) { _setLocalizedName = fn }
  override fun setLocalizedName(name: String?) = _setLocalizedName(name)

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var _hasLore: () -> Boolean = { TODO("Mock me!") }
  fun mockHasLore(fn: () -> Boolean) { _hasLore = fn }
  override fun hasLore() = _hasLore()

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var _getLore: () -> MutableList<String>? = { TODO("Mock me!") }
  fun mockGetLore(fn: () -> MutableList<String>?) { _getLore = fn }
  override fun getLore() = _getLore()

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var  _setLore: (MutableList<String>?) -> Unit = { TODO("Mock me!") }
  fun mockSetLore(fn: (lore: MutableList<String>?) -> Unit) { _setLore = fn }
  override fun setLore(lore: MutableList<String>?) = _setLore(lore)

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var _hasCustomModelData: () -> Boolean = { TODO("Mock me!") }
  fun mockHasCustomModelData(fn: () -> Boolean) { _hasCustomModelData = fn }
  override fun hasCustomModelData() = _hasCustomModelData()

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var _getCustomModelData: () -> Int = { TODO("Mock me!") }
  fun mockGetCustomModelData(fn: () -> Int) { _getCustomModelData = fn }
  override fun getCustomModelData() = _getCustomModelData()

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var  _setCustomModelData: (Int?) -> Unit = { TODO("Mock me!") }
  fun mockSetCustomModelData(fn: (data: Int?) -> Unit) { _setCustomModelData = fn }
  override fun setCustomModelData(data: Int?) = _setCustomModelData(data)

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var _hasEnchants: () -> Boolean = { TODO("Mock me!") }
  fun mockHasEnchants(fn: () -> Boolean) { _hasEnchants = fn }
  override fun hasEnchants() = _hasEnchants()

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var  _hasEnchant: (Enchantment) -> Boolean = { TODO("Mock me!") }
  fun mockHasEnchant(fn: (ench: Enchantment) -> Boolean) { _hasEnchant = fn }
  override fun hasEnchant(ench: Enchantment) = _hasEnchant(ench)

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var  _getEnchantLevel: (Enchantment) -> Int = { TODO("Mock me!") }
  fun mockGetEnchantLevel(fn: (ench: Enchantment) -> Int) { _getEnchantLevel = fn }
  override fun getEnchantLevel(ench: Enchantment) = _getEnchantLevel(ench)

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var _getEnchants: () -> MutableMap<Enchantment, Int> = { TODO("Mock me!") }
  fun mockGetEnchants(fn: () -> MutableMap<Enchantment, Int>) { _getEnchants = fn }
  override fun getEnchants() = _getEnchants()

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var  _addEnchant: (Enchantment, Int, Boolean) -> Boolean = { _, _, _ -> TODO("Mock me!") }
  fun mockAddEnchant(fn: (ench: Enchantment, level: Int, ignoreLevelRestriction: Boolean) -> Boolean) { _addEnchant = fn }
  override fun addEnchant(ench: Enchantment, level: Int, ignoreLevelRestriction: Boolean) = _addEnchant(ench, level, ignoreLevelRestriction)

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var  _removeEnchant: (Enchantment) -> Boolean = { TODO("Mock me!") }
  fun mockRemoveEnchant(fn: (ench: Enchantment) -> Boolean) { _removeEnchant = fn }
  override fun removeEnchant(ench: Enchantment) = _removeEnchant(ench)

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var  _hasConflictingEnchant: (Enchantment) -> Boolean = { TODO("Mock me!") }
  fun mockHasConflictingEnchant(fn: (ench: Enchantment) -> Boolean) { _hasConflictingEnchant = fn }
  override fun hasConflictingEnchant(ench: Enchantment) = _hasConflictingEnchant(ench)

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var _addItemFlags: (Array<out ItemFlag?>) -> Unit = { TODO("Mock me!") }
  fun mockAddItemFlags(fn: (itemFlags: Array<out ItemFlag?>) -> Unit) { _addItemFlags = fn }
  override fun addItemFlags(vararg itemFlags: ItemFlag?) = _addItemFlags(itemFlags)

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var _removeItemFlags: (Array<out ItemFlag?>) -> Unit = { TODO("Mock me!") }
  fun mockRemoveItemFlags(fn: (Array<out ItemFlag?>) -> Unit) { _removeItemFlags = fn}
  override fun removeItemFlags(vararg itemFlags: ItemFlag?)  = _removeItemFlags(itemFlags)

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var _getItemFlags: () -> MutableSet<ItemFlag> = { TODO("Mock me!") }
  fun mockGetItemFlags(fn: () -> MutableSet<ItemFlag>) { _getItemFlags = fn }
  override fun getItemFlags() = _getItemFlags()

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var  _hasItemFlag: (ItemFlag) -> Boolean = { TODO("Mock me!") }
  fun mockHasItemFlag(fn: (flag: ItemFlag) -> Boolean) { _hasItemFlag = fn }
  override fun hasItemFlag(flag: ItemFlag) = _hasItemFlag(flag)

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var _isUnbreakable: () -> Boolean = { TODO("Mock me!") }
  fun mockIsUnbreakable(fn: () -> Boolean) { _isUnbreakable = fn }
  override fun isUnbreakable() = _isUnbreakable()

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var  _setUnbreakable: (Boolean) -> Unit = { TODO("Mock me!") }
  fun mockSetUnbreakable(fn: (unbreakable: Boolean) -> Unit) { _setUnbreakable = fn }
  override fun setUnbreakable(unbreakable: Boolean) = _setUnbreakable(unbreakable)

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var _hasAttributeModifiers: () -> Boolean = { TODO("Mock me!") }
  fun mockHasAttributeModifiers(fn: () -> Boolean) { _hasAttributeModifiers = fn }
  override fun hasAttributeModifiers() = _hasAttributeModifiers()

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var _getAttributeModifiers: () -> Multimap<Attribute, AttributeModifier>? = { TODO("Mock me!") }
  fun mockGetAttributeModifiers(fn: () -> Multimap<Attribute, AttributeModifier>?) { _getAttributeModifiers = fn }
  override fun getAttributeModifiers() = _getAttributeModifiers()

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var  _getAttributeModifiers1: (EquipmentSlot) -> Multimap<Attribute, AttributeModifier> = { TODO("Mock me!") }
  fun mockGetAttributeModifiersByEquipmentSlot(fn: (slot: EquipmentSlot) -> Multimap<Attribute, AttributeModifier>) { _getAttributeModifiers1 = fn }
  override fun getAttributeModifiers(slot: EquipmentSlot) = _getAttributeModifiers1(slot)

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var  _getAttributeModifiers2: (Attribute) -> MutableCollection<AttributeModifier>? = { TODO("Mock me!") }
  fun mockGetAttributeModifiersByAttribute(fn: (attribute: Attribute) -> MutableCollection<AttributeModifier>?) { _getAttributeModifiers2 = fn }
  override fun getAttributeModifiers(attribute: Attribute) = _getAttributeModifiers2(attribute)

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var  _addAttributeModifier: (Attribute, AttributeModifier) -> Boolean = { _, _ -> TODO("Mock me!") }
  fun mockAddAttributeModifier(fn: (attribute: Attribute, modifier: AttributeModifier) -> Boolean) { _addAttributeModifier = fn }
  override fun addAttributeModifier(attribute: Attribute, modifier: AttributeModifier) = _addAttributeModifier(attribute, modifier)

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  fun mockSetAttributeModifiers(fn: (attributeModifiers: Multimap<Attribute, AttributeModifier>?) -> Unit) { _setAttributeModifiers = fn }
  private var  _setAttributeModifiers: (Multimap<Attribute, AttributeModifier>?) -> Unit = { TODO("Mock me!") }
  override fun setAttributeModifiers(attributeModifiers: Multimap<Attribute, AttributeModifier>?) = _setAttributeModifiers(attributeModifiers)

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var  _removeAttributeModifier1: (Attribute) -> Boolean = { TODO("Mock me!") }
  fun mockRemoveAttributeModifierByAttribute(fn: (attribute: Attribute) -> Boolean) { _removeAttributeModifier1 = fn }
  override fun removeAttributeModifier(attribute: Attribute) = _removeAttributeModifier1(attribute)

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var  _removeAttributeModifier2: (EquipmentSlot) -> Boolean = { TODO("Mock me!") }
  fun mockRemoveAttributeModifierByEquipmentSlot(fn: (slot: EquipmentSlot) -> Boolean) { _removeAttributeModifier2 = fn }
  override fun removeAttributeModifier(slot: EquipmentSlot) = _removeAttributeModifier2(slot)

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var  _removeAttributeModifier3: (Attribute, AttributeModifier) -> Boolean = { _, _ -> TODO("Mock me!") }
  fun mockRemoveAttributeModifier(fn: (attribute: Attribute, modifier: AttributeModifier) -> Boolean) { _removeAttributeModifier3 = fn }
  override fun removeAttributeModifier(attribute: Attribute, modifier: AttributeModifier) = _removeAttributeModifier3(attribute, modifier)

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var _getCustomTagContainer: () -> CustomItemTagContainer = { TODO("Mock me!") }
  fun mockGetCustomTagContainer(fn: () -> CustomItemTagContainer) { _getCustomTagContainer = fn }
  override fun getCustomTagContainer() = _getCustomTagContainer()

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private var  _setVersion: (Int) -> Unit = { TODO("Mock me!") }
  fun mockSetVersion(fn: (version: Int) -> Unit) { _setVersion = fn }
  override fun setVersion(version: Int) = _setVersion(version)
}