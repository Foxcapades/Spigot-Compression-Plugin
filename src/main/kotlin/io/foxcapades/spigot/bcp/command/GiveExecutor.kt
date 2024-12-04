package io.foxcapades.spigot.bcp.command

import io.foxcapades.spigot.bcp.Server
import io.foxcapades.spigot.bcp.compress.Compressibles
import io.foxcapades.spigot.bcp.compress.CompressionLevel
import io.foxcapades.spigot.bcp.compress.compress
import io.foxcapades.spigot.bcp.consts.Default
import io.foxcapades.spigot.bcp.util.get
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
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

    val material = when (val p = args[1].indexOf(':')) {
      -1 -> NamespacedKey(Default.Namespace, args[1])

      Default.Namespace.length ->
        if (args[0].startsWith(Default.Namespace))
          NamespacedKey(Default.Namespace, args[1].substring(p+1))
        else
          NamespacedKey(args[1][0, p], args[1].substring(p+1))

      else -> NamespacedKey(args[1][0, p], args[1].substring(p+1))
    }

    if (material !in Compressibles) {
      sender.sendMessage("${ChatColor.DARK_RED}$material is not compressible")
      return true
    }

    if (args[2].length != 1 || args[2][0] !in '1'..'9') {
      sender.sendMessage("${ChatColor.DARK_RED}invalid compression level")
      return true
    }

    val lvl = CompressionLevel(args[2].toInt())

    val qty = try {
      args[3].toInt()
    } catch (e: Exception) {
      sender.sendMessage("${ChatColor.DARK_RED}qty must be an integer")
      return true
    }

    for (v in player.inventory.addItem(Material.matchMaterial(material.toString())!!.compress(lvl, qty)).values)
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
      else -> emptyList()
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
