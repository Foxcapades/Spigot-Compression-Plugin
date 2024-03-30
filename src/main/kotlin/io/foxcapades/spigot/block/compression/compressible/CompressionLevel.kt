package io.foxcapades.spigot.block.compression.compressible

import io.foxcapades.spigot.block.compression.util.pow

@JvmInline
internal value class CompressionLevel(val value: Int) {
  init {
    if (value !in 0..9)
      throw IllegalArgumentException("invalid compression level: $value")
  }

  constructor(value: Byte) : this(value.toInt())

  inline val size get() = 9 pow value

  inline val hasNext get() = value < 9

  inline val next get() = CompressionLevel(value+1)

  inline val hasPrevious get() = value > 0

  inline val previous get() = CompressionLevel(value-1)

  override fun toString() = value.toString()

  companion object {
    val None = CompressionLevel(0)
    val One = CompressionLevel(1)
    val Two = CompressionLevel(2)
    val Three = CompressionLevel(3)
    val Four = CompressionLevel(4)
    val Five = CompressionLevel(5)
    val Six = CompressionLevel(6)
    val Seven = CompressionLevel(7)
    val Eight = CompressionLevel(8)
    val Nine = CompressionLevel(9)
  }
}
