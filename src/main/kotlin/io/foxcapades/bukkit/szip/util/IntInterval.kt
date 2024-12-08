package io.foxcapades.bukkit.szip.util

internal data class IntInterval(val startInclusive: Int, val endInclusive: Int) {
  @Suppress("NOTHING_TO_INLINE")
  internal inline fun toProgression(reverse: Boolean = false) =
    when {
      reverse -> IntProgression.fromClosedRange(endInclusive, startInclusive, -1)
      else    -> IntProgression.fromClosedRange(startInclusive, endInclusive, 1)
    }
}
