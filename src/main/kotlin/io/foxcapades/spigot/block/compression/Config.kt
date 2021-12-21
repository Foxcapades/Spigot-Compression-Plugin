package io.foxcapades.spigot.block.compression

import io.foxcapades.spigot.block.compression.facades.Facade
import io.foxcapades.spigot.block.compression.files.FileManager
import org.bukkit.Material
import org.yaml.snakeyaml.Yaml
import java.lang.IllegalStateException
import java.nio.charset.StandardCharsets

internal object Config {

  init {
    reload()
  }

  object OpenOnInteract {
    internal const val KeyRoot    = "open-on-interact"
    internal const val KeyEnabled = "enabled"
    internal const val KeyTarget  = "target"
    internal const val KeyHolding = "holding"

    var enabled: Boolean = true
      private set

    var targetBlockType: Material = Material.FLETCHING_TABLE
      private set

    var keyItemType: Material = Material.DIAMOND
      private set

    internal fun loadFrom(values: Map<String, Any>) {
      if (KeyEnabled in values)
        enabled = values[KeyEnabled] as Boolean

      if (KeyTarget in values) {
        var id = values[KeyTarget] as String

        if (!id.startsWith("minecraft:"))
          id = "minecraft:$id"

        targetBlockType = Material.matchMaterial(id)
          ?: throw IllegalStateException("Invalid open-on-interface.target value: ${values[KeyTarget]}")
      }

      if (KeyHolding in values) {
        var id = values[KeyTarget] as String

        if (!id.startsWith("minecraft:"))
          id = "minecraft:$id"

        keyItemType = Material.matchMaterial(id)
          ?: throw IllegalStateException("Invalid open-on-interface.holding value: ${values[KeyHolding]}")
      }
    }
  }

  @Suppress("UNCHECKED_CAST")
  fun reload() {
    Facade.logInfo("Loading plugin config.")

    FileManager.getPluginConfig()
      .reader(StandardCharsets.UTF_8)
      .use {
        Yaml().loadAll(it)
      }
      .forEach {
        val tmp = it as Map<String, Any>

        if (OpenOnInteract.KeyRoot in tmp)
          OpenOnInteract.loadFrom(tmp)
      }
  }
}