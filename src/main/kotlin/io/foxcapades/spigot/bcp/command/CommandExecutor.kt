package io.foxcapades.spigot.bcp.command

import io.foxcapades.spigot.bcp.util.ArrayView
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

internal object CommandExecutor : CommandExecutor, TabCompleter {
  private val subCommands = sequenceOf(GiveCommand, ReloadCommand, ZipCommand)
    .flatMap { com -> sequenceOf(com.name, *com.aliases).map { it to com } }
    .sortedBy { it.first }
    .toMap()

  override fun onCommand(
    sender: CommandSender,
    command: Command,
    alias: String,
    args: Array<out String>
  ) =
    args.takeIf { it.isNotEmpty() }
      ?.let { getCommand(sender, it[0]) }
      ?.tryExecute(sender, command, args[0], ArrayView(args, 1))
      .let { it ?: CommandResult.fail("unrecognized bc command") }
      .let {
        if (it.message.isNullOrEmpty()) {
          it.success
        } else {
          sender.sendMessage(if (it.success) it.message else ChatColor.DARK_RED.toString() + it.message)
          true
        }
      }

  override fun onTabComplete(
    sender: CommandSender,
    command: Command,
    alias: String,
    args: Array<out String>
  ) =
    when (args.size) {
      0    -> filterCommands(sender, "")
      1    -> filterCommands(sender, args[0])
      else -> getCommand(sender, args[0])?.tabComplete(sender, command, args[0], ArrayView(args, 1))
        ?: emptyList()
    }

  @Suppress("NOTHING_TO_INLINE")
  private inline fun getCommand(sender: CommandSender, name: String) =
    subCommands[name]?.takeIf { sender.hasPermission(it.permission) }

  @Suppress("NOTHING_TO_INLINE")
  private inline fun filterCommands(sender: CommandSender, start: String) =
    subCommands.asSequence()
      .filter { (k, _) -> k.startsWith(start) }
      .filter { (_, v) -> sender.hasPermission(v.permission) }
      .map(Map.Entry<String, *>::key)
      .toList()

}
