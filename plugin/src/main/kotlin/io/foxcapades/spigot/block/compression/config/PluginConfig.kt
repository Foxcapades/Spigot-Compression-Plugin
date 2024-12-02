package io.foxcapades.spigot.block.compression.config

import io.foxcapades.spigot.block.compression.Plugin
import io.foxcapades.spigot.block.compression.item.ZipTool
import io.foxcapades.spigot.block.compression.log.Logger
import org.bukkit.inventory.Recipe
import org.yaml.snakeyaml.LoaderOptions
import org.yaml.snakeyaml.TypeDescription
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor

internal object PluginConfig {
  private const val KeyTraceEnabled = "trace-logging"
  private const val KeyZipTool      = "zip-tool"
  private const val ConfigFileName = "config.yml"

  private val yamlParser = LoaderOptions()
    .apply { isProcessComments = false }
    .let { Constructor(DummyConfig::class.java, it)
      .apply { addTypeDescription(TypeDescription(DummyConfig::class.java).apply {
        substituteProperty(
          KeyTraceEnabled,
          Boolean::class.javaPrimitiveType,
          "getTraceLoggingEnabled",
          "setTraceLoggingEnabled"
        )
      }) } }
    .let(::Yaml)

  private var actualRecipe: Any? = null

  var TraceEnabled = false
    private set

  val ZipToolRecipe: Recipe
    get() = when (val a = actualRecipe) {
      is RecipeConfig -> ZipTool.parseRecipe(a).also { actualRecipe = it }
      is Recipe       -> actualRecipe as Recipe
      else            -> throw IllegalStateException("PluginConfig was accessed before it was initialized")
    }

  init {
    writeConfigFile()
  }

  fun reload() {
    Logger.info("loading plugin configuration")
    (Plugin.file(ConfigFileName).takeIf { it.exists() }
      ?.inputStream()
      ?: javaClass.getResourceAsStream(ConfigFileName)!!)
      .use { yamlParser.loadAs(it, DummyConfig::class.java) }
      .also {
        TraceEnabled = it.traceLoggingEnabled
        actualRecipe = it.recipes[KeyZipTool]
      }
  }

  private inline fun <reified T : Any> Any?.ifIs(fn: (T) -> Unit) =
    when (this) {
      is T -> fn(this)
      else -> Unit
    }

  private fun writeConfigFile() {
    Plugin.file(ConfigFileName)
      .also { if (it.exists()) return }
      .outputStream()
      .buffered()
      .use { out -> javaClass.getResourceAsStream(ConfigFileName)!!.use { it.transferTo(out) } }
    Logger.info("created config file %s", "${Plugin.dataFolder.name}/$ConfigFileName")
  }

  private class DummyConfig(
    var version: String = "",
    var traceLoggingEnabled: Boolean = false,
    var recipes: Map<String, RecipeConfig> = emptyMap(),
  )
}
