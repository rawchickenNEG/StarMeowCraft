package com.starmeow.smc.tags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class MCTags {
    public static final TagKey<Item> TRIMMABLE_ARMOR = mcItemTag("trimmable_armor");
    public static final TagKey<Item> ARROWS = mcItemTag("arrows");
    public static final TagKey<Item> TRIM_MATERIALS = mcItemTag("trim_materials");
    public static final TagKey<Item> TRIM_TEMPLATES = mcItemTag("trim_templates");

    private static TagKey<Item> mcItemTag(String path) {
        return ItemTags.create(new ResourceLocation("minecraft", path));
    }
}
