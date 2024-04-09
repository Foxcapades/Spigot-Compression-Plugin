package io.foxcapades.spigot.zip.event.handler.world

import io.foxcapades.spigot.zip.Config
import io.foxcapades.spigot.block.compression.consts.Permissions
import io.foxcapades.spigot.block.compression.entity.hasAnyPerm
import io.foxcapades.spigot.block.compression.entity.openCompressionWorkbench
import io.foxcapades.spigot.block.compression.event.BCPlayerInteractEvent
import org.bukkit.Material

internal object InteractHandler {
  fun handle(event: BCPlayerInteractEvent) {
    if (!io.foxcapades.spigot.zip.Config.OpenOnInteract.enabled)
      return

    if (!event.player.isSneaking) {
      return
    }

    if ((event.itemType ?: Material.AIR) != io.foxcapades.spigot.zip.Config.OpenOnInteract.keyItemType)
      return

    if (event.blockType != io.foxcapades.spigot.zip.Config.OpenOnInteract.targetBlockType)
      return

    if (!event.player.hasAnyPerm(Permissions.BlockInteract, Permissions.GUI))
      return

    event.player.openCompressionWorkbench()
  }
}