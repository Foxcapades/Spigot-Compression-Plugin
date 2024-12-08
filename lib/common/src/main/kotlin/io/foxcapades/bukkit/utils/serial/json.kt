package io.foxcapades.bukkit.utils.serial

import com.google.gson.*

internal val Gson: Gson = GsonBuilder()
  .serializeNulls()
  .disableHtmlEscaping()
  .setNumberToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
  .create()





data class SlimSerialValue(internal val value)
