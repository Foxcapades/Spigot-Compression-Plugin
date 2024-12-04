@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.spigot.block.compression.compress

import io.foxcapades.spigot.block.compression.Plugin
import io.foxcapades.spigot.block.compression.Server
import io.foxcapades.spigot.block.compression.consts.MetaKey
import io.foxcapades.spigot.block.compression.ext.get
import io.foxcapades.spigot.block.compression.i18n.I18N
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType

internal inline val ItemStack.isCompressed get() = compressionLevel() != CompressionLevel.Zero

internal inline fun <T> ItemStack.ifCompressed(fn: ItemStack.(CompressionLevel) -> T) =
  compressionLevel().takeUnless { it == CompressionLevel.Zero }?.also { fn(it) }

internal inline fun ItemStack.compressionLevel() =
  itemMeta?.get(MetaKey.CompressionLevel, PersistentDataType.BYTE)
    ?.let(::CompressionLevel)
    ?: CompressionLevel.Zero

internal inline fun Material.compress(lvl: CompressionLevel, qty: Int) = ItemStack(this).also {
  it.amount = qty
  it.applyCompressionTo(lvl, (it.itemMeta ?: Server.itemFactory.getItemMeta(this) ?: throw IllegalStateException()))
}

internal inline fun ItemStack.compress(lvl: CompressionLevel, qty: Int) = ItemStack(this).also {
  it.amount = qty
  applyCompressionTo(lvl, (itemMeta ?: Server.itemFactory.getItemMeta(type) ?: throw IllegalStateException()))
}

internal inline fun ItemStack.applyCompressionTo(lvl: CompressionLevel, out: ItemMeta): ItemMeta {
  return out.also { meta ->
    if (lvl == CompressionLevel.Zero) {
      meta.persistentDataContainer.remove(Plugin.newKey(MetaKey.CompressionLevel))
      meta.setDisplayName(null)
      meta.lore = emptyList()
      meta.setMaxStackSize(null)
    } else {
      val name = if (type.isBlock) I18N.blockName(type) else I18N.itemName(type)
      meta.setDisplayName(I18N.fillNameFor(lvl, name))
      meta.lore = listOf(I18N.fillLoreFor(lvl, name))
      meta.setMaxStackSize(64)
    }
  }
}

internal inline fun ItemStack.ifCompressible(action: (ItemStack) -> Unit) {
  if (this in Compressibles)
    action(this)
}
