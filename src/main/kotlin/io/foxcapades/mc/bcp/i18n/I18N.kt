package io.foxcapades.mc.bcp.i18n

import io.foxcapades.mc.bcp.Logger
import io.foxcapades.mc.bcp.Plugin
import io.foxcapades.mc.bcp.zip.CompressionLevel
import io.foxcapades.mc.bcp.config.Config
import io.foxcapades.mc.bcp.util.Observable
import org.bukkit.Material
import java.io.InputStream
import java.util.Properties

internal object I18N : Observable() {
  private const val ItemsFileName = "items.properties"
  private const val BlocksFileName = "blocks.properties"
  private const val StacksFileName = "stacks.properties"
  private const val PluginFileName = "plugin.properties"

  private const val DefaultLocale = "en_us"

  private inline val LocalePath
    get() = "locales"

  private inline val LocaleResourcePath
    get() = "/$LocalePath"

  private var bundles: Map<String, Bundle> = emptyMap()

  private var currentLocale = ""

  init {
    Config.Translation.observe {
      if ((Config.Translation.locale ?: DefaultLocale) != currentLocale) {
        reload()
        commit(it)
      }
    }
  }

  private fun reload() {
    if (Config.Translation.locale == null || Config.Translation.locale == DefaultLocale) {
      Logger.info("loading locale %s", DefaultLocale)
      bundles = HashMap<String, Bundle>(1).also { it[DefaultLocale] = loadDefaultBundle() }
    } else {
      Logger.info("loading locales %s and %s", Config.Translation.locale, DefaultLocale)
      bundles = HashMap<String, Bundle>(2).also {
        with(loadDefaultBundle()) {
          it[DefaultLocale] = this
          it[Config.Translation.locale!!] = tryLoadBundle(Config.Translation.locale!!, this)
        }
      }
    }
  }

  fun workbenchName(locale: String = currentLocale): String =
    bundleFor(locale).plugin["workbench.name"]

  fun zipItemName(locale: String = currentLocale): String =
    bundleFor(locale).plugin["ziptool.name"]

  fun zipItemLore(locale: String = currentLocale): String =
    bundleFor(locale).plugin["ziptool.lore"]

  fun itemName(mat: Material, locale: String = currentLocale): String =
    bundleFor(locale).items[mat.toKey()]

  fun blockName(mat: Material, locale: String = currentLocale): String =
    bundleFor(locale).blocks[mat.toKey()]

  /**
   * Returns the raw templates string for the lore text for the given
   * [CompressionLevel].
   *
   * @param lvl `CompressionLevel` whose lore line template string will be
   * retrieved.
   *
   * @return The raw template text for the lore line for the given
   * `CompressionLevel`.
   */
  fun loreTemplateFor(lvl: CompressionLevel, locale: String = currentLocale): String =
    bundleFor(locale).stacks["stack.$lvl.lore"]

  /**
   * Returns the raw templates string for the display name text for the given
   * [CompressionLevel].
   *
   * @param lvl `CompressionLevel` whose display name template string will be
   * retrieved.
   *
   * @return The raw template text for the display name for the given
   * `CompressionLevel`
   */
  fun nameTemplateFor(lvl: CompressionLevel, locale: String = currentLocale): String =
    bundleFor(locale).stacks["stack.$lvl.name"]

  /**
   * Returns the formatted lore text for the given [CompressionLevel] and
   * block/item name.
   *
   * @param lvl `CompressionLevel` whose lore line will be interpolated and
   * returned.
   *
   * @param name Name of the block/item to inject into the lore line string.
   *
   * @return Interpolated lore line for the given `CompressionLevel` and
   * block/item name.
   */
  @Suppress("NOTHING_TO_INLINE")
  inline fun fillLoreFor(lvl: CompressionLevel, name: String, locale: String = currentLocale) =
    loreTemplateFor(lvl, locale).replace("\${name}", name).replace("\${size}", lvl.size.toString())

  /**
   * Returns the formatted display name text for the given [CompressionLevel]
   * and block/item name.
   *
   * @param lvl `CompressionLevel` whose display name will be interpolated and
   * returned.
   *
   * @param name Name of the block/item to inject into the display name string.
   *
   * @return Interpolated display name for the given `CompressionLevel` and
   * block/item name.
   */
  @Suppress("NOTHING_TO_INLINE")
  inline fun fillNameFor(lvl: CompressionLevel, name: String, locale: String = currentLocale) =
    nameTemplateFor(lvl, locale).replace("\${name}", name).replace("\${size}", lvl.size.toString())

