package io.foxcapades.bukkit.utils.serial

data object ByteSlimSerializable : SlimSerializable<Byte> {
  override val currentVersion: Int
    get() = 1

  override val typeIndicator: String
    get() = "b"

  override fun deserializerFor(version: Int): SlimDeserializer<Byte> =
    SlimDeserializer { when (it) {
      "null" -> null
      else   -> it.toByteOrNull()
        ?: throw IllegalArgumentException("cannot parse value '$it' as a byte")
    } }

  override fun serialize(value: Byte) = value.toString()
}
