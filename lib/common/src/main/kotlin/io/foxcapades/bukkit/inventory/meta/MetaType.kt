package io.foxcapades.bukkit.inventory.meta

@JvmInline
internal value class MetaType private constructor(val value: String) {
  companion object {
    inline val Armor              get() = MetaType("armor")
    inline val Axolotl            get() = MetaType("axolotl")
    inline val Banner             get() = MetaType("banner")
    inline val BlockData          get() = MetaType("block-data")
    inline val BlockState         get() = MetaType("block-state")
    inline val Book               get() = MetaType("book")
    inline val Bundle             get() = MetaType("bundle")
    inline val ColorArmor         get() = MetaType("color-armor")
    inline val Compass            get() = MetaType("compass")
    inline val Crossbow           get() = MetaType("crossbow")
    inline val Damageable         get() = MetaType("damageable")
    inline val EnchantmentStorage get() = MetaType("enchants")
    inline val Firework           get() = MetaType("firework")
    inline val FireworkEffect     get() = MetaType("firework-effect")
    inline val KnowledgeBook      get() = MetaType("book-knowledge")
    inline val LeatherArmor       get() = MetaType("leather-armor")
    inline val Map                get() = MetaType("map")
    inline val MusicInstrument    get() = MetaType("music-instrument")
    inline val OminousBottle      get() = MetaType("potion-ominous")
    inline val Potion             get() = MetaType("potion")
    inline val Repairable         get() = MetaType("repairable")
    inline val Shield             get() = MetaType("shield")
    inline val Skull              get() = MetaType("skull")
    inline val SpawnEgg           get() = MetaType("egg")
    inline val SuspiciousStew     get() = MetaType("sus-stew")
    inline val TropicalFish       get() = MetaType("tropical-fish")
    inline val WritableBook       get() = MetaType("book-writable")
  }
}
