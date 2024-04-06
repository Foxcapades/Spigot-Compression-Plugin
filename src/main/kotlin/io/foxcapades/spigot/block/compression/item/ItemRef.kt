package io.foxcapades.spigot.block.compression.item

internal data class ItemRef(val key: String) {
  companion object {
    fun mc(key: String) = ItemRef("minecraft:$key")
  }
}
