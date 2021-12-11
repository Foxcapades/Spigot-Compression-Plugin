@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.spigot.block.compression.event

import io.foxcapades.spigot.block.compression.compressible.Compressibles
import io.foxcapades.spigot.block.compression.facades.Facade
import io.foxcapades.spigot.block.compression.item.Air
import io.foxcapades.spigot.block.compression.item.compressionLevel
import io.foxcapades.spigot.block.compression.item.isEmpty
import io.foxcapades.spigot.block.compression.wrap.BCCraftingInv
import org.bukkit.event.inventory.InventoryEvent


internal fun InventoryEvent.calculateResult() {
  val top = BCCraftingInv(view.topInventory)

  if (handleSingleStackIfPossible(top)) {
    return
  }

  if (handleAllStacksIfPossible(top))
    return

  top.setResult(null)
}

private inline fun InventoryEvent.handleSingleStackIfPossible(top: BCCraftingInv): Boolean {
  val single = top.getIfSingleStack()

  if (single === Air)
    return false

  if (single !in Compressibles)
    return false

  val lvl = single.compressionLevel()

  if (lvl.hasPrevious) {
    view.topInventory.setItem(0, single.compressionLevel(lvl.previous, 9))
  }

  return true
}

private inline fun InventoryEvent.handleAllStacksIfPossible(top: BCCraftingInv): Boolean {
  val all = top.getIfAllTheSame()

  if (all === Air)
    return false

  if (all !in Compressibles)
    return false

  val lvl = all.compressionLevel()

  if (lvl.hasNext) {
    view.topInventory.setItem(0, all.compressionLevel(lvl.next, 1))
  }

  return true
}
