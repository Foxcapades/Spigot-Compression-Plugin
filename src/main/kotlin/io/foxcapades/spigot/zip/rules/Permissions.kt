package io.foxcapades.spigot.zip.rules

internal data class Permissions(val allOf: Set<String>, val anyOf: Set<String>, val noneOf: Set<String>)
