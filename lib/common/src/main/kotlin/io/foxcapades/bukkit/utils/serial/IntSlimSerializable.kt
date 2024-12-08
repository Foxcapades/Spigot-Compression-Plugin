package io.foxcapades.bukkit.utils.serial

data object IntSlimSerializable : SlimSerializable<Int> {
  override val currentVersion: Int
    get() = 1

  override val typeIndicator: String
    get() = "i"

  override fun deserializerFor(version: Int): SlimDeserializer<Int> =
    SlimDeserializer { when (it) {
      "null" -> null
      else   -> it.toIntOrNull()
        ?: throw IllegalArgumentException("cannot parse value '$it' as an int")
    } }

  override fun serialize(value: Int) = value.toString()
}
