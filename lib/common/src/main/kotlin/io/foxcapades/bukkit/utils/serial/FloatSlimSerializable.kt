package io.foxcapades.bukkit.utils.serial

data object FloatSlimSerializable : SlimSerializable<Float> {
  override val currentVersion: Int
    get() = 1

  override val typeIndicator: String
    get() = "f"

  override fun deserializerFor(version: Int): SlimDeserializer<Float> =
    SlimDeserializer { when (it) {
      "null" -> null
      else   -> it.toFloatOrNull()
        ?: throw IllegalArgumentException("cannot parse value '$it' as a float")
    } }

  override fun serialize(value: Float) = value.toString()
}
