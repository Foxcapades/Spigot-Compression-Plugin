package io.foxcapades.spigot.block.compression.util

internal fun String.isOneOf(vararg options: String) = options.any { it == this }
