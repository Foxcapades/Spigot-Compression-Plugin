package io.foxcapades.spigot.block.compression.command

import io.foxcapades.spigot.block.compression.Server
import io.foxcapades.spigot.block.compression.compress.Compressibles
import io.foxcapades.spigot.block.compression.compress.CompressionLevel
import io.foxcapades.spigot.block.compression.compress.compress
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import java.util.stream.Stream

@Suppress("NOTHING_TO_INLINE")
internal object GiveExecutor : CommandExecutor, TabCompleter {
  private val levels = Array(9) { ('1'.code + it).toChar().toString() }

  override fun onCommand(
    sender: CommandSender,
    command: Command,
    label: String,
    args: Array<out String>
  ): Boolean {
    if (args.size != 4)
      return false

    // Find the target player by name or return false
    val player = playerNameStream()
      .filter { it == args[0] }
      .findFirst()
      .flatMap { name -> playerStream().filter { it.playerListName == name }.findFirst() }
      .orElse(null)
      ?: return false

    val material = if (args[1].startsWith("minecraft:")) args[1] else "minecraft:${args[1]}"
    if (material !in Compressibles)
      return false

    if (args[2] !in levels)
      return false

    val lvl = try {
      CompressionLevel(args[2].toInt())
    } catch (e: IllegalArgumentException) {
      sender.sendMessage("&4${e.message}")
      return false
    } catch (e: Exception) {
      return false
    }

    val qty = try {
      args[3].toInt()
    } catch (e: Exception) {
      return false
    }

    for (v in player.inventory.addItem(Material.matchMaterial(material)!!.compress(lvl, qty)).values)
      player.world.dropItem(player.location, v)

    return true
  }

  override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>) =
    when (args.size) {
      0 -> Server.onlinePlayers.asSequence()
        .map { it.playerListName }
        .toList()
      1 -> Server.onlinePlayers.asSequence()
        .map { it.playerListName }
        .filter { it.startsWith(args[0]) }
        .toList()
      2 -> filterCompressibles(args[1])
      3 -> levels.asList()
      4 -> listOf("1", "64")
      else -> null
    }

  private inline fun playerStream(): Stream<out Player> = Server.onlinePlayers.stream()

  private inline fun playerNameStream() = playerStream().map { it.playerListName }

  private inline fun filterCompressibles(text: String): List<String> {
    val primaryHits = ArrayList<String>(64)
    val secondaryHits = ArrayList<String>(128)
    val nsHits = ArrayList<String>(128)

    for ((ns, key) in Compressibles) {
      if (key.startsWith(text)) {
        primaryHits.add(key)

        if (text.length < 3 && primaryHits.size == 64)
          return primaryHits

        secondaryHits.add("$ns:$key")
      } else if (ns.startsWith(text)) {
        nsHits.add("$ns:key")
      }
    }

    return ArrayList<String>(primaryHits.size + secondaryHits.size + nsHits.size)
      .apply {
        addAll(primaryHits)
        addAll(secondaryHits)
        addAll(nsHits)
      }
  }
}
