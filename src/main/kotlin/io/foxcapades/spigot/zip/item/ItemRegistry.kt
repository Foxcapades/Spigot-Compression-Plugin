package io.foxcapades.spigot.zip.item

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack

internal object ItemRegistry {
  private val builtInRegistry = mapOf(
    ItemRef.mc("redstone"),
    ItemRef.mc("saddle"),
    ItemRef.mc("minecart"),
    ItemRef.mc("chest_minecart"),
    ItemRef.mc("furnace_minecart"),
    ItemRef.mc("tnt_minecart"),
    ItemRef.mc("hopper_minecart"),
    ItemRef.mc("carrot_on_a_stick"),
    ItemRef.mc("warped_fungus_on_a_stick"),
    ItemRef.mc("elytra"),
    ItemRef.mc("oak_boat"),
    ItemRef.mc("oak_chest_boat"),
    ItemRef.mc("spruce_boat"),
    ItemRef.mc("spruce_chest_boat"),
    ItemRef.mc("birch_boat"),
    ItemRef.mc("birch_chest_boat"),
    ItemRef.mc("jungle_boat"),
    ItemRef.mc("jungle_chest_boat"),
    ItemRef.mc("acacia_boat"),
    ItemRef.mc("acacia_chest_boat"),
    ItemRef.mc("cherry_boat"),
    ItemRef.mc("cherry_chest_boat"),
    ItemRef.mc("dark_oak_boat"),
    ItemRef.mc("dark_oak_chest_boat"),
    ItemRef.mc("mangrove_boat"),
    ItemRef.mc("mangrove_chest_boat"),
    ItemRef.mc("bamboo_raft"),
    ItemRef.mc("bamboo_chest_raft"),
    ItemRef.mc("turtle_helmet"),
    ItemRef.mc("scute"),
    ItemRef.mc("flint_and_steel"),
    ItemRef.mc("apple"),
    ItemRef.mc("bow"),
    ItemRef.mc("arrow"),
//    ItemRef.mc("coal"), // see: coal_block
    ItemRef.mc("charcoal"),
//    ItemRef.mc("diamond"), // see: diamond_block
//    ItemRef.mc("emerald"), // see: emerald_block
//    ItemRef.mc("lapis_lazuli"), // see: lapis_block
    ItemRef.mc("quartz"),
    ItemRef.mc("amethyst_shard"),
//    ItemRef.mc("raw_iron"), // see: raw_iron_block
//    ItemRef.mc("iron_ingot"), // see: iron_block
//    ItemRef.mc("raw_copper"), // see: raw_copper_block
//    ItemRef.mc("copper_ingot"), // see: copper_block
//    ItemRef.mc("raw_gold"), // see: raw_gold_block
//    ItemRef.mc("gold_ingot"), // see: gold_block
//    ItemRef.mc("netherite_ingot"), // see: netherite_block
    ItemRef.mc("netherite_scrap"),
    ItemRef.mc("wooden_sword"),
    ItemRef.mc("wooden_shovel"),
    ItemRef.mc("wooden_pickaxe"),
    ItemRef.mc("wooden_axe"),
    ItemRef.mc("wooden_hoe"),
    ItemRef.mc("stone_sword"),
    ItemRef.mc("stone_shovel"),
    ItemRef.mc("stone_pickaxe"),
    ItemRef.mc("stone_axe"),
    ItemRef.mc("stone_hoe"),
    ItemRef.mc("golden_sword"),
    ItemRef.mc("golden_shovel"),
    ItemRef.mc("golden_pickaxe"),
    ItemRef.mc("golden_axe"),
    ItemRef.mc("golden_hoe"),
    ItemRef.mc("iron_sword"),
    ItemRef.mc("iron_shovel"),
    ItemRef.mc("iron_pickaxe"),
    ItemRef.mc("iron_axe"),
    ItemRef.mc("iron_hoe"),
    ItemRef.mc("diamond_sword"),
    ItemRef.mc("diamond_shovel"),
    ItemRef.mc("diamond_pickaxe"),
    ItemRef.mc("diamond_axe"),
    ItemRef.mc("diamond_hoe"),
    ItemRef.mc("netherite_sword"),
    ItemRef.mc("netherite_shovel"),
    ItemRef.mc("netherite_pickaxe"),
    ItemRef.mc("netherite_axe"),
    ItemRef.mc("netherite_hoe"),
    ItemRef.mc("stick"),
    ItemRef.mc("bowl"),
    ItemRef.mc("mushroom_stew"),
    ItemRef.mc("string"),
    ItemRef.mc("feather"),
    ItemRef.mc("gunpowder"),
    ItemRef.mc("wheat_seeds"),
    ItemRef.mc("wheat"),
    ItemRef.mc("bread"),
    ItemRef.mc("leather_helmet"),
    ItemRef.mc("leather_chestplate"),
    ItemRef.mc("leather_leggings"),
    ItemRef.mc("leather_boots"),
    ItemRef.mc("chainmail_helmet"),
    ItemRef.mc("chainmail_chestplate"),
    ItemRef.mc("chainmail_leggings"),
    ItemRef.mc("chainmail_boots"),
    ItemRef.mc("iron_helmet"),
    ItemRef.mc("iron_chestplate"),
    ItemRef.mc("iron_leggings"),
    ItemRef.mc("iron_boots"),
    ItemRef.mc("diamond_helmet"),
    ItemRef.mc("diamond_chestplate"),
    ItemRef.mc("diamond_leggings"),
    ItemRef.mc("diamond_boots"),
    ItemRef.mc("golden_helmet"),
    ItemRef.mc("golden_chestplate"),
    ItemRef.mc("golden_leggings"),
    ItemRef.mc("golden_boots"),
    ItemRef.mc("netherite_helmet"),
    ItemRef.mc("netherite_chestplate"),
    ItemRef.mc("netherite_leggings"),
    ItemRef.mc("netherite_boots"),
    ItemRef.mc("flint"),
    ItemRef.mc("porkchop"),
    ItemRef.mc("cooked_porkchop"),
    ItemRef.mc("painting"),
    ItemRef.mc("golden_apple"),
    ItemRef.mc("enchanted_golden_apple"),
    ItemRef.mc("bucket"),
    ItemRef.mc("water_bucket"),
    ItemRef.mc("lava_bucket"),
    ItemRef.mc("powder_snow_bucket"),
    ItemRef.mc("snowball"),
    ItemRef.mc("leather"),
    ItemRef.mc("milk_bucket"),
    ItemRef.mc("pufferfish_bucket"),
    ItemRef.mc("salmon_bucket"),
    ItemRef.mc("cod_bucket"),
    ItemRef.mc("tropical_fish_bucket"),
    ItemRef.mc("axolotl_bucket"),
    ItemRef.mc("tadpole_bucket"),
    ItemRef.mc("brick"),
//    ItemRef.mc("clay_ball"), // clay
    ItemRef.mc("paper"),
    ItemRef.mc("book"),
//    ItemRef.mc("slime_ball"), // slime_block
    ItemRef.mc("egg"),
    ItemRef.mc("compass"),
    ItemRef.mc("recovery_compass"),
//    ItemRef.mc("bundle"),  // Just no.
    ItemRef.mc("fishing_rod"),
    ItemRef.mc("clock"),
//    ItemRef.mc("spyglass"), // TODO: prevent usage
    ItemRef.mc("glowstone_dust"),
    ItemRef.mc("cod"),
    ItemRef.mc("salmon"),
    ItemRef.mc("tropical_fish"),
    ItemRef.mc("pufferfish"),
    ItemRef.mc("cooked_cod"),
    ItemRef.mc("cooked_salmon"),
    ItemRef.mc("ink_sac"),
    ItemRef.mc("glow_ink_sac"),
    ItemRef.mc("cocoa_beans"),
    ItemRef.mc("white_dye"),
    ItemRef.mc("orange_dye"),
    ItemRef.mc("magenta_dye"),
    ItemRef.mc("light_blue_dye"),
    ItemRef.mc("yellow_dye"),
    ItemRef.mc("lime_dye"),
    ItemRef.mc("pink_dye"),
    ItemRef.mc("gray_dye"),
    ItemRef.mc("light_gray_dye"),
    ItemRef.mc("cyan_dye"),
    ItemRef.mc("purple_dye"),
    ItemRef.mc("blue_dye"),
    ItemRef.mc("brown_dye"),
    ItemRef.mc("green_dye"),
    ItemRef.mc("red_dye"),
    ItemRef.mc("black_dye"),
    ItemRef.mc("bone_meal"),
    ItemRef.mc("bone"),
    ItemRef.mc("sugar"),
    ItemRef.mc("cookie"),
//    ItemRef.mc("filled_map"), // TODO: is there a way to detect duplicate maps?
    ItemRef.mc("shears"),
//    ItemRef.mc("melon_slice"), // see: melon
//    ItemRef.mc("dried_kelp"), // see: dried_kelp_block
    ItemRef.mc("pumpkin_seeds"),
    ItemRef.mc("melon_seeds"),
    ItemRef.mc("beef"),
    ItemRef.mc("cooked_beef"),
    ItemRef.mc("chicken"),
    ItemRef.mc("cooked_chicken"),
    ItemRef.mc("rotten_flesh"),
    ItemRef.mc("ender_pearl"),
    ItemRef.mc("blaze_rod"),
    ItemRef.mc("ghast_tear"),
//    ItemRef.mc("gold_nugget"), // see: gold_ingot
    ItemRef.mc("nether_wart"),
//    ItemRef.mc("potion"), // TODO: compress on metadata match only
//    ItemRef.mc("glass_bottle"), // TODO: prevent usage
    ItemRef.mc("spider_eye"),
    ItemRef.mc("fermented_spider_eye"),
    ItemRef.mc("blaze_powder"),
    ItemRef.mc("magma_cream"),
    ItemRef.mc("ender_eye"),
    ItemRef.mc("glistering_melon_slice"),
    ItemRef.mc("allay_spawn_egg"),
    ItemRef.mc("axolotl_spawn_egg"),
    ItemRef.mc("bat_spawn_egg"),
    ItemRef.mc("bee_spawn_egg"),
    ItemRef.mc("blaze_spawn_egg"),
    ItemRef.mc("breeze_spawn_egg"),
    ItemRef.mc("cat_spawn_egg"),
    ItemRef.mc("camel_spawn_egg"),
    ItemRef.mc("cave_spider_spawn_egg"),
    ItemRef.mc("chicken_spawn_egg"),
    ItemRef.mc("cod_spawn_egg"),
    ItemRef.mc("cow_spawn_egg"),
    ItemRef.mc("creeper_spawn_egg"),
    ItemRef.mc("dolphin_spawn_egg"),
    ItemRef.mc("donkey_spawn_egg"),
    ItemRef.mc("drowned_spawn_egg"),
    ItemRef.mc("elder_guardian_spawn_egg"),
    ItemRef.mc("ender_dragon_spawn_egg"),
    ItemRef.mc("enderman_spawn_egg"),
    ItemRef.mc("endermite_spawn_egg"),
    ItemRef.mc("evoker_spawn_egg"),
    ItemRef.mc("fox_spawn_egg"),
    ItemRef.mc("frog_spawn_egg"),
    ItemRef.mc("ghast_spawn_egg"),
    ItemRef.mc("glow_squid_spawn_egg"),
    ItemRef.mc("goat_spawn_egg"),
    ItemRef.mc("guardian_spawn_egg"),
    ItemRef.mc("hoglin_spawn_egg"),
    ItemRef.mc("horse_spawn_egg"),
    ItemRef.mc("husk_spawn_egg"),
    ItemRef.mc("iron_golem_spawn_egg"),
    ItemRef.mc("llama_spawn_egg"),
    ItemRef.mc("magma_cube_spawn_egg"),
    ItemRef.mc("mooshroom_spawn_egg"),
    ItemRef.mc("mule_spawn_egg"),
    ItemRef.mc("ocelot_spawn_egg"),
    ItemRef.mc("panda_spawn_egg"),
    ItemRef.mc("parrot_spawn_egg"),
    ItemRef.mc("phantom_spawn_egg"),
    ItemRef.mc("pig_spawn_egg"),
    ItemRef.mc("piglin_spawn_egg"),
    ItemRef.mc("piglin_brute_spawn_egg"),
    ItemRef.mc("pillager_spawn_egg"),
    ItemRef.mc("polar_bear_spawn_egg"),
    ItemRef.mc("pufferfish_spawn_egg"),
    ItemRef.mc("rabbit_spawn_egg"),
    ItemRef.mc("ravager_spawn_egg"),
    ItemRef.mc("salmon_spawn_egg"),
    ItemRef.mc("sheep_spawn_egg"),
    ItemRef.mc("shulker_spawn_egg"),
    ItemRef.mc("silverfish_spawn_egg"),
    ItemRef.mc("skeleton_spawn_egg"),
    ItemRef.mc("skeleton_horse_spawn_egg"),
    ItemRef.mc("slime_spawn_egg"),
    ItemRef.mc("sniffer_spawn_egg"),
    ItemRef.mc("snow_golem_spawn_egg"),
    ItemRef.mc("spider_spawn_egg"),
    ItemRef.mc("squid_spawn_egg"),
    ItemRef.mc("stray_spawn_egg"),
    ItemRef.mc("strider_spawn_egg"),
    ItemRef.mc("tadpole_spawn_egg"),
    ItemRef.mc("trader_llama_spawn_egg"),
    ItemRef.mc("tropical_fish_spawn_egg"),
    ItemRef.mc("turtle_spawn_egg"),
    ItemRef.mc("vex_spawn_egg"),
    ItemRef.mc("villager_spawn_egg"),
    ItemRef.mc("vindicator_spawn_egg"),
    ItemRef.mc("wandering_trader_spawn_egg"),
    ItemRef.mc("warden_spawn_egg"),
    ItemRef.mc("witch_spawn_egg"),
    ItemRef.mc("wither_spawn_egg"),
    ItemRef.mc("wither_skeleton_spawn_egg"),
    ItemRef.mc("wolf_spawn_egg"),
    ItemRef.mc("zoglin_spawn_egg"),
    ItemRef.mc("zombie_spawn_egg"),
    ItemRef.mc("zombie_horse_spawn_egg"),
    ItemRef.mc("zombie_villager_spawn_egg"),
    ItemRef.mc("zombified_piglin_spawn_egg"),
    ItemRef.mc("experience_bottle"),
    ItemRef.mc("fire_charge"),
//    ItemRef.mc("writable_book"),
//    ItemRef.mc("written_book"),
    ItemRef.mc("item_frame"),
    ItemRef.mc("glow_item_frame"),
    ItemRef.mc("carrot"),
    ItemRef.mc("potato"),
    ItemRef.mc("baked_potato"),
    ItemRef.mc("poisonous_potato"),
    ItemRef.mc("map"),
    ItemRef.mc("golden_carrot"),
    ItemRef.mc("nether_star"),
    ItemRef.mc("pumpkin_pie"),
//    ItemRef.mc("firework_rocket"), // TODO: research fireworks
//    ItemRef.mc("firework_star"),
//    ItemRef.mc("enchanted_book"), // TODO: only stack on matching meta
    ItemRef.mc("nether_brick"),
    ItemRef.mc("prismarine_shard"),
    ItemRef.mc("prismarine_crystals"),
    ItemRef.mc("rabbit"),
    ItemRef.mc("cooked_rabbit"),
    ItemRef.mc("rabbit_stew"),
    ItemRef.mc("rabbit_foot"),
    ItemRef.mc("rabbit_hide"),
    ItemRef.mc("armor_stand"),
    ItemRef.mc("iron_horse_armor"),
    ItemRef.mc("golden_horse_armor"),
    ItemRef.mc("diamond_horse_armor"),
    ItemRef.mc("leather_horse_armor"),
    ItemRef.mc("lead"),
    ItemRef.mc("name_tag"), // TODO: only stack on unused
    ItemRef.mc("command_block_minecart"),
    ItemRef.mc("mutton"),
    ItemRef.mc("cooked_mutton"),
    ItemRef.mc("end_crystal"),
    ItemRef.mc("chorus_fruit"),
    ItemRef.mc("popped_chorus_fruit"),
    ItemRef.mc("torchflower_seeds"),
    ItemRef.mc("beetroot"),
    ItemRef.mc("beetroot_seeds"),
    ItemRef.mc("beetroot_soup"),
    ItemRef.mc("dragon_breath"),
//    ItemRef.mc("splash_potion"), // TODO: only stack on matching meta
    ItemRef.mc("spectral_arrow"),
//    ItemRef.mc("tipped_arrow"), // TODO: only stack on matching meta
//    ItemRef.mc("lingering_potion"), // TODO: only stack on matching meta
//    ItemRef.mc("shield"), // TODO: only stack on matching meta (color)
    ItemRef.mc("totem_of_undying"),
    ItemRef.mc("shulker_shell"),
//    ItemRef.mc("iron_nugget"), // see: iron_ingot
//    ItemRef.mc("knowledge_book"), // TODO: research knowledge book
//    ItemRef.mc("debug_stick"), // TODO: research debug stick
    ItemRef.mc("music_disc_13"),
    ItemRef.mc("music_disc_cat"),
    ItemRef.mc("music_disc_blocks"),
    ItemRef.mc("music_disc_chirp"),
    ItemRef.mc("music_disc_far"),
    ItemRef.mc("music_disc_mall"),
    ItemRef.mc("music_disc_mellohi"),
    ItemRef.mc("music_disc_stal"),
    ItemRef.mc("music_disc_strad"),
    ItemRef.mc("music_disc_ward"),
    ItemRef.mc("music_disc_11"),
    ItemRef.mc("music_disc_wait"),
    ItemRef.mc("music_disc_otherside"),
    ItemRef.mc("music_disc_relic"),
    ItemRef.mc("music_disc_5"),
    ItemRef.mc("music_disc_pigstep"),
    ItemRef.mc("disc_fragment_5"),
    ItemRef.mc("trident"),
    ItemRef.mc("phantom_membrane"),
    ItemRef.mc("nautilus_shell"),
    ItemRef.mc("heart_of_the_sea"),
    ItemRef.mc("crossbow"),
//    ItemRef.mc("suspicious_stew"), // TODO: only stack on matching meta

    // TODO: research banner patterns
//    ItemRef.mc("flower_banner_pattern"),
//    ItemRef.mc("creeper_banner_pattern"),
//    ItemRef.mc("skull_banner_pattern"),
//    ItemRef.mc("mojang_banner_pattern"),
//    ItemRef.mc("globe_banner_pattern"),
//    ItemRef.mc("piglin_banner_pattern"),

    ItemRef.mc("goat_horn"),
    ItemRef.mc("sweet_berries"),
    ItemRef.mc("glow_berries"),
    ItemRef.mc("honeycomb"),
    ItemRef.mc("honey_bottle"),
    ItemRef.mc("echo_shard"),
    ItemRef.mc("brush"),

    ItemRef.mc("netherite_upgrade_smithing_template"),
    ItemRef.mc("sentry_armor_trim_smithing_template"),
    ItemRef.mc("dune_armor_trim_smithing_template"),
    ItemRef.mc("coast_armor_trim_smithing_template"),
    ItemRef.mc("wild_armor_trim_smithing_template"),
    ItemRef.mc("ward_armor_trim_smithing_template"),
    ItemRef.mc("eye_armor_trim_smithing_template"),
    ItemRef.mc("vex_armor_trim_smithing_template"),
    ItemRef.mc("tide_armor_trim_smithing_template"),
    ItemRef.mc("snout_armor_trim_smithing_template"),
    ItemRef.mc("rib_armor_trim_smithing_template"),
    ItemRef.mc("spire_armor_trim_smithing_template"),
    ItemRef.mc("wayfinder_armor_trim_smithing_template"),
    ItemRef.mc("shaper_armor_trim_smithing_template"),
    ItemRef.mc("silence_armor_trim_smithing_template"),
    ItemRef.mc("raiser_armor_trim_smithing_template"),
    ItemRef.mc("host_armor_trim_smithing_template"),

    ItemRef.mc("angler_pottery_sherd"),
    ItemRef.mc("archer_pottery_sherd"),
    ItemRef.mc("arms_up_pottery_sherd"),
    ItemRef.mc("blade_pottery_sherd"),
    ItemRef.mc("brewer_pottery_sherd"),
    ItemRef.mc("burn_pottery_sherd"),
    ItemRef.mc("danger_pottery_sherd"),
    ItemRef.mc("explorer_pottery_sherd"),
    ItemRef.mc("friend_pottery_sherd"),
    ItemRef.mc("heart_pottery_sherd"),
    ItemRef.mc("heartbreak_pottery_sherd"),
    ItemRef.mc("howl_pottery_sherd"),
    ItemRef.mc("miner_pottery_sherd"),
    ItemRef.mc("mourner_pottery_sherd"),
    ItemRef.mc("plenty_pottery_sherd"),
    ItemRef.mc("prize_pottery_sherd"),
    ItemRef.mc("sheaf_pottery_sherd"),
    ItemRef.mc("shelter_pottery_sherd"),
    ItemRef.mc("skull_pottery_sherd"),
    ItemRef.mc("snort_pottery_sherd"),

    ItemRef.mc("trial_key"),
    ItemRef.mc(""),
    ItemRef.mc(""),
    ItemRef.mc(""),
    ItemRef.mc(""),
    ItemRef.mc(""),
    ItemRef.mc(""),
    ItemRef.mc(""),
    ItemRef.mc(""),
    ItemRef.mc(""),
    ItemRef.mc(""),
    ItemRef.mc(""),
    ItemRef.mc(""),
    ItemRef.mc(""),
  )

