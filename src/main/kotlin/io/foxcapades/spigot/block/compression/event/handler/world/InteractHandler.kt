package io.foxcapades.spigot.block.compression.event.handler.world

import io.foxcapades.spigot.block.compression.Config
import io.foxcapades.spigot.block.compression.entity.openCompressionWorkbench
import io.foxcapades.spigot.block.compression.event.BCPlayerInteractEvent
import org.bukkit.Material

internal object InteractHandler {
  fun handle(event: BCPlayerInteractEvent) {
    if (!event.player.isSneaking) {
      return
    }

    if ((event.itemUsed?.type ?: Material.AIR) != Config.OpenOnInteract.keyItemType) {
      return
    }

    if (event.blockClicked?.blockData?.material != Config.OpenOnInteract.targetBlockType) {
      return
    }

    event.player.openCompressionWorkbench()
  }
}