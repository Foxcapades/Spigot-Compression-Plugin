@file:JvmName("EnchantmentExtensions")
package io.foxcapades.bukkit.enchantments

import org.bukkit.enchantments.Enchantment

fun Enchantment.toSlimSerializable(): Any = key.toString()
