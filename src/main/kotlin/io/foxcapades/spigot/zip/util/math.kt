package io.foxcapades.spigot.zip.util

infix fun Int.pow(exp: Int): Int = when {
  exp < 0  -> throw IllegalArgumentException()
  exp == 0 -> 1
  exp == 1 -> this
  else     -> {
    var tmp = 1

    for (i in 1..exp)
      tmp *= this

    tmp
  }
}
