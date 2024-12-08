package io.foxcapades.bukkit.szip.util

internal inline fun <T, R> List<T>.ifExists(index: Int, fn: (T) -> R) =
  if (size > index)
    fn(get(index))
  else
    null
