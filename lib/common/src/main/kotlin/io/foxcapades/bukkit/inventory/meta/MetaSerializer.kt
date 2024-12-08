package io.foxcapades.bukkit.inventory.meta

import org.bukkit.inventory.meta.*


object MetaSerializer {

  fun serialize(meta: ItemMeta): Map<String, Any> = when (meta) {
    is BlockDataMeta  -> meta.toJsonSerializable()
    is BlockStateMeta -> meta.toJsonSerializable()

    // this MUST appear before both LeatherArmorMeta and ArmorMeta
    is ColorableArmorMeta -> meta.toJsonSerializable()
    is LeatherArmorMeta   -> meta.toJsonSerializable()
    is ArmorMeta          -> meta.toJsonSerializable()

    is BannerMeta -> meta.toJsonSerializable()

    is AxolotlBucketMeta -> meta.toJsonSerializable()
  }
}
