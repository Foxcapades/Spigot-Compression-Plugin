package io.foxcapades.mc.bcp.cmd

import io.foxcapades.mc.bcp.util.ArrayView
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

internal object DummySubCommand : Subcommand {
  override val name: String
    get() = "invalid-command"
  override val permission: String
    get() = throw UnsupportedOperationException()

  override fun tryExecute(
    sender: CommandSender,
    command: Command,
    alias: String,
    args: ArrayView<out String>,
  ): CommandResult {
    throw UnsupportedOperationException()
  }

  override fun usageFor(alias: String): String {
    throw UnsupportedOperationException()
  }

  override fun tabComplete(
    sender: CommandSender,
    command: Command,
    alias: String,
    args: ArrayView<out String>
  ): List<String> {
    throw UnsupportedOperationException()
  }
}
