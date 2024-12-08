@file:JvmName("FoodComponentExtensions")
package io.foxcapades.bukkit.inventory.meta.components

import io.foxcapades.bukkit.potion.toJsonSerializable
import org.bukkit.inventory.meta.components.FoodComponent

private const val KeyNutrition = "nutrition"
private const val KeySaturation = "saturation"
private const val KeyCanAlwaysEat = "always-eatable"
private const val KeyEatTime = "eat-time"
private const val KeyConvertsTo = "converts-to"
private const val KeyEffects = "effects"

fun FoodComponent.toJsonSerializable(): Map<String, Any> =
  HashMap<String, Any>(6).also { out ->
    if (nutrition != 0)
      out[KeyNutrition] = nutrition

    if (saturation != 0f)
      out[KeySaturation] = saturation

    if (canAlwaysEat())
      out[KeyCanAlwaysEat] = true

    if (eatSeconds != 0f)
      out[KeyEatTime] = eatSeconds

    usingConvertsTo?.also { out[KeyConvertsTo] = it.toJsonSerializable() }

    effects.takeUnless { it.isEmpty() }
      ?.also { out[KeyEffects] = it.map { e -> listOf(e.effect.toJsonSerializable(), e.probability) } }
  }
