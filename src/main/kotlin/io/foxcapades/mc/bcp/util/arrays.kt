package io.foxcapades.mc.bcp.util

@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
internal inline fun <T> Array<T>.append(value: T) =
  copyOf(size+1).also { it[size] = value } as Array<T>
