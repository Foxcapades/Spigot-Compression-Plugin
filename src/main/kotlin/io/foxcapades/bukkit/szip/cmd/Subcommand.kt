package io.foxcapades.bukkit.szip.cmd

import io.foxcapades.bukkit.szip.util.ArrayView
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

internal interface Subcommand {
  val name: String

  val aliases: Array<String>
    get() = emptyArray()

  val permission: String?

  fun tryExecute(sender: CommandSender, command: Command, alias: String, args: ArrayView<out String>): CommandResult

  fun tabComplete(sender: CommandSender, command: Command, alias: String, args: ArrayView<out String>): List<String> =
    emptyList()

  fun usageFor(alias: String): String = alias
}
