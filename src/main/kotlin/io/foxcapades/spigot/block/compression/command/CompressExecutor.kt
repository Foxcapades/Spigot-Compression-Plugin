package io.foxcapades.spigot.block.compression.command

import io.foxcapades.spigot.block.compression.consts.CompressorTitle
import io.foxcapades.spigot.block.compression.facades.Facade
import org.bukkit.command.*
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType.WORKBENCH

/**
 * Compress Command Executor Singleton
 *
 * @author Elizabeth Harper [foxcapades.io@gmail.com]
 * @since  v1.0.0
 */
object CompressExecutor : CommandExecutor {

  override fun onCommand(player: CommandSender, command: Command, alias: String, args: Array<out String>): Boolean {
    if (player !is Player)
      return false

    player.openInventory(Facade.server.createInventory(player, WORKBENCH, CompressorTitle))
    return true
  }
}