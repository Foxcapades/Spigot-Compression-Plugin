package io.foxcapades.spigot.block.compression.config

internal class RecipeConfigValues {
  var items = mapOf<String, String?>("N" to null)
  var layout = "NNNNNNNNN"

  override fun toString() = """
    Recipe {
      Items: $items
      Layout: >-
        $layout
    }
  """.trimIndent()
}
