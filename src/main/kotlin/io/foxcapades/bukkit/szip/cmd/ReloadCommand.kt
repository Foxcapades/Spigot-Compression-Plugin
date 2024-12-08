package io.foxcapades.bukkit.szip.cmd

import io.foxcapades.bukkit.szip.Logger
import io.foxcapades.bukkit.szip.Plugin
import io.foxcapades.bukkit.szip.consts.Permission
import io.foxcapades.bukkit.szip.util.ArrayView
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

internal class ReloadCommand() : Subcommand {
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
      ok("Reload complete.")
    } catch (e: Exception) {
      Logger.error { "reload failed:\n%s".format(e.stackTraceToString()) }
      error("reload encountered an error; see server logs for more details")
    }
  }

  override fun toString() = "/bcp $name"
}
