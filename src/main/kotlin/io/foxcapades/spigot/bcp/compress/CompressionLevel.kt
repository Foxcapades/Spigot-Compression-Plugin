package io.foxcapades.spigot.bcp.compress

@JvmInline
internal value class CompressionLevel(val value: Int) {
  init {
    if (value !in 0..9)
      throw IllegalArgumentException("invalid compression level: $value")
  }

  constructor(value: Byte) : this(value.toInt())

  inline val size
    get() = when (value) {
      1    -> 9
      2    -> 81
      3    -> 729
      4    -> 6_561
      5    -> 59_049
      6    -> 531_441
      7    -> 4_782_969
      8    -> 43_046_721
      9    -> 387_420_489
      else -> 0
    }

  inline val hasNext get() = value < 9

  inline val next get() = CompressionLevel(value+1)

  inline val hasPrevious get() = value > 0

  inline val previous get() = CompressionLevel(value-1)

  override fun toString() = value.toString()

  companion object {
    val Zero = CompressionLevel(0)
  }
}
