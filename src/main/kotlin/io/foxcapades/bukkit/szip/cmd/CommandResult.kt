package io.foxcapades.bukkit.szip.cmd

import org.bukkit.ChatColor

internal data class CommandResult(
  val type: Type,
  val message: String,
  val command: Subcommand,
) {
  enum class Type(val color: ChatColor) {
    BadUsage(ChatColor.RED),
    BadParam(ChatColor.RED),
    Error(ChatColor.DARK_RED),
    Success(ChatColor.DARK_GREEN),
    ;

    fun formatMessage(message: String) = "${color}${message}"
  }

  inline val hasMessage
    get() = message.isNotEmpty()

  inline val isSuccess
    get() = type == Type.Success

  inline val formattedMessage
    get() = type.formatMessage(message)
}
