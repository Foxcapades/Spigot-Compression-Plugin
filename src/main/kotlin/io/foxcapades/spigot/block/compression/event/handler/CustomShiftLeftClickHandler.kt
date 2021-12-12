package io.foxcapades.spigot.block.compression.event.handler

import io.foxcapades.spigot.block.compression.event.BCInvClickEvent
import io.foxcapades.spigot.block.compression.item.ifNotEmpty
import org.bukkit.event.inventory.InventoryType.SlotType.RESULT
import org.bukkit.inventory.ItemStack

internal object CustomShiftLeftClickHandler : ClickHandler {
  override fun handle(event: BCInvClickEvent) {
    event.ifUserClickedTopInv { return topClick() }
    event.ifUserClickedBottomInv { return bottomClick() }
    // The user clicked outside or something?
    event.uncancel()
  }

  private fun BCInvClickEvent.topClick() {
    val rem: Map<Int, ItemStack>

    if (slotType == RESULT)
      rem = bottom.addItem(*top.popAll().toTypedArray())
    else
      rem = bottom.addItem(top.take(slotIndex))

    for (item in rem.values)
      player.world.dropItem(player.location, item!!)
  }

  private fun BCInvClickEvent.bottomClick() =
    inventorySlot.ifNotEmpty { bottom.setItem(slotIndex, top.addItem(inventorySlot)) }
}