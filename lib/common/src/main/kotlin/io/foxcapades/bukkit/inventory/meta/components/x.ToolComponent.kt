@file:JvmName("ToolComponentExtensions")
package io.foxcapades.bukkit.inventory.meta.components

import io.foxcapades.bukkit.potion.toJsonSerializable
import org.bukkit.inventory.meta.components.ToolComponent

private const val KeyMiningSpeed = "mining-speed"
private const val DefaultMiningSpeed = 1f

private const val KeyDamagePerBlock = "dur-cost-per-block"
private const val KeyRules = "rules"

fun ToolComponent.toJsonSerializable(): Map<String, Any> =
  HashMap<String, Any>(3).also { out ->
    if (defaultMiningSpeed != DefaultMiningSpeed)
      out[KeyMiningSpeed] = defaultMiningSpeed

    if (damagePerBlock != 0)
      out[KeyDamagePerBlock] = damagePerBlock

    rules.takeUnless { it.isEmpty() }
      ?.also { out[KeyRules] = it.map(ToolComponent.ToolRule::toJsonSerializable) }
  }

private const val KeyBlocks = "blocks"
private const val KeyCorrect = "correct-tool"

fun ToolComponent.ToolRule.toJsonSerializable(): Map<String, Any> =
  HashMap<String, Any>(3).also { out ->
    blocks.takeUnless { it.isEmpty() }
      ?.also { blocks -> out[KeyBlocks] = blocks.map { it.key.toString() } }
    speed?.takeUnless { it == DefaultMiningSpeed }
      ?.also { out[KeyMiningSpeed] = it }
    isCorrectForDrops?.also { out[KeyCorrect] = it }
  }
