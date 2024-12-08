@file:JvmName("PotionEffectExtensions")
package io.foxcapades.bukkit.potion

import org.bukkit.potion.PotionEffect

private const val KeyAmplifier = "amplifier"
private const val KeyDuration = "duration"
private const val KeyType = "type"
private const val KeyAmbient = "ambient"
private const val KeyParticles = "particles"
private const val KeyIcon = "icon"

fun PotionEffect.toJsonSerializable(): Map<String, Any> =
  HashMap<String, Any>(6).also {
    it[KeyAmplifier] = amplifier
    it[KeyDuration] = duration
    it[KeyType] = type.key.toString()
    it[KeyAmbient] = isAmbient
    it[KeyParticles] = hasParticles()
    it[KeyIcon] = hasIcon()
  }
