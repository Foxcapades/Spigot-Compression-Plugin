package io.foxcapades.bukkit.szip.config

import org.yaml.snakeyaml.LoaderOptions
import org.yaml.snakeyaml.TypeDescription
import org.yaml.snakeyaml.constructor.Constructor

private const val KeyTraceEnabled = "trace-logging"
private const val KeyZipTool = "zip-tool"

internal class ConfigValuesConstructor : Constructor(
  object : TypeDescription(ConfigValues::class.java) {
    init {
      substituteProperty(KeyTraceEnabled, Boolean::class.java, "getTraceEnabled", "setTraceEnabled")
      substituteProperty(KeyZipTool, ZipToolConfigValues::class.java, "getZipTool", "setZipTool")
    }

    override fun getProperty(name: String) =
      super.getProperty(name).let {
        if (name == KeyTraceEnabled)
          io.foxcapades.bukkit.szip.util.YamlProperty<ConfigValues>(it) { traceEnabled = it as Boolean }
        else
          it
      }
  },
  listOf(
    TypeDescription(ZipToolConfigValues::class.java),
    TypeDescription(RecipeConfigValues::class.java),
  ),
  LoaderOptions().apply { isProcessComments = false },
)
