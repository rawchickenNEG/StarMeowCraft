package com.starmeow.smc.blockentities;

import com.starmeow.smc.StarMeowCraft;
import com.starmeow.smc.blocks.PotatoServerBlock;
import com.starmeow.smc.init.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.common.world.ForgeChunkManager;

public class PotatoServerBlockEntity extends BlockEntity {
    public PotatoServerBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.POTATO_SERVER.get(), pos, state);
    }

    @Override
    public void onLoad() {
        super.onLoad();

        if (!(level instanceof ServerLevel)) {
            return;
        }

        if (getBlockState().getValue(PotatoServerBlock.ACTIVE)) {
            enableChunkLoading();
        }
    }

    public void enableChunkLoading() {
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }
        ChunkPos center = new ChunkPos(worldPosition);

        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                ForgeChunkManager.forceChunk(serverLevel, StarMeowCraft.MODID, worldPosition, center.x + x, center.z + z, true, true);
            }
        }
        setChanged();
    }

    public void disableChunkLoading() {
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }
        ChunkPos center = new ChunkPos(worldPosition);

        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                ForgeChunkManager.forceChunk(serverLevel, StarMeowCraft.MODID, worldPosition, center.x + x, center.z + z, false, true
                );
            }
        }
        setChanged();
    }
}
