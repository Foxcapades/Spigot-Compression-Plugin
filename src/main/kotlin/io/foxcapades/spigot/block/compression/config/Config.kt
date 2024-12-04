package io.foxcapades.spigot.block.compression.config

import io.foxcapades.spigot.block.compression.log.Logger
import io.foxcapades.spigot.block.compression.util.Observable
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.introspector.BeanAccess
import io.foxcapades.spigot.block.compression.Plugin as BP

internal object Config {
  const val ConfigFileName  = "config.yml"

  private var iteration = 0u

  object Plugin : Observable() {
    var version: String? = null
  }

  object Logging : Observable() {
    var enableTrace = false
  }

  object Translation : Observable() {
    var locale: String? = null
  }

  object Items {
    object CompressionTool : Observable() {
      var texture: String? = null
      var recipe: RecipeConfigValues? = null
    }
  }

  fun reload() {
    iteration++

    val yaml = Yaml(ConfigValuesConstructor()).apply { setBeanAccess(BeanAccess.FIELD) }

    val fileConfig = BP.file(ConfigFileName)
      .takeIf { it.exists() }
      ?.inputStream()
      ?.use { yaml.loadAs(it, ConfigValues::class.java) }

    val resourceConfig = javaClass.getResourceAsStream("/$ConfigFileName")!!
      .use { yaml.loadAs(it, ConfigValues::class.java) }!!

    Logging.enableTrace = (fileConfig?.traceEnabled ?: resourceConfig.traceEnabled)
      .also { Logger.info("trace enabled: %s", it) }
    Plugin.version = (fileConfig?.version ?: resourceConfig.version)
      .also { Logger.trace("config version: %s", it) }
    Translation.locale = (fileConfig?.locale ?: resourceConfig.locale)
      .also { Logger.trace("locale: %s", it) }
    Items.CompressionTool.texture = (fileConfig?.zipTool?.texture
      ?: resourceConfig.zipTool?.texture)
      .also { Logger.trace("texture: %s", it) }
    Items.CompressionTool.recipe = (fileConfig?.zipTool?.recipe
      ?: resourceConfig.zipTool?.recipe)
      .also { Logger.trace("recipe: %s", it) }

    if (fileConfig == null)
      BP.file(ConfigFileName)
        .outputStream()
        .use { out -> javaClass.getResourceAsStream("/$ConfigFileName")!!.use { it.transferTo(out) } }

    Logging.commit(iteration)
    Translation.commit(iteration)
    Plugin.commit(iteration)
    Items.CompressionTool.commit(iteration)
  }
}

