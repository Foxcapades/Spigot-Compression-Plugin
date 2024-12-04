package io.foxcapades.spigot.bcp

import io.foxcapades.spigot.bcp.command.CommandExecutor
import io.foxcapades.spigot.bcp.command.ZipCommand
import io.foxcapades.spigot.bcp.compress.Compressibles
import io.foxcapades.spigot.bcp.config.Config
import io.foxcapades.spigot.bcp.config.ListFiles
import io.foxcapades.spigot.bcp.event.EventDispatch
import io.foxcapades.spigot.bcp.i18n.I18N
import io.foxcapades.spigot.bcp.item.ZipTool
import org.bukkit.NamespacedKey
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class BlockCompressionPlugin : JavaPlugin() {
  init {
    Plugin = this
  }

  override fun onLoad() {
    ListFiles.createLocalConfigsIfNeeded()
  }

  override fun onEnable() {
    // import for side effects
    I18N
    ZipTool

    // load config
    reload()

    getCommand("bcp")!!.apply {
      setExecutor(CommandExecutor)
      tabCompleter = CommandExecutor
    }

    getCommand("compress")!!.apply {
      setExecutor(ZipCommand)
    }

    server.pluginManager.registerEvents(EventDispatch, this)
  }

  @Suppress("NOTHING_TO_INLINE")
  internal inline fun newKey(key: String) = NamespacedKey(this, key)

  @Suppress("NOTHING_TO_INLINE")
  internal inline fun file(path: String) = File(dataFolder, path)

  internal fun reload() {
    Compressibles.reload()
    Config.reload()
  }
}
