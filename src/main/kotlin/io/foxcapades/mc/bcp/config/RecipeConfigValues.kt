package io.foxcapades.mc.bcp.config

internal class RecipeConfigValues {
  var ingredients = mapOf<String, String?>("N" to null)

  var layout = "NNNNNNNNN"

  override fun toString() = """
    Recipe {
      Ingredients: $ingredients
      Layout: $layout
    }
  """.trimIndent()
}
