package io.foxcapades.spigot.block.compression.config

import io.foxcapades.spigot.block.compression.Plugin
import io.foxcapades.spigot.block.compression.consts.FileName
import io.foxcapades.spigot.block.compression.log.Logger

internal object ListFiles {
  fun openBuiltinBlockConfig() = javaClass.getResourceAsStream(FileName.BlockListResource)!!

  fun openBuiltinItemConfig() = javaClass.getResourceAsStream(FileName.ItemListResource)!!

  fun openDisallowedItemList() = Plugin.file(FileName.DisallowedItemList).inputStream()

  fun openDisallowedBlockList() = Plugin.file(FileName.DisallowedBlockList).inputStream()

  fun openAllowedItemList() = Plugin.file(FileName.AllowedItemList).inputStream()

  fun openAllowedBlockList() = Plugin.file(FileName.AllowedBlockList).inputStream()

  fun createLocalConfigsIfNeeded() {
    if (!Plugin.dataFolder.exists())
      Plugin.dataFolder.mkdir()

    for (file in arrayOf(
      FileName.DisallowedItemList,
      FileName.DisallowedBlockList,
      FileName.AllowedItemList,
      FileName.AllowedBlockList
    )) {
      if (Plugin.file(file).createNewFile())
        Logger.info("created config file %s", "${Plugin.dataFolder.name}/$file")
    }
  }
}
