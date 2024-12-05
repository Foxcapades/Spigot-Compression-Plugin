package io.foxcapades.mc.bcp.util

internal inline fun <T> T.then(fn: (T) -> Unit) { fn(this) }
