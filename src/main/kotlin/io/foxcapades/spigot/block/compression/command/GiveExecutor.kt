package io.foxcapades.spigot.block.compression.command

import io.foxcapades.spigot.block.compression.compressible.Compressibles
import io.foxcapades.spigot.block.compression.compressible.CompressionLevel
import io.foxcapades.spigot.block.compression.facades.Facade
import io.foxcapades.spigot.block.compression.item.compressionLevel
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.stream.Collectors
import java.util.stream.Stream

@Suppress("NOTHING_TO_INLINE")
internal object GiveExecutor : CommandExecutor, TabCompleter {
  private val levels = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9")

  override fun onCommand(
    sender: CommandSender,
    command: Command,
    label: String,
    args: Array<out String>
  ): Boolean {
    if (args.size != 4)
      return false

    // Find the target player by name or return false
    val player = playerNameStream().filter { it == args[0] }
      .findFirst()
      .flatMap { name -> playerStream().filter { it.playerListName == name }.findFirst() }
      .orElse(null) ?: return false

    val material = if (args[1].startsWith("minecraft:")) args[1] else "minecraft:${args[1]}"
    if (material !in Compressibles)
      return false

    if (args[2] !in levels)
      return false

    val lvl: Byte

    try {
      lvl = args[2].toByte()
    } catch (e: Exception) {
      return false
    }

    val qty: Int

    try {
      qty = args[3].toInt()
    } catch (e: Exception) {
      return false
    }

    val rem = player.inventory.addItem(
      ItemStack(Material.matchMaterial(material)!!)
        .compressionLevel(CompressionLevel.from(lvl), qty)
    )

    for (v in rem.values)
      player.world.dropItem(player.location, v)

    return true
  }

  override fun onTabComplete(
    sender: CommandSender,
    command: Command,
    alias: String,
    args: Array<out String>,
  ): List<String>? {
    return when (args.size) {
      0 -> Facade.server.onlinePlayers.stream()
        .map { it.playerListName }
        .collect(Collectors.toList())
      1 -> Facade.server.onlinePlayers.stream()
        .map { it.playerListName }
        .filter { it.startsWith(args[0]) }
        .collect(Collectors.toList())
      2 -> Compressibles.allowed.stream()
        .filter { it.contains(args[1], true) }
        .collect(Collectors.toList())
      3 -> levels
      4 -> listOf("1", Material.matchMaterial(args[1])?.maxStackSize?.toString() ?: "64")
      else -> null
    }
  }


  private inline fun playerStream(): Stream<out Player> = Facade.server.onlinePlayers.stream()

  private inline fun playerNameStream() = playerStream().map { it.playerListName }
}