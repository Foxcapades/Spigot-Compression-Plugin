package io.foxcapades.bukkit.szip.ext

import org.bukkit.Material.*
import org.bukkit.inventory.ItemStack

internal inline val ItemStack.logName
  get() = itemMeta?.takeIf { it.hasDisplayName() }?.displayName ?: type.name

internal inline val ItemStack?.isProjectileWeapon
  get() = this != null && (type == BOW || type == CROSSBOW)

internal inline val ItemStack?.isAmmo
  get() = this != null && (type == ARROW || type == TIPPED_ARROW || type == SPECTRAL_ARROW)

internal var ItemStack?.size: Int
  get()  = this?.amount ?: 0
  set(v) { this?.apply { amount = v } }
