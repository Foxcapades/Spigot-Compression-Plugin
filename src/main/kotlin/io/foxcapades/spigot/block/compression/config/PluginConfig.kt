package io.foxcapades.spigot.block.compression.config

import io.foxcapades.spigot.block.compression.Plugin
import io.foxcapades.spigot.block.compression.item.ZipTool
import io.foxcapades.spigot.block.compression.log.Logger
import org.bukkit.inventory.Recipe
import org.yaml.snakeyaml.LoaderOptions
import org.yaml.snakeyaml.TypeDescription
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor
import org.yaml.snakeyaml.introspector.BeanAccess
import org.yaml.snakeyaml.introspector.Property
import kotlin.reflect.jvm.javaMethod

internal object PluginConfig {
  private const val KeyTraceEnabled = "trace-logging"
  private const val KeyZipTool      = "zip-tool"
  private const val ConfigFileName  = "config.yml"

  private const val FallbackTextureID = "6e78c297c065e5a7e42fbe4bfeef81797e2bab95cce3278640d3df29e18d14dd"

  private inline val ConfigResourcePath get() = "/$ConfigFileName"

  private val yamlParser = Yaml(DummyConfigConstructor()).apply { setBeanAccess(BeanAccess.FIELD) }

  private var actualRecipe: Any? = null

  var TraceEnabled = false
    private set

  val ZipToolRecipe: Recipe
    get() = when (val a = actualRecipe) {
      is RecipeConfig -> ZipTool.parseRecipe(a).also { actualRecipe = it }
      is Recipe       -> actualRecipe as Recipe
      else            -> throw IllegalStateException("PluginConfig was accessed before it was initialized")
    }

  var ZipToolTexture = FallbackTextureID
    private set

  init {
    writeConfigFile()
  }

  fun reload() {
    Logger.info("loading plugin configuration")
    (Plugin.file(ConfigFileName).takeIf { it.exists() }
      ?.inputStream()
      ?: javaClass.getResourceAsStream(ConfigResourcePath)!!)
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
      .use { out ->
        javaClass.getResourceAsStream(ConfigResourcePath)!!.use { it.transferTo(out) }
        out.flush()
      }
    Logger.info("created config file %s", "${Plugin.dataFolder.name}/$ConfigFileName")
  }

  private class DummyConfig(
    var version: String = "",
    var texture: String = FallbackTextureID,
    var traceLoggingEnabled: Boolean = false,
    var recipes: Map<String, RecipeConfig> = emptyMap(),
  )

  private class DummyMethodProperty(val delegate: Property) : Property(delegate.name, delegate.type) {
    override fun getActualTypeArguments(): Array<Class<*>> = delegate.actualTypeArguments

    override fun set(`object`: Any?, value: Any?) {
      (`object` as DummyConfig).traceLoggingEnabled = value as Boolean
    }

    override fun get(`object`: Any?): Any = delegate.get(`object`)

    override fun getAnnotations(): MutableList<Annotation> = delegate.annotations

    override fun <A : Annotation?> getAnnotation(annotationType: Class<A>?): A = delegate.getAnnotation(annotationType)

    override fun isWritable() = true
  }

  private class DummyConfigConstructor
    : Constructor(
      object : TypeDescription(DummyConfig::class.java) {
        override fun getProperty(name: String?): Property {
          return super.getProperty(name).let {
            if (name == KeyTraceEnabled)
              DummyMethodProperty(it)
            else
              it
          }
        }
      }.apply {
        substituteProperty(
          KeyTraceEnabled,
          Boolean::class.java,
          DummyConfig::traceLoggingEnabled.getter.javaMethod!!.name,
          DummyConfig::traceLoggingEnabled.setter.javaMethod!!.name,
        )
      }
    , LoaderOptions().apply { isProcessComments = false }
    )
}