  private fun localeExists(localeString: String): Boolean {
    return javaClass.getResource(localeFilePath(localeString, BlocksFileName)) != null
      || javaClass.getResource(localeFilePath(localeString, ItemsFileName)) != null
      || javaClass.getResource(localeFilePath(localeString, PluginFileName)) != null
      || javaClass.getResource(localeFilePath(localeString, StacksFileName)) != null
  }

  private fun tryLoadBundle(locale: String, fallback: Bundle): Bundle {
    var (blocks, items, plugin, stacks) = fallback

    tryLoadProperties(locale, BlocksFileName)?.let { blocks =
      io.foxcapades.mc.bcp.i18n.LocalizationTreeNode(it, blocks)
    }
      ?: Logger.info("falling back to %s locale instead of %s for %s", DefaultLocale, locale, BlocksFileName)
    tryLoadProperties(locale, ItemsFileName)?.let { items = io.foxcapades.mc.bcp.i18n.LocalizationTreeNode(it, items) }
      ?: Logger.info("falling back to %s locale instead of %s for %s", DefaultLocale, locale, ItemsFileName)
    tryLoadProperties(locale, PluginFileName)?.let { plugin =
      io.foxcapades.mc.bcp.i18n.LocalizationTreeNode(it, plugin)
    }
      ?: Logger.info("falling back to %s locale instead of %s for %s", DefaultLocale, locale, PluginFileName)
    tryLoadProperties(locale, StacksFileName)?.let { stacks =
      io.foxcapades.mc.bcp.i18n.LocalizationTreeNode(it, stacks)
    }
      ?: Logger.info("falling back to %s locale instead of %s for %s", DefaultLocale, locale, StacksFileName)

    return Bundle(blocks, items, plugin, stacks)
  }

  private fun loadDefaultBundle() =
    Bundle(
      blocks = io.foxcapades.mc.bcp.i18n.LocalizationTreeNode(requireProperties(DefaultLocale, BlocksFileName)),
      items  = io.foxcapades.mc.bcp.i18n.LocalizationTreeNode(requireProperties(DefaultLocale, ItemsFileName)),
      plugin = io.foxcapades.mc.bcp.i18n.LocalizationTreeNode(requireProperties(DefaultLocale, PluginFileName)),
      stacks = io.foxcapades.mc.bcp.i18n.LocalizationTreeNode(requireProperties(DefaultLocale, StacksFileName)),
    )

  private fun tryLoadProperties(locale: String, file: String) =
    getLocaleFileStream(locale, file)
      ?.use { Properties().apply { load(it) } }

  @Suppress("NOTHING_TO_INLINE", "SameParameterValue")
  private inline fun requireProperties(locale: String, file: String) =
    tryLoadProperties(locale, file)
      ?: throw IllegalStateException("missing required localization file $file for locale $locale")

  @Suppress("NOTHING_TO_INLINE")
  private inline fun getLocaleFileStream(locale: String, file: String): InputStream? =
    Plugin.file("$LocalePath/$locale/$file")
      .takeIf { it.exists() }
      ?.inputStream()
      ?: javaClass.getResourceAsStream("$LocaleResourcePath/$locale/$file")

  @Suppress("NOTHING_TO_INLINE")
  private inline fun localeFilePath(locale: String, file: String) = "$LocalePath/$locale/$file"

  @Suppress("NOTHING_TO_INLINE")
  private inline fun Material.toKey() = if (key.namespace == "minecraft") key.key else "${key.namespace}.${key.key}"

  @Suppress("NOTHING_TO_INLINE")
  private inline fun bundleFor(locale: String) = bundles[locale] ?: bundles[DefaultLocale]!!
}

private data class Bundle(
  val blocks: io.foxcapades.mc.bcp.i18n.LocalizationTreeNode,
  val items: io.foxcapades.mc.bcp.i18n.LocalizationTreeNode,
  val plugin: io.foxcapades.mc.bcp.i18n.LocalizationTreeNode,
  val stacks: io.foxcapades.mc.bcp.i18n.LocalizationTreeNode,
)
