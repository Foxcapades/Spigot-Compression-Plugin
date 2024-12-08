package io.foxcapades.bukkit.utils.serial

data object ShortSlimSerializable : SlimSerializable<Short> {
  override val currentVersion: Int
    get() = 1

  override val typeIndicator: String
    get() = "s"

  override fun deserializerFor(version: Int): SlimDeserializer<Short> =
    SlimDeserializer { when (it) {
      "null" -> null
      else   -> it.toShortOrNull()
        ?: throw IllegalArgumentException("cannot parse value '$it' as a short")
    } }

  override fun serialize(value: Short) = value.toString()
}
