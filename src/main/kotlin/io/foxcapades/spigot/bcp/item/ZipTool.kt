package io.foxcapades.spigot.bcp.item

import io.foxcapades.spigot.bcp.Plugin
import io.foxcapades.spigot.bcp.Server
import io.foxcapades.spigot.bcp.config.Config
import io.foxcapades.spigot.bcp.config.RecipeConfigValues
import io.foxcapades.spigot.bcp.consts.Default
import io.foxcapades.spigot.bcp.i18n.I18N
import io.foxcapades.spigot.bcp.Logger
import io.foxcapades.spigot.bcp.util.Observer
import io.foxcapades.spigot.bcp.util.then
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Recipe
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.meta.SkullMeta
import java.net.URI
import java.util.UUID

internal object ZipTool : Observer() {
  private const val TextureURL     = "https://textures.minecraft.net/texture/"
  private const val DefaultTexture = "6e78c297c065e5a7e42fbe4bfeef81797e2bab95cce3278640d3df29e18d14dd"

  private var lastTexture = ""


  val type = Material.PLAYER_HEAD

  val key = Plugin.newKey("zip_tool")

  val itemMeta
    get() = instance.itemMeta as SkullMeta

  lateinit var instance: ItemStack


  init {
    Config.Translation.observe(this)
    Config.Items.CompressionTool.observe(this)
  }


  inline val ItemStack?.isZipTool
    get() = this?.type == type
      && itemMeta?.let { it.hasDisplayName() && it.displayName == I18N.zipItemName() }
      ?: false

  override fun handleChange(change: UInt) {
    instance = ItemStack(type, 1)
    val meta = createMeta()

    Config.Items.CompressionTool.texture
      .let { it ?: DefaultTexture }
      .also { texture ->
        if (texture != lastTexture) {
          meta.ownerProfile = (meta.ownerProfile ?: createProfile())
            .apply {
              textures.skin = URI(TextureURL).resolve(texture).toURL()
              Logger.trace("using texture %s", textures.skin)
            }
          lastTexture = texture
        }
      }

    meta.setDisplayName(I18N.zipItemName())
    meta.lore = listOf(I18N.zipItemLore())

    instance.itemMeta = meta

    Logger.trace("new item data: %s", instance)

    if (Server.getRecipe(key) != null)
      Server.removeRecipe(key)

    Config.Items.CompressionTool.recipe
      ?.let { try {
        parseRecipe(it)
      } catch (e: Throwable) {
        Logger.error("failed to parse compression tool recipe: %s", e.message)
        null
      } }
      ?.also(Server::addRecipe)
  }

  private fun parseRecipe(config: RecipeConfigValues): Recipe {
    return ShapedRecipe(key, instance).apply {
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

  @Suppress("NOTHING_TO_INLINE")
  private inline fun makeTextureString(textureID: String) =
    """{"textures":{"SKIN":{"url":"$TextureURL/$textureID"}}}"""

  private fun createProfile() =
    Server.createPlayerProfile(UUID.fromString("c48188b9-6f87-4591-974e-9bc9dc2d40d7"), "zip_tool")

  private fun createMeta() = itemMeta.apply { setMaxStackSize(1) }
}


