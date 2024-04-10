@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.spigot.zip.facades

import io.foxcapades.spigot.zip.BlockCompressionPlugin
import org.bukkit.NamespacedKey

/**
 * Passthrough facade over this plugin.
 */
internal object Facade/* : Plugin*/ {

  var enableTrace = true

  val dataFolder get() = BlockCompressionPlugin.instance.dataFolder

  val server get() = BlockCompressionPlugin.instance.server

  val name get() = BlockCompressionPlugin.instance.name

  inline fun logInfo(log: String) = BlockCompressionPlugin.instance.logger.info(log)

  inline fun logTrace(log: String, vararg fill: Any?) {
    if (!enableTrace)
      return

    if (fill.isNotEmpty())
      BlockCompressionPlugin.instance.logger.info("[TRACE] ${log.format(*fill)}")
    else
      BlockCompressionPlugin.instance.logger.info("[TRACE] $log")
  }

  inline fun logWarn(log: String) = BlockCompressionPlugin.instance.logger.warning(log)

  inline fun key(key: String) = NamespacedKey(BlockCompressionPlugin.instance, key)

  fun runTask(op: () -> Unit) {
    BlockCompressionPlugin.instance.server.scheduler.runTask(BlockCompressionPlugin.instance, op)
  }
}
