package io.foxcapades.bukkit.szip.event.inventory

import io.foxcapades.bukkit.szip.Logger
import io.foxcapades.bukkit.szip.ext.isCompressionTool
import org.bukkit.Material.AIR
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryType.WORKBENCH
import org.bukkit.inventory.ItemStack

internal fun InventoryCloseEvent.handle() {
  if (view.topInventory.type != WORKBENCH)
    return

  if (!view.isCompressionTool())
    return

  var count = 0

  for (i in 1..9) {
    val current = view.topInventory.getItem(i)

    if (current != null && current.type != AIR)
      count++
  }

  if (count == 0)
    return

  Logger.trace {
    "attempting to give player %s back %d stacks of items on compression GUI close"
      .format(player.name, count)
  }

  val tmp = arrayOfNulls<ItemStack>(count)

  for (i in 1..9) {
    val current = view.topInventory.getItem(i)

    if (current != null && current.type != AIR)
      tmp[--count] = current
  }

  val remain = view.bottomInventory.addItem(*tmp)

  for (v in remain.values)
    player.world.dropItem(player.location, v)
}
