package io.foxcapades.spigot.zip.block

internal data class BlockRef(val key: String) {
  companion object {
    fun mc(key: String) = BlockRef("minecraft:$key")
  }
}
