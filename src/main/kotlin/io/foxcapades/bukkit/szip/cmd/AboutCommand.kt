package io.foxcapades.bukkit.szip.cmd

import io.foxcapades.bukkit.szip.Plugin
import io.foxcapades.bukkit.szip.util.ArrayView
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

internal class AboutCommand : Subcommand {
  override val name: String
    get() = "about"

  override val permission: String?
    get() = null

  override fun tryExecute(
    sender: CommandSender,
    command: Command,
    alias: String,
    args: ArrayView<out String>,
  ): CommandResult {
    val desc = Plugin.description

    sender.sendMessage("""
      BlockCompression - ${desc.website}
      Version ${desc.version}
      
      ${desc.description}
    """.trimIndent())

    return ok()
  }
}
