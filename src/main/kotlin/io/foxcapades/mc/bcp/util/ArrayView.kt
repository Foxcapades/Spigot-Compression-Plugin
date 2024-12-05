package io.foxcapades.mc.bcp.util

internal class ArrayView<T>(
  private val raw: Array<T>,
  private val start: Int,
  endExc: Int = raw.size,
) {
  val size = endExc - start

  inline val lastIndex
    get() = size - 1

  inline val indices
    get() = IntRange(0, size)

  inline val last
    get() = get(lastIndex)

  operator fun get(index: Int): T = when {
    index < 0 || index >= size -> throw IndexOutOfBoundsException()
    else -> raw[index + start]
  }

  operator fun set(index: Int, value: T) = when {
    index < 0 || index >= size -> throw IndexOutOfBoundsException()
    else -> raw[index + start] = value
  }

  operator fun iterator() = iterator {
    for (i in indices)
      yield(get(i))
  }
}
