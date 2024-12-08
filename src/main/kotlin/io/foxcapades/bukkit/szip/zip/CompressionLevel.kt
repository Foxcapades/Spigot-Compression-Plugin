package io.foxcapades.bukkit.szip.zip

import io.foxcapades.bukkit.szip.util.pow
import org.bukkit.inventory.ItemStack

/**
 * Item stack compression level.
 *
 * Defines a new-type to ensure compression level validity.
 *
 * @constructor Attempts to create a new `CompressionLevel` instance from the
 * given int value.
 *
 * @param value Compression level value.
 *
 * @throws InvalidCompressionLevelException If the given value is less than
 * [MinimumCompressionLevel] or is greater than [MaximumCompressionLevel].
 */
@JvmInline
internal value class CompressionLevel(val value: Int) {
  init {
    if (value !in MinimumCompressionLevel ..< MaximumCompressionLevel)
      throw InvalidCompressionLevelException(IllegalArgumentException("invalid compression level $value, must be in the range [$MinimumCompressionLevel-$MaximumCompressionLevel]"))
  }

  /**
   * Attempts to create a new `CompressionLevel` instance from the given byte
   * value.
   *
   * @param value Compression level value.
   *
   * @throws InvalidCompressionLevelException If the given value is less than
   * [MinimumCompressionLevel] or is greater than [MaximumCompressionLevel].
   */
  constructor(value: Byte) : this(value.toInt())

  /**
   * The count of items represented by a stack at this compression level.
   */
  inline val count
    get() = when (value) {
      MinimumCompressionLevel -> 0
      else    -> sizes[value-1]
    }

  /**
   * Indicates whether there is a possible higher level of compression.
   */
  inline val hasNext
    get() = value < MaximumCompressionLevel

  /**
   * Returns the next (higher) compression level.
   *
   * @throws InvalidCompressionLevelException if this compression level is
   * already at the maximum compression level.  Use [hasNext] to test whether it
   * is safe to get this value.
   */
  inline val next
    get() = when {
      hasNext -> CompressionLevel(value+1)
      else    -> throw InvalidCompressionLevelException("cannot compress an item stack more than $MaximumCompressionLevel times")
    }

  /**
   * Indicates whether there is a possible lower level of compression.
   */
  inline val hasPrevious
    get() = value > MinimumCompressionLevel

  /**
   * Returns the previous (lower) compression level.
   *
   * @throws InvalidCompressionLevelException if this compression level is
   * already at the minimum compression level.  Use [hasPrevious] to test
   * whether it is safe to get this value.
   */
  inline val previous
    get() = when {
      hasPrevious -> CompressionLevel(value-1)
      else        -> throw InvalidCompressionLevelException("cannot decompress an item stack that isn't compressed")
    }

  @Suppress("NOTHING_TO_INLINE")
  inline operator fun compareTo(rhs: CompressionLevel) = value.compareTo(rhs.value)

  override fun toString() = value.toString()

  companion object {
    const val MinimumCompressionLevel = 0
    const val MaximumCompressionLevel = 9

    private const val Ingredients = 9

    private val sizes = IntArray(MaximumCompressionLevel) { Ingredients pow (it+1) }

    /**
     * Compression level zero, a.k.a. uncompressed.
     *
     * This value shouldn't be written to [ItemStack] metadata as the absence of
     * a compression level is equivalent to compression level zero.
     */
    val Zero = CompressionLevel(MinimumCompressionLevel)
  }
}

internal class InvalidCompressionLevelException : Exception {
  constructor(msg: String) : super(msg)
  constructor(cause: Throwable) : super(cause)
}
