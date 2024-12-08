package io.foxcapades.bukkit.szip.ext

import io.foxcapades.bukkit.inventory.*
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.PlayerInventory

internal fun PlayerInventory.distributeStacks(stacks: Iterable<ItemStack>): Iterable<ItemStack> {
  val remainder = ArrayList<ItemStack>(9)
  val inbound = stacks.iterator()

  outer@while (inbound.hasNext()) {
    val stack = inbound.next()
    var targets = itemSlotSequence(reverseSections = true)
      .filter { (_, v) -> stack.isSimilar(v) && v!!.hasSpace() }
      .iterator()

    // Try and distribute the stack into other stacks in the inventory.
    while (targets.hasNext()) {
      val (_, target) = targets.next()
      val take = target!!.freeSpace

      if (take >= stack.amount) {
        target.amount += stack.amount
        continue@outer
      }

      stack.amount -= take
    }

    // If we made it here, then there wasn't enough space in other stacks to
    // consume the inbound stack.  Now put the rest into the first empty space
    // we find.
    targets = lowerSlotSequence(reverseSections = true)
      .filter { it.stack.isEmpty() }
      .iterator()

    while (targets.hasNext()) {
      val (i, _) = targets.next()
      setItem(i, stack)
      continue@outer
    }

    // If we made it _this_ far, then we couldn't place the stack at all.  This
    // is one for the output.
    remainder.add(stack)
  }

  return remainder
}
