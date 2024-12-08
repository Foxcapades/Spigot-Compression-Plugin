package io.foxcapades.bukkit

import org.bukkit.Color
import org.bukkit.FireworkEffect

private const val KeyFlicker    = "flicker"
private const val KeyTrail      = "trail"
private const val KeyColors     = "colors"
private const val KeyFadeColors = "fade-colors"
private const val KeyType       = "type"

fun FireworkEffect.toJsonSerializable(): Map<String, Any> =
  HashMap<String, Any>(5).also {
    it[KeyFlicker] = hasFlicker()
    it[KeyTrail] = hasTrail()
    it[KeyColors] = colors.map(Color::asARGB)
    it[KeyFadeColors] = fadeColors.map(Color::asARGB)
    it[KeyType] = type.name
  }
