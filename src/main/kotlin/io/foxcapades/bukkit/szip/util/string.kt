package io.foxcapades.bukkit.szip.util

internal fun String.isOneOf(vararg options: String) = options.any { it == this }

@Suppress("NOTHING_TO_INLINE")
internal inline operator fun CharSequence.get(startInc: Int, endExc: Int) =
  substring(startInc, endExc)
