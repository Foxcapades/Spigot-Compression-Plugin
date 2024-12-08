package io.foxcapades.bukkit.szip.zip

import io.foxcapades.bukkit.szip.Plugin
import io.foxcapades.bukkit.szip.Server
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType

private val KeyZipLevel = Plugin.newKey("level")
private val KeyOgData   = Plugin.newKey("og_data")
private val KeyOgType   = Plugin.newKey("og_type")

// region Getters

/**
 * Tests whether the target ItemStack is compressed.
 *
 * Tests both the current (v2) compression keys, then falls back to the legacy
 * (v1) keys.
 */
internal inline val ItemStack.isCompressed
  get() = isCompressed2 || isCompressed1

private inline val ItemStack.isCompressed2
  get() = itemMeta?.compressionLevel2
    ?.let { it > CompressionLevel.Zero }
    ?: false

/**
 * Fetches the compression level of the target ItemStack instance.
 */
internal inline val ItemStack.compressionLevel
  get() = itemMeta?.compressionLevel ?: CompressionLevel.Zero

/**
 * Fetches the compression level of the target ItemMeta instance.
 *
 * Looks for both the current (v2) and legacy (v1) compression keys.
 */
internal inline val ItemMeta.compressionLevel
  get() = compressionLevel2 ?: compressionLevel1 ?: CompressionLevel.Zero

private inline val ItemMeta.compressionLevel2
  get() = persistentDataContainer[KeyZipLevel, PersistentDataType.BYTE]
    ?.let(::CompressionLevel)

// endregion Getters

internal inline fun ItemStack.compress() =
  when {
    isCompressed2 -> incrementCompression2()
    isCompressed1 -> incrementCompression1()
    else          -> initialCompress()
  }

//private inline fun ItemStack.

private inline fun ItemStack.incrementCompression2(): ItemStack {
  itemMeta = itemMeta!!.also { meta ->
    val ogType = Material.getMaterial(meta.persistentDataContainer[KeyOgType, PersistentDataType.STRING]!!)!!
//    Server.itemFactory.getItemMeta(ogType)!!.value
    val ogMeta = meta.persistentDataContainer[KeyOgData, PersistentDataType.TAG_CONTAINER]


    meta.displayName
  }
  TODO()
}

/**
 * Increases and converts legacy v1 compressed [ItemStack] into a new v2 stack.
 */
internal inline fun ItemStack.incrementCompression1(): ItemStack {
  return compressOld(compressionLevel.next, 1)
}

private inline fun ItemStack.initialCompress(): ItemStack {
  itemMeta = getOrCreateMeta().apply {
    val oldMeta = this.asComponentString
  }
  TODO()
}

private inline fun ItemStack.getOrCreateMeta() = itemMeta
  ?: Server.itemFactory.getItemMeta(type)
  ?: throw IllegalStateException("failed to create meta for type $type")

private inline fun String.huntDisplayName(): String= ""
