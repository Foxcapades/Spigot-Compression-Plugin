package io.foxcapades.mc.bcp.inv

import io.foxcapades.mc.bcp.zip.compress
import io.foxcapades.mc.bcp.zip.compressionLevel
import io.foxcapades.mc.bcp.zip.ifCompressible
import io.foxcapades.mc.bcp.ext.*
import io.foxcapades.mc.bcp.item.Air
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.PlayerInventory

@JvmInline
internal value class CraftInventory(private val view: InventoryView) {

  private inline val top
    get() = view.topInventory
  private inline val bottom
    get() = view.bottomInventory as PlayerInventory

  private val materialized: Boolean
    get() {
      val cur = top[0] ?: return false
      ifSingleStack { return cur.amount < 9 }
      return false
    }

  /**
   * Pops the result stack out of this [CraftInventory], decrementing the
   * amounts of all the ingredients if necessary.
   *
   * If the result was materialized at the time this method was called, the
   * ingredient amounts will not be decremented as the cost was already paid
   * when the result was materialized.
   *
   * @return The result stack from this [CraftInventory].
   *
   * @throws IllegalStateException If this method was called when there was no
   * result present.
   */
  fun popResult() =
    top[0]?.also { if (!materialized) reduceAllBy(1) }
      ?.also { top[0] = null }
      ?.also { calculateResult() }
      ?: throw IllegalStateException("Attempted to pop a non-existent result.")

  /**
   * Splits the result stack, returning half.
   *
   * This method will decrement the amounts of the ingredients and will
   * materialize the result.
   *
   * @return Half of the result stack.
   *
   * @throws IllegalStateException If this method was called when there was no
   * result present.
   */
  fun splitResult(): ItemStack {
    val tmp = top[0] ?: throw IllegalStateException("Attempted to split a non-existent result.")

    if (!materialized)
      reduceAllBy(1)

    val retVal = when (val new = tmp.amount / 2) {
      // If this is 0, then the original stack size was 1.
      0    -> {
        top[0] = null
        calculateResult()
        tmp
      }

      else -> {
        val clone = tmp.clone(new + tmp.amount % 2)
        tmp.amount = new
        clone
      }
    }

    return retVal
  }

  fun popAll(): List<ItemStack> = run {
    val out = ArrayList<ItemStack>(2)

    if (materialized)
      out.add(popResult())

    ifSingleStack { stack ->
      stack.ifCompressible { popAllSingular(it, out) }
      return@run out
    }

    ifAllTheSame { it, qty ->
      it.ifCompressible {
        val lvl = it.compressionLevel()

        if (!lvl.hasNext)
          return@run out

        out.add(it.compress(lvl.next, qty))
        reduceAllBy(qty)
      }

      return@run out
    }

    return@run out
  }.also { calculateResult() }

  private fun popAllSingular(it: ItemStack, out: MutableList<ItemStack>) {
    val lvl = it.compressionLevel()

    if (!lvl.hasPrevious)
      return

    val decom = it.compress(lvl.previous, 0)
    val max   = decom.maxStackSize
    var total = it.amount * 9

    while (total > max) {
      out.add(decom.clone(max))
      total -= max
    }

    if (total > 0) {
      decom.amount = total
      out.add(decom)
    }

    reduceAllBy(it.amount)
  }

  /**
   * Runs the given action if there exists only 1 stack of items in the
   * ingredient grid portion of this crafting table.
   *
   * The action will be called with the singular [ItemStack].
   *
   * @param action Action to run if there exists exactly 1 non-empty [ItemStack]
   * in the crafting grid portion of this [CraftInventory].
   */
  inline fun ifSingleStack(action: (ItemStack) -> Unit) {
    var found: ItemStack = Air

    each { _, it ->
      it.ifNotEmpty {
        if (found === Air)
          found = it
        else
          return
      }
    }

    if (found !== Air)
      action(found)
  }

  /**
   * Runs the given action if all the slots in the ingredient grid portion of
   * this crafting table are non-empty and are compatible with one another.
   *
   * The action will be called with the first stack in the ingredient grid along
   * with the maximum common quantity across all stacks.
   *
   * @param action Action to run if all slots in the ingredient grid of this
   * crafting table are compatible.
   */
  inline fun ifAllTheSame(action: (item: ItemStack, commonQty: Int) -> Unit) {
    val first = top.getItem(1) ?: return
    var max = first.amount

    for (i in 2 .. 9) {
      val cur = top.getItem(i) ?: return

      if (!first.isSimilar(cur))
        return

      if (max > cur.amount)
        max = cur.amount
    }

    action(first, max)
  }

  /**
   * Runs the given action on each slot in the ingredient grid portion of this
   * crafting table.
   *
   * The action will be called with the current raw slot index as well as the
   * [ItemStack] in that slot.  If the slot is empty, the given `ItemStack` will
   * be the [Air] singleton.
   *
   * The given index will start at `1` as the result slot is not included in
   * the iteration.
   *
   * @param action Action to call on each slot in the ingredient grid portion of
   * this crafting table.
   */
  inline fun each(action: (index: Int, item: ItemStack) -> Unit) {
    for (i in 1 .. 9)
      action(i, top.getItem(i) ?: Air)
  }

  inline fun eachNonEmpty(action: (index: Int, stack: ItemStack) -> Unit) {
    for (i in 1 .. 9)
      action(i, top.getItem(i) ?: continue)
  }

  fun take(index: Int): ItemStack {
    if (index == 0)
      throw IllegalStateException("Attempted to take the result value using the incorrect method.")

    val out = top.getItem(index) ?: Air

    out.ifNotEmpty {
      top.setItem(index, Air)

      calculateResult()

      return out
    }

    throw IllegalStateException("Attempted to take from an empty slot.")
  }

  fun putItem(index: Int, stack: ItemStack): ItemStack {
    if (index == 0)
      throw IllegalStateException("Attempted to set the result value externally.")

    val slot = top.getItem(index) ?: Air

    slot.ifEmpty {
      top.setItem(index, stack)
      calculateResult()
      return Air
    }

    // If compatible:
    //   If slot has space, subtract from input stack and add to slot stack
    //   If slot does not have space, swap stacks.
    slot.ifSimilar(stack) {
      slot.ifHasSpace { space ->
        // If the amount of space available is less than the amount we want to
        // put.
        return if (stack.amount > space) {
          slot.amount = slot.maxStackSize
          stack.clone(stack.amount-space)
        } else {
          slot.amount += stack.amount
          Air
        }
      }

      // Slot does not have space, same as incompatible.
    }

    // If incompatible, swap stacks.
    top.setItem(index, stack)
    return slot
  }

  fun addItem(stack: ItemStack): ItemStack {
    var amount = stack.size
    var empty  = -1

    for (i in 1..9) {
      val slot = top.getItem(i)

      if (slot.isEmpty()) {
        if (empty == -1)
          empty = i

        continue
      }

      if (!stack.isSimilar(slot))
        continue

      if (!slot.hasSpace())
        continue

      val space = slot.freeSpace()

      if (amount > space) {
        slot.amount = slot.maxStackSize
        amount -= space
      } else {
        slot.amount += amount
        amount = 0
        break
      }
    }

    if (amount > 0) {
      if (empty > -1) {
        top.setItem(empty, stack.clone(amount))
        amount = 0
      }
    }

    calculateResult()

    return if (amount > 0) stack.clone(amount) else Air
  }

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
  fun fillGrid(stack: ItemStack, slotIndex: Int) {

    // If the input stack is empty, there is nothing to spread so end here.
    if (stack.isEmpty())
      return

    val slots = calculateSpace(stack)

    if (slots.isEmpty())
      return

    // If the given stack has less than 9 items, it cannot be fully spread
    // across the grid, so end here.
    if (stack.amount < slots.size && stack.maxStackSize == 1) {
      tryFillGridFromInv(stack, slotIndex, slots)
    } else {
      bottom[slotIndex] = applySpread(stack, slots)
    }
    calculateResult()
  }

  private fun tryFillGridFromInv(stack: ItemStack, slotIndex: Int, slots: List<AvailableSlot>) {
    val indices = ArrayList<Int>(slots.size).apply { add(slotIndex) }
    var count = stack.size

    // Search the bottom inventory for items to try and fill the crafting grid.
    for (i in 0 ..< bottom.size) {
      // Skip over the index the user clicked on because we've already counted
      // that one.
      if (i == slotIndex)
        continue

      val otherStack = bottom[i] ?: Air

      // Skip over empty slots and incompatible items.
      if (otherStack === Air || !otherStack.isSimilar(stack))
        continue

      // Increase the amount of available items by the number in the compatible
      // stack we've found.
      count += otherStack.size

      // Add the index of the located item to the list of usable indices.
      indices.add(i)

      // If we've seen enough items to fill the available slots then we can stop
      // looking.
      if (count >= slots.size)
        break
    }

    // If we didn't find enough items to fill the open slots, then do nothing.
    if (count < slots.size)
      return

    // Spread the available items
    applySpread(indices, slots)
  }

  /**
   * Determines what slots are available to fill or merge into.
   *
   * @return A list of available slots that are guaranteed to be either empty or
   * compatible with the input [stack].
   */
  private fun calculateSpace(stack: ItemStack): List<AvailableSlot> =
    ArrayList<AvailableSlot>(9).apply {
      each { i, slot ->
        if (slot.isEmpty()) {
          add(AvailableSlot(i, true))
          return@each
        }

        // Refuse to fill on incompatible stacks
        if (!stack.isSimilar(slot))
          return emptyList()

        if (slot.hasSpace())
          add(AvailableSlot(i, false, slot))
      }
    }

  /**
   * Spread items from multiple stacks across the crafting grid.
   *
   * This method is only called when a single stack isn't enough to fill the
   * crafting grid, but the player does have enough of the target item in their
   * inventory to perform a compression.
   *
   * This means that the input list of stacks will be consumed in an attempt to
   * put 1 item into each available slot in the crafting grid.
   */
  private fun applySpread(stacks: Iterable<Int>, slots: Iterable<AvailableSlot>) {
    val stackIterator = stacks.iterator()
    val slotIterator = slots.iterator()

    var slot: AvailableSlot
    var stack: ItemStack = Air
    var remaining = 0
    var index = 0

    outer@while (stackIterator.hasNext()) {
      index = stackIterator.next()
      stack     = bottom[index]!!
      remaining = stack.amount

      while (remaining-- > 0) {
        if (!slotIterator.hasNext())
          break@outer

        slot = slotIterator.next()
        top[slot.index] = if (slot.empty) stack.clone(1) else top[slot.index].let { it.clone(it.size + 1) }
      }

      bottom[index] = null
    }

    bottom[index] = if (remaining > 0) stack.clone(remaining) else null
  }

  private fun applySpread(stack: ItemStack, slots: List<AvailableSlot>): ItemStack {
    val amt = Count(stack.amount)
    val ava = Count(slots.size)
    var div = amt.n / ava.n

    for (slot in slots) {
      if (slot.empty) {
        top[slot.index] = stack.clone(div)
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

  /**
   * Reduces the quantity of all non-empty stacks in the crafting grid by the
   * given amount ([qty]).
   */
  private fun reduceAllBy(qty: Int) {
    eachNonEmpty { index, stack ->
      when {
        stack.amount > qty  -> stack.amount -= qty
        stack.amount == qty -> top.setItem(index, null)
        else                -> throw IllegalStateException("Attempted to reduce the size of $stack by $qty.")
      }
    }
  }

  /**
   * Determines if the current contents of the crafting grid are a valid
   * (de)compression recipe and updates the result slot accordingly.
   */
  fun calculateResult() {
    ifSingleStack {
      val lvl = it.compressionLevel()

      if (lvl.hasPrevious)
        top.setItem(0, it.compress(lvl.previous, 9))

      return
    }

    ifAllTheSame { it, _ ->
      it.ifCompressible {
        val lvl = it.compressionLevel()

        if (lvl.hasNext)
          top.setItem(0, it.compress(lvl.next, 1))
      }

      return
    }

    top.clear(0)
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
  var full: Boolean = false,
)
