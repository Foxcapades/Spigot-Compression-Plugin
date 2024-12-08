package io.foxcapades.bukkit.szip.config

import io.foxcapades.bukkit.szip.Logger
import io.foxcapades.bukkit.szip.util.Observable
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.introspector.BeanAccess
import io.foxcapades.bukkit.szip.Plugin as BP

internal object Config {
  private inline val FileName get() = "config.yml"
  private inline val ResourceName get() = "/${FileName}"

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

    val fileConfig = BP.file(FileName)
      .takeIf { it.exists() }
      ?.inputStream()
      ?.use { yaml.loadAs(it, ConfigValues::class.java) }

    val resourceConfig = javaClass.getResourceAsStream(ResourceName)!!
      .use { yaml.loadAs(it, ConfigValues::class.java) }!!

    Logging.enableTrace = (fileConfig?.traceEnabled ?: resourceConfig.traceEnabled)
    Plugin.version = (fileConfig?.version ?: resourceConfig.version)
      .also { Logger.trace("[CONFIG] config version: %s", it) }
    Translation.locale = (fileConfig?.locale ?: resourceConfig.locale)
      .also { Logger.trace("[CONFIG] locale: %s", it) }
    Items.CompressionTool.texture = (fileConfig?.zipTool?.texture
      ?: resourceConfig.zipTool?.texture)
      .also { Logger.trace("[CONFIG] texture: %s", it) }
    Items.CompressionTool.recipe = (fileConfig?.zipTool?.recipe
      ?: resourceConfig.zipTool?.recipe)
      .also { Logger.trace("[CONFIG] recipe: %s", it) }

    if (fileConfig == null)
      BP.file(FileName)
        .outputStream()
        .use { out -> javaClass.getResourceAsStream(ResourceName)!!.use { it.transferTo(out) } }

    Logging.commit(iteration)
    Translation.commit(iteration)
    Plugin.commit(iteration)
    Items.CompressionTool.commit(iteration)
  }
}

