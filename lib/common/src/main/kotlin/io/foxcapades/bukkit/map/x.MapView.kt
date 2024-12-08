package io.foxcapades.bukkit.map

import org.bukkit.map.MapView

private const val KeyID = "id"
private const val KeyVirtual = "virtual"
private const val KeyScale = "scale"
private const val KeyCenterX = "center-x"
private const val KeyCenterZ = "center-z"
private const val KeyWorld = "world"
private const val KeyTrackingPosition = "track-position"
private const val KeyTrackUnlimited = "track-unlimited"
private const val KeyLocked = "locked"

fun MapView.toJsonSerializable(): Map<String, Any> =
  HashMap<String, Any>().also {
    it[KeyID] = id
    it[KeyVirtual] = isVirtual
    it[KeyScale] = scale.ordinal
    it[KeyCenterX] = centerX
    it[KeyCenterZ] = centerZ
    world?.also { world -> it[KeyWorld] = world.key.toString() }
    it[KeyTrackingPosition] = isTrackingPosition
    it[KeyTrackUnlimited] = isUnlimitedTracking
    it[KeyLocked] = isLocked
  }
