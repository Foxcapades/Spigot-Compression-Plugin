package io.foxcapades.spigot.bcp.config

internal class RecipeConfigValues {
  var items = mapOf<String, String?>("N" to null)
  var layout = "NNNNNNNNN"

  override fun toString() = """
    Recipe {
      Items: $items
      Layout: $layout
    }
  """.trimIndent()
}
