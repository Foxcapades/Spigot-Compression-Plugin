package io.foxcapades.spigot.block.compression

import io.foxcapades.spigot.block.compression.files.FileManager
import org.bukkit.Material
import org.bukkit.configuration.file.FileConfiguration

internal object Config {

  private var raw: FileConfiguration = BlockCompressionPlugin.instance!!.config

  object OpenOnInteract {
    private const val Key = "open-on-interact"

    inline val enabled: Boolean
      get() = raw["$Key.enabled"] as Boolean

    inline val targetBlockType: Material
      get() = Material.matchMaterial(raw["$Key.target"] as String)
        ?: throw IllegalStateException("Invalid $Key.target value.  Must be a valid material id.")

    inline val keyItemType: Material
      get() = Material.matchMaterial(raw["$Key.holding"] as String)
        ?: throw IllegalStateException("Invalid $Key.holding value.  Must be a valid material id.")
  }

  fun reload() {
    raw.load(FileManager.getPluginConfig())
  }
}