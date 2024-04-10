package io.foxcapades.spigot.zip.custom

import io.foxcapades.spigot.zip.facades.Facade

internal data class BlockCustomization(
  val additionalBlocks: Map<String, AdditionalEntry>,
  val blockRules: List<CustomizationRule>
)
