package io.foxcapades.spigot.zip

import io.foxcapades.spigot.block.compression.facades.Facade
import io.foxcapades.spigot.block.compression.files.FileManager
import org.bukkit.Material
import org.yaml.snakeyaml.Yaml
import java.lang.IllegalStateException
import java.nio.charset.StandardCharsets

internal object Config {

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
      if (io.foxcapades.spigot.zip.Config.OpenOnInteract.KeyEnabled in values)
        io.foxcapades.spigot.zip.Config.OpenOnInteract.enabled = values[io.foxcapades.spigot.zip.Config.OpenOnInteract.KeyEnabled] as Boolean

      if (io.foxcapades.spigot.zip.Config.OpenOnInteract.KeyTarget in values) {
        var id = values[io.foxcapades.spigot.zip.Config.OpenOnInteract.KeyTarget] as String

        if (!id.startsWith("minecraft:"))
          id = "minecraft:$id"

        io.foxcapades.spigot.zip.Config.OpenOnInteract.targetBlockType = Material.matchMaterial(id)
          ?: throw IllegalStateException("Invalid open-on-interface.target value: ${values[io.foxcapades.spigot.zip.Config.OpenOnInteract.KeyTarget]}")
      }

      if (io.foxcapades.spigot.zip.Config.OpenOnInteract.KeyHolding in values) {
        var id = values[io.foxcapades.spigot.zip.Config.OpenOnInteract.KeyHolding] as String

        if (!id.startsWith("minecraft:"))
          id = "minecraft:$id"

        io.foxcapades.spigot.zip.Config.OpenOnInteract.keyItemType = Material.matchMaterial(id)
          ?: throw IllegalStateException("Invalid open-on-interface.holding value: ${values[io.foxcapades.spigot.zip.Config.OpenOnInteract.KeyHolding]}")
      }
    }

    override fun toString() = "OpenOnInteract{enabled=${io.foxcapades.spigot.zip.Config.OpenOnInteract.enabled}, target=${io.foxcapades.spigot.zip.Config.OpenOnInteract.targetBlockType}, holding=${io.foxcapades.spigot.zip.Config.OpenOnInteract.keyItemType}}"
  }

  @Suppress("UNCHECKED_CAST")
  fun reload() {
    Facade.logInfo("Loading plugin config.")

    FileManager.getPluginConfig()
      .reader(StandardCharsets.UTF_8)
      .use {
        val yaml = Yaml().loadAll(it)
        yaml.forEach { doc ->
         val tmp = doc as Map<String, Any>

          if (io.foxcapades.spigot.zip.Config.OpenOnInteract.KeyRoot in tmp)
            io.foxcapades.spigot.zip.Config.OpenOnInteract.loadFrom(tmp[io.foxcapades.spigot.zip.Config.OpenOnInteract.KeyRoot] as Map<String, Any>)
        }
      }
  }
}