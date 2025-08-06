package io.foxcapades.bukkit.szip.unsafe

import net.minecraft.nbt.NBTReadLimiter
import net.minecraft.nbt.NBTTagCompound

import java.io.ByteArrayInputStream
import java.io.DataInputStream

internal fun decodeCompoundTag(raw: ByteArray): NBTTagCompound =
  NBTTagCompound.b.c(DataInputStream(ByteArrayInputStream(raw)), NBTReadLimiter.a())
