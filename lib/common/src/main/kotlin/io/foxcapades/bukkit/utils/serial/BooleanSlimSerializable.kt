package io.foxcapades.bukkit.utils.serial

data object BooleanSlimSerializable : SlimSerializable<Boolean> {
  override val currentVersion: Int
    get() = 1

  override val typeIndicator: String
    get() = "B"

  override fun deserializerFor(version: Int): SlimDeserializer<Boolean> =
    SlimDeserializer { when (it) {
      "0"    -> false
      "1"    -> true
      "null" -> null
      else -> throw IllegalArgumentException("cannot parse value $it as boolean value.")
    } }

  override fun serialize(value: Boolean) =
    when (value) { true -> "1" ; else -> "0" }
}
