package com.starmeow.smc.data;

import com.starmeow.smc.StarMeowCraft;
import com.starmeow.smc.init.BlockRegistry;
import com.starmeow.smc.tags.SMCTags;
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
                        BlockRegistry.CLEAVER.get(),
                        BlockRegistry.ANCIENT_SMITHING_TABLE.get(),
                        BlockRegistry.GRANITE_ANVIL.get(),
                        BlockRegistry.ZINC_BLOCK.get(),
                        BlockRegistry.ZINC_ORE.get(),
                        BlockRegistry.TUFF_ZINC_ORE.get(),
                        BlockRegistry.END_ZINC_ORE.get()
                );

        this.tag(BlockTags.MINEABLE_WITH_HOE)
                .add(
                        BlockRegistry.CLOUD.get(),
                        BlockRegistry.CIRRUS_CLOUD.get(),
                        BlockRegistry.THIN_CLOUD.get(),
                        BlockRegistry.THIN_CIRRUS_CLOUD.get(),
                        BlockRegistry.CLOUD_FLOOR.get(),
                        BlockRegistry.CLOUD_PILLAR.get(),
                        BlockRegistry.CLOUD_BRICKS.get(),
                        BlockRegistry.CLOUD_WALL.get(),
                        BlockRegistry.CLOUD_FLOOR_WALL.get(),
                        BlockRegistry.CLOUD_BRICKS_WALL.get(),
                        BlockRegistry.CLOUD_STAIRS.get(),
                        BlockRegistry.CLOUD_FLOOR_STAIRS.get(),
                        BlockRegistry.CLOUD_BRICKS_STAIRS.get(),
                        BlockRegistry.CLOUD_SLAB.get(),
                        BlockRegistry.CLOUD_FLOOR_SLAB.get(),
                        BlockRegistry.CLOUD_BRICKS_SLAB.get()
                );

        this.tag(BlockTags.WALLS)
                .add(
                        BlockRegistry.CLOUD_WALL.get(),
                        BlockRegistry.CLOUD_FLOOR_WALL.get(),
                        BlockRegistry.CLOUD_BRICKS_WALL.get()
                );

        this.tag(BlockTags.STAIRS)
                .add(
                        BlockRegistry.CLOUD_STAIRS.get(),
                        BlockRegistry.CLOUD_FLOOR_STAIRS.get(),
                        BlockRegistry.CLOUD_BRICKS_STAIRS.get()
                );

        this.tag(BlockTags.SLABS)
                .add(
                        BlockRegistry.CLOUD_SLAB.get(),
                        BlockRegistry.CLOUD_FLOOR_SLAB.get(),
                        BlockRegistry.CLOUD_BRICKS_SLAB.get()
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
                        BlockRegistry.ANCIENT_SMITHING_TABLE.get(),
                        BlockRegistry.ZINC_BLOCK.get(),
                        BlockRegistry.ZINC_ORE.get(),
                        BlockRegistry.TUFF_ZINC_ORE.get(),
                        BlockRegistry.END_ZINC_ORE.get()
                );

        this.tag(BlockTags.ANVIL)
                .add(
                        BlockRegistry.GRANITE_ANVIL.get()
                );

        this.tag(SMCTags.CLOUD_BLOCK)
                .add(
                        BlockRegistry.CLOUD.get(),
                        BlockRegistry.CIRRUS_CLOUD.get(),
                        BlockRegistry.THIN_CLOUD.get(),
                        BlockRegistry.THIN_CIRRUS_CLOUD.get(),
                        BlockRegistry.CLOUD_FLOOR.get(),
                        BlockRegistry.CLOUD_PILLAR.get(),
                        BlockRegistry.CLOUD_BRICKS.get(),
                        BlockRegistry.CLOUD_WALL.get(),
                        BlockRegistry.CLOUD_FLOOR_WALL.get(),
                        BlockRegistry.CLOUD_BRICKS_WALL.get(),
                        BlockRegistry.CLOUD_STAIRS.get(),
                        BlockRegistry.CLOUD_FLOOR_STAIRS.get(),
                        BlockRegistry.CLOUD_BRICKS_STAIRS.get(),
                        BlockRegistry.CLOUD_SLAB.get(),
                        BlockRegistry.CLOUD_FLOOR_SLAB.get(),
                        BlockRegistry.CLOUD_BRICKS_SLAB.get()
                );

        this.tag(SMCTags.EASTER_BUNNY_EGG_HATCH_BOOST)
                .add(
                        BlockRegistry.CLOUD.get(),
                        BlockRegistry.CIRRUS_CLOUD.get(),
                        BlockRegistry.CLOUD_FLOOR.get(),
                        BlockRegistry.CLOUD_PILLAR.get(),
                        BlockRegistry.CLOUD_BRICKS.get()
                );
    }
}