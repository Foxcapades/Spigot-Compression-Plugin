@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.spigot.block.compression.event

import io.foxcapades.spigot.block.compression.compressible.Compressibles
import io.foxcapades.spigot.block.compression.facades.Facade
import io.foxcapades.spigot.block.compression.inventory.allTheSame
import io.foxcapades.spigot.block.compression.inventory.singularStack
import io.foxcapades.spigot.block.compression.item.compressionLevel
import io.foxcapades.spigot.block.compression.item.isEmpty
import org.bukkit.event.inventory.InventoryEvent


internal fun InventoryEvent.calculateResult() {
  Facade.logTrace("InventoryEvent#calculateResult()")

  val top = view.topInventory

  if (handleSingleStackIfPossible())
    return

  if (handleAllStacksIfPossible())
    return

  top.setItem(0, null)
}

private inline fun InventoryEvent.handleSingleStackIfPossible(): Boolean {
  Facade.logTrace("InventoryEvent#handleSingleStackIfPossible()")

  val stack = view.topInventory.singularStack()

  if (stack.isEmpty())
    return false

  if (stack !in Compressibles)
    return false

  val lvl = stack.compressionLevel()

  if (lvl.hasPrevious) {
    view.topInventory.setItem(0, stack.compressionLevel(lvl.previous, 9))
  }

  return true
}

private inline fun InventoryEvent.handleAllStacksIfPossible(): Boolean {
  Facade.logTrace("InventoryEvent#handleAllStacksIfPossible()")

  val stack = view.topInventory.allTheSame()

  if (stack.isEmpty())
    return false

  if (stack !in Compressibles)
    return false

  val lvl = stack.compressionLevel()

  if (lvl.hasNext) {
    view.topInventory.setItem(0, stack.compressionLevel(lvl.next, 1))
  }

  return true
}
