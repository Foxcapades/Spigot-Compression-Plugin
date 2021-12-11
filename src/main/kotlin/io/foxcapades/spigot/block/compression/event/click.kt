@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.spigot.block.compression.event

import io.foxcapades.spigot.block.compression.consts.CompressorTitle
import io.foxcapades.spigot.block.compression.facades.Facade
import org.bukkit.event.inventory.InventoryClickEvent


internal fun InventoryClickEvent.handleClick() {
  if (view.title == CompressorTitle)
    handleCustomClick()
  else
    handleStandardClick()
}
