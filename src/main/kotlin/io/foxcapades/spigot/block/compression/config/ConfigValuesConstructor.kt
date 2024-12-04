package io.foxcapades.spigot.block.compression.config

import io.foxcapades.spigot.block.compression.util.YamlProperty
import org.yaml.snakeyaml.LoaderOptions
import org.yaml.snakeyaml.TypeDescription
import org.yaml.snakeyaml.constructor.Constructor
import kotlin.reflect.jvm.javaMethod

private const val KeyTraceEnabled = "trace-logging"
private const val KeyZipTool = "zip-tool"

internal class ConfigValuesConstructor : Constructor(
  object : TypeDescription(ConfigValues::class.java) {
    init {
      substituteProperty(
        KeyTraceEnabled,
        Boolean::class.java,
        ConfigValues::traceEnabled.getter.javaMethod!!.name,
        ConfigValues::traceEnabled.setter.javaMethod!!.name,
      )
      substituteProperty(
        KeyZipTool,
        ZipToolConfigValues::class.java,
        ConfigValues::zipTool.getter.javaMethod!!.name,
        ConfigValues::zipTool.setter.javaMethod!!.name,
      )
    }

    override fun getProperty(name: String) =
      super.getProperty(name).let {
        if (name == KeyTraceEnabled)
          YamlProperty<ConfigValues>(it) { traceEnabled = it as Boolean }
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
