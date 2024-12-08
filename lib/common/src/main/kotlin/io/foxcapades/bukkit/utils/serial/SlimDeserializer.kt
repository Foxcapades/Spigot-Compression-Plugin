package io.foxcapades.bukkit.utils.serial

fun interface SlimDeserializer<D : Any> {
  fun deserialize(raw: String): D?
}
