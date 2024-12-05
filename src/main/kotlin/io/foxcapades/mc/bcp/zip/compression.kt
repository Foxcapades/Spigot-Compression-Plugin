@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.mc.bcp.zip

import io.foxcapades.mc.bcp.Plugin
import io.foxcapades.mc.bcp.Server
import io.foxcapades.mc.bcp.i18n.I18N
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType

private val CompressionKey = Plugin.newKey("bcp_lvl")
private val OriginalNameKey = Plugin.newKey("og_name")

internal inline val ItemStack?.isCompressed
  get() = this?.compressionLevel()?.takeUnless { it == CompressionLevel.Zero } != null

internal inline fun ItemStack.compressionLevel() =
  itemMeta?.persistentDataContainer?.get(CompressionKey, PersistentDataType.BYTE)
    ?.let(::CompressionLevel)
    ?: CompressionLevel.Zero

internal inline fun Material.compress(lvl: CompressionLevel, qty: Int) = ItemStack(this).also {
  it.amount = qty
  it.itemMeta = it.buildMeta(lvl)
}

internal inline fun ItemStack.compress(lvl: CompressionLevel, qty: Int) = ItemStack(this).also {
  it.amount = qty
  it.itemMeta = buildMeta(lvl)
}

internal inline fun ItemStack.ifCompressible(action: (ItemStack) -> Unit) {
  if (this in Compressibles)
    action(this)
}

private inline fun ItemStack.buildMeta(lvl: CompressionLevel): ItemMeta {
  val meta = itemMeta
    ?: Server.itemFactory.getItemMeta(type)
    ?: throw IllegalStateException("failed to create meta for type $type")

  val oldLevel = meta.persistentDataContainer[CompressionKey, PersistentDataType.BYTE]?.toInt() ?: 0

  if (lvl == CompressionLevel.Zero) {
    meta.persistentDataContainer.remove(CompressionKey)
    if (meta.persistentDataContainer.has(OriginalNameKey)) {
      meta.setDisplayName(meta.persistentDataContainer[OriginalNameKey, PersistentDataType.STRING])
      meta.persistentDataContainer.remove(OriginalNameKey)
    } else {
      meta.setDisplayName(null)
    }
    meta.lore = meta.lore?.let { if (it.size > 1) it.subList(1, it.size) else null }
    meta.setMaxStackSize(null)
    meta.setEnchantmentGlintOverride(null)
  } else {
    val name = when {
      // If the stack has an original name, use that
      meta.persistentDataContainer.has(OriginalNameKey) ->
        meta.persistentDataContainer[OriginalNameKey, PersistentDataType.STRING]!!

      // else, if the stack has a custom display name, use that
      meta.hasDisplayName() && oldLevel == 0 -> {
        meta.persistentDataContainer[OriginalNameKey, PersistentDataType.STRING] = meta.displayName
        meta.displayName
      }

      // else, if the stack is of blocks, use the block name
      type.isBlock -> I18N.blockName(type)

      // else, it's an item, use the item name
      else -> I18N.itemName(type)
    }

    meta.persistentDataContainer[CompressionKey, PersistentDataType.BYTE] = lvl.value.toByte()
    meta.setDisplayName(I18N.fillNameFor(lvl, name))
    meta.lore = meta.lore.let {
      if (it.isNullOrEmpty() || it.size == 1 && oldLevel > 0)
        listOf(I18N.fillLoreFor(lvl, name))
      else
        ArrayList<String>(it.size + 1).apply { add(I18N.fillLoreFor(lvl, name)); addAll(it) }
    }
    meta.setMaxStackSize(64)
    meta.setEnchantmentGlintOverride(true)
  }

  return meta
}
