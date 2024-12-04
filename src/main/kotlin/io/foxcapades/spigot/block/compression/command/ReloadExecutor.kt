package io.foxcapades.spigot.block.compression.command

import io.foxcapades.spigot.block.compression.compress.Compressibles
import io.foxcapades.spigot.block.compression.config.Config
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

internal object ReloadExecutor : CommandExecutor {
  override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
    sender.sendMessage("Reloading compressible blocks and items configs.")
    performReload()
    return true
  }

  @Suppress("NOTHING_TO_INLINE")
  inline fun performReload() {
    Compressibles.reload()
    Config.reload()
  }
}
