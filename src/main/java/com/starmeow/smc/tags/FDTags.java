package com.starmeow.smc.tags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class FDTags {
    public static final TagKey<Item> FD_KNIFES = fdItemTag("tools/knives");

    private static TagKey<Item> fdItemTag(String path) {
        return ItemTags.create(new ResourceLocation("farmersdelight", path));
    }
}
