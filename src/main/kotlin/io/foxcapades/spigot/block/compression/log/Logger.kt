package io.foxcapades.spigot.block.compression.log

import io.foxcapades.spigot.block.compression.Plugin
import io.foxcapades.spigot.block.compression.config.PluginConfig

@Suppress("NOTHING_TO_INLINE")
internal object Logger {
  inline fun warn(log: String, arg: Any?) = Plugin.logger.warning { log.format(arg) }

  inline fun info(log: String) = Plugin.logger.info(log)

  inline fun info(log: String, arg1: Any?) = Plugin.logger.info { log.format(arg1) }

  inline fun trace(log: String, arg1: Any?, arg2: Any?) {
    if (PluginConfig.TraceEnabled)
      Plugin.logger.info { "[TRACE] ${log.format(arg1, arg2)}" }
  }
}
