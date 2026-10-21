package com.starmeow.smc.tier;

import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.ForgeTier;

public class ItemTier {
    public static final Tier FROSTIUM = new ForgeTier(3, 1260, 8.0F, 4.0F, 14,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(ItemRegistry.FROSTIUM_INGOT.get()));
    public static final Tier PERKIN = new ForgeTier(2, 350, 12.0F, 3.0F, 22,
            BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(ItemRegistry.PERKIN_INGOT.get()));
    public static final Tier PERFROSTITE = new ForgeTier(4, 1862, 12.0F, 6.0F, 22,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(ItemRegistry.PERFROSTITE_INGOT.get()));
    public static final Tier BROCCOLI = new ForgeTier(1, 150, 6.0F, 2.0F, 10,
            BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemRegistry.BROCCOLI.get()));
    public static final Tier LOLLIPOP = new ForgeTier(1, 50, 4.0F, 4.0F, 10,
            BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(Items.SUGAR));
    public static final Tier CARROT = new ForgeTier(2, 250, 6.0F, 2.0F, 14,
            BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.CARROT));
    public static final Tier GOLDEN_CARROT = new ForgeTier(2, 350, 12.0F, 3.0F, 22,
            BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.GOLDEN_CARROT));
    public static final Tier DEVOURER = new ForgeTier(4, 8888, 12.0F, 4.0F, 22,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(Items.NETHERITE_INGOT));
    public static final Tier CALIBUR = new ForgeTier(4, 7777, 10.0F, 14.0F, 20,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.EMPTY);
    public static final Tier MEOWMERE = new ForgeTier(4, 8888, 12.0F, 15.0F, 22,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.EMPTY);
    public static final Tier ZENISH = new ForgeTier(4, 9999, 12.0F, 15.0F, 30,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.EMPTY);
    public static final Tier GOD = new ForgeTier(4, 1260, 8.0F, 4.0F, 22,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.EMPTY);
    public static final Tier SALT_FISH = new ForgeTier(4, 1540, 8.0F, 6.0F, 10,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(ItemRegistry.SALT_FISH.get()));
    public static final Tier ZINC = new ForgeTier(2, 350, 8.0F, 3.0F, 10,
            BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(ItemRegistry.ZINC_INGOT.get()));
    public static final Tier BEEF = new ForgeTier(1, 50, 4.0F, 1.0F, 10,
            BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(Items.BEEF));
    public static final Tier COOKED_BEEF = new ForgeTier(1, 50, 6.0F, 2.0F, 10,
            BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(Items.COOKED_BEEF));
    public static final Tier END_ROD = new ForgeTier(1, 250, 6.0F, 1.0F, 25,
            BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(Items.END_ROD));
    public static final Tier SAVAGE = new ForgeTier(4, 1540, 8.0F, 5.0F, 25,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(Items.GOLD_INGOT));
    public static final Tier GRAND = new ForgeTier(3, 1260, 8.0F, 4.0F, 25,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(Items.EMERALD));
    public static final Tier SUGARCANE = new ForgeTier(1, 50, 4.0F, 2.0F, 1,
            BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(Items.SUGAR_CANE));
}