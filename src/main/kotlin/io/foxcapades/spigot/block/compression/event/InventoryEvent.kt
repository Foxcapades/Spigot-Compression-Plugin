@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.spigot.block.compression.event

import io.foxcapades.spigot.block.compression.compressible.Compressibles
import io.foxcapades.spigot.block.compression.facades.Facade
import io.foxcapades.spigot.block.compression.inventory.allTheSame
import io.foxcapades.spigot.block.compression.inventory.singularStack
import io.foxcapades.spigot.block.compression.item.compressionLevel
import io.foxcapades.spigot.block.compression.item.isEmpty
import org.bukkit.event.inventory.InventoryEvent


internal fun InventoryEvent.calculateResult(async: Boolean) {
  val top = view.topInventory

  if (handleSingleStackIfPossible(async))
    return

  if (handleAllStacksIfPossible(async))
    return

  if (async)
    Facade.runTask { top.setItem(0, null) }
  else
    top.setItem(0, null)
}

private inline fun InventoryEvent.handleSingleStackIfPossible(async: Boolean): Boolean {
  val stack = view.topInventory.singularStack()

  if (stack.isEmpty())
    return false

  if (stack !in Compressibles)
    return false

  val lvl = stack.compressionLevel()

  if (lvl.hasPrevious) {
    if (async)
      Facade.runTask { view.topInventory.setItem(0, stack.compressionLevel(lvl.previous, 9)) }
    else
      view.topInventory.setItem(0, stack.compressionLevel(lvl.previous, 9))
  }

  return true
}

private inline fun InventoryEvent.handleAllStacksIfPossible(async: Boolean): Boolean {
  val stack = view.topInventory.allTheSame()

  if (stack.isEmpty())
    return false

  if (stack !in Compressibles)
    return false

  val lvl = stack.compressionLevel()

  if (lvl.hasNext) {
    if (async)
      Facade.runTask { view.topInventory.setItem(0, stack.compressionLevel(lvl.next, 1)) }
    else
      view.topInventory.setItem(0, stack.compressionLevel(lvl.next, 1))
  }

  return true
}
