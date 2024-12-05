package io.foxcapades.mc.bcp.config

import io.foxcapades.mc.bcp.Logger
import io.foxcapades.mc.bcp.Plugin

internal object ListFiles {
  private inline val BlockResource get() = "/blocks/minecraft.tsv"
  private inline val ItemResource get() = "/items/minecraft.tsv"

  private inline val AllowedBlockList get() = "enabled-blocks.txt"
  private inline val AllowedItemList get() = "enabled-items.txt"
  private inline val DisallowedBlockList get() = "disabled-blocks.txt"
  private inline val DisallowedItemList get() = "disabled-items.txt"

  fun openBuiltinBlockConfig() = javaClass.getResourceAsStream(BlockResource)!!

  fun openBuiltinItemConfig() = javaClass.getResourceAsStream(ItemResource)!!

  fun openDisallowedItemList() = Plugin.file(DisallowedItemList).inputStream()

  fun openDisallowedBlockList() = Plugin.file(DisallowedBlockList).inputStream()

  fun openAllowedItemList() = Plugin.file(AllowedItemList).inputStream()

  fun openAllowedBlockList() = Plugin.file(AllowedBlockList).inputStream()

  fun createLocalConfigsIfNeeded() {
    if (!Plugin.dataFolder.exists())
      Plugin.dataFolder.mkdir()

    for (file in arrayOf(
      DisallowedItemList,
      DisallowedBlockList,
      AllowedItemList,
      AllowedBlockList
    )) {
      if (Plugin.file(file).createNewFile())
        Logger.info("created config file %s", "${Plugin.dataFolder.name}/$file")
    }
  }
}
