@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.spigot.block.compression.files

import io.foxcapades.spigot.block.compression.facades.Facade
import java.io.*

internal object FileManager {
  const val BlockConfigFileName  = "compressible-blocks.txt"
  const val ItemConfigFileName   = "compressible-items.txt"
  const val GlobalConfigFileName = "config.yml"

  fun createLocalConfigsIfNeeded() {
    ensureDataFolder()
    ensureBlockConfig()
    ensureItemConfig()
    ensureGlobalConfig()
  }

  inline fun getBlockConfig() = file(BlockConfigFileName)

  inline fun getItemConfig() = file(ItemConfigFileName)

  private inline fun ensureBlockConfig() {
    if (!file(BlockConfigFileName).exists())
      copyFromInternal(BlockConfigFileName)
  }

  private inline fun ensureItemConfig() {
    if (!file(ItemConfigFileName).exists())
      copyFromInternal(ItemConfigFileName)
  }

  private inline fun ensureDataFolder() {
    if (!Facade.dataFolder.exists())
      Facade.dataFolder.mkdir()
  }

  private inline fun ensureGlobalConfig() {
    if (!file(GlobalConfigFileName).exists())
      copyFromInternal(GlobalConfigFileName)
  }

  private inline fun copyFromInternal(fileName: String) {

    // Get a handle on the external (data folder) copy of the current file.
    val file = File(Facade.dataFolder, fileName)

    // Create an empty file.
    file.createNewFile()

    // Copy the contents of the jar resource to the data folder.
    BufferedOutputStream(FileOutputStream(file)).use { output ->
      BufferedInputStream(Facade::class.java.getResourceAsStream("/$fileName")!!).use { input ->
        input.copyTo(output)
        output.flush()
      }
    }
  }

  private inline fun file(name: String) = File(Facade.dataFolder, name)
}