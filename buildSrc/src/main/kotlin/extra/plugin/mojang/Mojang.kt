package extra.plugin.mojang

import groovy.json.JsonSlurper
import java.net.URI
import java.util.Base64

@JvmInline
value class UserID internal constructor(internal val raw: String) {
  override fun toString() = raw
}

@JvmInline
value class TextureRef internal constructor(internal val url: String) {
  override fun toString() = url
}

object Mojang {
  private val JSON = JsonSlurper()

  private val UserLookupURL = URI("https://api.mojang.com/users/profiles/minecraft/")
  private val SessionLookupURL = URI("https://sessionserver.mojang.com/session/minecraft/profile/")

  fun lookupUserID(username: String) =
    @Suppress("UNCHECKED_CAST")
    UserID((JsonSlurper().parse(UserLookupURL.resolve(username).toURL()) as Map<String, String>)["id"]!!)

  fun lookupTextureData(userID: UserID) =
    @Suppress("UNCHECKED_CAST")
    (JSON.parse(SessionLookupURL.resolve(userID.raw).toURL()) as Map<String, Any>)["properties"]
      ?.let { it as Collection<Map<String, String>> }
      ?.firstOrNull { it["name"] == "textures" }
      ?.let { it["value"] }
      ?.let { Base64.getDecoder().decode(it) }
      ?.let { JSON.parse(it) as Map<String, Map<String, Map<String, String>>> }
      ?.let { it["textures"] }
      ?.let { it["SKIN"] }
      ?.let { it["url"] }
      ?.let(::TextureRef)

  fun encodeTextureData(textureRef: TextureRef) =
    Base64.getEncoder().encodeToString("""{"textures":{"SKIN":{"url":"${textureRef.url}"}}}""".toByteArray())!!

  fun buildItemMeta(textureRef: TextureRef) =
    """[profile={properties:[{name:textures,value:"${encodeTextureData(textureRef)}"}]}]"""
}
