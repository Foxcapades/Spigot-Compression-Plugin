package io.foxcapades.bukkit.szip.unsafe

import net.minecraft.nbt.NBTBase

fun NBTBase.encode(initialBufferSize: Int = 128): ByteArray =
  withDataOutput(initialBufferSize) { a(it) }
