package io.foxcapades.bukkit.utils

internal inline fun <T, reified R> Collection<T>.toSlimArray(
  filter: (T) -> Boolean = { true },
  convert: (T) -> R?,
): Array<R?> {
  val out = arrayOfNulls<R>(size)
  var i = 0

  forEach {
    if (filter(it))
      out[i++] = convert(it)
  }

  return if (i == size) out else out.copyOf(i)
}
