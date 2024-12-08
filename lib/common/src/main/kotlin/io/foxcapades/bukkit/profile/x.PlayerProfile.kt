@file:JvmName("PlayerProfileExtensions")
package io.foxcapades.bukkit.profile

import org.bukkit.profile.PlayerProfile

private const val KeyUUID = "uuid"
private const val KeyName = "name"

fun PlayerProfile.toJsonSerializable(): Map<String, Any> =
  HashMap<String, Any>().also {
    uniqueId?.also { uuid -> it[KeyUUID] = uuid.toString() }
    name?.also { name -> it[KeyName] = name }
  }
