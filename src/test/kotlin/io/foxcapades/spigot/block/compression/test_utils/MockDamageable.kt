package io.foxcapades.spigot.block.compression.test_utils

import org.bukkit.inventory.meta.Damageable

class MockDamageable : MockItemMeta(), Damageable {

  private var _clone:     () -> Damageable = { TODO("Mock me!") }
  private var _hasDamage: BoolProvider     = { TODO("Mock me!") }
  private var _getDamage: IntProvider      = { TODO("Mock me!") }
  private var _setDamage: IntConsumer      = { TODO("Mock me!") }

  fun mockDamageableClone(fn: () -> Damageable) { _clone = fn }
  override fun clone() = _clone()

  fun mockHasDamage(fn: BoolProvider) { _hasDamage = fn }
  override fun hasDamage() = _hasDamage()

  fun mockGetDamage(fn: IntProvider) { _getDamage = fn }
  override fun getDamage() = _getDamage()

  fun mockSetDamage(fn: IntConsumer) { _setDamage = fn }
  override fun setDamage(damage: Int) = _setDamage(damage)
}