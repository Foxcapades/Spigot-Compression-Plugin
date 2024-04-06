package io.foxcapades.spigot.block.compression.block

object Blocks {
  private val builtInRegistry = mapOf(
    BlockRef.mc("air"),
    BlockRef.mc("stone"),
    BlockRef.mc("granite"),
    BlockRef.mc("polished_granite"),
    BlockRef.mc("diorite"),
    BlockRef.mc("polished_diorite"),
    BlockRef.mc("andesite"),
    BlockRef.mc("polished_andesite"),
    BlockRef.mc("deepslate"),
    BlockRef.mc("cobbled_deepslate"),
    BlockRef.mc("polished_deepslate"),
    BlockRef.mc("calcite"),
    BlockRef.mc("tuff"),
    BlockRef.mc("tuff_slab"),
    BlockRef.mc("tuff_stairs"),
    BlockRef.mc("tuff_wall"),
    BlockRef.mc("chiseled_tuff"),
    BlockRef.mc("polished_tuff"),
    BlockRef.mc("polished_tuff_slab"),
    BlockRef.mc("polished_tuff_stairs"),
    BlockRef.mc("polished_tuff_wall"),
    BlockRef.mc("tuff_bricks"),
    BlockRef.mc("tuff_brick_slab"),
    BlockRef.mc("tuff_brick_stairs"),
    BlockRef.mc("tuff_brick_wall"),
    BlockRef.mc("chiseled_tuff_bricks"),
    BlockRef.mc("dripstone_block"),
    BlockRef.mc("grass_block"),
    BlockRef.mc("dirt"),
    BlockRef.mc("coarse_dirt"),
    BlockRef.mc("podzol"),
    BlockRef.mc("rooted_dirt"),
    BlockRef.mc("mud"),
    BlockRef.mc("crimson_nylium"),
    BlockRef.mc("warped_nylium"),
    BlockRef.mc("cobblestone"),
    BlockRef.mc("oak_planks"),
    BlockRef.mc("spruce_planks"),
    BlockRef.mc("birch_planks"),
    BlockRef.mc("jungle_planks"),
    BlockRef.mc("acacia_planks"),
    BlockRef.mc("cherry_planks"),
    BlockRef.mc("dark_oak_planks"),
    BlockRef.mc("mangrove_planks"),
    BlockRef.mc("bamboo_planks"),
    BlockRef.mc("crimson_planks"),
    BlockRef.mc("warped_planks"),
    BlockRef.mc("bamboo_mosaic"),
    BlockRef.mc("oak_sapling"),
    BlockRef.mc("spruce_sapling"),
    BlockRef.mc("birch_sapling"),
    BlockRef.mc("jungle_sapling"),
    BlockRef.mc("acacia_sapling"),
    BlockRef.mc("cherry_sapling"),
    BlockRef.mc("dark_oak_sapling"),
    BlockRef.mc("mangrove_propagule"),
    BlockRef.mc("bedrock"),
    BlockRef.mc("sand"),
    BlockRef.mc("suspicious_sand"),
    BlockRef.mc("suspicious_gravel"),
    BlockRef.mc("red_sand"),
    BlockRef.mc("gravel"),
    BlockRef.mc("coal_ore"),
    BlockRef.mc("deepslate_coal_ore"),
    BlockRef.mc("iron_ore"),
    BlockRef.mc("deepslate_iron_ore"),
    BlockRef.mc("copper_ore"),
    BlockRef.mc("deepslate_copper_ore"),
    BlockRef.mc("gold_ore"),
    BlockRef.mc("deepslate_gold_ore"),
    BlockRef.mc("redstone_ore"),
    BlockRef.mc("deepslate_redstone_ore"),
    BlockRef.mc("emerald_ore"),
    BlockRef.mc("deepslate_emerald_ore"),
    BlockRef.mc("lapis_ore"),
    BlockRef.mc("deepslate_lapis_ore"),
    BlockRef.mc("diamond_ore"),
    BlockRef.mc("deepslate_diamond_ore"),
    BlockRef.mc("nether_gold_ore"),
    BlockRef.mc("nether_quartz_ore"),
    BlockRef.mc("ancient_debris"),
    BlockRef.mc("coal_block"),
    BlockRef.mc("raw_iron_block"),
    BlockRef.mc("raw_copper_block"),
    BlockRef.mc("raw_gold_block"),
    BlockRef.mc("amethyst_block"),
    BlockRef.mc("budding_amethyst"),
    BlockRef.mc("iron_block"),
    BlockRef.mc("copper_block"),
    BlockRef.mc("gold_block"),
    BlockRef.mc("diamond_block"),
    BlockRef.mc("netherite_block"),
    BlockRef.mc("exposed_copper"),
    BlockRef.mc("weathered_copper"),
    BlockRef.mc("oxidized_copper"),
    BlockRef.mc("chiseled_copper"),
    BlockRef.mc("exposed_chiseled_copper"),
    BlockRef.mc("weathered_chiseled_copper"),
    BlockRef.mc("oxidized_chiseled_copper"),
    BlockRef.mc("cut_copper"),
    BlockRef.mc("exposed_cut_copper"),
    BlockRef.mc("weathered_cut_copper"),
    BlockRef.mc("oxidized_cut_copper"),
    BlockRef.mc("cut_copper_stairs"),
    BlockRef.mc("exposed_cut_copper_stairs"),
    BlockRef.mc("weathered_cut_copper_stairs"),
    BlockRef.mc("oxidized_cut_copper_stairs"),
    BlockRef.mc("cut_copper_slab"),
    BlockRef.mc("exposed_cut_copper_slab"),
    BlockRef.mc("weathered_cut_copper_slab"),
    BlockRef.mc("oxidized_cut_copper_slab"),
    BlockRef.mc("waxed_copper_block"),
    BlockRef.mc("waxed_exposed_copper"),
    BlockRef.mc("waxed_weathered_copper"),
    BlockRef.mc("waxed_oxidized_copper"),
    BlockRef.mc("waxed_chiseled_copper"),
    BlockRef.mc("waxed_exposed_chiseled_copper"),
    BlockRef.mc("waxed_weathered_chiseled_copper"),
    BlockRef.mc("waxed_oxidized_chiseled_copper"),
    BlockRef.mc("waxed_cut_copper"),
    BlockRef.mc("waxed_exposed_cut_copper"),
    BlockRef.mc("waxed_weathered_cut_copper"),
    BlockRef.mc("waxed_oxidized_cut_copper"),
    BlockRef.mc("waxed_cut_copper_stairs"),
    BlockRef.mc("waxed_exposed_cut_copper_stairs"),
    BlockRef.mc("waxed_weathered_cut_copper_stairs"),
    BlockRef.mc("waxed_oxidized_cut_copper_stairs"),
    BlockRef.mc("waxed_cut_copper_slab"),
    BlockRef.mc("waxed_exposed_cut_copper_slab"),
    BlockRef.mc("waxed_weathered_cut_copper_slab"),
    BlockRef.mc("waxed_oxidized_cut_copper_slab"),
    BlockRef.mc("oak_log"),
    BlockRef.mc("spruce_log"),
    BlockRef.mc("birch_log"),
    BlockRef.mc("jungle_log"),
    BlockRef.mc("acacia_log"),
    BlockRef.mc("cherry_log"),
    BlockRef.mc("dark_oak_log"),
    BlockRef.mc("mangrove_log"),
    BlockRef.mc("mangrove_roots"),
    BlockRef.mc("muddy_mangrove_roots"),
    BlockRef.mc("crimson_stem"),
    BlockRef.mc("warped_stem"),
    BlockRef.mc("bamboo_block"),
    BlockRef.mc("stripped_oak_log"),
    BlockRef.mc("stripped_spruce_log"),
    BlockRef.mc("stripped_birch_log"),
    BlockRef.mc("stripped_jungle_log"),
    BlockRef.mc("stripped_acacia_log"),
    BlockRef.mc("stripped_cherry_log"),
    BlockRef.mc("stripped_dark_oak_log"),
    BlockRef.mc("stripped_mangrove_log"),
    BlockRef.mc("stripped_crimson_stem"),
    BlockRef.mc("stripped_warped_stem"),
    BlockRef.mc("stripped_oak_wood"),
    BlockRef.mc("stripped_spruce_wood"),
    BlockRef.mc("stripped_birch_wood"),
    BlockRef.mc("stripped_jungle_wood"),
    BlockRef.mc("stripped_acacia_wood"),
    BlockRef.mc("stripped_cherry_wood"),
    BlockRef.mc("stripped_dark_oak_wood"),
    BlockRef.mc("stripped_mangrove_wood"),
    BlockRef.mc("stripped_crimson_hyphae"),
    BlockRef.mc("stripped_warped_hyphae"),
    BlockRef.mc("stripped_bamboo_block"),
    BlockRef.mc("oak_wood"),
    BlockRef.mc("spruce_wood"),
    BlockRef.mc("birch_wood"),
    BlockRef.mc("jungle_wood"),
    BlockRef.mc("acacia_wood"),
    BlockRef.mc("cherry_wood"),
    BlockRef.mc("dark_oak_wood"),
    BlockRef.mc("mangrove_wood"),
    BlockRef.mc("crimson_hyphae"),
    BlockRef.mc("warped_hyphae"),
    BlockRef.mc("oak_leaves"),
    BlockRef.mc("spruce_leaves"),
    BlockRef.mc("birch_leaves"),
    BlockRef.mc("jungle_leaves"),
    BlockRef.mc("acacia_leaves"),
    BlockRef.mc("cherry_leaves"),
    BlockRef.mc("dark_oak_leaves"),
    BlockRef.mc("mangrove_leaves"),
    BlockRef.mc("azalea_leaves"),
    BlockRef.mc("flowering_azalea_leaves"),
    BlockRef.mc("sponge"),
    BlockRef.mc("wet_sponge"),
    BlockRef.mc("glass"),
    BlockRef.mc("tinted_glass"),
    BlockRef.mc("lapis_block"),
    BlockRef.mc("sandstone"),
    BlockRef.mc("chiseled_sandstone"),
    BlockRef.mc("cut_sandstone"),
    BlockRef.mc("cobweb"),
    BlockRef.mc("short_grass"),
    BlockRef.mc("fern"),
    BlockRef.mc("azalea"),
    BlockRef.mc("flowering_azalea"),
    BlockRef.mc("dead_bush"),
    BlockRef.mc("seagrass"),
    BlockRef.mc("sea_pickle"),
    BlockRef.mc("white_wool"),
    BlockRef.mc("orange_wool"),
    BlockRef.mc("magenta_wool"),
    BlockRef.mc("light_blue_wool"),
    BlockRef.mc("yellow_wool"),
    BlockRef.mc("lime_wool"),
    BlockRef.mc("pink_wool"),
    BlockRef.mc("gray_wool"),
    BlockRef.mc("light_gray_wool"),
    BlockRef.mc("cyan_wool"),
    BlockRef.mc("purple_wool"),
    BlockRef.mc("blue_wool"),
    BlockRef.mc("brown_wool"),
    BlockRef.mc("green_wool"),
    BlockRef.mc("red_wool"),
    BlockRef.mc("black_wool"),
    BlockRef.mc("dandelion"),
    BlockRef.mc("poppy"),
    BlockRef.mc("blue_orchid"),
    BlockRef.mc("allium"),
    BlockRef.mc("azure_bluet"),
    BlockRef.mc("red_tulip"),
    BlockRef.mc("orange_tulip"),
    BlockRef.mc("white_tulip"),
    BlockRef.mc("pink_tulip"),
    BlockRef.mc("oxeye_daisy"),
    BlockRef.mc("cornflower"),
    BlockRef.mc("lily_of_the_valley"),
    BlockRef.mc("wither_rose"),
    BlockRef.mc("torchflower"),
    BlockRef.mc("pitcher_plant"),
    BlockRef.mc("spore_blossom"),
    BlockRef.mc("brown_mushroom"),
    BlockRef.mc("red_mushroom"),
    BlockRef.mc("crimson_fungus"),
    BlockRef.mc("warped_fungus"),
    BlockRef.mc("crimson_roots"),
    BlockRef.mc("warped_roots"),
    BlockRef.mc("nether_sprouts"),
    BlockRef.mc("weeping_vines"),
    BlockRef.mc("twisting_vines"),
    BlockRef.mc("sugar_cane"),
    BlockRef.mc("kelp"),
    BlockRef.mc("moss_carpet"),
    BlockRef.mc("pink_petals"),
    BlockRef.mc("moss_block"),
    BlockRef.mc("hanging_roots"),
    BlockRef.mc("big_dripleaf"),
    BlockRef.mc("small_dripleaf"),
    BlockRef.mc("bamboo"),
    BlockRef.mc("oak_slab"),
    BlockRef.mc("spruce_slab"),
    BlockRef.mc("birch_slab"),
    BlockRef.mc("jungle_slab"),
    BlockRef.mc("acacia_slab"),
    BlockRef.mc("cherry_slab"),
    BlockRef.mc("dark_oak_slab"),
    BlockRef.mc("mangrove_slab"),
    BlockRef.mc("bamboo_slab"),
    BlockRef.mc("bamboo_mosaic_slab"),
    BlockRef.mc("crimson_slab"),
    BlockRef.mc("warped_slab"),
    BlockRef.mc("stone_slab"),
    BlockRef.mc("smooth_stone_slab"),
    BlockRef.mc("sandstone_slab"),
    BlockRef.mc("cut_sandstone_slab"),
    BlockRef.mc("petrified_oak_slab"),
    BlockRef.mc("cobblestone_slab"),
    BlockRef.mc("brick_slab"),
    BlockRef.mc("stone_brick_slab"),
    BlockRef.mc("mud_brick_slab"),
    BlockRef.mc("nether_brick_slab"),
    BlockRef.mc("quartz_slab"),
    BlockRef.mc("red_sandstone_slab"),
    BlockRef.mc("cut_red_sandstone_slab"),
    BlockRef.mc("purpur_slab"),
    BlockRef.mc("prismarine_slab"),
    BlockRef.mc("prismarine_brick_slab"),
    BlockRef.mc("dark_prismarine_slab"),
    BlockRef.mc("smooth_quartz"),
    BlockRef.mc("smooth_red_sandstone"),
    BlockRef.mc("smooth_sandstone"),
    BlockRef.mc("smooth_stone"),
    BlockRef.mc("bricks"),
    BlockRef.mc("bookshelf"),
    BlockRef.mc("chiseled_bookshelf"),
    BlockRef.mc("decorated_pot"),
    BlockRef.mc("mossy_cobblestone"),
    BlockRef.mc("obsidian"),
    BlockRef.mc("torch"),
    BlockRef.mc("end_rod"),
    BlockRef.mc("chorus_plant"),
    BlockRef.mc("chorus_flower"),
    BlockRef.mc("purpur_block"),
    BlockRef.mc("purpur_pillar"),
    BlockRef.mc("purpur_stairs"),
    BlockRef.mc("spawner"),
    BlockRef.mc("chest"),
    BlockRef.mc("crafting_table"),
    BlockRef.mc("farmland"),
    BlockRef.mc("furnace"),
    BlockRef.mc("ladder"),
    BlockRef.mc("cobblestone_stairs"),
    BlockRef.mc("snow"),
    BlockRef.mc("ice"),
    BlockRef.mc("snow_block"),
    BlockRef.mc("cactus"),
    BlockRef.mc("clay"),
    BlockRef.mc("jukebox"),
    BlockRef.mc("oak_fence"),
    BlockRef.mc("spruce_fence"),
    BlockRef.mc("birch_fence"),
    BlockRef.mc("jungle_fence"),
    BlockRef.mc("acacia_fence"),
    BlockRef.mc("cherry_fence"),
    BlockRef.mc("dark_oak_fence"),
    BlockRef.mc("mangrove_fence"),
    BlockRef.mc("bamboo_fence"),
    BlockRef.mc("crimson_fence"),
    BlockRef.mc("warped_fence"),
    BlockRef.mc("pumpkin"),
    BlockRef.mc("carved_pumpkin"),
    BlockRef.mc("jack_o_lantern"),
    BlockRef.mc("netherrack"),
    BlockRef.mc("soul_sand"),
    BlockRef.mc("soul_soil"),
    BlockRef.mc("basalt"),
    BlockRef.mc("polished_basalt"),
    BlockRef.mc("smooth_basalt"),
    BlockRef.mc("soul_torch"),
    BlockRef.mc("glowstone"),
    BlockRef.mc("infested_stone"),
    BlockRef.mc("infested_cobblestone"),
    BlockRef.mc("infested_stone_bricks"),
    BlockRef.mc("infested_mossy_stone_bricks"),
    BlockRef.mc("infested_cracked_stone_bricks"),
    BlockRef.mc("infested_chiseled_stone_bricks"),
    BlockRef.mc("infested_deepslate"),
    BlockRef.mc("stone_bricks"),
    BlockRef.mc("mossy_stone_bricks"),
    BlockRef.mc("cracked_stone_bricks"),
    BlockRef.mc("chiseled_stone_bricks"),
    BlockRef.mc("packed_mud"),
    BlockRef.mc("mud_bricks"),
    BlockRef.mc("deepslate_bricks"),
    BlockRef.mc("cracked_deepslate_bricks"),
    BlockRef.mc("deepslate_tiles"),
    BlockRef.mc("cracked_deepslate_tiles"),
    BlockRef.mc("chiseled_deepslate"),
    BlockRef.mc("reinforced_deepslate"),
    BlockRef.mc("brown_mushroom_block"),
    BlockRef.mc("red_mushroom_block"),
    BlockRef.mc("mushroom_stem"),
    BlockRef.mc("iron_bars"),
    BlockRef.mc("chain"),
    BlockRef.mc("glass_pane"),
    BlockRef.mc("melon"),
    BlockRef.mc("vine"),
    BlockRef.mc("glow_lichen"),
    BlockRef.mc("brick_stairs"),
    BlockRef.mc("stone_brick_stairs"),
    BlockRef.mc("mud_brick_stairs"),
    BlockRef.mc("mycelium"),
    BlockRef.mc("lily_pad"),
    BlockRef.mc("nether_bricks"),
    BlockRef.mc("cracked_nether_bricks"),
    BlockRef.mc("chiseled_nether_bricks"),
    BlockRef.mc("nether_brick_fence"),
    BlockRef.mc("nether_brick_stairs"),
    BlockRef.mc("sculk"),
    BlockRef.mc("sculk_vein"),
    BlockRef.mc("sculk_catalyst"),
    BlockRef.mc("sculk_shrieker"),
    BlockRef.mc("enchanting_table"),
    BlockRef.mc("end_portal_frame"),
    BlockRef.mc("end_stone"),
    BlockRef.mc("end_stone_bricks"),
    BlockRef.mc("dragon_egg"),
    BlockRef.mc("sandstone_stairs"),
    BlockRef.mc("ender_chest"),
    BlockRef.mc("emerald_block"),
    BlockRef.mc("oak_stairs"),
    BlockRef.mc("spruce_stairs"),
    BlockRef.mc("birch_stairs"),
    BlockRef.mc("jungle_stairs"),
    BlockRef.mc("acacia_stairs"),
    BlockRef.mc("cherry_stairs"),
    BlockRef.mc("dark_oak_stairs"),
    BlockRef.mc("mangrove_stairs"),
    BlockRef.mc("bamboo_stairs"),
    BlockRef.mc("bamboo_mosaic_stairs"),
    BlockRef.mc("crimson_stairs"),
    BlockRef.mc("warped_stairs"),
    BlockRef.mc("command_block"),
    BlockRef.mc("beacon"),
    BlockRef.mc("cobblestone_wall"),
    BlockRef.mc("mossy_cobblestone_wall"),
    BlockRef.mc("brick_wall"),
    BlockRef.mc("prismarine_wall"),
    BlockRef.mc("red_sandstone_wall"),
    BlockRef.mc("mossy_stone_brick_wall"),
    BlockRef.mc("granite_wall"),
    BlockRef.mc("stone_brick_wall"),
    BlockRef.mc("mud_brick_wall"),
    BlockRef.mc("nether_brick_wall"),
    BlockRef.mc("andesite_wall"),
    BlockRef.mc("red_nether_brick_wall"),
    BlockRef.mc("sandstone_wall"),
    BlockRef.mc("end_stone_brick_wall"),
    BlockRef.mc("diorite_wall"),
    BlockRef.mc("blackstone_wall"),
    BlockRef.mc("polished_blackstone_wall"),
    BlockRef.mc("polished_blackstone_brick_wall"),
    BlockRef.mc("cobbled_deepslate_wall"),
    BlockRef.mc("polished_deepslate_wall"),
    BlockRef.mc("deepslate_brick_wall"),
    BlockRef.mc("deepslate_tile_wall"),
    BlockRef.mc("anvil"),
    BlockRef.mc("chipped_anvil"),
    BlockRef.mc("damaged_anvil"),
    BlockRef.mc("chiseled_quartz_block"),
    BlockRef.mc("quartz_block"),
    BlockRef.mc("quartz_bricks"),
    BlockRef.mc("quartz_pillar"),
    BlockRef.mc("quartz_stairs"),
    BlockRef.mc("white_terracotta"),
    BlockRef.mc("orange_terracotta"),
    BlockRef.mc("magenta_terracotta"),
    BlockRef.mc("light_blue_terracotta"),
    BlockRef.mc("yellow_terracotta"),
    BlockRef.mc("lime_terracotta"),
    BlockRef.mc("pink_terracotta"),
    BlockRef.mc("gray_terracotta"),
    BlockRef.mc("light_gray_terracotta"),
    BlockRef.mc("cyan_terracotta"),
    BlockRef.mc("purple_terracotta"),
    BlockRef.mc("blue_terracotta"),
    BlockRef.mc("brown_terracotta"),
    BlockRef.mc("green_terracotta"),
    BlockRef.mc("red_terracotta"),
    BlockRef.mc("black_terracotta"),
    BlockRef.mc("barrier"),
    BlockRef.mc("light"),
    BlockRef.mc("hay_block"),
    BlockRef.mc("white_carpet"),
    BlockRef.mc("orange_carpet"),
    BlockRef.mc("magenta_carpet"),
    BlockRef.mc("light_blue_carpet"),
    BlockRef.mc("yellow_carpet"),
    BlockRef.mc("lime_carpet"),
    BlockRef.mc("pink_carpet"),
    BlockRef.mc("gray_carpet"),
    BlockRef.mc("light_gray_carpet"),
    BlockRef.mc("cyan_carpet"),
    BlockRef.mc("purple_carpet"),
    BlockRef.mc("blue_carpet"),
    BlockRef.mc("brown_carpet"),
    BlockRef.mc("green_carpet"),
    BlockRef.mc("red_carpet"),
    BlockRef.mc("black_carpet"),
    BlockRef.mc("terracotta"),
    BlockRef.mc("packed_ice"),
    BlockRef.mc("dirt_path"),
    BlockRef.mc("sunflower"),
    BlockRef.mc("lilac"),
    BlockRef.mc("rose_bush"),
    BlockRef.mc("peony"),
    BlockRef.mc("tall_grass"),
    BlockRef.mc("large_fern"),
    BlockRef.mc("white_stained_glass"),
    BlockRef.mc("orange_stained_glass"),
    BlockRef.mc("magenta_stained_glass"),
    BlockRef.mc("light_blue_stained_glass"),
    BlockRef.mc("yellow_stained_glass"),
    BlockRef.mc("lime_stained_glass"),
    BlockRef.mc("pink_stained_glass"),
    BlockRef.mc("gray_stained_glass"),
    BlockRef.mc("light_gray_stained_glass"),
    BlockRef.mc("cyan_stained_glass"),
    BlockRef.mc("purple_stained_glass"),
    BlockRef.mc("blue_stained_glass"),
    BlockRef.mc("brown_stained_glass"),
    BlockRef.mc("green_stained_glass"),
    BlockRef.mc("red_stained_glass"),
    BlockRef.mc("black_stained_glass"),
    BlockRef.mc("white_stained_glass_pane"),
    BlockRef.mc("orange_stained_glass_pane"),
    BlockRef.mc("magenta_stained_glass_pane"),
    BlockRef.mc("light_blue_stained_glass_pane"),
    BlockRef.mc("yellow_stained_glass_pane"),
    BlockRef.mc("lime_stained_glass_pane"),
    BlockRef.mc("pink_stained_glass_pane"),
    BlockRef.mc("gray_stained_glass_pane"),
    BlockRef.mc("light_gray_stained_glass_pane"),
    BlockRef.mc("cyan_stained_glass_pane"),
    BlockRef.mc("purple_stained_glass_pane"),
    BlockRef.mc("blue_stained_glass_pane"),
    BlockRef.mc("brown_stained_glass_pane"),
    BlockRef.mc("green_stained_glass_pane"),
    BlockRef.mc("red_stained_glass_pane"),
    BlockRef.mc("black_stained_glass_pane"),
    BlockRef.mc("prismarine"),
    BlockRef.mc("prismarine_bricks"),
    BlockRef.mc("dark_prismarine"),
    BlockRef.mc("prismarine_stairs"),
    BlockRef.mc("prismarine_brick_stairs"),
    BlockRef.mc("dark_prismarine_stairs"),
    BlockRef.mc("sea_lantern"),
    BlockRef.mc("red_sandstone"),
    BlockRef.mc("chiseled_red_sandstone"),
    BlockRef.mc("cut_red_sandstone"),
    BlockRef.mc("red_sandstone_stairs"),
    BlockRef.mc("repeating_command_block"),
    BlockRef.mc("chain_command_block"),
    BlockRef.mc("magma_block"),
    BlockRef.mc("nether_wart_block"),
    BlockRef.mc("warped_wart_block"),
    BlockRef.mc("red_nether_bricks"),
    BlockRef.mc("bone_block"),
    BlockRef.mc("structure_void"),
    BlockRef.mc("shulker_box"),
    BlockRef.mc("white_shulker_box"),
    BlockRef.mc("orange_shulker_box"),
    BlockRef.mc("magenta_shulker_box"),
    BlockRef.mc("light_blue_shulker_box"),
    BlockRef.mc("yellow_shulker_box"),
    BlockRef.mc("lime_shulker_box"),
    BlockRef.mc("pink_shulker_box"),
    BlockRef.mc("gray_shulker_box"),
    BlockRef.mc("light_gray_shulker_box"),
    BlockRef.mc("cyan_shulker_box"),
    BlockRef.mc("purple_shulker_box"),
    BlockRef.mc("blue_shulker_box"),
    BlockRef.mc("brown_shulker_box"),
    BlockRef.mc("green_shulker_box"),
    BlockRef.mc("red_shulker_box"),
    BlockRef.mc("black_shulker_box"),
    BlockRef.mc("white_glazed_terracotta"),
    BlockRef.mc("orange_glazed_terracotta"),
    BlockRef.mc("magenta_glazed_terracotta"),
    BlockRef.mc("light_blue_glazed_terracotta"),
    BlockRef.mc("yellow_glazed_terracotta"),
    BlockRef.mc("lime_glazed_terracotta"),
    BlockRef.mc("pink_glazed_terracotta"),
    BlockRef.mc("gray_glazed_terracotta"),
    BlockRef.mc("light_gray_glazed_terracotta"),
    BlockRef.mc("cyan_glazed_terracotta"),
    BlockRef.mc("purple_glazed_terracotta"),
    BlockRef.mc("blue_glazed_terracotta"),
    BlockRef.mc("brown_glazed_terracotta"),
    BlockRef.mc("green_glazed_terracotta"),
    BlockRef.mc("red_glazed_terracotta"),
    BlockRef.mc("black_glazed_terracotta"),
    BlockRef.mc("white_concrete"),
    BlockRef.mc("orange_concrete"),
    BlockRef.mc("magenta_concrete"),
    BlockRef.mc("light_blue_concrete"),
    BlockRef.mc("yellow_concrete"),
    BlockRef.mc("lime_concrete"),
    BlockRef.mc("pink_concrete"),
    BlockRef.mc("gray_concrete"),
    BlockRef.mc("light_gray_concrete"),
    BlockRef.mc("cyan_concrete"),
    BlockRef.mc("purple_concrete"),
    BlockRef.mc("blue_concrete"),
    BlockRef.mc("brown_concrete"),
    BlockRef.mc("green_concrete"),
    BlockRef.mc("red_concrete"),
    BlockRef.mc("black_concrete"),
    BlockRef.mc("white_concrete_powder"),
    BlockRef.mc("orange_concrete_powder"),
    BlockRef.mc("magenta_concrete_powder"),
    BlockRef.mc("light_blue_concrete_powder"),
    BlockRef.mc("yellow_concrete_powder"),
    BlockRef.mc("lime_concrete_powder"),
    BlockRef.mc("pink_concrete_powder"),
    BlockRef.mc("gray_concrete_powder"),
    BlockRef.mc("light_gray_concrete_powder"),
    BlockRef.mc("cyan_concrete_powder"),
    BlockRef.mc("purple_concrete_powder"),
    BlockRef.mc("blue_concrete_powder"),
    BlockRef.mc("brown_concrete_powder"),
    BlockRef.mc("green_concrete_powder"),
    BlockRef.mc("red_concrete_powder"),
    BlockRef.mc("black_concrete_powder"),
    BlockRef.mc("turtle_egg"),
    BlockRef.mc("sniffer_egg"),
    BlockRef.mc("dead_tube_coral_block"),
    BlockRef.mc("dead_brain_coral_block"),
    BlockRef.mc("dead_bubble_coral_block"),
    BlockRef.mc("dead_fire_coral_block"),
    BlockRef.mc("dead_horn_coral_block"),
    BlockRef.mc("tube_coral_block"),
    BlockRef.mc("brain_coral_block"),
    BlockRef.mc("bubble_coral_block"),
    BlockRef.mc("fire_coral_block"),
    BlockRef.mc("horn_coral_block"),
    BlockRef.mc("tube_coral"),
    BlockRef.mc("brain_coral"),
    BlockRef.mc("bubble_coral"),
    BlockRef.mc("fire_coral"),
    BlockRef.mc("horn_coral"),
    BlockRef.mc("dead_tube_coral"),
    BlockRef.mc("dead_brain_coral"),
    BlockRef.mc("dead_bubble_coral"),
    BlockRef.mc("dead_fire_coral"),
    BlockRef.mc("dead_horn_coral"),
    BlockRef.mc("tube_coral_fan"),
    BlockRef.mc("brain_coral_fan"),
    BlockRef.mc("bubble_coral_fan"),
    BlockRef.mc("fire_coral_fan"),
    BlockRef.mc("horn_coral_fan"),
    BlockRef.mc("dead_tube_coral_fan"),
    BlockRef.mc("dead_brain_coral_fan"),
    BlockRef.mc("dead_bubble_coral_fan"),
    BlockRef.mc("dead_fire_coral_fan"),
    BlockRef.mc("dead_horn_coral_fan"),
    BlockRef.mc("blue_ice"),
    BlockRef.mc("conduit"),
    BlockRef.mc("polished_granite_stairs"),
    BlockRef.mc("smooth_red_sandstone_stairs"),
    BlockRef.mc("mossy_stone_brick_stairs"),
    BlockRef.mc("polished_diorite_stairs"),
    BlockRef.mc("mossy_cobblestone_stairs"),
    BlockRef.mc("end_stone_brick_stairs"),
    BlockRef.mc("stone_stairs"),
    BlockRef.mc("smooth_sandstone_stairs"),
    BlockRef.mc("smooth_quartz_stairs"),
    BlockRef.mc("granite_stairs"),
    BlockRef.mc("andesite_stairs"),
    BlockRef.mc("red_nether_brick_stairs"),
    BlockRef.mc("polished_andesite_stairs"),
    BlockRef.mc("diorite_stairs"),
    BlockRef.mc("cobbled_deepslate_stairs"),
    BlockRef.mc("polished_deepslate_stairs"),
    BlockRef.mc("deepslate_brick_stairs"),
    BlockRef.mc("deepslate_tile_stairs"),
    BlockRef.mc("polished_granite_slab"),
    BlockRef.mc("smooth_red_sandstone_slab"),
    BlockRef.mc("mossy_stone_brick_slab"),
    BlockRef.mc("polished_diorite_slab"),
    BlockRef.mc("mossy_cobblestone_slab"),
    BlockRef.mc("end_stone_brick_slab"),
    BlockRef.mc("smooth_sandstone_slab"),
    BlockRef.mc("smooth_quartz_slab"),
    BlockRef.mc("granite_slab"),
    BlockRef.mc("andesite_slab"),
    BlockRef.mc("red_nether_brick_slab"),
    BlockRef.mc("polished_andesite_slab"),
    BlockRef.mc("diorite_slab"),
    BlockRef.mc("cobbled_deepslate_slab"),
    BlockRef.mc("polished_deepslate_slab"),
    BlockRef.mc("deepslate_brick_slab"),
    BlockRef.mc("deepslate_tile_slab"),
    BlockRef.mc("scaffolding"),
    BlockRef.mc("redstone_torch"),
    BlockRef.mc("redstone_block"),
    BlockRef.mc("repeater"),
    BlockRef.mc("comparator"),
    BlockRef.mc("piston"),
    BlockRef.mc("sticky_piston"),
    BlockRef.mc("slime_block"),
    BlockRef.mc("honey_block"),
    BlockRef.mc("observer"),
    BlockRef.mc("hopper"),
    BlockRef.mc("dispenser"),
    BlockRef.mc("dropper"),
    BlockRef.mc("lectern"),
    BlockRef.mc("target"),
    BlockRef.mc("lever"),
    BlockRef.mc("lightning_rod"),
    BlockRef.mc("daylight_detector"),
    BlockRef.mc("sculk_sensor"),
    BlockRef.mc("calibrated_sculk_sensor"),
    BlockRef.mc("tripwire_hook"),
    BlockRef.mc("trapped_chest"),
    BlockRef.mc("tnt"),
    BlockRef.mc("redstone_lamp"),
    BlockRef.mc("note_block"),
    BlockRef.mc("stone_button"),
    BlockRef.mc("polished_blackstone_button"),
    BlockRef.mc("oak_button"),
    BlockRef.mc("spruce_button"),
    BlockRef.mc("birch_button"),
    BlockRef.mc("jungle_button"),
    BlockRef.mc("acacia_button"),
    BlockRef.mc("cherry_button"),
    BlockRef.mc("dark_oak_button"),
    BlockRef.mc("mangrove_button"),
    BlockRef.mc("bamboo_button"),
    BlockRef.mc("crimson_button"),
    BlockRef.mc("warped_button"),
    BlockRef.mc("stone_pressure_plate"),
    BlockRef.mc("polished_blackstone_pressure_plate"),
    BlockRef.mc("light_weighted_pressure_plate"),
    BlockRef.mc("heavy_weighted_pressure_plate"),
    BlockRef.mc("oak_pressure_plate"),
    BlockRef.mc("spruce_pressure_plate"),
    BlockRef.mc("birch_pressure_plate"),
    BlockRef.mc("jungle_pressure_plate"),
    BlockRef.mc("acacia_pressure_plate"),
    BlockRef.mc("cherry_pressure_plate"),
    BlockRef.mc("dark_oak_pressure_plate"),
    BlockRef.mc("mangrove_pressure_plate"),
    BlockRef.mc("bamboo_pressure_plate"),
    BlockRef.mc("crimson_pressure_plate"),
    BlockRef.mc("warped_pressure_plate"),
    BlockRef.mc("iron_door"),
    BlockRef.mc("oak_door"),
    BlockRef.mc("spruce_door"),
    BlockRef.mc("birch_door"),
    BlockRef.mc("jungle_door"),
    BlockRef.mc("acacia_door"),
    BlockRef.mc("cherry_door"),
    BlockRef.mc("dark_oak_door"),
    BlockRef.mc("mangrove_door"),
    BlockRef.mc("bamboo_door"),
    BlockRef.mc("crimson_door"),
    BlockRef.mc("warped_door"),
    BlockRef.mc("copper_door"),
    BlockRef.mc("exposed_copper_door"),
    BlockRef.mc("weathered_copper_door"),
    BlockRef.mc("oxidized_copper_door"),
    BlockRef.mc("waxed_copper_door"),
    BlockRef.mc("waxed_exposed_copper_door"),
    BlockRef.mc("waxed_weathered_copper_door"),
    BlockRef.mc("waxed_oxidized_copper_door"),
    BlockRef.mc("iron_trapdoor"),
    BlockRef.mc("oak_trapdoor"),
    BlockRef.mc("spruce_trapdoor"),
    BlockRef.mc("birch_trapdoor"),
    BlockRef.mc("jungle_trapdoor"),
    BlockRef.mc("acacia_trapdoor"),
    BlockRef.mc("cherry_trapdoor"),
    BlockRef.mc("dark_oak_trapdoor"),
    BlockRef.mc("mangrove_trapdoor"),
    BlockRef.mc("bamboo_trapdoor"),
    BlockRef.mc("crimson_trapdoor"),
    BlockRef.mc("warped_trapdoor"),
    BlockRef.mc("copper_trapdoor"),
    BlockRef.mc("exposed_copper_trapdoor"),
    BlockRef.mc("weathered_copper_trapdoor"),
    BlockRef.mc("oxidized_copper_trapdoor"),
    BlockRef.mc("waxed_copper_trapdoor"),
    BlockRef.mc("waxed_exposed_copper_trapdoor"),
    BlockRef.mc("waxed_weathered_copper_trapdoor"),
    BlockRef.mc("waxed_oxidized_copper_trapdoor"),
    BlockRef.mc("oak_fence_gate"),
    BlockRef.mc("spruce_fence_gate"),
    BlockRef.mc("birch_fence_gate"),
    BlockRef.mc("jungle_fence_gate"),
    BlockRef.mc("acacia_fence_gate"),
    BlockRef.mc("cherry_fence_gate"),
    BlockRef.mc("dark_oak_fence_gate"),
    BlockRef.mc("mangrove_fence_gate"),
    BlockRef.mc("bamboo_fence_gate"),
    BlockRef.mc("crimson_fence_gate"),
    BlockRef.mc("warped_fence_gate"),
    BlockRef.mc("powered_rail"),
    BlockRef.mc("detector_rail"),
    BlockRef.mc("rail"),
    BlockRef.mc("activator_rail"),
    BlockRef.mc("structure_block"),
    BlockRef.mc("jigsaw"),
    BlockRef.mc("oak_sign"),
    BlockRef.mc("spruce_sign"),
    BlockRef.mc("birch_sign"),
    BlockRef.mc("acacia_sign"),
    BlockRef.mc("cherry_sign"),
    BlockRef.mc("jungle_sign"),
    BlockRef.mc("dark_oak_sign"),
    BlockRef.mc("mangrove_sign"),
    BlockRef.mc("bamboo_sign"),
    BlockRef.mc("crimson_sign"),
    BlockRef.mc("warped_sign"),
    BlockRef.mc("oak_hanging_sign"),
    BlockRef.mc("spruce_hanging_sign"),
    BlockRef.mc("birch_hanging_sign"),
    BlockRef.mc("acacia_hanging_sign"),
    BlockRef.mc("cherry_hanging_sign"),
    BlockRef.mc("jungle_hanging_sign"),
    BlockRef.mc("dark_oak_hanging_sign"),
    BlockRef.mc("mangrove_hanging_sign"),
    BlockRef.mc("bamboo_hanging_sign"),
    BlockRef.mc("crimson_hanging_sign"),
    BlockRef.mc("warped_hanging_sign"),
    BlockRef.mc("dried_kelp_block"),
    BlockRef.mc("cake"),
    BlockRef.mc("white_bed"),
    BlockRef.mc("orange_bed"),
    BlockRef.mc("magenta_bed"),
    BlockRef.mc("light_blue_bed"),
    BlockRef.mc("yellow_bed"),
    BlockRef.mc("lime_bed"),
    BlockRef.mc("pink_bed"),
    BlockRef.mc("gray_bed"),
    BlockRef.mc("light_gray_bed"),
    BlockRef.mc("cyan_bed"),
    BlockRef.mc("purple_bed"),
    BlockRef.mc("blue_bed"),
    BlockRef.mc("brown_bed"),
    BlockRef.mc("green_bed"),
    BlockRef.mc("red_bed"),
    BlockRef.mc("black_bed"),
    BlockRef.mc("crafter"),
    BlockRef.mc("brewing_stand"),
    BlockRef.mc("cauldron"),
    BlockRef.mc("flower_pot"),
    BlockRef.mc("skeleton_skull"),
    BlockRef.mc("wither_skeleton_skull"),
//    BlockRef.mc("player_head"),
    BlockRef.mc("zombie_head"),
    BlockRef.mc("creeper_head"),
    BlockRef.mc("dragon_head"),
    BlockRef.mc("piglin_head"),

    // TODO: only stack on matching metadata
//    BlockRef.mc("white_banner"),
//    BlockRef.mc("orange_banner"),
//    BlockRef.mc("magenta_banner"),
//    BlockRef.mc("light_blue_banner"),
//    BlockRef.mc("yellow_banner"),
//    BlockRef.mc("lime_banner"),
//    BlockRef.mc("pink_banner"),
//    BlockRef.mc("gray_banner"),
//    BlockRef.mc("light_gray_banner"),
//    BlockRef.mc("cyan_banner"),
//    BlockRef.mc("purple_banner"),
//    BlockRef.mc("blue_banner"),
//    BlockRef.mc("brown_banner"),
//    BlockRef.mc("green_banner"),
//    BlockRef.mc("red_banner"),
//    BlockRef.mc("black_banner"),

    BlockRef.mc("loom"),
    BlockRef.mc("composter"),
    BlockRef.mc("barrel"),
    BlockRef.mc("smoker"),
    BlockRef.mc("blast_furnace"),
    BlockRef.mc("cartography_table"),
    BlockRef.mc("fletching_table"),
    BlockRef.mc("grindstone"),
    BlockRef.mc("smithing_table"),
    BlockRef.mc("stonecutter"),
    BlockRef.mc("bell"),
    BlockRef.mc("lantern"),
    BlockRef.mc("soul_lantern"),
    BlockRef.mc("campfire"),
    BlockRef.mc("soul_campfire"),
    BlockRef.mc("shroomlight"),
    BlockRef.mc("bee_nest"),
    BlockRef.mc("beehive"),
    BlockRef.mc("honeycomb_block"),
    BlockRef.mc("lodestone"),
    BlockRef.mc("crying_obsidian"),
    BlockRef.mc("blackstone"),
    BlockRef.mc("blackstone_slab"),
    BlockRef.mc("blackstone_stairs"),
    BlockRef.mc("gilded_blackstone"),
    BlockRef.mc("polished_blackstone"),
    BlockRef.mc("polished_blackstone_slab"),
    BlockRef.mc("polished_blackstone_stairs"),
    BlockRef.mc("chiseled_polished_blackstone"),
    BlockRef.mc("polished_blackstone_bricks"),
    BlockRef.mc("polished_blackstone_brick_slab"),
    BlockRef.mc("polished_blackstone_brick_stairs"),
    BlockRef.mc("cracked_polished_blackstone_bricks"),
    BlockRef.mc("respawn_anchor"),
    BlockRef.mc("candle"),
    BlockRef.mc("white_candle"),
    BlockRef.mc("orange_candle"),
    BlockRef.mc("magenta_candle"),
    BlockRef.mc("light_blue_candle"),
    BlockRef.mc("yellow_candle"),
    BlockRef.mc("lime_candle"),
    BlockRef.mc("pink_candle"),
    BlockRef.mc("gray_candle"),
    BlockRef.mc("light_gray_candle"),
    BlockRef.mc("cyan_candle"),
    BlockRef.mc("purple_candle"),
    BlockRef.mc("blue_candle"),
    BlockRef.mc("brown_candle"),
    BlockRef.mc("green_candle"),
    BlockRef.mc("red_candle"),
    BlockRef.mc("black_candle"),
    BlockRef.mc("small_amethyst_bud"),
    BlockRef.mc("medium_amethyst_bud"),
    BlockRef.mc("large_amethyst_bud"),
    BlockRef.mc("amethyst_cluster"),
    BlockRef.mc("pointed_dripstone"),
    BlockRef.mc("ochre_froglight"),
    BlockRef.mc("verdant_froglight"),
    BlockRef.mc("pearlescent_froglight"),
    BlockRef.mc("frogspawn"),
    BlockRef.mc("copper_grate"),
    BlockRef.mc("exposed_copper_grate"),
    BlockRef.mc("weathered_copper_grate"),
    BlockRef.mc("oxidized_copper_grate"),
    BlockRef.mc("waxed_copper_grate"),
    BlockRef.mc("waxed_exposed_copper_grate"),
    BlockRef.mc("waxed_weathered_copper_grate"),
    BlockRef.mc("waxed_oxidized_copper_grate"),
    BlockRef.mc("trial_spawner"),
  )

  private fun mapOf(vararg ref: BlockRef): Map<String, BlockRef> =
    HashMap<String, BlockRef>(ref.size).apply { ref.forEach { put(it.key, it) } }
}