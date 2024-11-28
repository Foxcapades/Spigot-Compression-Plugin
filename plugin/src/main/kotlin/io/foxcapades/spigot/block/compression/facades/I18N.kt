package io.foxcapades.spigot.block.compression.facades

import io.foxcapades.spigot.block.compression.compressible.CompressionLevel
import org.bukkit.Material
import java.util.*

internal object I18N {

  private val stacksBundle: ResourceBundle

  private val itemsBundle: ResourceBundle

  private val blocksBundle: ResourceBundle

  init {
    Facade.logInfo("Loading locales.")

    stacksBundle = PropertyResourceBundle(I18N::class.java.getResourceAsStream("/locales/stacks.properties"))
    itemsBundle  = PropertyResourceBundle(I18N::class.java.getResourceAsStream("/locales/items.properties"))
    blocksBundle = PropertyResourceBundle(I18N::class.java.getResourceAsStream("/locales/blocks.properties"))
  }

  fun itemName(mat: Material): String = itemsBundle.getString("${mat.key.namespace}.${mat.key.key}")

  fun blockName(mat: Material): String = blocksBundle.getString("${mat.key.namespace}.${mat.key.key}")

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
  fun loreTemplateFor(lvl: CompressionLevel): String = stacksBundle.getString("stack.$lvl.lore")

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
  fun nameTemplateFor(lvl: CompressionLevel): String = stacksBundle.getString("stack.$lvl.name")

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
  inline fun fillLoreFor(lvl: CompressionLevel, name: String) =
    loreTemplateFor(lvl).replace("\${name}", name).replace("\${size}", lvl.size.toString())

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
  inline fun fillNameFor(lvl: CompressionLevel, name: String) =
    nameTemplateFor(lvl).replace("\${name}", name).replace("\${size}", lvl.size.toString())
}