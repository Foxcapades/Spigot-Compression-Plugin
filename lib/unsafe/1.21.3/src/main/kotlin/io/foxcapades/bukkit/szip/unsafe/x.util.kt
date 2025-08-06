package io.foxcapades.bukkit.szip.unsafe

import java.io.ByteArrayOutputStream
import java.io.DataOutput
import java.io.DataOutputStream

internal inline fun withDataOutput(initialSize: Int = 128, fn: (DataOutput) -> Unit): ByteArray =
  ByteArrayOutputStream(initialSize)
    .also { fn(DataOutputStream(it)) }
    .toByteArray()
