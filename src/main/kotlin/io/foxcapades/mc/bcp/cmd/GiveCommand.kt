package io.foxcapades.mc.bcp.cmd

import io.foxcapades.mc.bcp.Server
import io.foxcapades.mc.bcp.consts.Default
import io.foxcapades.mc.bcp.consts.Permission
import io.foxcapades.mc.bcp.util.ArrayView
import io.foxcapades.mc.bcp.util.get
import io.foxcapades.mc.bcp.zip.Compressibles
import io.foxcapades.mc.bcp.zip.CompressionLevel
import io.foxcapades.mc.bcp.zip.compress
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

@Suppress("NOTHING_TO_INLINE")
internal class GiveCommand : ParameterizedSubcommand() {
  private val levels = Array(9) { ('1'.code + it).toChar().toString() }

  override val name: String
    get() = "give"

  override val permission: String
    get() = Permission.GiveCommand

  override val parameters: Array<Parameter>
    get() = arrayOf(
      Parameter("player", true) { playerNameStream().filter { it.startsWith(it) } },
      Parameter("item", true, ::filterCompressibles),
      Parameter("level", true) { levels.asSequence() },
      Parameter("qty", false) { sequenceOf("1", "64") }
    )

  override fun tryExecuteSafe(
    sender: CommandSender,
    command: Command,
    alias: String,
    args: ArrayView<out String>
  ): CommandResult {
    // Find the target player by name or return false
    val player = playerStream()
      .filter { it.playerListName == args[0] }
      .firstOrNull()
      ?: return badParam(0, "unknown or offline player")

    val material = when (val p = args[1].indexOf(':')) {
      -1 -> NamespacedKey(Default.Namespace, args[1])

      Default.Namespace.length ->
        if (args[0].startsWith(Default.Namespace))
          NamespacedKey(Default.Namespace, args[1].substring(p+1))
        else
          NamespacedKey(args[1][0, p], args[1].substring(p+1))

      else -> NamespacedKey(args[1][0, p], args[1].substring(p+1))
    }

    if (material !in Compressibles)
      return badParam(1, "$material is not compressible")

    if (args[2].length != 1 || args[2][0] !in '1'..'9')
      return badParam(2, "invalid compression level")

    val lvl = CompressionLevel(args[2].toInt())

    val qty = try {
      args.takeIf { it.size > 3 }?.get(3)?.toInt()
    } catch (e: Exception) {
      return badParam(3, "qty must be an integer")
    } ?: 1

    for (v in player.inventory.addItem(Material.matchMaterial(material.toString())!!.compress(lvl, qty)).values)
      player.world.dropItem(player.location, v)

    return ok()
  }

  override fun toString() = "/bcp $name"

  private inline fun playerStream() = Server.onlinePlayers.asSequence()

  private inline fun playerNameStream() = playerStream().map { it.playerListName }

  private inline fun filterCompressibles(text: String): Sequence<String> {
    val primaryHits = ArrayList<String>(64)
    val secondaryHits = ArrayList<String>(64)
    val nsHits = ArrayList<String>(64)

    var totalHits = 0

    for ((ns, key) in Compressibles) {
      if (key.startsWith(text)) {
        primaryHits.add(key)
        secondaryHits.add("$ns:$key")
        totalHits++
      } else if (ns.startsWith(text)) {
        nsHits.add("$ns:key")
        totalHits++
      }

      if (totalHits >= 32)
        break
    }

    return sequence {
      primaryHits.forEach { yield(it) }
      secondaryHits.forEach { yield(it) }
      nsHits.forEach { yield(it) }
    }.take(32)
  }
}
