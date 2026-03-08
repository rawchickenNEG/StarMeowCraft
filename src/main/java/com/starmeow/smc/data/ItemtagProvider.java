package com.starmeow.smc.data;

import com.starmeow.smc.StarMeowCraft;
import com.starmeow.smc.init.ItemRegistry;
import com.starmeow.smc.tags.FDTags;
import com.starmeow.smc.tags.ForgeTags;
import com.starmeow.smc.tags.MCTags;
import com.starmeow.smc.tags.SMCTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ItemtagProvider extends ItemTagsProvider {
    public ItemtagProvider(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_,
                           CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, StarMeowCraft.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ItemTags.MUSIC_DISCS)
                .add(ItemRegistry.MUSIC_DISC_LAODA.get())
                .add(ItemRegistry.MUSIC_DISC_NYAN_CAT.get());
        this.tag(ForgeTags.CHICKEN).add(Items.COOKED_CHICKEN);
        this.tag(ForgeTags.BEEF).add(Items.COOKED_BEEF);
        this.tag(ForgeTags.MUTTON).add(Items.COOKED_MUTTON);
        this.tag(ForgeTags.PORK).add(Items.COOKED_PORKCHOP);
        this.tag(ForgeTags.MILK).add(Items.MILK_BUCKET);
        this.tag(SMCTags.CHICKEN_COMPAT)
                .add(Items.COOKED_CHICKEN)
                .addTag(ForgeTags.CHICKEN);
        this.tag(SMCTags.BEEF_COMPAT)
                .add(Items.COOKED_BEEF)
                .addTag(ForgeTags.BEEF);
        this.tag(SMCTags.MILK_COMPAT)
                .add(Items.MILK_BUCKET)
                .addTag(ForgeTags.MILK);
        this.tag(SMCTags.MUTTON_COMPAT)
                .add(Items.COOKED_MUTTON)
                .addTag(ForgeTags.MUTTON);
        this.tag(SMCTags.PORK_COMPAT)
                .add(Items.COOKED_PORKCHOP)
                .addTag(ForgeTags.PORK);
        this.tag(SMCTags.MEAT_COMPAT)
                .addTag(SMCTags.BEEF_COMPAT)
                .addTag(SMCTags.CHICKEN_COMPAT)
                .addTag(SMCTags.PORK_COMPAT)
                .addTag(SMCTags.MUTTON_COMPAT);
        this.tag(SMCTags.VACUUM_SNIFFER_DROP)
                .add(Items.TORCHFLOWER_SEEDS)
                .add(Items.PITCHER_POD);
        this.tag(ForgeTags.SEEDS)
                .add(ItemRegistry.BROCCOLI_SEED.get())
                .add(ItemRegistry.PEA.get());
        this.tag(ForgeTags.CROPS)
                .add(ItemRegistry.BROCCOLI.get())
                .add(ItemRegistry.PEA.get())
                .add(ItemRegistry.PEA_POD.get());
        this.tag(ForgeTags.VEGETABLES)
                .add(ItemRegistry.BROCCOLI.get())
                .add(ItemRegistry.PEA.get());
        this.tag(ForgeTags.INGOTS)
                .add(ItemRegistry.PERKIN_INGOT.get())
                .add(ItemRegistry.FROSTIUM_INGOT.get());
        this.tag(ForgeTags.NUGGETS)
                .add(ItemRegistry.PERKIN_NUGGET.get())
                .add(ItemRegistry.FROSTIUM_NUGGET.get());
        this.tag(ForgeTags.STORAGE_BLOCKS)
                .add(ItemRegistry.PERKIN_BLOCK.get())
                .add(ItemRegistry.FROSTIUM_BLOCK.get());
        this.tag(ForgeTags.RAW_MATERIALS)
                .add(ItemRegistry.RAW_FROSTIUM.get())
                .add(ItemRegistry.RAW_PERKIN.get());
        this.tag(ForgeTags.ORES)
                .add(ItemRegistry.PERKIN_ORE.get())
                .add(ItemRegistry.FROSTIUM_ORE.get())
                .add(ItemRegistry.DEEPSLATE_PERKIN_ORE.get())
                .add(ItemRegistry.BLUE_ICE_FROSTIUM_ORE.get());
        this.tag(MCTags.TRIMMABLE_ARMOR)
                .add(ItemRegistry.FROSTIUM_HELMET.get())
                .add(ItemRegistry.BROCCOLI_BOOTS.get())
                .add(ItemRegistry.FROSTIUM_BOOTS.get());
        this.tag(MCTags.ARROWS)
                .add(ItemRegistry.FROST_ARROW.get());
        this.tag(FDTags.FD_KNIFES)
                .add(ItemRegistry.SWISS_ARMY_KNIFE.get())
                .add(ItemRegistry.DIAMOND_SWISS_ARMY_KNIFE.get())
                .add(ItemRegistry.NETHERITE_SWISS_ARMY_KNIFE.get());
        this.tag(ForgeTags.FG_KNIFES)
                .add(ItemRegistry.SWISS_ARMY_KNIFE.get())
                .add(ItemRegistry.DIAMOND_SWISS_ARMY_KNIFE.get())
                .add(ItemRegistry.NETHERITE_SWISS_ARMY_KNIFE.get());
    }
}