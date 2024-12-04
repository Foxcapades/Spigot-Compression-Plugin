package io.foxcapades.spigot.block.compression.item

private val ws = Regex("\\s+")

@JvmInline
internal value class RecipeLayout(val bytes: ByteArray) {
  inline val indices get() = bytes.indices

  init {
    if (bytes.size != 9) {
      throw IllegalArgumentException("recipe layouts must contain exactly 9 letters or numbers")
    }
  }

  constructor(configString: String) : this(ws.replace(configString, "").toByteArray())

  operator fun iterator() = iterator {
    for (b in bytes)
      yield(b)
  }

  @Suppress("NOTHING_TO_INLINE")
  inline operator fun get(index: Int) = bytes[index]
}
