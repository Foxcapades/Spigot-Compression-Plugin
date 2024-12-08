package io.foxcapades.bukkit.utils.serial

data object LongSlimSerializable : SlimSerializable<Long> {
  override val currentVersion: Int
    get() = 1

  override val typeIndicator: String
    get() = "l"

  override fun deserializerFor(version: Int): SlimDeserializer<Long> =
    SlimDeserializer { when (it) {
      "null" -> null
      else   -> it.toLongOrNull()
        ?: throw IllegalArgumentException("cannot parse value '$it' as a long")
    } }

  override fun serialize(value: Long) = value.toString()
}
