package io.foxcapades.bukkit.utils.serial

import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

data object BinarySlimSerializable : SlimSerializable<ByteArray> {
  override val currentVersion: Int
    get() = 1

  override val typeIndicator: String
    get() = "ba"

  @OptIn(ExperimentalEncodingApi::class)
  override fun deserializerFor(version: Int): SlimDeserializer<ByteArray> =
    SlimDeserializer { when (it) {
      "null" -> null
      else   -> try { Base64.decode(it) }
      catch (e: Throwable) { throw IllegalArgumentException("cannot parse value as binary", e) }
    } }

  @OptIn(ExperimentalEncodingApi::class)
  override fun serialize(value: ByteArray) = Base64.encode(value)
}
