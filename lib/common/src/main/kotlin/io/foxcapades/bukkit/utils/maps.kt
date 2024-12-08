package io.foxcapades.bukkit.utils

import com.google.common.collect.Multimap

internal inline fun <K, V, reified R> Map<K, V>.toSlimArray(
  filter: (K, V) -> Boolean = { _, _ -> true },
  convert: (K, V) -> R?
): Array<R?> {
  val out = arrayOfNulls<R?>(size)
  var i = 0

  forEach { (k, v) ->
    if (filter(k, v))
      out[i++] = convert(k, v)
  }

  return if (i == size) out else out.copyOf(i)
}

internal inline fun <K, V, reified R> Multimap<K, V>.toSlimArray(
  filter: (K, Collection<V>) -> Boolean = { _, _ -> true },
  convert: (K, Collection<V>) -> R?
): Array<R?> {
  val map = asMap()

  val out = arrayOfNulls<R?>(map.size)
  var i = 0

  map.forEach { (k, v) ->
    if (filter(k, v))
      out[i++] = convert(k, v)
  }

  return if (i == map.size) out else out.copyOf(i)
}
