package io.foxcapades.spigot.block.compression.compressible

import io.foxcapades.spigot.block.compression.util.pow

enum class CompressionLevel {
  None,
  One,
  Two,
  Three,
  Four,
  Five,
  Six,
  Seven,
  Eight,
  Nine,
  ;

  val size get() = 9 pow ordinal

  val hasNext get() = this != Nine

  val next
    get() = when (this) {
      None  -> One
      One   -> Two
      Two   -> Three
      Three -> Four
      Four  -> Five
      Five  -> Six
      Six   -> Seven
      Seven -> Eight
      Eight -> Nine
      else  -> throw IndexOutOfBoundsException()
    }

  val hasPrevious get() = this != None

  val previous
    get() = when (this) {
      One   -> None
      Two   -> One
      Three -> Two
      Four  -> Three
      Five  -> Four
      Six   -> Five
      Seven -> Six
      Eight -> Seven
      Nine  -> Eight
      else  -> throw IndexOutOfBoundsException()
    }

  override fun toString() = ordinal.toString()

  companion object {
    @JvmStatic
    @Suppress("NOTHING_TO_INLINE")
    inline fun from(lvl: Byte) = when (lvl) {
      in 0..7 -> values()[lvl.toInt()]
      else    -> throw IllegalArgumentException("Invalid compression level value: $lvl")
    }
  }
}
