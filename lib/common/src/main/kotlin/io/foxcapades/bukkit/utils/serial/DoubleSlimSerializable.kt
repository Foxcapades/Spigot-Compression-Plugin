package io.foxcapades.bukkit.utils.serial

data object DoubleSlimSerializable : SlimSerializable<Double> {
  override val currentVersion: Int
    get() = 1

  override val typeIndicator: String
    get() = "d"

  override fun deserializerFor(version: Int): SlimDeserializer<Double> =
    SlimDeserializer { when (it) {
      "null" -> null
      else   -> it.toDoubleOrNull()
        ?: throw IllegalArgumentException("cannot parse value '$it' as a double")
    } }

  override fun serialize(value: Double) = value.toString()
}