  private val customRegistry = mutableMapOf<String, ItemRef>()

  /**
   * Tests whether the given namespaced key is registered with the
   * [ItemRegistry] object.
   *
   * @param namespacedKey Namespaced key value such as "minecraft:acacia_boat".
   *
   * @return `true` if the given item reference exists in the item registry,
   * else `false`.
   */
  @JvmStatic
  operator fun contains(namespacedKey: String) = namespacedKey in builtInRegistry || namespacedKey in customRegistry

  /**
   * Tests whether the given [NamespacedKey] is registered with the
   * [ItemRegistry] object.
   *
   * @param namespacedKey NamespacedKey instance to test for.
   *
   * @return `true` if the given item reference exists in the item registry,
   * else `false`.
   */
  @JvmStatic
  operator fun contains(namespacedKey: NamespacedKey) = namespacedKey.toString() in this

  /**
   * Tests whether the given [Material] is registered with the [ItemRegistry]
   * object.
   *
   * @param material Material instance to test for.
   *
   * @return `true` if the given material is of an item type and exists in the
   * item registry, else `false`.
   */
  @JvmStatic
  operator fun contains(material: Material) = material.isItem && material.key in this

  /**
   * Tests whether the item type of the given [ItemStack] is registered with the
   * [ItemRegistry] object.
   *
   * @param item ItemStack instance whose type will be tested for.
   *
   * @return `true` if the type of the given ItemStack is of an item type and
   * exists in the item registry, else `false`.
   */
  @JvmStatic
  operator fun contains(item: ItemStack) = item.type in this

  @JvmStatic
  fun clearCustomRegistry() {
    customRegistry.clear()
  }

  @JvmStatic
  fun putCustom(namespacedKey: String, allowOverwrite: Boolean = false) {
    if (!allowOverwrite && namespacedKey in customRegistry)
      throw IllegalStateException("attempted to add multiple instances of the item reference $namespacedKey to the item registry")

    customRegistry[namespacedKey] = ItemRef(namespacedKey)
  }

  private fun mapOf(vararg ref: ItemRef): Map<String, ItemRef> =
    HashMap<String, ItemRef>(ref.size).apply { ref.forEach { put(it.key, it) } }
}