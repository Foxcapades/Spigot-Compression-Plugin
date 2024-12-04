package io.foxcapades.spigot.bcp.command

import io.foxcapades.spigot.bcp.consts.Permission
import io.foxcapades.spigot.bcp.ext.openCompressionGUI
import io.foxcapades.spigot.bcp.util.ArrayView
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

internal object ZipCommand : Subcommand, CommandExecutor {
  override val name: String
    get() = "compress"

  override val aliases: Array<String>
    get() = arrayOf("cmp", "zip")

  override val permission: String
    get() = Permission.GiveCommand

  override fun tryExecute(
    sender: CommandSender,
    command: Command,
    alias: String,
    args: ArrayView<out String>,
  ) =
    if (sender is Player) {
      sender.openCompressionGUI()
      CommandResult.ok()
    } else {
      CommandResult.fail("This command cannot be executed in the console.")
    }

  @Suppress("KotlinConstantConditions")
  override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<out String>) =
    tryExecute(p0, p1, p2, ArrayView(p3, 0))
      .let {
        if (!it.success)
          p0.sendMessage(ChatColor.DARK_RED.toString() + it.message)
        true
      }
}
