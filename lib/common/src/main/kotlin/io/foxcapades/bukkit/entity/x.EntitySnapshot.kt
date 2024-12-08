package io.foxcapades.bukkit.entity

import org.bukkit.entity.EntitySnapshot

private const val KeyType = "type"

fun EntitySnapshot.toJsonSerializable(): Map<String, Any> =
  HashMap<String, Any>(1).also { it[KeyType] = entityType.toString() }
