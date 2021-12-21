package io.foxcapades.spigot.block.compression.compressible

import io.foxcapades.spigot.block.compression.facades.Facade
import io.foxcapades.spigot.block.compression.files.FileManager
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import java.io.*
import java.util.Scanner
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

internal object Compressibles {

  @JvmStatic
  val allowed: Set<String> = HashSet(1024)

  @JvmStatic
  operator fun contains(namespace: String?) = namespace in allowed

  @JvmStatic
  operator fun contains(namespace: NamespacedKey?) = namespace?.toString() in allowed

  @JvmStatic
  operator fun contains(mat: Material?) = mat?.key?.toString() in allowed

  @JvmStatic
  operator fun contains(item: ItemStack?) = item?.type?.key?.toString() in allowed

  @JvmStatic
  fun parseConfig(file: File): List<String> {
    val out = ArrayList<String>(1024)

    BufferedInputStream(FileInputStream(file)).use {
      Scanner(it).forEach { line -> if (line.isNotBlank()) { out.add(line) } }
    }

    return out
  }

  @JvmStatic
  fun clear() {
    (allowed as HashSet).clear()
  }

  @JvmStatic
  fun populate() {
    Facade.logInfo("Loading compressed block/item configuration.")
    (allowed as MutableSet).addAll(parseConfig(FileManager.getBlockConfig()))
    allowed.addAll(parseConfig(FileManager.getItemConfig()))
  }

  @JvmStatic
  fun reload() {
    clear()
    populate()
  }
}