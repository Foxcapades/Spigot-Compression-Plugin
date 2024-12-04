package io.foxcapades.spigot.bcp.command

import io.foxcapades.spigot.bcp.compress.Compressibles
import io.foxcapades.spigot.bcp.config.Config
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

internal object ReloadExecutor : CommandExecutor {
  override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
    sender.sendMessage("Reloading configuration.")
    performReload()
    return true
  }

  @Suppress("NOTHING_TO_INLINE")
  inline fun performReload() {
    Compressibles.reload()
    Config.reload()
  }
}
