package io.foxcapades.spigot.block.compression.command

import org.bukkit.command.*
import org.bukkit.entity.Player
import io.foxcapades.spigot.block.compression.ext.openCompressionWorkbench
import io.foxcapades.spigot.block.compression.log.Logger

/**
 * Compress Command Executor Singleton
 *
 * @author Elizabeth Harper [foxcapades.io@gmail.com]
 * @since  v1.0.0
 */
internal object CompressExecutor : CommandExecutor {
  override fun onCommand(player: CommandSender, command: Command, alias: String, args: Array<out String>): Boolean {
    if (player !is Player) {
      player.sendMessage("&4Only players may execute this command.")
      return false
    }

    Logger.trace("%s executing command %s", player.name, command.name)
    player.openCompressionWorkbench()
    return true
  }
}
