package io.foxcapades.mc.bcp.cmd

import io.foxcapades.mc.bcp.util.ArrayView
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

internal sealed class ParameterizedSubcommand : Subcommand {
  protected abstract val parameters: Array<Parameter>

  protected val requiredParameters
    get() = parameters.sumOf { it.required.toInt() }

  override fun tryExecute(
    sender: CommandSender,
    command: Command,
    alias: String,
    args: ArrayView<out String>
  ): CommandResult {
    if (args.size < requiredParameters)
      return badUsage()

    return tryExecuteSafe(sender, command, alias, args)
  }

  override fun tabComplete(
    sender: CommandSender,
    command: Command,
    alias: String,
    args: ArrayView<out String>
  ) =
    when {
      args.size == 0              -> parameters[0].tabCompleter("")
      args.size > parameters.size -> emptySequence()
      else                        -> parameters[args.lastIndex].tabCompleter(args.last)
    }.toList()

  override fun usageFor(alias: String) =
    StringBuilder(alias)
      .apply {  parameters.forEach { append(' ').append(it.formattedName) } }
      .toString()

  protected abstract fun tryExecuteSafe(
    sender: CommandSender,
    command: Command,
    alias: String,
    args: ArrayView<out String>
  ): CommandResult

  @Suppress("NOTHING_TO_INLINE")
  protected inline fun badParam(index: Int, note: String? = null) = badParam(parameters[index].formattedName, note)

  @Suppress("NOTHING_TO_INLINE")
  private inline fun Boolean.toInt(): Int = if (this) 1 else 0
}
