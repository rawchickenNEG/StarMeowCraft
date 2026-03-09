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
                output.accept(ItemRegistry.PERKIN_ORE.get());
                output.accept(ItemRegistry.DEEPSLATE_PERKIN_ORE.get());
                output.accept(ItemRegistry.PERKIN_BLOCK.get());
                output.accept(ItemRegistry.RAW_PERKIN.get());
                output.accept(ItemRegistry.PERKIN_INGOT.get());
                output.accept(ItemRegistry.PERKIN_NUGGET.get());
                output.accept(ItemRegistry.PERKIN_STAR.get());
                output.accept(ItemRegistry.PERFROSTITE_INGOT.get());
                output.accept(ItemRegistry.PERFROSTITE_UPGRADE_SCROLL.get());
                output.accept(ItemRegistry.SNIFFER_BEAK.get());
                //output.accept(ItemRegistry.FISH.get());
                //output.accept(ItemRegistry.FISH_NUGGET.get());
                output.accept(ItemRegistry.BROCCOLI_SEED.get());
                output.accept(ItemRegistry.RAINBOW_CHIP.get());
                output.accept(ItemRegistry.BROCCOLI.get());
                output.accept(ItemRegistry.BROCCOLI_CRATE.get());

                output.accept(ItemRegistry.EASTER_BUNNY_SPAWN_EGG.get());
                //blocks
                output.accept(ItemRegistry.GRANITE_ANVIL.get());
                //foods
                output.accept(ItemRegistry.BROCCOLI.get());
                output.accept(ItemRegistry.GOLDEN_BROCCOLI.get());
                output.accept(ItemRegistry.CHOCOLATE.get());
                output.accept(ItemRegistry.ICE_TEA.get());
                output.accept(ItemRegistry.PEA.get());
                output.accept(ItemRegistry.PEA_POD.get());
                output.accept(ItemRegistry.BROCCOLI_LOLLIPOP.get());
                output.accept(ItemRegistry.FROST_LOLLIPOP.get());
                output.accept(ItemRegistry.PERKIN_LOLLIPOP.get());
                output.accept(ItemRegistry.COLORFUL_ICE_CREAM.get());
                output.accept(ItemRegistry.ASTERA_JERKY.get());
                output.accept(ItemRegistry.ASTERA_APPLE.get());
                output.accept(ItemRegistry.ASTERA_PUDDING.get());
                output.accept(ItemRegistry.FROST_BERRIES.get());
                ItemRegistry.FROST_PIE.ifPresent(i -> output.accept(i.get()));
                if (ModList.get().isLoaded("farmersdelight")) {
                    output.accept(ItemRegistry.FROST_PIE_SLICE.get());
                }
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
                output.accept(ItemRegistry.RAINBOW_COOKIE.get());
                //gears
                output.accept(ItemRegistry.BROCCOLI_HOE.get());
                output.accept(ItemRegistry.BROCCOLI_BOOTS.get());
                output.accept(ItemRegistry.BROCCOLI_FISHING_ROD.get());
                output.accept(ItemRegistry.FROSTIUM_AXE.get());
                output.accept(ItemRegistry.FROSTIUM_SWORD.get());
                output.accept(ItemRegistry.FROSTIUM_PICKAXE.get());
                output.accept(ItemRegistry.FROSTIUM_HELMET.get());
                output.accept(ItemRegistry.FROSTIUM_BOOTS.get());
                output.accept(ItemRegistry.FROSTIUM_BOW.get());
                output.accept(ItemRegistry.FROST_EYE.get());
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
                //output.accept(ItemRegistry.PERFROSTITE_BOW.get());
                output.accept(ItemRegistry.RAINBOW_FISHING_ROD.get());
                output.accept(ItemRegistry.RAINBOW_BOW.get());

                output.accept(ItemRegistry.SWISS_ARMY_KNIFE.get());
                output.accept(ItemRegistry.DIAMOND_SWISS_ARMY_KNIFE.get());
                output.accept(ItemRegistry.NETHERITE_SWISS_ARMY_KNIFE.get());
                output.accept(ItemRegistry.DEVOUR_SWORD.get());
                output.accept(ItemRegistry.TEMPLATE_SHROUD.get());
                //random_things
                output.accept(ItemRegistry.KATANA.get());
                output.accept(ItemRegistry.KNIFE.get());
                output.accept(ItemRegistry.CAT_PAW.get());
                //output.accept(ItemRegistry.SPEAR_GUN.get());
                output.accept(ItemRegistry.COMMAND_BLOCK_WAND.get());
                output.accept(ItemRegistry.CARROT_PICKAXE.get());
                output.accept(ItemRegistry.GOLDEN_CARROT_PICKAXE.get());
                output.accept(ItemRegistry.CHOCOLATE_SWORD.get());
                output.accept(ItemRegistry.CHOCOLATE_HELMET.get());
                output.accept(ItemRegistry.CHOCOLATE_CHESTPLATE.get());
                output.accept(ItemRegistry.CHOCOLATE_LEGGINGS.get());
                output.accept(ItemRegistry.CHOCOLATE_BOOTS.get());
                output.accept(ItemRegistry.STAR_DUST.get());
                output.accept(ItemRegistry.PEA_SHOOTER_POT.get());
                output.accept(ItemRegistry.SUNFLOWER_POT.get());
                output.accept(ItemRegistry.WALLNUT_POT.get());
                output.accept(ItemRegistry.FIRE_EXTINGUISHER.get());
                output.accept(ItemRegistry.VACUUM_SNIFFER.get());
                output.accept(ItemRegistry.SLINGSHOT.get());
                output.accept(ItemRegistry.ARCHAEOLOGICAL_SHOVEL.get());
                output.accept(ItemRegistry.LUCKY_CLOVER.get());
                output.accept(ItemRegistry.LUCKY_NUGGET.get());
                output.accept(ItemRegistry.KNIFE.get());
                output.accept(ItemRegistry.ORE_DETECTOR.get());
                output.accept(ItemRegistry.BOT.get());
                output.accept(ItemRegistry.CANDY_JAR.get());
                output.accept(ItemRegistry.BROCCOLI_CANDY.get());
                output.accept(ItemRegistry.FROST_CANDY.get());
                output.accept(ItemRegistry.STAR_CANDY.get());
                output.accept(ItemRegistry.COFFEE.get());
                output.accept(ItemRegistry.COPPER_WATERING_CAN.get());
                output.accept(ItemRegistry.IRON_WATERING_CAN.get());
                output.accept(ItemRegistry.WATERING_CAN.get());
                output.accept(ItemRegistry.CHOP_SHIELD.get());
                output.accept(ItemRegistry.CHOP_KEBAB.get());
                output.accept(ItemRegistry.SPORE_BUD.get());
                output.accept(ItemRegistry.WATER_DISPENSER.get());
                output.accept(ItemRegistry.MINI_BEDROCK.get());
                output.accept(ItemRegistry.MCR_SWORD.get());
                output.accept(ItemRegistry.DELUXE_CAKE.get());
                output.accept(ItemRegistry.GOLDEN_BOAT.get());
                output.accept(ItemRegistry.GOLDEN_CHEST_BOAT.get());
                output.accept(ItemRegistry.GOLDEN_TRANSPARENT_BOAT.get());
                output.accept(ItemRegistry.END_BOAT.get());
                output.accept(ItemRegistry.END_CHEST_BOAT.get());
                if (ModList.get().isLoaded("farmersdelight")) {
                    output.accept(ItemRegistry.DELUXE_CAKE_SLICE.get());
                }
                //misc
                output.accept(ItemRegistry.MUSIC_DISC_LAODA.get());
                output.accept(ItemRegistry.MUSIC_DISC_NYAN_CAT.get());
            }).build());
}
