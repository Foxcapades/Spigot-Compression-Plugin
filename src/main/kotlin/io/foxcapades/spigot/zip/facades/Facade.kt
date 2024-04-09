@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.spigot.zip.facades

import io.foxcapades.spigot.zip.BlockCompressionPlugin
import org.bukkit.NamespacedKey

/**
 * Passthrough facade over this plugin.
 */
internal object Facade/* : Plugin*/ {

  var enableTrace = true

  val dataFolder get() = io.foxcapades.spigot.zip.BlockCompressionPlugin.instance!!.dataFolder

  val server get() = io.foxcapades.spigot.zip.BlockCompressionPlugin.instance!!.server

  val name get() = io.foxcapades.spigot.zip.BlockCompressionPlugin.instance!!.name

  inline fun logInfo(log: String) = io.foxcapades.spigot.zip.BlockCompressionPlugin.instance!!.logger.info(log)

  inline fun logTrace(log: String, vararg fill: Any?) {
    if (!enableTrace)
      return

    if (fill.isNotEmpty())
      io.foxcapades.spigot.zip.BlockCompressionPlugin.instance!!.logger.info("[TRACE] ${log.format(*fill)}")
    else
      io.foxcapades.spigot.zip.BlockCompressionPlugin.instance!!.logger.info("[TRACE] $log")
  }

  inline fun key(key: String) = NamespacedKey(io.foxcapades.spigot.zip.BlockCompressionPlugin.instance!!, key)

  fun runTask(op: () -> Unit) {
    io.foxcapades.spigot.zip.BlockCompressionPlugin.instance!!.server.scheduler.runTask(io.foxcapades.spigot.zip.BlockCompressionPlugin.instance!!, op)
  }
}
