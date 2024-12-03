package io.foxcapades.spigot.block.compression.consts

internal object FileName {
  const val DisallowedItemList = "disabled-items.txt"
  const val AllowedItemList    = "enabled-items.txt"

  const val DisallowedBlockList = "disabled-blocks.txt"
  const val AllowedBlockList    = "enabled-blocks.txt"

  const val BlockListResource = "/blocks/minecraft.tsv"
  const val ItemListResource  = "/items/minecraft.tsv"
}
