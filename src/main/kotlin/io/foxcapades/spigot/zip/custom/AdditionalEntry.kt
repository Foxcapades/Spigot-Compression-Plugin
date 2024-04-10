package io.foxcapades.spigot.zip.custom

import io.foxcapades.spigot.zip.facades.Facade
import java.util.Locale

private const val DefaultLocaleKey = "default"

internal class AdditionalEntry private constructor(val key: String, private val localNames: Map<String, String>) {

  fun localName(locale: Locale): String {
    if (locale.language in localNames)
      return localNames[locale.language]!!

    val key = locale.key()
    if (key in localNames)
      return localNames[key]!!

    return localNames[DefaultLocaleKey]!!
  }

  override fun equals(other: Any?) = when {
    this === other                 -> true
    other !is AdditionalEntry -> false
    key != other.key               -> false
    localNames != other.localNames -> false
    else                           -> true
  }

  override fun hashCode() = 31 * key.hashCode() + localNames.hashCode()

  companion object {
    @JvmStatic
    fun of(key: String, localNames: Map<String, String>): AdditionalEntry? {
      if (key.isBlank()) {
        Facade.logWarn("ignoring custom item/block entry with blank identifier")
        return null
      }

      if (localNames.size == 1) {
        if (DefaultLocaleKey !in localNames) {
          val repl = HashMap<String, String>(2)

          for (k in localNames.keys)
            repl[DefaultLocaleKey] = localNames[k]!!

          return AdditionalEntry(key, repl)
        }

        return AdditionalEntry(key, localNames)
      }

      if (DefaultLocaleKey !in localNames) {
        Facade.logWarn("ignoring custom item/block entry $key as it has no default name value")
        return null
      }

      return AdditionalEntry(key, localNames)
    }

    @JvmStatic
    fun of(key: String, name: String): AdditionalEntry? {
      if (key.isBlank()) {
        Facade.logWarn("ignoring custom item/block entry with blank identifier")
        return null
      }

      return AdditionalEntry(key, mapOf(DefaultLocaleKey to name))
    }
  }
}

private fun Locale.key() = "$language-$country"