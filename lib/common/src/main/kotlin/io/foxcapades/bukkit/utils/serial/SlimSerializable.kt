package io.foxcapades.bukkit.utils.serial

sealed interface SlimSerializable<T : Any> {
  val currentVersion: Int

  val typeIndicator: String

  val supportedVersions: IntArray
    get() = intArrayOf(currentVersion)

  fun deserializerFor(version: Int): SlimDeserializer<T>

  fun serialize(value: T): String
}

