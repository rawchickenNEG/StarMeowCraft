package com.starmeow.smc.blocks;

import com.starmeow.smc.init.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ThinCloudBlocks extends CommonTransparentBlocks {
    public ThinCloudBlocks(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public boolean skipRendering(BlockState p_53972_, BlockState p_53973_, Direction p_53974_) {
        return p_53973_.is(BlockRegistry.THIN_CLOUD.get()) || p_53973_.is(BlockRegistry.THIN_CIRRUS_CLOUD.get())|| super.skipRendering(p_53972_, p_53973_, p_53974_);
    }

    public void fallOn(Level p_153362_, BlockState p_153363_, BlockPos p_153364_, Entity p_153365_, float p_153366_) {
        p_153365_.causeFallDamage(p_153366_, 0.1F, p_153362_.damageSources().fall());
    }
}
