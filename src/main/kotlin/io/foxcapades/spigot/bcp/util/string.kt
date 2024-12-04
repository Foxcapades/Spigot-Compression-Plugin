package io.foxcapades.spigot.bcp.util

internal fun String.isOneOf(vararg options: String) = options.any { it == this }
