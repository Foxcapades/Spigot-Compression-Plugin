package io.foxcapades.spigot.block.compression.command

import io.foxcapades.spigot.block.compression.consts.CompressorTitle
import io.foxcapades.spigot.block.compression.facades.Facade
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType.WORKBENCH

object CompressExecutor : CommandExecutor {
  override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<out String>): Boolean {
    if (p0 !is Player)
      return false

    p0.openInventory(Facade.server.createInventory(p0, WORKBENCH, CompressorTitle))
    return true
  }
}