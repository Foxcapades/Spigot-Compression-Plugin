package io.foxcapades.bukkit.utils.serial

data object CharSlimSerializable : SlimSerializable<Char> {
  override val currentVersion: Int
    get() = 1

  override val typeIndicator: String
    get() = "c"

  override fun deserializerFor(version: Int): SlimDeserializer<Char> =
    SlimDeserializer { when (it) {
      "null" -> null
      else   -> it.toIntOrNull()?.toChar()
        ?: throw IllegalArgumentException("cannot parse value '$it' as a char")
    } }

  override fun serialize(value: Char) = value.toString()
}
