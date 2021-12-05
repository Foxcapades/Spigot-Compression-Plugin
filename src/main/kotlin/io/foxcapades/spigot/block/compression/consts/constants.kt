@file:Suppress("DEPRECATION")

package io.foxcapades.spigot.block.compression.consts

import org.bukkit.Material
import org.bukkit.Material.AIR
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.material.MaterialData

const val CompressorTitle = "Compression Workbench"

object Air : ItemStack(AIR, 0) {
  override fun getMaxStackSize() = 0
  override fun getAmount() = 0
  override fun getData(): MaterialData? = null
  override fun getEnchantments(): MutableMap<Enchantment, Int> = mutableMapOf()
  override fun clone(): ItemStack = this
  override fun hasItemMeta() = false
  override fun getType(): Material = AIR
  override fun getItemMeta(): ItemMeta? = null
  override fun containsEnchantment(ench: Enchantment) = false
  override fun getEnchantmentLevel(ench: Enchantment) = 0
  override fun setItemMeta(itemMeta: ItemMeta?) = throw UnsupportedOperationException()
  override fun setData(data: MaterialData?) = throw UnsupportedOperationException()
  override fun setType(type: Material) = throw UnsupportedOperationException()
  override fun removeEnchantment(ench: Enchantment) = throw UnsupportedOperationException()
  override fun addUnsafeEnchantments(enchantments: MutableMap<Enchantment, Int>) = throw UnsupportedOperationException()
  override fun addUnsafeEnchantment(ench: Enchantment, level: Int) = throw UnsupportedOperationException()
  override fun setAmount(amount: Int) = throw UnsupportedOperationException()
  override fun addEnchantments(enchantments: MutableMap<Enchantment, Int>) = throw UnsupportedOperationException()
  override fun addEnchantment(ench: Enchantment, level: Int) = throw UnsupportedOperationException()
}

