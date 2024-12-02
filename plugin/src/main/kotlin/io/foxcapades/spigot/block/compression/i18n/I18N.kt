package io.foxcapades.spigot.block.compression.i18n

import io.foxcapades.spigot.block.compression.compress.CompressionLevel
import io.foxcapades.spigot.block.compression.log.Logger
import org.bukkit.Material
import java.util.Locale
import java.util.Properties

internal object I18N {
  private const val ItemsFileName = "items.properties"
  private const val BlocksFileName = "blocks.properties"
  private const val StacksFileName = "stacks.properties"
  private const val PluginFileName = "plugin.properties"

  private const val LocalePath = "/locales"

  private val DefaultLocale = Locale.US

  private val bundles: Map<Locale, Bundle>

  init {
    Logger.info("Loading locales.")

    if (Locale.getDefault() == Locale.US) {
      bundles = HashMap(1)
      bundles[Locale.US] = loadDefaultBundle()
    } else {
      bundles = HashMap(2)
      bundles[Locale.US] = loadDefaultBundle()
        .also { bundles[Locale.getDefault()] = tryLoadBundle(Locale.getDefault(), it) }
    }
  }

  fun workbenchName(locale: Locale = Locale.getDefault()): String =
    bundleFor(locale).plugin["workbench.name"]

  fun zipItemName(locale: Locale = Locale.getDefault()): String =
    bundleFor(locale).plugin["ziptool.name"]

  fun zipItemLore(locale: Locale = Locale.getDefault()): String =
    bundleFor(locale).plugin["ziptool.lore"]

  fun itemName(mat: Material, locale: Locale = Locale.getDefault()): String =
    bundleFor(locale).items[mat.toKey()]

  fun blockName(mat: Material, locale: Locale = Locale.getDefault()): String =
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
  fun loreTemplateFor(lvl: CompressionLevel, locale: Locale = Locale.getDefault()): String =
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
  fun nameTemplateFor(lvl: CompressionLevel, locale: Locale = Locale.getDefault()): String =
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
  inline fun fillLoreFor(lvl: CompressionLevel, name: String, locale: Locale = Locale.getDefault()) =
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
  inline fun fillNameFor(lvl: CompressionLevel, name: String, locale: Locale = Locale.getDefault()) =
    nameTemplateFor(lvl, locale).replace("\${name}", name).replace("\${size}", lvl.size.toString())

  private fun localeExists(localeString: String): Boolean {
    return javaClass.getResource(localeFilePath(localeString, BlocksFileName)) != null
      || javaClass.getResource(localeFilePath(localeString, ItemsFileName)) != null
      || javaClass.getResource(localeFilePath(localeString, PluginFileName)) != null
      || javaClass.getResource(localeFilePath(localeString, StacksFileName)) != null
  }

  private fun localePathVariants(locale: Locale) = sequence {
    val variant  = locale.variant.lowercase()
    val country  = locale.country.lowercase()
    val language = locale.language.lowercase()

    yield(language)

    if (country.isNotBlank()) {
      yield("${language}_${country}")

      if (variant.isNotBlank()) {
        yield("${language}_${country}_${variant}")
      }
    }
  }

  private fun tryLoadBundle(locale: Locale, fallback: Bundle): Bundle {
    var (blocks, items, plugin, stacks) = fallback

    for (variant in localePathVariants(locale)) {
      tryLoadProperties(variant, BlocksFileName)?.let { blocks = LocalizationTreeNode(it, blocks) }
      tryLoadProperties(variant, ItemsFileName)?.let { items = LocalizationTreeNode(it, items) }
      tryLoadProperties(variant, PluginFileName)?.let { plugin = LocalizationTreeNode(it, plugin) }
      tryLoadProperties(variant, StacksFileName)?.let { stacks = LocalizationTreeNode(it, stacks) }
    }

    return Bundle(blocks, items, plugin, stacks)
  }

  private fun loadDefaultBundle() =
    Bundle(
      blocks = LocalizationTreeNode(tryLoadProperties("en_us", BlocksFileName)!!),
      items  = LocalizationTreeNode(tryLoadProperties("en_us", ItemsFileName)!!),
      plugin = LocalizationTreeNode(tryLoadProperties("en_us", PluginFileName)!!),
      stacks = LocalizationTreeNode(tryLoadProperties("en_us", StacksFileName)!!),
    )

  private fun tryLoadProperties(localeString: String, file: String) =
    javaClass.getResourceAsStream("$LocalePath/$localeString/$file")
      ?.let { Properties().apply { load(it) } }

  @Suppress("NOTHING_TO_INLINE")
  private inline fun localeFilePath(locale: String, file: String) = "$LocalePath/$locale/$file"

  @Suppress("NOTHING_TO_INLINE")
  private inline fun Material.toKey() = if (key.namespace == "minecraft") key.key else "${key.namespace}.${key.key}"

  @Suppress("NOTHING_TO_INLINE")
  private inline fun bundleFor(locale: Locale) = bundles[locale] ?: bundles[DefaultLocale]!!
}

private data class Bundle(
  val blocks: LocalizationTreeNode,
  val items:  LocalizationTreeNode,
  val plugin: LocalizationTreeNode,
  val stacks: LocalizationTreeNode,
)
