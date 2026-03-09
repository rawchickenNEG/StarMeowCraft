package com.starmeow.smc.init;

import com.mojang.brigadier.Command;
import com.starmeow.smc.StarMeowCraft;
import com.starmeow.smc.items.*;
import com.starmeow.smc.tier.ArmorTier;
import com.starmeow.smc.tier.ItemTier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.fixes.BlockNameFlatteningFix;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Optional;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, StarMeowCraft.MODID);

    public static final RegistryObject<Item> BROCCOLI = ITEMS.register("broccoli", () -> new Item(defaultBuilder().food((new FoodProperties.Builder()).nutrition(4).saturationMod(0.4f).build())));
    public static final RegistryObject<Item> BROCCOLI_SEED = ITEMS.register("broccoli_seeds", () -> new ItemNameBlockItem(BlockRegistry.BROCCOLI_PLANT_BLOCK.get(),defaultBuilder()));
    public static final RegistryObject<Item> CHOCOLATE = ITEMS.register("chocolate", () -> new Item(defaultBuilder().food((new FoodProperties.Builder()).nutrition(6).saturationMod(0.5f).build())));
    public static final RegistryObject<Item> GOLDEN_BROCCOLI = ITEMS.register("golden_broccoli", () -> new Item(defaultBuilder().food((new FoodProperties.Builder()).nutrition(6).saturationMod(1.2f)
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 400, 1), 1.0F)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200, 0), 1.0F)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300, 0), 1.0F)
            .effect(new MobEffectInstance(MobEffects.LUCK, 400, 0), 1.0F)
            .alwaysEat().build())));
    public static final RegistryObject<Item> ASTERA_JERKY = ITEMS.register("astera_jerky", () -> new AsteraFoods(defaultBuilder().food((new FoodProperties.Builder()).nutrition(8).saturationMod(1.0f).alwaysEat().fast().build())));
    public static final RegistryObject<Item> ASTERA_PUDDING = ITEMS.register("astera_pudding", () -> new AsteraFoods(defaultBuilder().food((new FoodProperties.Builder()).nutrition(12).saturationMod(1.0f).alwaysEat().build())));
    public static final RegistryObject<Item> ASTERA_APPLE = ITEMS.register("astera_apple", () -> new AsteraFoods(defaultBuilder().food((new FoodProperties.Builder()).nutrition(4).saturationMod(1.0f).alwaysEat().build())));
    public static final RegistryObject<Item> FROST_BERRIES = ITEMS.register("frost_berries", () -> new FrostBerries(defaultBuilder().food((new FoodProperties.Builder()).nutrition(1).saturationMod(1.0f).alwaysEat().build())));
    public static final RegistryObject<Item> SPICY_STRIPS = ITEMS.register("spicy_strips", () -> new Item(defaultBuilder().food((new FoodProperties.Builder()).nutrition(2).saturationMod(0.5f).fast()
            .effect(new MobEffectInstance(MobEffects.DIG_SPEED, 200, 0), 0.2F)
            .build())));
    public static final RegistryObject<Item> SPICY_BAR = ITEMS.register("spicy_bar", () -> new Item(defaultBuilder().food((new FoodProperties.Builder()).nutrition(4).saturationMod(0.6f).fast()
            .effect(new MobEffectInstance(MobEffects.DIG_SPEED, 200, 0), 0.2F)
            .build())));
    public static final RegistryObject<Item> JERKY = ITEMS.register("jerky", () -> new Item(defaultBuilder().food((new FoodProperties.Builder()).nutrition(8).saturationMod(1.1f).fast().build())));
    public static final RegistryObject<Item> PEA = ITEMS.register("pea", () -> new ItemNameBlockItem(BlockRegistry.PEA_PLANT_BLOCK.get(),defaultBuilder().food((new FoodProperties.Builder()).nutrition(1).saturationMod(0.5f).build())));
    public static final RegistryObject<Item> PEA_POD = ITEMS.register("pea_pod", () -> new Item(defaultBuilder()));
    public static final RegistryObject<Item> LIGHT_MEAL = ITEMS.register("light_meal", () -> new BowlFoodItem(defaultBuilder().food((new FoodProperties.Builder()).nutrition(14).saturationMod(1.1f).build())));
    public static final RegistryObject<Item> CREAM_BROCCOLI = ITEMS.register("cream_broccoli", () -> new Item(defaultBuilder().food((new FoodProperties.Builder()).nutrition(7).saturationMod(0.9f).build())));
    public static final RegistryObject<Item> BOILED_BROCCOLI = ITEMS.register("boiled_broccoli", () -> new BowlFoodItem(defaultBuilder().food((new FoodProperties.Builder()).nutrition(12).saturationMod(1.1f).build())));
    public static final RegistryObject<Item> PEA_SOUP = ITEMS.register("pea_soup", () -> new BowlFoodItem(defaultBuilder().food((new FoodProperties.Builder()).nutrition(10).saturationMod(1.1f).build())));
    public static final RegistryObject<Item> BRAISED_BEEF_WITH_PEAS = ITEMS.register("braised_beef_with_peas", () -> new BowlFoodItem(defaultBuilder().food((new FoodProperties.Builder()).nutrition(14).saturationMod(1.1f).build())));
    public static final RegistryObject<Item> STEAMED_PEA_FLOUR_CAKE = ITEMS.register("steamed_pea_flour_cake", () -> new Item(defaultBuilder().food((new FoodProperties.Builder()).nutrition(6).saturationMod(1.2f).build())));
    public static final RegistryObject<Item> CHICKEN_CHOP = ITEMS.register("chicken_chop", () -> new ChickenChop(defaultBuilder().food((new FoodProperties.Builder()).nutrition(20).saturationMod(1.0f)
            .effect(new MobEffectInstance(PotionEffectRegistry.CHOP_PROTECTION.get(), 1200, 0), 1.0F)
            .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 1), 1.0F)
            .effect(new MobEffectInstance(MobEffects.ABSORPTION, 1200, 2), 1.0F)
            .alwaysEat().build())));
    public static final RegistryObject<Item> COOKED_DIRT = ITEMS.register("cooked_dirt", () -> new Item(defaultBuilder().food((new FoodProperties.Builder()).nutrition(4).saturationMod(0).build())));
    public static final RegistryObject<Item> GRASS_BLOCK_PIE = ITEMS.register("grass_block_pie", () -> new Item(defaultBuilder().food((new FoodProperties.Builder()).nutrition(8).saturationMod(0.5f).build())));
    public static final RegistryObject<Item> HIGH_CALCIUM_MILK = ITEMS.register("high_calcium_milk", () -> new HighCalciumMilk(defaultBuilder()));
    public static final RegistryObject<Item> DELUXE_CAKE_SLICE = ITEMS.register("deluxe_cake_slice", () -> new Item(defaultBuilder().food((new FoodProperties.Builder()).nutrition(2).saturationMod(0.1f).build())));
    public static final RegistryObject<Item> BOWL_OF_WATER = ITEMS.register("bowl_of_water", () -> new WaterBowlItem(defaultBuilder().stacksTo(1).food((new FoodProperties.Builder()).nutrition(0).saturationMod(0).alwaysEat().build())));
    public static final RegistryObject<Item> BOWL_OF_HOT_WATER = ITEMS.register("bowl_of_hot_water", () -> new WaterBowlItem(defaultBuilder().stacksTo(1).food((new FoodProperties.Builder()).nutrition(0).saturationMod(0).alwaysEat().build())));

    public static final RegistryObject<Item> BROCCOLI_CRATE = ITEMS.register("broccoli_crate", () -> new ItemNameBlockItem(BlockRegistry.BROCCOLI_CRATE_BLOCK.get(), defaultBuilder()));

    public static final RegistryObject<Item> FROSTIUM_BLOCK = ITEMS.register("frostium_block", () -> new ItemNameBlockItem(BlockRegistry.FROSTIUM_BLOCK.get(), defaultBuilder()));
    public static final RegistryObject<Item> FROSTIUM_ORE = ITEMS.register("frostium_ore", () -> new TippedBlockItems(BlockRegistry.FROSTIUM_ORE.get(), defaultBuilder()));
    public static final RegistryObject<Item> BLUE_ICE_FROSTIUM_ORE = ITEMS.register("blue_ice_frostium_ore", () -> new TippedBlockItems(BlockRegistry.BLUE_ICE_FROSTIUM_ORE.get(), defaultBuilder()));
    public static final RegistryObject<Item> PERKIN_BLOCK = ITEMS.register("perkin_block", () -> new ItemNameBlockItem(BlockRegistry.PERKIN_BLOCK.get(), defaultBuilder()));
    public static final RegistryObject<Item> PERKIN_ORE = ITEMS.register("perkin_ore", () -> new TippedBlockItems(BlockRegistry.PERKIN_ORE.get(), defaultBuilder()));
    public static final RegistryObject<Item> DEEPSLATE_PERKIN_ORE = ITEMS.register("deepslate_perkin_ore", () -> new TippedBlockItems(BlockRegistry.DEEPSLATE_PERKIN_ORE.get(), defaultBuilder()));
    public static final RegistryObject<Item> FISH = ITEMS.register("salt_fish", () -> new Item(defaultBuilder()));
    public static final RegistryObject<Item> FISH_NUGGET = ITEMS.register("salt_fish_nugget", () -> new Item(defaultBuilder()));
    public static final RegistryObject<Item> SALT_FISH_BLOCK = ITEMS.register("salt_fish_block", () -> new ItemNameBlockItem(BlockRegistry.SALT_FISH_BLOCK.get(), defaultBuilder()));
    public static final RegistryObject<Item> FROSTIUM_INGOT = ITEMS.register("frostium_ingot", () -> new Item(defaultBuilder()));
    public static final RegistryObject<Item> FROST_EYE = ITEMS.register("frost_eye", () -> new Item(defaultBuilder().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> FROSTIUM_NUGGET = ITEMS.register("frostium_nugget", () -> new Item(defaultBuilder()));
    public static final RegistryObject<Item> RAW_FROSTIUM = ITEMS.register("raw_frostium", () -> new Item(defaultBuilder()));
    public static final RegistryObject<Item> FROSTIUM_SWORD = ITEMS.register("frostium_sword", () -> new FrostiumSword(ItemTier.FROSTIUM, 0, -1.8F, defaultBuilder()));
    public static final RegistryObject<Item> FROSTIUM_AXE = ITEMS.register("frostium_axe", () -> new FrostiumAxe(ItemTier.FROSTIUM, 3.0F, -2.6F, defaultBuilder()));
    public static final RegistryObject<Item> FROSTIUM_PICKAXE = ITEMS.register("frostium_pickaxe", () -> new FrostiumPickaxe(ItemTier.FROSTIUM, 0, -2.4F, defaultBuilder()));
    public static final RegistryObject<Item> FROSTIUM_HELMET = ITEMS.register("frostium_helmet", () -> new FrostiumArmorItems(ArmorTier.FROSTIUM, ArmorItem.Type.HELMET, defaultBuilder()));
    public static final RegistryObject<Item> FROSTIUM_BOOTS = ITEMS.register("frostium_boots", () -> new FrostiumArmorItems(ArmorTier.FROSTIUM, ArmorItem.Type.BOOTS, defaultBuilder()));
    public static final RegistryObject<Item> FROSTIUM_BOW = ITEMS.register("frostium_bow", () -> new FrostiumBow(defaultBuilder().durability(1260)));
    public static final RegistryObject<Item> FROST_ARROW = ITEMS.register("frost_arrow", () -> new FrostArrowItem(defaultBuilder()));


    public static final RegistryObject<Item> GRIMOIRE = ITEMS.register("perkin_wand", () -> new Grimoire(defaultBuilder().durability(350)));
    public static final RegistryObject<Item> ICE_TEA = ITEMS.register("ice_tea", () -> new IceTea(defaultBuilder().food((new FoodProperties.Builder()).nutrition(8).saturationMod(0.75f)
            .effect(new MobEffectInstance(MobEffects.SLOW_FALLING, 1200, 1), 1.0F)
            .effect(new MobEffectInstance(PotionEffectRegistry.ELBOWING.get(), 1000, 2), 1.0F)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 0), 1.0F)
            .effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 2400, 0), 1.0F)
            .alwaysEat().build())));
    public static final RegistryObject<Item> REDSTONE_ICE_CREAM = ITEMS.register("redstone_ice_cream", () -> new Item(defaultBuilder().food((new FoodProperties.Builder()).nutrition(10).saturationMod(0.75f)
            .effect(new MobEffectInstance(MobEffects.DIG_SPEED, 3600, 1), 1.0F)
            .effect(new MobEffectInstance(PotionEffectRegistry.EXTENSION.get(), 3600, 1), 1.0F)
            .alwaysEat().build())));
    public static final RegistryObject<Item> KATANA = ITEMS.register("katana", () -> new Katana(Tiers.DIAMOND, 3, -2.4F, defaultBuilder().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> CAT_PAW = ITEMS.register("cat_paw", () -> new CatPaw(Tiers.DIAMOND, 3, -2.4F, defaultBuilder()));
    public static final RegistryObject<Item> DELUXE_CAKE = ITEMS.register("deluxe_cake", () -> new ItemNameBlockItem(BlockRegistry.DELUXE_CAKE_BLOCK.get(), defaultBuilder()));
    public static final RegistryObject<Item> WATER_DISPENSER = ITEMS.register("water_dispenser", () -> new ItemNameBlockItem(BlockRegistry.WATER_DISPENSER.get(), defaultBuilder()));

    public static final RegistryObject<Item> BROCCOLI_LOLLIPOP = ITEMS.register("lollipop_broccoli", () -> new LollipopItem(ItemTier.LOLLIPOP, 3, -3F, defaultBuilder()));
    public static final RegistryObject<Item> FROST_LOLLIPOP = ITEMS.register("lollipop_frost", () -> new LollipopItem(ItemTier.LOLLIPOP, 3, -3F, defaultBuilder()));
    public static final RegistryObject<Item> PERKIN_LOLLIPOP = ITEMS.register("lollipop_perkin", () -> new LollipopItem(ItemTier.LOLLIPOP, 3, -3F, defaultBuilder()));
    public static final RegistryObject<Item> COLORFUL_ICE_CREAM = ITEMS.register("colorful_ice_cream", () -> new LollipopItem(ItemTier.LOLLIPOP, 6, -3F, defaultBuilder().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> CANDY_JAR = ITEMS.register("candy_jar", () -> new CandyJar(defaultBuilder().durability(512).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BROCCOLI_CANDY = ITEMS.register("broccoli_candy", () -> new Item(defaultBuilder().food((new FoodProperties.Builder()).nutrition(1).saturationMod(0.25f)
            .effect(new MobEffectInstance(PotionEffectRegistry.POISON_RESISTANCE.get(), 100), 0.65F)
            .alwaysEat().fast().build())));
    public static final RegistryObject<Item> FROST_CANDY = ITEMS.register("frost_candy", () -> new Item(defaultBuilder().food((new FoodProperties.Builder()).nutrition(1).saturationMod(0.25f)
            .effect(new MobEffectInstance(PotionEffectRegistry.FROST_RESISTANCE.get(), 100), 0.65F)
            .alwaysEat().fast().build())));
    public static final RegistryObject<Item> STAR_CANDY = ITEMS.register("star_candy", () -> new Item(defaultBuilder().food((new FoodProperties.Builder()).nutrition(1).saturationMod(0.25f)
            .effect(new MobEffectInstance(PotionEffectRegistry.STAR_LIGHT.get(), 100), 0.65F)
            .alwaysEat().fast().build())));
    public static final RegistryObject<Item> ZONGZI = ITEMS.register("zongzi", () -> new Item(defaultBuilder().food((new FoodProperties.Builder()).nutrition(6).saturationMod(0.5f)
            .effect(new MobEffectInstance(PotionEffectRegistry.PEACE.get(), 600), 1)
            .alwaysEat().build())));
    public static final RegistryObject<Item> CHOP_KEBAB = ITEMS.register("chop_kebab", () -> new LollipopItem(ItemTier.LOLLIPOP, 15, -3.1F, defaultBuilder()));
    public static final RegistryObject<Item> STAR_DUST = ITEMS.register("star_dust", () -> new Item(defaultBuilder()));
    public static final RegistryObject<Item> PERKIN_INGOT = ITEMS.register("perkin_ingot", () -> new Item(defaultBuilder()));
    public static final RegistryObject<Item> PERKIN_NUGGET = ITEMS.register("perkin_nugget", () -> new Item(defaultBuilder()));
    public static final RegistryObject<Item> PERKIN_STAR = ITEMS.register("perkin_star", () -> new Item(defaultBuilder().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> RAW_PERKIN = ITEMS.register("raw_perkin", () -> new Item(defaultBuilder()));
    public static final RegistryObject<Item> BROCCOLI_HOE = ITEMS.register("broccoli_hoe", () -> new BroccoliHoe(ItemTier.BROCCOLI, -2, -1.0F, defaultBuilder()));
    public static final RegistryObject<Item> ARCHAEOLOGICAL_SHOVEL = ITEMS.register("archaeological_shovel", () -> new BroccoliShovel(ItemTier.BROCCOLI, 0, -3.0F, defaultBuilder()));
    public static final RegistryObject<Item> BROCCOLI_BOOTS = ITEMS.register("broccoli_boots", () -> new BroccoliArmorItems(ArmorTier.BROCCOLI, ArmorItem.Type.BOOTS, defaultBuilder()));
    public static final RegistryObject<Item> BROCCOLI_FISHING_ROD = ITEMS.register("broccoli_fishing_rod", () -> new BroccoliFishingRod(defaultBuilder().durability(400)));
    public static final RegistryObject<Item> PERKIN_SPEAR = ITEMS.register("perkin_spear", () -> new PerkinSpear(defaultBuilder().durability(350)));
    public static final RegistryObject<Item> PERKIN_AXE = ITEMS.register("perkin_axe", () -> new AxeItem(ItemTier.PERKIN, 5, -3.0F, defaultBuilder()));
    public static final RegistryObject<Item> PERKIN_PICKAXE = ITEMS.register("perkin_pickaxe", () -> new PickaxeItem(ItemTier.PERKIN, -1, -2.8F, defaultBuilder()));
    public static final RegistryObject<Item> PERKIN_SHOVEL = ITEMS.register("perkin_shovel", () -> new ShovelItem(ItemTier.PERKIN, -0.5F, -3.0F, defaultBuilder()));
    public static final RegistryObject<Item> PERKIN_HOE = ITEMS.register("perkin_hoe", () -> new HoeItem(ItemTier.PERKIN, -3, 0, defaultBuilder()));
    public static final RegistryObject<Item> PERFROSTITE_INGOT = ITEMS.register("perfrostite_ingot", () -> new Item(defaultBuilder()));
    public static final RegistryObject<Item> PERFROSTITE_SWORD = ITEMS.register("perfrostite_sword", () -> new PerfrostiteSword(ItemTier.PERFROSTITE, 2, -2.4F, defaultBuilder()));
    public static final RegistryObject<Item> PERFROSTITE_AXE = ITEMS.register("perfrostite_axe", () -> new PerfrostiteAxe(ItemTier.PERFROSTITE, 5, -3.0F, defaultBuilder()));
    public static final RegistryObject<Item> PERFROSTITE_PICKAXE = ITEMS.register("perfrostite_pickaxe", () -> new PerfrostitePickaxe(ItemTier.PERFROSTITE, -1, -2.8F, defaultBuilder()));
    public static final RegistryObject<Item> PERFROSTITE_SHOVEL = ITEMS.register("perfrostite_shovel", () -> new PerfrostiteShovel(ItemTier.PERFROSTITE, -0.5F, -3.0F, defaultBuilder()));
    public static final RegistryObject<Item> PERFROSTITE_HOE = ITEMS.register("perfrostite_hoe", () -> new PerfrostiteHoe(ItemTier.PERFROSTITE, -6, 0, defaultBuilder()));
    public static final RegistryObject<Item> PERFROSTITE_BOW = ITEMS.register("perfrostite_bow", () -> new PerfrostiteBow(defaultBuilder().durability(1862)));
    public static final RegistryObject<Item> PERFROSTITE_UPGRADE_SCROLL = ITEMS.register("perfrostite_upgrade_scroll", () -> new SmithingTemplateItem(
            Component.translatable("item.smc.perfrostite_upgrade_scroll.applies_to").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.smc.perfrostite_upgrade_scroll.ingredients").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.smc.perfrostite_upgrade_scroll.title").withStyle(ChatFormatting.GRAY),
            Component.translatable("item.smc.perfrostite_upgrade_scroll.base_slot_description"),
            Component.translatable("item.smc.perfrostite_upgrade_scroll.additions_slot_description"),
            createPerfrostiteUpgradeIconList(),
            createePerfrostiteUpgradeMaterialList()
    ));
    public static final RegistryObject<Item> DEVOUR_SWORD = ITEMS.register("devour_sword", () -> new DevourSword(ItemTier.DEVOURER, 0, -2.4F, defaultBuilder().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> TEMPLATE_SHROUD = ITEMS.register("template_shroud", () -> new TemplateShroud(ArmorTier.TEMPLATE, ArmorItem.Type.CHESTPLATE, defaultBuilder().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> SPEAR_GUN = ITEMS.register("spear_gun", () -> new SpearGun(defaultBuilder().durability(200)));
    public static final RegistryObject<Item> COMMAND_BLOCK_WAND = ITEMS.register("command_block_wand", () -> new CommandBlockWand(defaultBuilder().durability(200)));

    public static final RegistryObject<Item> RAINBOW_FISHING_ROD = ITEMS.register("rainbow_fishing_rod", () -> new RainbowFishingRod(defaultBuilder().durability(1200)));
    public static final RegistryObject<Item> RAINBOW_BOW = ITEMS.register("rainbow_bow", () -> new RainbowBow(defaultBuilder().durability(1200)));

    public static final RegistryObject<Item> PEA_SHOOTER_POT = ITEMS.register("pea_shooter_pot", () -> new PeaShooterPot(defaultBuilder().durability(400)));
    public static final RegistryObject<Item> SUNFLOWER_POT = ITEMS.register("sunflower_pot", () -> new SunflowerPot(defaultBuilder().durability(400)));
    public static final RegistryObject<Item> WALLNUT_POT = ITEMS.register("wallnut_pot", () -> new WallnutPotShield(defaultBuilder().durability(1200)));
    public static final RegistryObject<Item> SNIFFER_BEAK = ITEMS.register("sniffer_beak", () -> new Item(defaultBuilder()));
    public static final RegistryObject<Item> RAINBOW_CHIP = ITEMS.register("rainbow_chip", () -> new TippedItems(defaultBuilder().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> RAINBOW_COOKIE= ITEMS.register("rainbow_cookie", () -> new Item(defaultBuilder().rarity(Rarity.RARE).food((new FoodProperties.Builder()).nutrition(2).saturationMod(0.75f)
            .effect(new MobEffectInstance(PotionEffectRegistry.RAINBOW.get(), 600, 2), 1.0F)
            .alwaysEat().build())));

    public static final RegistryObject<Item> SPORE_BUD = ITEMS.register("spore_bud", () -> new SporeBud(defaultBuilder()));
    public static final RegistryObject<Item> LUCKY_CLOVER = ITEMS.register("lucky_clover", () -> new LuckyClover(defaultBuilder().rarity(Rarity.UNCOMMON), false));
    public static final RegistryObject<Item> CREATIVE_CLOVER = ITEMS.register("creative_clover", () -> new LuckyClover(defaultBuilder().rarity(Rarity.EPIC), true));
    public static final RegistryObject<Item> VACUUM_SNIFFER = ITEMS.register("vacuum_sniffer", () -> new VacuumSniffer(defaultBuilder().durability(200)));
    public static final RegistryObject<Item> FIRE_EXTINGUISHER = ITEMS.register("fire_extinguisher", () -> new FireExtinguisher(defaultBuilder().durability(200)));
    public static final RegistryObject<Item> SLINGSHOT = ITEMS.register("slingshot", () -> new Slingshot(defaultBuilder().durability(64)));
    public static final RegistryObject<Item> ORE_DETECTOR = ITEMS.register("ore_detector", () -> new OreDetector(defaultBuilder().stacksTo(1)));
    public static final RegistryObject<Item> WATERING_CAN = ITEMS.register("watering_can", () -> new WateringCan(defaultBuilder().stacksTo(1), 4, 500, 22));
    public static final RegistryObject<Item> COPPER_WATERING_CAN = ITEMS.register("copper_watering_can", () -> new WateringCan(defaultBuilder().stacksTo(1), 1, 200, 5));
    public static final RegistryObject<Item> IRON_WATERING_CAN = ITEMS.register("iron_watering_can", () -> new WateringCan(defaultBuilder().stacksTo(1), 2, 300, 10));
    public static final RegistryObject<Item> CHOP_SHIELD = ITEMS.register("chop_shield", () -> new ChopShield(defaultBuilder().durability(800)));
    public static final RegistryObject<Item> SWISS_ARMY_KNIFE = ITEMS.register("swiss_army_knife", () -> new SwissArmyKnife(Tiers.IRON, 3, -2.4F, defaultBuilder()));
    public static final RegistryObject<Item> DIAMOND_SWISS_ARMY_KNIFE = ITEMS.register("diamond_swiss_army_knife", () -> new SwissArmyKnife(Tiers.DIAMOND, 3, -2.4F, defaultBuilder()));
    public static final RegistryObject<Item> NETHERITE_SWISS_ARMY_KNIFE = ITEMS.register("netherite_swiss_army_knife", () -> new SwissArmyKnife(Tiers.NETHERITE, 3, -2.4F, defaultBuilder().fireResistant()));
    public static final RegistryObject<Item> KNIFE = ITEMS.register("knife", () -> new KnifeItem(Tiers.IRON, 3, -2.4F, defaultBuilder().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> LUCKY_NUGGET = ITEMS.register("lucky_nugget", () -> new LuckyNugget(defaultBuilder().food((new FoodProperties.Builder()).nutrition(8).saturationMod(1.1f).fast().build())));
    public static final RegistryObject<Item> MINI_BEDROCK = ITEMS.register("mini_bedrock", () -> new MiniBedrock(defaultBuilder().stacksTo(1).rarity(Rarity.EPIC).fireResistant()));
    public static final RegistryObject<Item> COFFEE = ITEMS.register("coffee", () -> new CoffeeItem(defaultBuilder().food((new FoodProperties.Builder()).nutrition(2).saturationMod(0.5f).build())));
    public static final RegistryObject<Item> BOT = ITEMS.register("bot", () -> new BotItem(defaultBuilder().stacksTo(1).rarity(Rarity.RARE).fireResistant()));
    public static final RegistryObject<Item> CARROT_PICKAXE = ITEMS.register("carrot_pickaxe", () -> new CarrotPickaxe(ItemTier.CARROT, -1, -2.8F, defaultBuilder(), false));
    public static final RegistryObject<Item> GOLDEN_CARROT_PICKAXE = ITEMS.register("golden_carrot_pickaxe", () -> new CarrotPickaxe(ItemTier.GOLDEN_CARROT, -1, -2.8F, defaultBuilder(), true));
    public static final RegistryObject<Item> CHOCOLATE_SWORD = ITEMS.register("chocolate_sword", () -> new ChocolateSword(Tiers.WOOD, 3, -2.4F, defaultBuilder()));
    public static final RegistryObject<Item> CHOCOLATE_HELMET = ITEMS.register("chocolate_helmet", () -> new ChocolateArmorItems(ArmorTier.CHOCOLATE, ArmorItem.Type.HELMET, defaultBuilder()));
    public static final RegistryObject<Item> CHOCOLATE_CHESTPLATE = ITEMS.register("chocolate_chestplate", () -> new ChocolateArmorItems(ArmorTier.CHOCOLATE, ArmorItem.Type.CHESTPLATE, defaultBuilder()));
    public static final RegistryObject<Item> CHOCOLATE_LEGGINGS = ITEMS.register("chocolate_leggings", () -> new ChocolateArmorItems(ArmorTier.CHOCOLATE, ArmorItem.Type.LEGGINGS, defaultBuilder()));
    public static final RegistryObject<Item> CHOCOLATE_BOOTS = ITEMS.register("chocolate_boots", () -> new ChocolateArmorItems(ArmorTier.CHOCOLATE, ArmorItem.Type.BOOTS, defaultBuilder()));
    public static final RegistryObject<Item> GOOGOO_STEW = ITEMS.register("googoo_stew", () -> new TippedItems(defaultBuilder().food((new FoodProperties.Builder()).nutrition(8).saturationMod(1.0f)
            .effect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 1800, 1), 1.0F)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1800, 2), 1.0F)
            .alwaysEat().build())));
    public static final RegistryObject<Item> GUAGUA_JELLY = ITEMS.register("guagua_jelly", () -> new TippedItems(defaultBuilder().food((new FoodProperties.Builder()).nutrition(8).saturationMod(1.0f)
            .effect(new MobEffectInstance(MobEffects.JUMP, 1800, 1), 1.0F)
            .effect(new MobEffectInstance(PotionEffectRegistry.POISON_RESISTANCE.get(), 3600, 0), 1.0F)
            .alwaysEat().build())));
    public static final RegistryObject<Item> END_ROD_CANDY = ITEMS.register("end_rod_candy", () -> new TippedItems(defaultBuilder().food((new FoodProperties.Builder()).nutrition(2).saturationMod(0.5f)
            .effect(new MobEffectInstance(MobEffects.GLOWING, 36000, 0), 1.0F)
            .alwaysEat().build())));
    public static final RegistryObject<Item> GOLDEN_BOAT = ITEMS.register("golden_boat", () -> new BoatItem(false, Boat.Type.byName("golden"), (new Item.Properties()).stacksTo(1)));
    public static final RegistryObject<Item> GOLDEN_CHEST_BOAT = ITEMS.register("golden_chest_boat", () -> new BoatItem(true, Boat.Type.byName("golden"), (new Item.Properties()).stacksTo(1).fireResistant()));
    public static final RegistryObject<Item> GOLDEN_TRANSPARENT_BOAT = ITEMS.register("golden_transparent_boat", () -> new BoatItem(false, Boat.Type.byName("glass_golden"), (new Item.Properties()).stacksTo(1).fireResistant()));
    public static final RegistryObject<Item> END_BOAT = ITEMS.register("end_boat", () -> new BoatItem(false, Boat.Type.byName("end"), (new Item.Properties()).stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> END_CHEST_BOAT = ITEMS.register("end_chest_boat", () -> new BoatItem(true, Boat.Type.byName("end"), (new Item.Properties()).stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SMC_ICON = ITEMS.register("smc_icon", () -> new Item(defaultBuilder()));
    public static final RegistryObject<Item> MCR_SWORD = ITEMS.register("mcr_sword", () -> new MCrSword(Tiers.DIAMOND, 99999, -2.4F, defaultBuilder().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> MUSIC_DISC_LAODA = ITEMS.register("music_disc_laoda",
            () -> new RecordItem(6, SoundRegistry.MUSIC_DISC_LAODA, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 2420));
    public static final RegistryObject<Item> MUSIC_DISC_NYAN_CAT= ITEMS.register("music_disc_nyan_cat",
            () -> new RecordItem(6, SoundRegistry.MUSIC_DISC_NYAN_CAT, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 4520));
    public static final RegistryObject<Item> DOLL_1 = ITEMS.register("doll_bf_meow", () -> new DollItems(BlockRegistry.DOLL_1.get(), defaultBuilder().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> DOLL_2 = ITEMS.register("doll_huacai", () -> new DollItems(BlockRegistry.DOLL_2.get(), defaultBuilder().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> DOLL_3 = ITEMS.register("doll_raw_chicken", () -> new DollItems(BlockRegistry.DOLL_3.get(), defaultBuilder().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> DOLL_4 = ITEMS.register("doll_starjump", () -> new DollItems(BlockRegistry.DOLL_4.get(), defaultBuilder().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> EASTER_BUNNY_SPAWN_EGG = ITEMS.register("easter_bunny_spawn_egg", () -> new ForgeSpawnEggItem(EntityTypeRegistry.EASTER_BUNNY, -1522757, -1546072, defaultBuilder()));
    public static final RegistryObject<Item> FROST_PIE_SLICE = ITEMS.register("frost_pie_slice", () -> new Item(defaultBuilder().food((new FoodProperties.Builder()).nutrition(8).saturationMod(1.2f)
            .effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 18000, 0), 1.0F)
            .alwaysEat().build())));

    public static final Optional<RegistryObject<Item>> FROST_PIE;

    static {
        if (ModList.get().isLoaded("farmersdelight")) {
            FROST_PIE = Optional.of(
                    ITEMS.register("frost_pie", () -> new ItemNameBlockItem(BlockRegistry.FROST_PIE_BLOCK.get(), defaultBuilder()))
            );
        } else {
            FROST_PIE = Optional.of(ITEMS.register("frost_pie", () -> new Item(defaultBuilder().food((new FoodProperties.Builder()).nutrition(8).saturationMod(1.2f)
                    .effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, -1, 0), 1.0F)
                    .alwaysEat().build()))));
        }
    }

    private static Item.Properties defaultBuilder() {
        return new Item.Properties();
    }

    private static List<ResourceLocation> createPerfrostiteUpgradeIconList() {
        return List.of(
                //new ResourceLocation("item/empty_armor_slot_helmet"),
                //new ResourceLocation("item/empty_armor_slot_chestplate"),
                //new ResourceLocation("item/empty_armor_slot_leggings"),
                //new ResourceLocation("item/empty_armor_slot_boots"),
                new ResourceLocation("item/empty_slot_sword"),
                new ResourceLocation("item/empty_slot_hoe"),
                new ResourceLocation("item/empty_slot_axe"),
                new ResourceLocation("item/empty_slot_pickaxe"),
                new ResourceLocation("item/empty_slot_shovel")
        );
    }

    private static List<ResourceLocation> createePerfrostiteUpgradeMaterialList() {
        return List.of(new ResourceLocation("item/empty_slot_ingot"));
    }
}
