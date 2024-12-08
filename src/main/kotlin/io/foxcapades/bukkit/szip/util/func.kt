package io.foxcapades.bukkit.szip.util

internal inline fun <T> T.then(fn: (T) -> Unit) { fn(this) }
