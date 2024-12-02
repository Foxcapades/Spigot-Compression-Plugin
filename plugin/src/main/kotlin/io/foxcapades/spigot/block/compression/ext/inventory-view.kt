package io.foxcapades.spigot.block.compression.ext

import io.foxcapades.spigot.block.compression.i18n.I18N
import org.bukkit.inventory.InventoryView

@Suppress("NOTHING_TO_INLINE")
internal inline fun InventoryView.isCompressionTool() = title == I18N.workbenchName()
