@file:Suppress("unused")

package io.foxcapades.spigot.block.compression.test_utils

import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.inventory.ItemFactory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class MockItemFactory : ItemFactory {

  //////////////////////////////////////////////////////////////////////////////

  fun mockEquals(fn: (meta1: ItemMeta?, meta2: ItemMeta?) -> Boolean) {
    _equals = fn
  }

  private var _equals: (ItemMeta?, ItemMeta?) -> Boolean = { _, _ -> TODO("Mock me!") }

  override fun equals(meta1: ItemMeta?, meta2: ItemMeta?) = _equals(meta1, meta2)

  //////////////////////////////////////////////////////////////////////////////

  fun mockGetItemMeta(fn: (material: Material) -> ItemMeta?) {
    _getItemMeta = fn
  }

  private var _getItemMeta: (Material) -> ItemMeta? = { TODO("Mock me!") }

  override fun getItemMeta(material: Material) = _getItemMeta(material)

  //////////////////////////////////////////////////////////////////////////////

  fun mockIsApplicableByStack(fn: (meta: ItemMeta?, stack: ItemStack?) -> Boolean) {
    _isApplicable1 = fn
  }

  private var _isApplicable1: (ItemMeta?, ItemStack?) -> Boolean = { _, _ -> TODO("Mock me!") }

  override fun isApplicable(meta: ItemMeta?, stack: ItemStack?) = _isApplicable1(meta, stack)

  //////////////////////////////////////////////////////////////////////////////

  fun mockIsApplicableByMaterial(fn: (meta: ItemMeta?, material: Material?) -> Boolean) {
    _isApplicable2 = fn
  }

  private var _isApplicable2: (ItemMeta?, Material?) -> Boolean = { _, _ -> TODO("Mock me!") }

  override fun isApplicable(meta: ItemMeta?, material: Material?) = _isApplicable2(meta, material)

  //////////////////////////////////////////////////////////////////////////////

  fun mockAsMetaForStack(fn: (meta: ItemMeta, stack: ItemStack) -> ItemMeta?) {
    _asMetaFor1 = fn
  }

  private var _asMetaFor1: (ItemMeta, stack: ItemStack) -> ItemMeta? = { _, _ -> TODO("Mock me!") }

  override fun asMetaFor(meta: ItemMeta, stack: ItemStack) = _asMetaFor1(meta, stack)

  //////////////////////////////////////////////////////////////////////////////

  fun mockAsMetaForMaterial(fn: (meta: ItemMeta, material: Material) -> ItemMeta?) {
    _asMetaFor2 = fn
  }

  private var _asMetaFor2: (ItemMeta, material: Material) -> ItemMeta? = { _, _ -> TODO("Mock me!") }

  override fun asMetaFor(meta: ItemMeta, material: Material) = _asMetaFor2(meta, material)

  //////////////////////////////////////////////////////////////////////////////

  fun mockGetDefaultLeatherColor(fn: () -> Color) {
    _getDefaultLeatherColor = fn
  }

  private var _getDefaultLeatherColor: () -> Color = { TODO("Mock me!") }

  override fun getDefaultLeatherColor() = _getDefaultLeatherColor()

  //////////////////////////////////////////////////////////////////////////////

  fun mockUpdateMaterial(fn: (meta: ItemMeta, material: Material) -> Material) {
    _updateMaterial = fn
  }

  private var _updateMaterial: (ItemMeta, Material) -> Material = { _, _ -> TODO("Mock me!") }

  override fun updateMaterial(meta: ItemMeta, material: Material) = _updateMaterial(meta, material)
}