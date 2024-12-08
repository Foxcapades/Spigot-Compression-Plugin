@file:JvmName("PlayerInventoryExtensions")
package io.foxcapades.bukkit.inventory

import org.bukkit.inventory.PlayerInventory

private const val HotBarStart = 0
private const val HotBarEnd   = 8

private const val InvStart = 9
private const val InvEnd   = 35

private const val OffHand = 40

/**
 * Returns a [Sequence] of 27 [SlotReference] instances representing the item
 * slots in the storage section of the `PlayerInventory`.
 *
 * The storage section is the 9x3 grid below the armor and crafting section, and
 * above the hot-bar section, containing slots [9..35]
 *
 * @param reverse Set whether the slots should be iterated over in reverse (from
 * the highest slot index to the lowest)
 *
 * @return A sequence of `SlotReferences`.
 */
fun PlayerInventory.storageSlotSequence(reverse: Boolean = false) = sequence {
  for (i in if (reverse) InvEnd downTo InvStart else InvStart .. InvEnd)
    yield(SlotReference(this@storageSlotSequence, i))
}

/**
 * Returns a [Sequence] of 9 [SlotReference] instances representing the item
 * slots in the hot-bar section of the `PlayerInventory`.
 *
 * The hot-bar section contains slots [0..8].
 *
 * @param reverse Set whether the slots should be iterated over in reverse (from
 * the highest slot index to the lowest)
 *
 * @return A sequence of `SlotReferences`.
 */
fun PlayerInventory.hotBarSlotSequence(reverse: Boolean = false) = sequence {
  for (i in if (reverse) HotBarEnd downTo HotBarStart else HotBarStart .. HotBarEnd)
    yield(SlotReference(this@hotBarSlotSequence, i))
}

/**
 * Returns a [Sequence] of [SlotReference] instances over the 35 lower slots in
 * the player inventory, consisting of both the hot-bar slots and storage slots.
 *
 * This is a convenience method combining the sequences returned by
 * [hotBarSlotSequence] and [storageSlotSequence].
 *
 * @param reverseSections Whether the indices of each individual section should
 * be iterated over in reverse.
 *
 * *WARNING*: THIS DOES NOT REVERSE THE FULL SEQUENCE!  The hot-bar slots will
 * come first regardless of this setting.  When set to true, the sequence will
 * return slots 8 down to 0 followed by slots 35 down to 9.
 *
 * @return A combined sequence of hot-bar slots followed by storage slots.
 */
fun PlayerInventory.lowerSlotSequence(reverseSections: Boolean = false) =
  (hotBarSlotSequence(reverseSections) + storageSlotSequence(reverseSections))

/**
 * Returns a [Sequence] of [SlotReference] instances over the 35 lower slots in
 * the player inventory along with the off-hand slot.
 *
 * This is a convenience method adding the off-hand slot to the sequence
 * returned by [lowerSlotSequence].
 *
 * @param reverseSections Whether the indices of each individual section should
 * be iterated over in reverse.
 *
 * *WARNING*: THIS DOES NOT REVERSE THE FULL SEQUENCE!  The hot-bar slots will
 * come first regardless of this setting.  When set to true, the sequence will
 * return slots 8 down to 0, then slots 35 down to 9, and lastly the off-hand
 * slot (40).
 *
 * @return A combined sequence of hot-bar slots followed by storage slots.
 */
fun PlayerInventory.itemSlotSequence(reverseSections: Boolean = false) =
  (lowerSlotSequence(reverseSections) + sequenceOf(SlotReference(this, OffHand)))
