package io.foxcapades.spigot.zip.custom

internal data class CustomizationRule(val enabled: Boolean, val users: Set<String>, val permissions: CustomizationPermissions)

