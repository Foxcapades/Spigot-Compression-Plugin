package io.foxcapades.spigot.zip.command

import org.bukkit.command.*
import org.bukkit.entity.Player
import io.foxcapades.spigot.block.compression.entity.openCompressionWorkbench

/**
 * Compress Command Executor Singleton
 *
 * @author Elizabeth Harper [foxcapades.io@gmail.com]
 * @since  v1.0.0
 */
internal object CompressExecutor : CommandExecutor {

  override fun onCommand(player: CommandSender, command: Command, alias: String, args: Array<out String>): Boolean {
    if (player !is Player)
      return false

    player.openCompressionWorkbench()

    return true
  }
}