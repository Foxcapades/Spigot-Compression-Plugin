package io.foxcapades.spigot.block.compression.event

import io.foxcapades.spigot.block.compression.compressible.CompressionLevel.None
import io.foxcapades.spigot.block.compression.item.compressionLevel
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType.*
import org.bukkit.inventory.ItemStack

internal fun InventoryClickEvent.handleStandardLeftClick() {
  if (cursor != null)
    return handleStandardItemPlace(cursor!!)
}

/**
 * Prevents placing of compressed blocks into top inventories that are not
 * chest-like.
 */
internal fun InventoryClickEvent.handleStandardItemPlace(cursor: ItemStack) {
  // They are placing in their own inventory, do not prevent.
  if (clickedInventory === view.bottomInventory)
    return

  // If the item stack is uncompressed, do not prevent.
  if (cursor.compressionLevel() == None)
    return

  // We allow freely placing into these inventory types
  when (view.topInventory.type) {
    CHEST         -> return
    DISPENSER     -> return
    DROPPER       -> return
    PLAYER        -> return
    CREATIVE      -> return
    ENDER_CHEST   -> return
    HOPPER        -> return
    SHULKER_BOX   -> return
    BARREL        -> return
    // Since they are clicking on the top inventory and it is not an allowed type,
    // cancel the event.
    else          -> isCancelled = true
  }
}
