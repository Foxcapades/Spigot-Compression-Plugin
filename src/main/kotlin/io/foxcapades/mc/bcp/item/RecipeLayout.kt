package io.foxcapades.mc.bcp.item

@JvmInline
internal value class RecipeLayout private constructor(val bytes: ByteArray) {
  inline val indices get() = bytes.indices

  constructor(configString: String) : this(configString.getBytes())

  operator fun iterator() = iterator {
    for (b in bytes)
      yield(b)
  }

  @Suppress("NOTHING_TO_INLINE")
  inline operator fun get(index: Int) = bytes[index]
}

private fun String.getBytes(): ByteArray {
  val out = ByteArray(9)
  var p = 0

  for (i in indices) {
    val c = get(i)
    if (c.isValid()) {
      if (p == 9)
        throw IllegalArgumentException("recipe layout contained more than 9 item markers!")
      out[p++] = c.code.toByte()
    } else if (!c.isWS()) {
      throw IllegalArgumentException("recipe layout character ${i + 1} is invalid!")
    }
  }

  if (p < 9)
    throw IllegalArgumentException("recipe layouts must contain exactly 9 letters or numbers")

  return out
}

@Suppress("NOTHING_TO_INLINE")
private inline fun Char.isWS() = this == ' ' || this == '\n' || this == '\r' || this == '\t'

@Suppress("NOTHING_TO_INLINE")
private inline fun Char.isValid() = this in '!'..'~'
