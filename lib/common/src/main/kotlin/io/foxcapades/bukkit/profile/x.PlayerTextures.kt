@file:JvmName("PlayerTexturesExtensions")
package io.foxcapades.bukkit.profile

import org.bukkit.Bukkit
import org.bukkit.profile.PlayerTextures
import org.bukkit.profile.PlayerTextures.SkinModel
import java.net.URI
import java.util.logging.Logger

private const val KeySkin = "skin"
private const val KeyModel = "model"
private const val KeyCape = "cape"

val DefaultSkinModel = SkinModel.CLASSIC

fun PlayerTextures.toJsonSerializable(): Map<String, Any> =
  HashMap<String, Any>(3).also { out ->
    skin?.also { url ->
      out[KeySkin] = url.toString()
      skinModel.takeUnless { it == DefaultSkinModel }?.also { out[KeyModel] = it }
    }
    cape?.also { url -> out[KeyCape] = url.toString() }
  }

fun PlayerTextures.applySerialized(values: Map<String, Any>, logger: Logger? = null): PlayerTextures = apply {
  @Suppress("NAME_SHADOWING")
  val logger = logger ?: Bukkit.getLogger()

  values[KeySkin]?.also { url ->
    try {
      setSkin(URI(url as String).toURL(), values[KeyModel]?.let { model ->
        try {
          SkinModel.valueOf(model as String)
        } catch (e: Throwable) {
          logger.warning { "invalid PlayerTextures.skinModel value:\n${e.stackTraceToString()}" }
          null
        }
      } ?: DefaultSkinModel)
    } catch (e: Throwable) {
      logger.warning { "failed to set PlayerTextures.skin value:\n${e.stackTraceToString()}" }
    }
  }

  values[KeyCape]?.also { url -> try {
    cape = URI(url as String).toURL()
  } catch (e: Throwable) {
    logger.warning { "failed to set PlayerTextures.cape value:\n${e.stackTraceToString()}" }
  } }
}
