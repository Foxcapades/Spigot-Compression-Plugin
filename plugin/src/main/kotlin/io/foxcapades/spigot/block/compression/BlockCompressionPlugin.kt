package io.foxcapades.spigot.block.compression

import io.foxcapades.spigot.block.compression.command.CompressExecutor
import io.foxcapades.spigot.block.compression.command.GiveExecutor
import io.foxcapades.spigot.block.compression.command.ReloadExecutor
import io.foxcapades.spigot.block.compression.compress.Compressibles
import io.foxcapades.spigot.block.compression.event.EventDispatch
import io.foxcapades.spigot.block.compression.config.ListFiles
import io.foxcapades.spigot.block.compression.config.PluginConfig
import org.bukkit.NamespacedKey
import org.bukkit.Server
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

lateinit var Plugin: BlockCompressionPlugin
  private set

inline val Server: Server
  get() = Plugin.server

class BlockCompressionPlugin : JavaPlugin() {
  init {
    Plugin = this
  }

  override fun onLoad() {
    ListFiles.createLocalConfigsIfNeeded()
  }

  override fun onEnable() {
    Compressibles.reload()
    PluginConfig.reload()

    // Register compress/zip command handler.
    getCommand("compress")!!.setExecutor(CompressExecutor)
    getCommand("bcreload")!!.setExecutor(ReloadExecutor)

    val give = getCommand("bcgive")!!
    give.tabCompleter = GiveExecutor
    give.setExecutor(GiveExecutor)

    server.pluginManager.registerEvents(EventDispatch, this)
  }

  @Suppress("NOTHING_TO_INLINE")
  internal inline fun newKey(key: String) = NamespacedKey(this, key)

  @Suppress("NOTHING_TO_INLINE")
  internal inline fun file(path: String) = File(dataFolder, path)
}
