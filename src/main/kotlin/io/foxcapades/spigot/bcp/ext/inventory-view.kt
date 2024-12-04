package io.foxcapades.spigot.bcp.ext

import io.foxcapades.spigot.bcp.i18n.I18N
import org.bukkit.inventory.InventoryView

@Suppress("NOTHING_TO_INLINE")
internal inline fun InventoryView.isCompressionTool() = title == I18N.workbenchName()
