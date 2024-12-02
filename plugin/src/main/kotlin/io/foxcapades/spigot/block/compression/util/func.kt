package io.foxcapades.spigot.block.compression.util

internal inline fun <T> T.then(fn: (T) -> Unit) { fn(this) }
