package io.foxcapades.bukkit.szip.event.entity

import io.foxcapades.bukkit.szip.Logger
import io.foxcapades.bukkit.szip.zip.isCompressed
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityShootBowEvent

internal fun EntityShootBowEvent.handle() {
  if (entity is Player && consumable?.isCompressed == true) {
    Logger.trace { "cancelling bow shoot event for (player=%s, ammo=%s)".format(entity.name, consumable?.itemMeta?.displayName) }
    isCancelled = true
  }
}
