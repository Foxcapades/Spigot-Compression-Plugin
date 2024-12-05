package io.foxcapades.mc.bcp.cmd

import io.foxcapades.mc.bcp.consts.Permission
import io.foxcapades.mc.bcp.ext.openCompressionGUI
import io.foxcapades.mc.bcp.util.ArrayView
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
      ok()
    } else {
      error("This command cannot be executed in the console.")
    }

  @Suppress("KotlinConstantConditions")
  override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<out String>) =
    tryExecute(p0, p1, p2, ArrayView(p3, 0))
      .let {
        if (it.type != CommandResult.Type.Success)
          p0.sendMessage(it.formattedMessage)
        true
      }

  override fun usageFor(alias: String) = alias

  override fun toString() = "/bcp $name"
}

