package io.foxcapades.spigot.block.compression.i18n

import io.foxcapades.spigot.block.compression.log.Logger
import java.util.Properties

internal class LocalizationTreeNode(
  private val entries: Map<String, String>,
  private val parent: LocalizationTreeNode? = null,
) {
  @Suppress("UNCHECKED_CAST")
  constructor(entries: Properties, parent: LocalizationTreeNode? = null) : this(HashMap(entries as Map<String, String>), parent)

  operator fun get(key: String): String = entries[key] ?: parent?.get(key) ?: fallback(key)

  private fun fallback(key: String): String {
    Logger.warn("failed to lookup localized string for key \"%s\"", key)
    return key
  }
}