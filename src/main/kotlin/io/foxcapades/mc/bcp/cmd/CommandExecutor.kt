package io.foxcapades.mc.bcp.cmd

import io.foxcapades.mc.bcp.util.ArrayView
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.command.TabCompleter

internal object CommandExecutor : CommandExecutor, TabCompleter {
  private val subCommands = sequenceOf(
    AboutCommand(),
    GiveCommand(),
    ReloadCommand(),
    ZipCommand
  )
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
      .let { it ?: CommandResult(CommandResult.Type.Error, "unrecognized bcp command", DummySubCommand) }
      .let {
        when (it.type) {
          CommandResult.Type.BadUsage -> {
            if (it.hasMessage) {
              sender.sendMessage(it.formattedMessage)
            } else {
              sender.sendMessage(it.type.formatMessage("Usage: /$alias ${it.command.usageFor(args[0])}"))
            }

            true
          }

          CommandResult.Type.BadParam,
          CommandResult.Type.Error -> {
            sender.sendMessage(it.formattedMessage)
            true
          }

          CommandResult.Type.Success -> {
            if (it.hasMessage)
              sender.sendMessage(it.formattedMessage)
            true
          }
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
    subCommands[name]?.takeIf { it.permission?.let(sender::hasPermission) ?: true || sender is ConsoleCommandSender }

  @Suppress("NOTHING_TO_INLINE")
  private inline fun filterCommands(sender: CommandSender, start: String) =
    subCommands.asSequence()
      .filter { (k, _) -> k.startsWith(start) }
      .filter { (_, v) -> v.permission?.let(sender::hasPermission) ?: true || sender is ConsoleCommandSender }
      .map(Map.Entry<String, *>::key)
      .toList()


}
