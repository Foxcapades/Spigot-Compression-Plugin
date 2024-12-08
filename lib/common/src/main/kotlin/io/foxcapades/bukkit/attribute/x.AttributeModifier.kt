package io.foxcapades.bukkit.attribute

import org.bukkit.attribute.AttributeModifier

private const val CurrentVersion = 1

fun AttributeModifier.toSlimSerializable(): Any =
  arrayOf(CurrentVersion, key.toString(), amount, operation.name, slotGroup.toString())
