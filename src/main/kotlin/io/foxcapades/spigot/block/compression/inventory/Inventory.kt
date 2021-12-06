@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.spigot.block.compression.inventory

import io.foxcapades.spigot.block.compression.item.freeSpace
import io.foxcapades.spigot.block.compression.item.hasSpace
import io.foxcapades.spigot.block.compression.item.isEmpty
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

internal inline fun Inventory.spreadCraftingTable(item: ItemStack): Int {
  // Holder for the count of items we are trying to spread into the crafting
  // grid portion of this inventory.  This value will be updated as the stack
  // is successfully spread.
  var toSpread = item.amount

  // Holder for the index of the first empty slot in the crafting grid.
  var firstEmpty = -1

  // For each non-result slot in the crafting table:
  for (i in 1..9) {

    val current = getItem(i)

    // If the current item is null or is of type AIR:
    if (current.isEmpty()) {

      // If we have not yet found an empty slot to fall back to:
      if (firstEmpty == -1) {

        // Record this position as the fallback fill slot.
        firstEmpty = i
      }

      // Move on to the next item.
      continue
    }

    // If the stack currently in the crafting grid is compatible with the stack
    // we are trying to spread AND the stack in grid has space to add more
    // items:
    if (current.isSimilar(item) && current.hasSpace()) {

      // Calculate how much space we have available.
      val space = current.freeSpace()

      // If the space we have available is not enough to hold the full stack
      // being spread:
      if (space < toSpread) {

        // Max out the size of the stack already in the grid.
        current.amount = current.maxStackSize

        // Subtract the amount we were able to add from the item we are trying
        // to spread:
        toSpread -= space
      } else {
        // Here we know that the space available is equal to or greater than the
        // space we actually need to consume the remainder of the stack being
        // spread.

        // Increase the slotted stack amount by the remainder of the stack to
        // spread.
        current.amount += toSpread

        // Return 0 to indicate the stack was fully spread.
        return  0
      }
    } // END: Items are compatible and slotted stack has space.
  }

  // If we made it here, there is some amount remaining to be placed in the
  // crafting grid.

  // If we didn't find an empty slot to dump the remainder in:
  if (firstEmpty == -1)
    // Return the quantity remaining that was unplaceable:
    return toSpread

  // If we _did_ have an empty slot to dump the remainder in, do that.
  setItem(firstEmpty, item.clone())

  // Return 0 as we were able to consume everything.
  return 0
}

internal inline fun ItemStack.cloneWith(qty: Int): ItemStack {
  val out = ItemStack(this)
  out.amount = qty
  return out
}