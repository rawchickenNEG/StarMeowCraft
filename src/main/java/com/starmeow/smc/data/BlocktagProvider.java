package com.starmeow.smc.data;

import com.starmeow.smc.StarMeowCraft;
import com.starmeow.smc.init.BlockRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class BlocktagProvider extends BlockTagsProvider {
    public BlocktagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, StarMeowCraft.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(
                        BlockRegistry.FROSTIUM_BLOCK.get(),
                        BlockRegistry.FROSTIUM_ORE.get(),
                        BlockRegistry.BLUE_ICE_FROSTIUM_ORE.get(),
                        BlockRegistry.PERKIN_BLOCK.get(),
                        BlockRegistry.PERKIN_ORE.get(),
                        BlockRegistry.DEEPSLATE_PERKIN_ORE.get(),
                        BlockRegistry.WATER_DISPENSER.get(),
                        BlockRegistry.FRIDGE.get(),
                        BlockRegistry.KNIFE.get(),
                        BlockRegistry.ANCIENT_SMITHING_TABLE.get(),
                        BlockRegistry.GRANITE_ANVIL.get()
                );

        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .add(
                        BlockRegistry.BROCCOLI_CRATE_BLOCK.get()
                );

        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(
                        BlockRegistry.FROSTIUM_BLOCK.get(),
                        BlockRegistry.FROSTIUM_ORE.get(),
                        BlockRegistry.BLUE_ICE_FROSTIUM_ORE.get(),
                        BlockRegistry.PERKIN_BLOCK.get(),
                        BlockRegistry.PERKIN_ORE.get(),
                        BlockRegistry.DEEPSLATE_PERKIN_ORE.get(),
                        BlockRegistry.ANCIENT_SMITHING_TABLE.get()
                );

        this.tag(BlockTags.ANVIL)
                .add(
                        BlockRegistry.GRANITE_ANVIL.get()
                );
    }
}