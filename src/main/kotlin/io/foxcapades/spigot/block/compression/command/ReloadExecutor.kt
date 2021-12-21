package io.foxcapades.spigot.block.compression.command

import io.foxcapades.spigot.block.compression.compressible.Compressibles
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

object ReloadExecutor : CommandExecutor {
  override fun onCommand(
    sender: CommandSender,
    command: Command,
    label: String,
    args: Array<out String>
  ): Boolean {
    sender.sendMessage("Reloading compressible blocks and items configs.")
    Compressibles.reload()
    return true
  }
}