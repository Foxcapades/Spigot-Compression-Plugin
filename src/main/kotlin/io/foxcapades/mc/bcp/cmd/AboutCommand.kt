package io.foxcapades.mc.bcp.cmd

import io.foxcapades.mc.bcp.Plugin
import io.foxcapades.mc.bcp.util.ArrayView
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
