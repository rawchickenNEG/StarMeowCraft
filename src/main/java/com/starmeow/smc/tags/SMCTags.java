package com.starmeow.smc.tags;

import com.starmeow.smc.StarMeowCraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class SMCTags {
    public static final TagKey<Item> CHICKEN_COMPAT = modItemTag("chicken_compat");
    public static final TagKey<Item> BEEF_COMPAT = modItemTag("beef_compat");
    public static final TagKey<Item> MUTTON_COMPAT = modItemTag("mutton_compat");
    public static final TagKey<Item> MILK_COMPAT = modItemTag("milk_compat");
    public static final TagKey<Item> PORK_COMPAT = modItemTag("pork_compat");
    public static final TagKey<Item> MEAT_COMPAT = modItemTag("meat_compat");
    public static final TagKey<Item> VACUUM_SNIFFER_DROP = modItemTag("vacuum_sniffer_drop");

    private static TagKey<Item> modItemTag(String path) {
        return ItemTags.create(new ResourceLocation(StarMeowCraft.MODID, path));
    }
}
