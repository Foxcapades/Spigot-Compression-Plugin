package io.foxcapades.spigot.block.compression.compressible

import io.foxcapades.spigot.block.compression.util.pow

enum class CompressionLevel(val value: Int) {
  None(0),
  One(1),
  Two(2),
  Three(3),
  Four(4),
  Five(5),
  Six(6),
  Seven(7),
  Eight(8),
  Nine(9),
  ;

  val size get() = 9 pow value

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

  override fun toString() = value.toString()

  companion object {
    @JvmStatic
    @Suppress("NOTHING_TO_INLINE")
    inline fun from(lvl: Byte) = when (lvl) {
      in 0..9 -> values()[lvl.toInt()]
      else    -> throw IllegalArgumentException("Invalid compression level value: $lvl")
    }
  }
}
