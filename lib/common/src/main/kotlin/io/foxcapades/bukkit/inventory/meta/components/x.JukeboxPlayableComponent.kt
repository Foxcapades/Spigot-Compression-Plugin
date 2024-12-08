@file:JvmName("JukeboxPlayableComponentExtensions")
package io.foxcapades.bukkit.inventory.meta.components

import io.foxcapades.bukkit.potion.toJsonSerializable
import org.bukkit.inventory.meta.components.JukeboxPlayableComponent

private const val KeySong = "song-key"
private const val KeyShow = "show-song"

fun JukeboxPlayableComponent.toJsonSerializable(): Map<String, Any> =
  HashMap<String, Any>(2).also { out ->
    out[KeySong] = songKey.toString()
    out[KeyShow] = isShowInTooltip
  }
