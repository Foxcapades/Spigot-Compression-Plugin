@file:Suppress("NOTHING_TO_INLINE")
@file:JvmName("SubcommandExt")
package io.foxcapades.mc.bcp.cmd

import io.foxcapades.mc.bcp.cmd.CommandResult.Type
import org.bukkit.ChatColor

internal inline fun Subcommand.ok(message: String = "") = CommandResult(Type.Success, message, this)

internal inline fun Subcommand.badUsage(message: String = "") = CommandResult(Type.BadUsage, message, this)

internal inline fun Subcommand.badParam(paramName: String, note: String? = null) = CommandResult(
  Type.BadParam,
  "invalid value given for argument ${ChatColor.DARK_GREEN}$paramName".let {
    if (note != null)
      it + Type.BadParam.formatMessage(": $note")
    else
      it
  },
  this
)

internal inline fun Subcommand.error(message: String) = CommandResult(Type.Error, message, this)
