package io.foxcapades.bukkit.szip.util

internal infix fun Int.pow(by: Int): Int {
  return when {
    by == 0   -> 1
    by.isEven -> pow(by / 2).let { it * it }
    else      -> pow(by / 2).let { this * it * it }
  }
}

internal inline val Int.isEven
  get() = this % 2 == 0
