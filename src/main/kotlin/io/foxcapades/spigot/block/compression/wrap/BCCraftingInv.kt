package io.foxcapades.spigot.block.compression.wrap

import io.foxcapades.spigot.block.compression.facades.Facade
import io.foxcapades.spigot.block.compression.item.*
import io.foxcapades.spigot.block.compression.item.freeSpace
import io.foxcapades.spigot.block.compression.item.hasSpace
import io.foxcapades.spigot.block.compression.item.isEmpty
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

@JvmInline
@Suppress("NOTHING_TO_INLINE")
internal value class BCCraftingInv(val raw: Inventory) {

  /**
   * Spreads the given item stack across all the available or compatible slots
   * in the crafting grid of the wrapped custom workbench.
   *
   * The input stack will be divided among all possible slots and whatever could
   * not be evenly divided will be returned as a new [ItemStack].
   *
   * If there is no empty or compatible slots in the crafting grid, the original
   * stack will be returned.
   *
   * @param
   */
  fun spread(stack: ItemStack): ItemStack {

    // If the input stack is empty, there is nothing to spread so end here.
    if (stack.isEmpty()) {
      return Air
    }

    // If the given stack has less then 9 items, it cannot be fully spread
    // across the grid, so end here.
    if (stack.amount < 9) {
      return stack
    }

    val slots = calculateSpace(stack)

    if (slots.isEmpty())
      return stack

    return applySpread(stack, slots)
  }

  /**
   * Determines what slots are available to fill or merge into.
   *
   * @return A list of available slots that are guaranteed to be either empty or
   * compatible with the input [stack].
   */
  private fun calculateSpace(stack: ItemStack): List<AvailableSlot> {

    val availableSlots = ArrayList<AvailableSlot>(9)

    for (i in 1 .. 9) {
      val slot = raw.getItem(i)

      if (slot.isEmpty()) {
        availableSlots.add(AvailableSlot(i, true))
        continue
      }

      if (stack.isSimilar(slot) && slot.hasSpace())
        availableSlots.add(AvailableSlot(i, false, slot))
    }

    return availableSlots
  }

  private fun applySpread(stack: ItemStack, slots: List<AvailableSlot>): ItemStack {

    val amt = Count(stack.amount)
    val ava = Count(slots.size)
    var div = amt.n / ava.n

    for (slot in slots) {
      if (slot.empty) {
        raw.setItem(slot.index, stack.clone(div))
        amt.n -= div
        slot.empty = false
      } else {
        applySpread(slot, div, amt, ava)
      }
    }

    if (amt.n == 0) {
      return Air
    }

    if (ava.n == 0) {
      val out = stack.clone(amt.n)
      return out
    }

    if (amt.n < ava.n) {
      val out = stack.clone(amt.n)
      return out
    }

    div = amt.n / ava.n

    for (slot in slots)
      if (!slot.full)
        applySpread(slot, div, amt, ava)

    return when (amt.n) {
      0    -> Air
      else -> stack.clone(amt.n)
    }
  }

  private fun applySpread(slot: AvailableSlot, div: Int, amt: Count, ava: Count) {

    val free = slot.stack.freeSpace()

    if (free <= div) {

      slot.stack.amount = slot.stack.maxStackSize
      amt.n -= free
      ava.n--
      slot.full = true

    } else {

      slot.stack.amount += div
      amt.n -= div

    }
  }

  fun setResult(item: ItemStack?) {
    raw.setItem(0, item)
  }

  fun maxAllStackSize(): Int {
    var max = raw.getItem(1)?.amount ?: -1

    for (i in 2 .. 9) {
      val c = raw.getItem(i) ?: continue

      when {
        max == -1      -> max = c.amount
        c.amount < max -> max = c.amount
      }
    }

    return max
  }

  operator fun dec(): BCCraftingInv {
    for (i in 1 .. 9) {
      val current = raw.getItem(i) ?: continue

      if (current.amount == 1) {
        raw.setItem(i, null)
      } else {
        current.amount--
      }
    }

    return this
  }

  fun reduceAllBy(qty: Int) {
    for (i in 1 .. 9) {
      val c = raw.getItem(i) ?: continue

      when {
        c.amount > qty  -> c.amount -= qty
        c.amount == qty -> raw.setItem(i, null)
        else            -> throw IllegalStateException("Attempted to reduce a stack of size ${c.amount} by $qty.")
      }
    }
  }

  fun getIfAllTheSame(): ItemStack {
    val first = raw.getItem(1) ?: return Air

    if (first.isEmpty())
      return Air

    for (i in 2 .. 9)
      if (!first.isSimilar(raw.getItem(i)))
        return Air

    return first
  }

  fun getIfSingleStack(): ItemStack {

    // Holder for the found item (or just Air if no item was found)
    var found: ItemStack = Air

    for (i in 1 .. 9) {
      val v = raw.getItem(i)

      if (v.isNotEmpty()) {
        if (found === Air)
          found = v
        else
          return Air
      }
    }

    return found
  }

  fun spreadCraftingTable(item: ItemStack): Int {
    // Holder for the count of items we are trying to spread into the crafting
    // grid portion of this inventory.  This value will be updated as the stack
    // is successfully spread.
    var toSpread = item.amount

    // Holder for the index of the first empty slot in the crafting grid.
    var firstEmpty = -1

    // For each non-result slot in the crafting table:
    for (i in 1 .. 9) {

      val current = raw.getItem(i)

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
          return 0
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
    raw.setItem(firstEmpty, item.clone())

    // Return 0 as we were able to consume everything.
    return 0
  }

}

private data class Count(var n: Int)

/**
 * Represents a slot that is available to spread into.
 *
 * @param index Index of the slot in the inventory.
 * @param empty Whether the slot is empty or not.
 * @param stack The stack that exists in the slot (or Air if empty).
 * @param full  Whether the stack in the slot is full.  This should always be
 *              `false` at first.
 */
private data class AvailableSlot(
  val index: Int,
  var empty: Boolean,
  val stack: ItemStack = Air,
  var full:  Boolean = false
)
