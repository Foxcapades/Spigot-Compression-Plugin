package io.foxcapades.mc.bcp

import io.foxcapades.mc.bcp.cmd.CommandExecutor
import io.foxcapades.mc.bcp.cmd.ZipCommand
import io.foxcapades.mc.bcp.zip.Compressibles
import io.foxcapades.mc.bcp.config.Config
import io.foxcapades.mc.bcp.config.ListFiles
import io.foxcapades.mc.bcp.event.EventDispatch
import io.foxcapades.mc.bcp.i18n.I18N
import io.foxcapades.mc.bcp.item.ZipTool
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
