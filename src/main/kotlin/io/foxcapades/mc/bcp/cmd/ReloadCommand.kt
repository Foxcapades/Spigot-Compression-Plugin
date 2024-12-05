package io.foxcapades.mc.bcp.cmd

import io.foxcapades.mc.bcp.Logger
import io.foxcapades.mc.bcp.Plugin
import io.foxcapades.mc.bcp.consts.Permission
import io.foxcapades.mc.bcp.util.ArrayView
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
      ok("Reload complete.")
    } catch (e: Exception) {
      Logger.error { "reload failed:\n%s".format(e.stackTraceToString()) }
      error("reload encountered an error; see server logs for more details")
    }
  }

  override fun usageFor(alias: String) = alias

  override fun toString() = "/bcp $name"
}
