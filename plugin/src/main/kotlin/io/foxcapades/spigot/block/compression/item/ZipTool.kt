package io.foxcapades.spigot.block.compression.item

import io.foxcapades.spigot.block.compression.Plugin
import io.foxcapades.spigot.block.compression.config.RecipeConfig
import io.foxcapades.spigot.block.compression.consts.Default
import io.foxcapades.spigot.block.compression.i18n.I18N
import io.foxcapades.spigot.block.compression.util.then
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Recipe
import org.bukkit.inventory.ShapedRecipe

internal object ZipTool {
  private val ws = Regex("\\s+")

  const val ModelData = 666

  fun create() = ItemStack(Material.ENCHANTED_BOOK).apply { itemMeta = createMeta() }

  @Suppress("NOTHING_TO_INLINE")
  private inline fun ItemStack.createMeta() = itemMeta!!.apply {
    setCustomModelData(ModelData)
    setDisplayName(I18N.zipItemName())
    lore = listOf(I18N.zipItemLore())
    setMaxStackSize(1)
  }

  fun parseRecipe(config: RecipeConfig): Recipe {
    return ShapedRecipe(Plugin.newKey("zip_tool"), create()).apply {
      val lines = arrayOf(
        ByteArray(3) { 32 },
        ByteArray(3) { 32 },
        ByteArray(3) { 32 },
      )

      val chars = config.items.asSequence()
        .associate { (k, v) -> k[0].code.toByte() to v?.let(NamespacedKey::fromString) }

      val layout = RecipeLayout(config.layout)

      var line = -1
      for (i in layout.indices) {
        val mod = i % 3

        if (mod == 0)
          line++

        if (!chars.containsKey(layout[i]))
          throw IllegalStateException("invalid zip tool recipe: unmapped character '%c'".format(layout[i]))

        chars[layout[i]]
          ?.takeUnless { it.namespace.lowercase() == Default.Namespace && key.key.lowercase() == "air" }
          ?.then { lines[line][mod] = layout[i] }
      }

      shape(String(lines[0]), String(lines[1]), String(lines[2]))

      for ((k, v) in config.items)
        if (v != null)
          setIngredient(k[0], Material.matchMaterial(v)!!)
    }
  }

  @JvmInline
  private value class RecipeLayout(val bytes: ByteArray) {
    inline val indices get() = bytes.indices

    init {
      if (bytes.size != 9) {
        throw IllegalArgumentException("recipe layouts must contain exactly 9 letters or numbers")
      }
    }

    constructor(configString: String) : this(ws.replace(configString, "").toByteArray())

    operator fun iterator() = iterator {
      for (b in bytes)
        yield(b)
    }

    @Suppress("NOTHING_TO_INLINE")
    inline operator fun get(index: Int) = bytes[index]
  }
}
