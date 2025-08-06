package io.foxcapades.bukkit.szip.unsafe

import net.minecraft.core.IRegistryCustom
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.ItemStack as MinecraftItemStack

import org.bukkit.craftbukkit.v1_21_R2.inventory.CraftItemStack

import org.bukkit.inventory.ItemStack as BukkitItemStack

fun BukkitItemStack.encode(): ByteArray =
  when (this) {
    is CraftItemStack -> this.encode()
    else              -> CraftItemStack.asCraftCopy(this).encode()
  }

fun decodeItemStack(data: ByteArray): BukkitItemStack? =
  CraftItemStack.asBukkitCopy(MinecraftItemStack.a(
    IRegistryCustom.c(listOf(BuiltInRegistries.g)),
    decodeCompoundTag(data)
  ))


internal fun CraftItemStack.encode(): ByteArray =
  CraftItemStack.asNMSCopy(this)
    .a(IRegistryCustom.c(listOf(BuiltInRegistries.g)))
    .encode(512)
