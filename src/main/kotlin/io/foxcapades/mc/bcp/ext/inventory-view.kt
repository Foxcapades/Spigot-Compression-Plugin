package io.foxcapades.mc.bcp.ext

import io.foxcapades.mc.bcp.i18n.I18N
import org.bukkit.inventory.InventoryView

@Suppress("NOTHING_TO_INLINE")
internal inline fun InventoryView.isCompressionTool() = title == I18N.workbenchName()
