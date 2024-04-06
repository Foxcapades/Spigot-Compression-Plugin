package io.foxcapades.spigot.block.compression.block

internal data class BlockRef(val key: String) {
  companion object {
    fun mc(key: String) = BlockRef("minecraft:$key")
  }
}
