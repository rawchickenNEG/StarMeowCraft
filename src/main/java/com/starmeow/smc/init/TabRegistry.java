package com.starmeow.smc.init;

import com.starmeow.smc.StarMeowCraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class TabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, StarMeowCraft.MODID);

    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("smc_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.smc_tab"))
            .icon(ItemRegistry.SMC_ICON.get()::getDefaultInstance)
            .displayItems((parameters, output) -> {
                //resources
                output.accept(ItemRegistry.FROSTIUM_ORE.get());
                output.accept(ItemRegistry.BLUE_ICE_FROSTIUM_ORE.get());
                output.accept(ItemRegistry.FROSTIUM_BLOCK.get());
                output.accept(ItemRegistry.RAW_FROSTIUM.get());
                output.accept(ItemRegistry.FROSTIUM_INGOT.get());
                output.accept(ItemRegistry.FROSTIUM_NUGGET.get());
                output.accept(ItemRegistry.FROST_EYE.get());

                output.accept(ItemRegistry.PERKIN_ORE.get());
                output.accept(ItemRegistry.DEEPSLATE_PERKIN_ORE.get());
                output.accept(ItemRegistry.PERKIN_BLOCK.get());
                output.accept(ItemRegistry.RAW_PERKIN.get());
                output.accept(ItemRegistry.PERKIN_INGOT.get());
                output.accept(ItemRegistry.PERKIN_NUGGET.get());
                output.accept(ItemRegistry.STAR_DUST.get());
                output.accept(ItemRegistry.PERKIN_STAR.get());

                output.accept(ItemRegistry.PERFROSTITE_INGOT.get());
                output.accept(ItemRegistry.PERFROSTITE_UPGRADE_SCROLL.get());

                output.accept(ItemRegistry.ZINC_ORE.get());
                output.accept(ItemRegistry.TUFF_ZINC_ORE.get());
                output.accept(ItemRegistry.END_ZINC_ORE.get());
                output.accept(ItemRegistry.ZINC_BLOCK.get());
                output.accept(ItemRegistry.RAW_ZINC.get());
                output.accept(ItemRegistry.ZINC_INGOT.get());
                output.accept(ItemRegistry.ZINC_NUGGET.get());

                output.accept(ItemRegistry.SNIFFER_BEAK.get());
                output.accept(ItemRegistry.BROCCOLI_SEED.get());
                output.accept(ItemRegistry.RAINBOW_CHIP.get());
                output.accept(ItemRegistry.BROCCOLI.get());
                output.accept(ItemRegistry.BROCCOLI_CRATE.get());
                output.accept(ItemRegistry.CHICKEN_CHOP.get());
                output.accept(ItemRegistry.SALT_FISH.get());
                output.accept(ItemRegistry.SALT_FISH_BUCKET.get());
                output.accept(ItemRegistry.SALT_FISH_NUGGET.get());
                output.accept(ItemRegistry.SALT_FISH_BLOCK.get());
                output.accept(ItemRegistry.FOX_FUR.get());
                output.accept(ItemRegistry.SNOW_FOX_FUR.get());
                output.accept(ItemRegistry.PURE_FOX_FUR.get());
                output.accept(ItemRegistry.IRON_ARMOR_TRIM_SMITHING_TEMPLATE.get());
                output.accept(ItemRegistry.CHAINMAIL_ARMOR_TRIM_SMITHING_TEMPLATE.get());
                output.accept(ItemRegistry.NETHERITE_ARMOR_TRIM_SMITHING_TEMPLATE.get());
                output.accept(ItemRegistry.CHOP_ARMOR_TRIM_SMITHING_TEMPLATE.get());

                //blocks
                output.accept(ItemRegistry.GRANITE_ANVIL.get());
                output.accept(ItemRegistry.ANCIENT_SMITHING_TABLE.get());
                output.accept(ItemRegistry.CALIBUR_BLOCK.get());
                output.accept(ItemRegistry.WATER_DISPENSER.get());
                output.accept(ItemRegistry.FRIDGE.get());
                output.accept(ItemRegistry.EASTER_BUNNY_EGG.get());
                output.accept(ItemRegistry.CHICKEN_HARVESTER_EGG.get());

                //random_things
                output.accept(ItemRegistry.BROCCOLI_BOMB.get());
                output.accept(ItemRegistry.BROCCOLI_NUKE.get());
                output.accept(ItemRegistry.GOLDEN_BOAT.get());
                output.accept(ItemRegistry.GOLDEN_CHEST_BOAT.get());
                output.accept(ItemRegistry.GOLDEN_TRANSPARENT_BOAT.get());
                output.accept(ItemRegistry.END_BOAT.get());
                output.accept(ItemRegistry.END_CHEST_BOAT.get());
                output.accept(ItemRegistry.PEA_SHOOTER_POT.get());
                output.accept(ItemRegistry.SUNFLOWER_POT.get());
                output.accept(ItemRegistry.WALLNUT_POT.get());
                output.accept(ItemRegistry.FIRE_EXTINGUISHER.get());
                output.accept(ItemRegistry.VACUUM_SNIFFER.get());
                output.accept(ItemRegistry.LUCKY_CLOVER.get());
                output.accept(ItemRegistry.LUCKY_NUGGET.get());
                output.accept(ItemRegistry.BAD_APPLE.get());
                output.accept(ItemRegistry.ORE_DETECTOR.get());
                output.accept(ItemRegistry.TREASURE_DETECTOR.get());
                output.accept(ItemRegistry.GIANT_CARROT.get());
                output.accept(ItemRegistry.BOT.get());
                output.accept(ItemRegistry.CANDY_JAR.get());
                output.accept(ItemRegistry.BROCCOLI_CANDY.get());
                output.accept(ItemRegistry.FROST_CANDY.get());
                output.accept(ItemRegistry.STAR_CANDY.get());
                output.accept(ItemRegistry.COFFEE.get());
                output.accept(ItemRegistry.COPPER_WATERING_CAN.get());
                output.accept(ItemRegistry.IRON_WATERING_CAN.get());
                output.accept(ItemRegistry.WATERING_CAN.get());
                output.accept(ItemRegistry.SUPER_BONEMEAL.get());
                output.accept(ItemRegistry.SPORE_BUD.get());
                output.accept(ItemRegistry.MINI_BEDROCK.get());
                output.accept(ItemRegistry.HANGER.get());
                output.accept(ItemRegistry.GOLDEN_HANGER.get());
                output.accept(ItemRegistry.INFINITE_FUEL.get());

                output.accept(ItemRegistry.EASTER_BUNNY_SPAWN_EGG.get());
                output.accept(ItemRegistry.CLOUDIAN_SPAWN_EGG.get());
                output.accept(ItemRegistry.CHICKEN_HARVESTER_SPAWN_EGG.get());
                output.accept(ItemRegistry.SALT_FISH_SPAWN_EGG.get());
                //misc
                output.accept(ItemRegistry.MUSIC_DISC_LAODA.get());
                output.accept(ItemRegistry.MUSIC_DISC_NYAN_CAT.get());
                output.accept(ItemRegistry.MUSIC_DISC_BAD_APPLE.get());
                output.accept(ItemRegistry.MUSIC_DISC_GOD_PICKAXE.get());
                //dev
                output.accept(ItemRegistry.MCR_SWORD.get());
                output.accept(ItemRegistry.COMMAND_BLOCK_WAND.get());
            }).build());
    public static final RegistryObject<CreativeModeTab> FOOD_TAB = CREATIVE_MODE_TABS.register("smc_food_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.smc_food_tab"))
            .icon(ItemRegistry.ICON_FOOD.get()::getDefaultInstance)
            .withTabsBefore(EXAMPLE_TAB.getId())
            .displayItems((parameters, output) -> {
                output.accept(ItemRegistry.BROCCOLI.get());
                output.accept(ItemRegistry.GOLDEN_BROCCOLI.get());
                output.accept(ItemRegistry.CHOCOLATE.get());
                output.accept(ItemRegistry.ICE_TEA.get());
                output.accept(ItemRegistry.CHOCLIZ.get());
                output.accept(ItemRegistry.CHOCLIZ_BERRY.get());
                output.accept(ItemRegistry.PEA.get());
                output.accept(ItemRegistry.PEA_POD.get());

                output.accept(ItemRegistry.BROCCOLI_LOLLIPOP.get());
                output.accept(ItemRegistry.FROST_LOLLIPOP.get());
                output.accept(ItemRegistry.PERKIN_LOLLIPOP.get());
                output.accept(ItemRegistry.COLORFUL_ICE_CREAM.get());
                output.accept(ItemRegistry.CHOP_KEBAB.get());
                output.accept(ItemRegistry.RAW_TOMAHAWK_STEAK.get());
                output.accept(ItemRegistry.TOMAHAWK_STEAK.get());

                if (ModList.get().isLoaded("farmersdelight")) {
                    output.accept(ItemRegistry.ASTERA_CAKE.get());
                    output.accept(ItemRegistry.ASTERA_CAKE_SLICE.get());
                    output.accept(ItemRegistry.BROCCOLI_CAKE.get());
                    output.accept(ItemRegistry.BROCCOLI_CAKE_SLICE.get());
                    output.accept(ItemRegistry.FROST_CAKE.get());
                    output.accept(ItemRegistry.FROST_CAKE_SLICE.get());
                    output.accept(ItemRegistry.RAINBOW_CAKE.get());
                    output.accept(ItemRegistry.RAINBOW_CAKE_SLICE.get());
                } else {
                    output.accept(ItemRegistry.ASTERA_CAKE.get());
                    output.accept(ItemRegistry.BROCCOLI_CAKE.get());
                    output.accept(ItemRegistry.FROST_CAKE.get());
                    output.accept(ItemRegistry.RAINBOW_CAKE.get());
                }
                if (ModList.get().isLoaded("neapolitan")) {
                    output.accept(ItemRegistry.ASTERA_MILKSHAKE.get());
                    output.accept(ItemRegistry.BROCCOLI_MILKSHAKE.get());
                    output.accept(ItemRegistry.FROST_MILKSHAKE.get());
                    output.accept(ItemRegistry.RAINBOW_MILKSHAKE.get());
                    output.accept(ItemRegistry.ASTERA_ICE_CREAM.get());
                    output.accept(ItemRegistry.BROCCOLI_ICE_CREAM.get());
                    output.accept(ItemRegistry.FROST_ICE_CREAM.get());
                    output.accept(ItemRegistry.RAINBOW_ICE_CREAM.get());
                    output.accept(ItemRegistry.SMC_ICE_CREAM.get());
                }
                output.accept(ItemRegistry.ASTERA_JERKY.get());
                output.accept(ItemRegistry.ASTERA_APPLE.get());
                output.accept(ItemRegistry.ASTERA_PUDDING.get());
                output.accept(ItemRegistry.FROST_BERRIES.get());
                ItemRegistry.FROST_PIE.ifPresent(i -> output.accept(i.get()));
                if (ModList.get().isLoaded("farmersdelight")) {
                    output.accept(ItemRegistry.FROST_PIE_SLICE.get());
                }

                output.accept(ItemRegistry.SALT_FISH.get());
                output.accept(ItemRegistry.COOKED_SALT_FISH.get());
                output.accept(ItemRegistry.SALT_FISH_NUGGET.get());
                output.accept(ItemRegistry.FISH_COOKIE.get());
                output.accept(ItemRegistry.SALT_FISH_STEW.get());
                ItemRegistry.SALT_FISH_PIE.ifPresent(i -> output.accept(i.get()));
                if (ModList.get().isLoaded("farmersdelight")) {
                    output.accept(ItemRegistry.SALT_FISH_PIE_SLICE.get());
                    output.accept(ItemRegistry.RAW_SALT_FISH_SLICE.get());
                    output.accept(ItemRegistry.COOKED_SALT_FISH_SLICE.get());
                    output.accept(ItemRegistry.SALT_FISH_ROLL.get());
                }
                output.accept(ItemRegistry.FRIED_EASTER_BUNNY_EGG.get());
                output.accept(ItemRegistry.FRIED_CHICKEN_HARVESTER_EGG.get());
                output.accept(ItemRegistry.EASTER_BUNNY_EGG_SANDWICH.get());
                output.accept(ItemRegistry.CHICKEN_HARVESTER_EGG_SANDWICH.get());

                output.accept(ItemRegistry.SPICY_BAR.get());
                output.accept(ItemRegistry.JERKY.get());
                output.accept(ItemRegistry.SPICY_STRIPS.get());
                output.accept(ItemRegistry.REDSTONE_ICE_CREAM.get());
                output.accept(ItemRegistry.HIGH_CALCIUM_MILK.get());
                output.accept(ItemRegistry.GOOGOO_STEW.get());
                output.accept(ItemRegistry.GUAGUA_JELLY.get());
                output.accept(ItemRegistry.END_ROD_CANDY.get());
                output.accept(ItemRegistry.ZONGZI.get());
                output.accept(ItemRegistry.BOWL_OF_WATER.get());
                output.accept(ItemRegistry.BOWL_OF_HOT_WATER.get());
                output.accept(ItemRegistry.SPICY_FIRED_RICE.get());
                output.accept(ItemRegistry.SWEET_FRESH_SALAD.get());
                output.accept(ItemRegistry.ONEO.get());
                output.accept(ItemRegistry.TNET.get());
                output.accept(ItemRegistry.OREEPER.get());
                output.accept(ItemRegistry.GIANT_CARROT.get());
                output.accept(ItemRegistry.SNOW_GOLEM_ICE_CREAM.get());
                output.accept(ItemRegistry.DANDELION_SALAD.get());
                output.accept(ItemRegistry.MELON_HALF.get());
                output.accept(ItemRegistry.PUMPKIN_PILE.get());
                output.accept(ItemRegistry.GQKJ_CHAR_SIU_PORK_KNUCKLE_RICE.get());
                output.accept(ItemRegistry.BOWL_OF_GQKJ_CHAR_SIU_PORK_KNUCKLE_RICE.get());
                //meals
                output.accept(ItemRegistry.BOILED_BROCCOLI.get());
                output.accept(ItemRegistry.LIGHT_MEAL.get());
                output.accept(ItemRegistry.CREAM_BROCCOLI.get());
                output.accept(ItemRegistry.PEA_SOUP.get());
                output.accept(ItemRegistry.STEAMED_PEA_FLOUR_CAKE.get());
                output.accept(ItemRegistry.BRAISED_BEEF_WITH_PEAS.get());
                output.accept(ItemRegistry.CHICKEN_CHOP.get());
                output.accept(ItemRegistry.GRASS_BLOCK_PIE.get());
                output.accept(ItemRegistry.COOKED_DIRT.get());
                output.accept(ItemRegistry.BLOOM_CAKE.get());
                output.accept(ItemRegistry.RAINBOW_COOKIE.get());
                output.accept(ItemRegistry.DELUXE_CAKE.get());

            }).build());
    public static final RegistryObject<CreativeModeTab> BLOCK_TAB = CREATIVE_MODE_TABS.register("smc_build_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.smc_build_tab"))
            .icon(ItemRegistry.ICON_BLOCK.get()::getDefaultInstance)
            .withTabsBefore(FOOD_TAB.getId())
            .displayItems((parameters, output) -> {
                output.accept(ItemRegistry.CLOUD.get());
                output.accept(ItemRegistry.CIRRUS_CLOUD.get());
                output.accept(ItemRegistry.THIN_CLOUD.get());
                output.accept(ItemRegistry.THIN_CIRRUS_CLOUD.get());
                output.accept(ItemRegistry.CLOUD_BRICKS.get());
                output.accept(ItemRegistry.CLOUD_FLOOR.get());
                output.accept(ItemRegistry.CLOUD_PILLAR.get());
                output.accept(ItemRegistry.CLOUD_STAIRS.get());
                output.accept(ItemRegistry.CLOUD_SLAB.get());
                output.accept(ItemRegistry.CLOUD_WALL.get());
                output.accept(ItemRegistry.CLOUD_FLOOR_STAIRS.get());
                output.accept(ItemRegistry.CLOUD_FLOOR_SLAB.get());
                output.accept(ItemRegistry.CLOUD_FLOOR_WALL.get());
                output.accept(ItemRegistry.CLOUD_BRICKS_STAIRS.get());
                output.accept(ItemRegistry.CLOUD_BRICKS_SLAB.get());
                output.accept(ItemRegistry.CLOUD_BRICKS_WALL.get());
            }).build());
    public static final RegistryObject<CreativeModeTab> GEAR_TAB = CREATIVE_MODE_TABS.register("smc_gear_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.smc_gear_tab"))
            .icon(ItemRegistry.ICON_GEAR.get()::getDefaultInstance)
            .withTabsBefore(BLOCK_TAB.getId())
            .displayItems((parameters, output) -> {
                //gears
                output.accept(ItemRegistry.BROCCOLI_HOE.get());
                output.accept(ItemRegistry.BROCCOLI_BOOTS.get());
                output.accept(ItemRegistry.BROCCOLI_FISHING_ROD.get());
                output.accept(ItemRegistry.BROCCOLI_BOMB.get());
                output.accept(ItemRegistry.BROCCOLI_NUKE.get());
                output.accept(ItemRegistry.FROSTIUM_AXE.get());
                output.accept(ItemRegistry.FROSTIUM_SWORD.get());
                output.accept(ItemRegistry.FROSTIUM_PICKAXE.get());
                output.accept(ItemRegistry.FROSTIUM_HELMET.get());
                output.accept(ItemRegistry.FROSTIUM_BOOTS.get());
                output.accept(ItemRegistry.FROSTIUM_BOW.get());
                output.accept(ItemRegistry.FROST_ARROW.get());
                output.accept(ItemRegistry.PERKIN_AXE.get());
                output.accept(ItemRegistry.PERKIN_SPEAR.get());
                output.accept(ItemRegistry.PERKIN_PICKAXE.get());
                output.accept(ItemRegistry.PERKIN_SHOVEL.get());
                output.accept(ItemRegistry.PERKIN_HOE.get());
                output.accept(ItemRegistry.GRIMOIRE.get());
                output.accept(ItemRegistry.PERFROSTITE_AXE.get());
                output.accept(ItemRegistry.PERFROSTITE_SWORD.get());
                output.accept(ItemRegistry.PERFROSTITE_PICKAXE.get());
                output.accept(ItemRegistry.PERFROSTITE_SHOVEL.get());
                output.accept(ItemRegistry.PERFROSTITE_HOE.get());
                output.accept(ItemRegistry.ZINC_AXE.get());
                output.accept(ItemRegistry.ZINC_SWORD.get());
                output.accept(ItemRegistry.ZINC_PICKAXE.get());
                output.accept(ItemRegistry.ZINC_SHOVEL.get());
                output.accept(ItemRegistry.ZINC_HOE.get());
                output.accept(ItemRegistry.BATTERY.get());
                //output.accept(ItemRegistry.PERFROSTITE_BOW.get());
                output.accept(ItemRegistry.RAINBOW_FISHING_ROD.get());
                output.accept(ItemRegistry.RAINBOW_BOW.get());

                output.accept(ItemRegistry.SALT_FISH_SWORD.get());

                output.accept(ItemRegistry.SWISS_ARMY_KNIFE.get());
                output.accept(ItemRegistry.DIAMOND_SWISS_ARMY_KNIFE.get());
                output.accept(ItemRegistry.NETHERITE_SWISS_ARMY_KNIFE.get());

                output.accept(ItemRegistry.CHOCOLATE_SWORD.get());
                output.accept(ItemRegistry.CHOCOLATE_HELMET.get());
                output.accept(ItemRegistry.CHOCOLATE_CHESTPLATE.get());
                output.accept(ItemRegistry.CHOCOLATE_LEGGINGS.get());
                output.accept(ItemRegistry.CHOCOLATE_BOOTS.get());
                output.accept(ItemRegistry.BROCCOLI_LOLLIPOP.get());
                output.accept(ItemRegistry.FROST_LOLLIPOP.get());
                output.accept(ItemRegistry.PERKIN_LOLLIPOP.get());
                output.accept(ItemRegistry.COLORFUL_ICE_CREAM.get());
                output.accept(ItemRegistry.CHOP_SHIELD.get());
                output.accept(ItemRegistry.CHOP_KEBAB.get());
                output.accept(ItemRegistry.RAW_TOMAHAWK_STEAK.get());
                output.accept(ItemRegistry.TOMAHAWK_STEAK.get());
                output.accept(ItemRegistry.FOX_HELMET.get());
                output.accept(ItemRegistry.FOX_CHESTPLATE.get());
                output.accept(ItemRegistry.FOX_LEGGINGS.get());
                output.accept(ItemRegistry.FOX_BOOTS.get());
                output.accept(ItemRegistry.SNOW_FOX_HELMET.get());
                output.accept(ItemRegistry.SNOW_FOX_CHESTPLATE.get());
                output.accept(ItemRegistry.SNOW_FOX_LEGGINGS.get());
                output.accept(ItemRegistry.SNOW_FOX_BOOTS.get());
                //独立武器
                output.accept(ItemRegistry.KATANA.get());
                output.accept(ItemRegistry.KNIFE.get());
                output.accept(ItemRegistry.CLEAVER.get());
                output.accept(ItemRegistry.CAT_PAW.get());
                output.accept(ItemRegistry.SLINGSHOT.get());
                output.accept(ItemRegistry.ARCHAEOLOGICAL_SHOVEL.get());
                output.accept(ItemRegistry.CARROT_PICKAXE.get());
                output.accept(ItemRegistry.GOLDEN_CARROT_PICKAXE.get());
                output.accept(ItemRegistry.SPEAR_GUN.get());
                output.accept(ItemRegistry.DEVOUR_SWORD.get());
                output.accept(ItemRegistry.DEVOUR_KNIFE.get());
                output.accept(ItemRegistry.TEMPLATE_SHROUD.get());
                output.accept(ItemRegistry.CALIBUR.get());
                output.accept(ItemRegistry.EXCALIBUR.get());
                output.accept(ItemRegistry.HANGER.get());
                output.accept(ItemRegistry.GOLDEN_HANGER.get());
                output.accept(ItemRegistry.ROD_SWORD.get());
                output.accept(ItemRegistry.ROAD_SIGN.get());
                output.accept(ItemRegistry.MEOWMERE.get());
                output.accept(ItemRegistry.ZENISH.get());
                output.accept(ItemRegistry.CHAINSAW.get());
                //饰品
                output.accept(ItemRegistry.DIVINE_WING.get());
                output.accept(ItemRegistry.DIVINE_SHARD.get());
                output.accept(ItemRegistry.DIVINE_HALO.get());
                output.accept(ItemRegistry.FOX_TAIL.get());
                output.accept(ItemRegistry.FROSTIUM_SHACKLES.get());
                output.accept(ItemRegistry.GLITCH_PARTICLE.get());
                output.accept(ItemRegistry.RED_FRAME_GLASSES.get());
                //特殊
                output.accept(ItemRegistry.MCR_SWORD.get());
                output.accept(ItemRegistry.GOD_PICKAXE.get());
                output.accept(ItemRegistry.COMMAND_BLOCK_WAND.get());
            }).build());
    public static final RegistryObject<CreativeModeTab> COMPAT_TAB = CREATIVE_MODE_TABS.register("smc_compat_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.smc_compat_tab"))
            .icon(ItemRegistry.ICON_COMPAT.get()::getDefaultInstance)
            .withTabsBefore(GEAR_TAB.getId())
            .displayItems((parameters, output) -> {
                if (ModList.get().isLoaded("farmersdelight")) {
                    output.accept(ItemRegistry.ASTERA_CAKE.get());
                    output.accept(ItemRegistry.ASTERA_CAKE_SLICE.get());
                    output.accept(ItemRegistry.BROCCOLI_CAKE.get());
                    output.accept(ItemRegistry.BROCCOLI_CAKE_SLICE.get());
                    output.accept(ItemRegistry.FROST_CAKE.get());
                    output.accept(ItemRegistry.FROST_CAKE_SLICE.get());
                    output.accept(ItemRegistry.RAINBOW_CAKE.get());
                    output.accept(ItemRegistry.RAINBOW_CAKE_SLICE.get());
                } else {
                    output.accept(ItemRegistry.ASTERA_CAKE.get());
                    output.accept(ItemRegistry.BROCCOLI_CAKE.get());
                    output.accept(ItemRegistry.FROST_CAKE.get());
                    output.accept(ItemRegistry.RAINBOW_CAKE.get());
                }
                if (ModList.get().isLoaded("neapolitan")) {
                    output.accept(ItemRegistry.ASTERA_MILKSHAKE.get());
                    output.accept(ItemRegistry.BROCCOLI_MILKSHAKE.get());
                    output.accept(ItemRegistry.FROST_MILKSHAKE.get());
                    output.accept(ItemRegistry.RAINBOW_MILKSHAKE.get());
                    output.accept(ItemRegistry.ASTERA_ICE_CREAM.get());
                    output.accept(ItemRegistry.BROCCOLI_ICE_CREAM.get());
                    output.accept(ItemRegistry.FROST_ICE_CREAM.get());
                    output.accept(ItemRegistry.RAINBOW_ICE_CREAM.get());
                    output.accept(ItemRegistry.SMC_ICE_CREAM.get());
                }
                ItemRegistry.FROST_PIE.ifPresent(i -> output.accept(i.get()));
                if (ModList.get().isLoaded("farmersdelight")) {
                    output.accept(ItemRegistry.GOOGOO_STEW.get());
                    output.accept(ItemRegistry.GUAGUA_JELLY.get());
                    output.accept(ItemRegistry.FROST_PIE_SLICE.get());
                    output.accept(ItemRegistry.DELUXE_CAKE_SLICE.get());
                    output.accept(ItemRegistry.RAW_SALT_FISH_SLICE.get());
                    output.accept(ItemRegistry.COOKED_SALT_FISH_SLICE.get());
                    output.accept(ItemRegistry.SALT_FISH_ROLL.get());
                    output.accept(ItemRegistry.SALT_FISH_PIE_SLICE.get());
                }
                output.accept(ItemRegistry.GOD_PICKAXE.get());

            }).build());
}
