package io.foxcapades.bukkit.szip.item

import io.foxcapades.bukkit.szip.Plugin
import io.foxcapades.bukkit.szip.i18n.I18N
import io.foxcapades.bukkit.szip.unsafe.decodeItemStack
import io.foxcapades.bukkit.szip.unsafe.encode
import io.foxcapades.bukkit.szip.util.requireMeta
import io.foxcapades.bukkit.szip.zip.*
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType

private val LevelKey = Plugin.newKey("zip_lvl")
private val LegacyLevelKey = NamespacedKey("block-compression", "bcp_lvl")

private val DisplayNameKey = Plugin.newKey("dname")
private val ItemNameKey = Plugin.newKey("iname")
private val OriginalData = Plugin.newKey("data")


class ZipStack private constructor(
  val rawStack: ItemStack,
) {
  val compressionLevel: CompressionLevel
    get() = rawStack.compressionLevel

  @JvmOverloads
  fun decompress(levels: Int = 1): ZipStack {
    var lvl = rawStack.compressionLevel

    if (lvl == CompressionLevel.Zero)
      return this

    var steps = 0
    while (steps < levels && lvl.hasPrevious) {

    }
  }

  private fun reconstruct(): ItemStack {
    decodeItemStack(rawStack.itemMeta!!.persistentDataContainer[OriginalData, PersistentDataType.BYTE_ARRAY]!!)
      ?: throw IllegalStateException("failed to reconstruct item stack from encoded data!")
  }

  companion object {
    @JvmStatic
    fun compress(stack: ItemStack, qty: Int) =
      create(stack, CompressionLevel(1), qty)

    @JvmStatic
    fun create(stack: ItemStack, level: CompressionLevel) =
      create(stack, level, 1)

    @JvmStatic
    fun create(stack: ItemStack, level: CompressionLevel, qty: Int): ZipStack {
      if (stack.compressionLevel != CompressionLevel.Zero)
        throw IllegalArgumentException("given item stack is already compressed!")

      val stackData = stack.clone()
        .apply {
          // don't keep the original quantity, we only really care about the
          // metadata.
          amount = 1
        }
        .encode()

      val newStack = new(qty).also { it.itemMeta!!.apply {
        persistentDataContainer[LevelKey, PersistentDataType.BYTE] = level.value.toByte()
        persistentDataContainer[OriginalData, PersistentDataType.BYTE_ARRAY] = stackData

        if (stack.hasItemMeta()) {
          populateFrom(stack.itemMeta!!, it.type, level)
        } else {
          populateNew(it.type, level)
        }
      } }

      return ZipStack(newStack)
    }

    private fun ItemStack.recompress(current: CompressionLevel, target: CompressionLevel): ZipStack {}

    private fun ItemStack.recompressLegacy(current: CompressionLevel, target: CompressionLevel): ZipStack {}

    private fun new(qty: Int): ItemStack =
      ItemStack(Material.BARREL, qty).also { it.itemMeta = it.requireMeta().apply {
        setEnchantmentGlintOverride(true)
      } }

    /**
     * Fetches the compression level of the target ItemStack instance.
     */
    private inline val ItemStack.compressionLevel
      get() = itemMeta?.compressionLevel ?: CompressionLevel.Zero

    private inline val ItemStack.compressionLevel1
      get() = itemMeta?.compressionLevel1 ?: CompressionLevel.Zero

    private inline val ItemStack.compressionLevel2
      get() = itemMeta?.compressionLevel2 ?: CompressionLevel.Zero

    /**
     * Fetches the compression level of the target ItemMeta instance.
     *
     * Looks for both the current (v2) and legacy (v1) compression keys.
     */
    private inline val ItemMeta.compressionLevel
      get() = compressionLevel2 ?: compressionLevel1 ?: CompressionLevel.Zero

    private inline val ItemMeta.compressionLevel1
      get() = persistentDataContainer[LegacyLevelKey, PersistentDataType.BYTE]
        ?.let(::CompressionLevel)

    private inline val ItemMeta.compressionLevel2
      get() = persistentDataContainer[LevelKey, PersistentDataType.BYTE]
        ?.let(::CompressionLevel)

    private fun ItemMeta.populateFrom(old: ItemMeta, type: Material, level: CompressionLevel) {
      val name = if (old.hasDisplayName()) {
        persistentDataContainer[DisplayNameKey, PersistentDataType.STRING] = old.displayName
        old.displayName
      } else if (old.hasItemName()) {
        persistentDataContainer[ItemNameKey, PersistentDataType.STRING] = old.itemName
        old.itemName
      } else if (type.isBlock) {
        I18N.blockName(type)
      } else {
        I18N.itemName(type)
      }

      setDisplayName(I18N.fillNameFor(level, name))

      lore = if (old.hasLore()) {
        listOf(I18N.fillLoreFor(level, name)) + old.lore!!
      } else {
        listOf(I18N.fillLoreFor(level, name))
      }
    }

    private fun ItemMeta.populateNew(type: Material, level: CompressionLevel) {
      val name = if (type.isBlock) {
        I18N.blockName(type)
      } else {
        I18N.itemName(type)
      }

      setDisplayName(I18N.fillNameFor(level, name))
      lore = listOf(I18N.fillLoreFor(level, name))
    }
  }
}
