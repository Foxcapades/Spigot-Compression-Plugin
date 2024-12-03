package io.foxcapades.spigot.block.compression.compress

import io.foxcapades.spigot.block.compression.config.*
import io.foxcapades.spigot.block.compression.consts.Default
import io.foxcapades.spigot.block.compression.log.Logger
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

private typealias CompressionTargets = Set<String>

internal object Compressibles : Iterable<Pair<String, String>> {
  private val mutex = ReentrantReadWriteLock()

  private lateinit var blocksByNamespace: Map<String, CompressionTargets>

  private lateinit var itemsByNamespace: Map<String, CompressionTargets>

  operator fun contains(key: NamespacedKey) = mutex.read {
    blocksByNamespace[key.namespace]?.contains(key.key) ?: false
      || itemsByNamespace[key.namespace]?.contains(key.key) ?: false
  }

  operator fun contains(key: String) = mutex.read {
    blocksByNamespace[Default.Namespace]?.contains(key) ?: false
      || itemsByNamespace[Default.Namespace]?.contains(key) ?: false
  }

  fun contains(namespace: String, key: String) = mutex.read {
    blocksByNamespace[namespace]?.contains(key) ?: false
      || itemsByNamespace[namespace]?.contains(key) ?: false
  }

  operator fun contains(stack: ItemStack) = mutex.read {
    stack.type.key.let {
      blocksByNamespace[it.namespace]?.contains(it.key) ?: false
        || itemsByNamespace[it.namespace]?.contains(it.key) ?: false
    }
  }

  fun toSequence() = sequence {
    blocksByNamespace.forEach { (ns, blocks) -> blocks.forEach { yield(ns to it) } }
    itemsByNamespace.forEach { (ns, items) -> items.forEach { yield(ns to it) } }
  }

  override fun iterator() = toSequence().iterator()

  fun reload() {
    Logger.info("loading compressible item index")

    val mcBlocks = HashMap<String, CompressionTargets>(4)

    mcBlocks[Default.Namespace] = withBuiltinBlockRules { HashSet<String>(1200).apply { it.forEach(::add) } }

    withAllowedBlockRules { it.forEach { (ns, name) ->
      (mcBlocks.computeIfAbsent(ns) { HashSet() } as MutableSet).add(name)
    } }

    withDisallowedBlockRules { it.forEach { (ns, name) -> (mcBlocks[ns] as MutableSet?)?.apply {
      remove(name)
      isEmpty()
      mcBlocks.remove(ns)
    } } }

    Logger.info("found %d compressible blocks", mcBlocks.values.sumOf { it.size })

    val mcItems = HashMap<String, CompressionTargets>(4)

    mcItems[Default.Namespace] = withBuiltinItemRules { HashSet<String>(500).apply { it.forEach(::add) } }

    withAllowedItemRules { it.forEach { (ns, name) ->
      (mcItems.computeIfAbsent(ns) { HashSet() } as MutableSet).add(name)
    } }

    withDisallowedItemRules { it.forEach { (ns, name) -> (mcItems[ns] as MutableSet?)?.apply {
      remove(name)
      isEmpty()
      mcItems.remove(ns)
    } } }

    Logger.info("found %d compressible items", mcItems.values.sumOf { it.size })

    mutex.write {
      blocksByNamespace = mcBlocks
      itemsByNamespace = mcItems
    }
  }
}
