package io.foxcapades.spigot.bcp.command

import io.foxcapades.spigot.bcp.Logger
import io.foxcapades.spigot.bcp.Plugin
import io.foxcapades.spigot.bcp.consts.Permission
import io.foxcapades.spigot.bcp.util.ArrayView
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

internal object ReloadCommand : Subcommand {
  override val name: String
    get() = "reload"

  override val permission: String
    get() = Permission.ReloadCommand

  override fun tryExecute(
    sender: CommandSender,
    command: Command,
    alias: String,
    args: ArrayView<out String>
  ): CommandResult {
    sender.sendMessage("Reloading configuration.")
    return try {
      Plugin.reload()
      CommandResult.ok("Reload complete.")
    } catch (e: Exception) {
      Logger.error { "reload failed:\n%s".format(e.stackTraceToString()) }
      CommandResult.fail("Reload encountered an error. see server logs for more details.")
    }
  }
}
