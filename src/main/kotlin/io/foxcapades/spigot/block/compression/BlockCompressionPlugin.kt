@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.spigot.block.compression

import io.foxcapades.spigot.block.compression.command.CompressExecutor
import io.foxcapades.spigot.block.compression.command.GiveExecutor
import io.foxcapades.spigot.block.compression.command.ReloadExecutor
import io.foxcapades.spigot.block.compression.compressible.Compressibles
import io.foxcapades.spigot.block.compression.event.EventDispatch
import io.foxcapades.spigot.block.compression.files.FileManager
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin

class BlockCompressionPlugin : JavaPlugin() {

  internal companion object {
    var instance: Plugin? = null
      private set
  }

  init {
    instance = this
  }

  override fun onLoad() {
    FileManager.createLocalConfigsIfNeeded()
  }

  override fun onEnable() {
    Config.reload()
    Compressibles.reload()

    // Register compress/zip command handler.
    getCommand("compress")!!.setExecutor(CompressExecutor)
    getCommand("bcreload")!!.setExecutor(ReloadExecutor)

    val give = getCommand("bcgive")!!
    give.tabCompleter = GiveExecutor
    give.setExecutor(GiveExecutor)

    server.pluginManager.registerEvents(EventDispatch, this)
  }
}

