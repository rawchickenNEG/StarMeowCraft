package com.starmeow.smc.tags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ForgeTags {
    public static final TagKey<Item> CROPS = forgeItemTag("crops");
    public static final TagKey<Item> SEEDS = forgeItemTag("seeds");
    public static final TagKey<Item> VEGETABLES = forgeItemTag("vegetables");
    public static final TagKey<Item> CHICKEN = forgeItemTag("cooked_chicken");
    public static final TagKey<Item> BEEF = forgeItemTag("cooked_beef");
    public static final TagKey<Item> MILK = forgeItemTag("milk");
    public static final TagKey<Item> MUTTON = forgeItemTag("cooked_mutton");
    public static final TagKey<Item> PORK = forgeItemTag("cooked_pork");
    public static final TagKey<Item> ORES = forgeItemTag("ores");
    public static final TagKey<Item> INGOTS = forgeItemTag("ingots");
    public static final TagKey<Item> NUGGETS = forgeItemTag("nuggets");
    public static final TagKey<Item> STORAGE_BLOCKS = forgeItemTag("storage_blocks");
    public static final TagKey<Item> RAW_MATERIALS = forgeItemTag("raw_materials");
    public static final TagKey<Item> FG_KNIFES = forgeItemTag("tools/knives");

    private static TagKey<Item> forgeItemTag(String path) {
        return ItemTags.create(new ResourceLocation("forge", path));
    }
}
