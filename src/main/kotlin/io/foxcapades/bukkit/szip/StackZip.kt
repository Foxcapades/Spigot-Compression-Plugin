package io.foxcapades.bukkit.szip

import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.ProtocolManager
import io.foxcapades.bukkit.szip.cmd.CommandExecutor
import io.foxcapades.bukkit.szip.cmd.ZipCommand
import io.foxcapades.bukkit.szip.zip.Compressibles
import io.foxcapades.bukkit.szip.config.Config
import io.foxcapades.bukkit.szip.config.ListFiles
import io.foxcapades.bukkit.szip.event.EventDispatch
import io.foxcapades.bukkit.szip.i18n.I18N
import io.foxcapades.bukkit.szip.item.ZipTool
import io.foxcapades.bukkit.szip.packets.registerPacketListeners
import org.bukkit.NamespacedKey
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class StackZip : JavaPlugin() {
  internal lateinit var protocolManager: ProtocolManager

  init {
    Plugin = this
  }

  override fun onLoad() {
    ListFiles.createLocalConfigsIfNeeded()
  }

  override fun onEnable() {
    protocolManager = ProtocolLibrary.getProtocolManager()

    // import for side effects
    I18N
    ZipTool

    // load config
    reload()

    registerPacketListeners()

    getCommand("sz")!!.apply {
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
