@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.spigot.block.compression

import com.google.common.collect.Multimap
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.Server
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFactory
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.meta.Damageable
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataContainer

internal inline fun persistentDataContainer(adapterContext: PersistentDataAdapterContext? = null) =
  mockk<PersistentDataContainer> {
    if (adapterContext != null)
      every { adapterContext } returns adapterContext
  }

internal inline fun damageable(
  persistentDataContainer: PersistentDataContainer? = null,
  displayName: String? = null,
  localizedName: String? = null,
  lore: List<String>? = null,
  customModelData: Int? = null,
  enchants: Map<Enchantment, Int>? = null,
  itemFlags: Set<ItemFlag>? = null,
  unbreakable: Boolean = false,
  attributeModifiers: Multimap<Attribute, AttributeModifier>? = null,
  init: Damageable.() -> Unit = {},
) =
  mockk<Damageable> {

    if (persistentDataContainer != null)
      every { this@mockk.persistentDataContainer } returns persistentDataContainer

    if (displayName != null)
      every { this@mockk.displayName } returns displayName

    if (localizedName != null)
      every { this@mockk.localizedName } returns localizedName

    if (lore != null)
      every { this@mockk.lore } returns lore

    if (customModelData != null)
      every { this@mockk.customModelData } returns customModelData

    if (enchants != null)
      every { this@mockk.enchants } returns enchants

    if (itemFlags != null)
      every { this@mockk.itemFlags } returns itemFlags

    every { this@mockk.isUnbreakable } returns unbreakable

    if (attributeModifiers != null)
      every { this@mockk.attributeModifiers } returns attributeModifiers

    init()
  }

internal inline fun itemFactory(
  itemMeta: Map<Material, ItemMeta>,
  defaultLeatherColor: Color? = null,
  init: ItemFactory.() -> Unit = init@{
    with(damageable()) meta@{ every { this@init.getItemMeta(any()) } returns this@meta }
  },
) =
  mockk<ItemFactory> {
    // Use a capture to
    val mat = slot<Material>()
    every { this@mockk.getItemMeta(capture(mat)) } returns itemMeta[mat.captured]

    if (defaultLeatherColor != null)
      every { this@mockk.defaultLeatherColor } returns defaultLeatherColor

    init()
  }

internal inline fun itemFactory(
  itemMeta: ItemMeta = damageable(),
  defaultLeatherColor: Color? = null,
  init: ItemFactory.() -> Unit = init@{
    with(damageable()) meta@{ every { this@init.getItemMeta(any()) } returns this@meta }
  },
) =
  mockk<ItemFactory> {
    every { this@mockk.getItemMeta(any()) } returns itemMeta

    if (defaultLeatherColor != null)
      every { this@mockk.defaultLeatherColor } returns defaultLeatherColor

    init()
  }

internal inline fun server(itemFactory: ItemFactory) =
  server init@{ with(itemFactory) fac@{ every { this@init.itemFactory } returns this@fac } }

internal inline fun server(
  init: Server.() -> Unit = init@{
    with(itemFactory()) fac@{ every { this@init.itemFactory } returns this@fac }
  },
): Server =
  mockk {
    init()

    with(Bukkit::class.java.getDeclaredField("server")) {
      isAccessible = true
      set(null, this@mockk)
    }
  }

internal fun overrideBukkit(server: Server = server()) =
  mockkStatic(Bukkit::getServer) { every { Bukkit.getServer() } returns server }


