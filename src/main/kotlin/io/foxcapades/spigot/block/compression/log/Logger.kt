package io.foxcapades.spigot.block.compression.log

import io.foxcapades.spigot.block.compression.Plugin
import io.foxcapades.spigot.block.compression.config.Config

@Suppress("NOTHING_TO_INLINE")
internal object Logger {
  inline fun error(log: String) = Plugin.logger.severe(log)
  inline fun error(log: String, arg1: Any?) = Plugin.logger.severe { log.format(arg1) }
  inline fun error(crossinline log: () -> String) = Plugin.logger.severe { log() }

  inline fun warn(log: String, arg: Any?) = Plugin.logger.warning { log.format(arg) }

  inline fun info(log: String) = Plugin.logger.info(log)
  inline fun info(log: String, arg1: Any?) = Plugin.logger.info { log.format(arg1) }
  inline fun info(log: String, arg1: Any?, arg2: Any?) = Plugin.logger.info { log.format(arg1, arg2) }
  inline fun info(log: String, arg1: Any?, arg2: Any?, arg3: Any?) = Plugin.logger.info { log.format(arg1, arg2, arg3) }

  inline fun trace(log: () -> String) {
    if (Config.Logging.enableTrace)
      trace(log())
  }

  inline fun trace(log: String) {
    if (Config.Logging.enableTrace)
      Plugin.logger.info { "[TRACE] $log" }
  }

  inline fun trace(log: String, arg1: Any?) {
    if (Config.Logging.enableTrace)
      Plugin.logger.info { "[TRACE] ${log.format(arg1)}" }
  }

  inline fun trace(log: String, arg1: Any?, arg2: Any?) {
    if (Config.Logging.enableTrace)
      Plugin.logger.info { "[TRACE] ${log.format(arg1, arg2)}" }
  }
}
