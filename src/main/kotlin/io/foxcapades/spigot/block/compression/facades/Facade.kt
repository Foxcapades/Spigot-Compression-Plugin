@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.spigot.block.compression.facades

import io.foxcapades.spigot.block.compression.BlockCompressionPlugin
import org.bukkit.NamespacedKey
import java.io.InputStream

/**
 * Passthrough facade over this plugin.
 */
internal object Facade/* : Plugin*/ {

  var enableTrace = true

  val dataFolder get() = BlockCompressionPlugin.instance!!.dataFolder

  val server get() = BlockCompressionPlugin.instance!!.server

  val name get() = BlockCompressionPlugin.instance!!.name

  inline fun logInfo(log: String) = BlockCompressionPlugin.instance!!.logger.info(log)

  inline fun logTrace(log: String) {
    if (enableTrace)
      BlockCompressionPlugin.instance!!.logger.info("[TRACE] $log")
  }

  inline fun key(key: String) = NamespacedKey(BlockCompressionPlugin.instance!!, key)

  fun runTask(op: () -> Unit) {
    BlockCompressionPlugin.instance!!.server.scheduler.runTask(BlockCompressionPlugin.instance!!, op)
  }

  fun runTaskLater(ticks: Long, op: () -> Unit) {
    BlockCompressionPlugin.instance!!.server.scheduler.runTaskLater(BlockCompressionPlugin.instance!!, op, ticks)
  }

  fun getResource(filename: String): InputStream? = BlockCompressionPlugin.instance!!.getResource(filename)
}
