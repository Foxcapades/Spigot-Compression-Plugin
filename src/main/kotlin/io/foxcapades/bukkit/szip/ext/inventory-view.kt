package io.foxcapades.bukkit.szip.ext

import io.foxcapades.bukkit.szip.i18n.I18N
import org.bukkit.inventory.InventoryView

@Suppress("NOTHING_TO_INLINE")
internal inline fun InventoryView.isCompressionTool() = title == I18N.workbenchName()
