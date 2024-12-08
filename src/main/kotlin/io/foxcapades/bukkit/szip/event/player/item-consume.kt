package io.foxcapades.bukkit.szip.event.player

import io.foxcapades.bukkit.szip.Logger
import io.foxcapades.bukkit.szip.zip.isCompressed
import org.bukkit.event.player.PlayerItemConsumeEvent

internal fun PlayerItemConsumeEvent.handle() {
  if (item.isCompressed) {
    Logger.trace { "cancelling item consume event for (player=%s, item=%s)".format(player.name, item.itemMeta?.displayName) }
    isCancelled = true
  }
}
