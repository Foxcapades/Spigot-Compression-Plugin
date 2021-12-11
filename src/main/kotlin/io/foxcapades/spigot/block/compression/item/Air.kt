package io.foxcapades.spigot.block.compression.item

import org.bukkit.Material
import org.bukkit.Material.AIR
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.material.MaterialData

object Air: ItemStack(AIR, 0) {
  override fun setType(type: Material) = throw UnsupportedOperationException()

  override fun setAmount(amount: Int) = throw UnsupportedOperationException()

  override fun setData(data: MaterialData?) = throw UnsupportedOperationException()

  override fun setDurability(durability: Short) = throw UnsupportedOperationException()

  override fun addEnchantments(enchantments: MutableMap<Enchantment, Int>) = throw UnsupportedOperationException()

  override fun addEnchantment(ench: Enchantment, level: Int) = throw UnsupportedOperationException()

  override fun addUnsafeEnchantments(enchantments: MutableMap<Enchantment, Int>) = throw UnsupportedOperationException()

  override fun addUnsafeEnchantment(ench: Enchantment, level: Int) = throw UnsupportedOperationException()

  override fun removeEnchantment(ench: Enchantment): Int = throw UnsupportedOperationException()

  override fun setItemMeta(itemMeta: ItemMeta?): Boolean = throw UnsupportedOperationException()
}