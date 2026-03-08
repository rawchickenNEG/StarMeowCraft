package com.starmeow.smc.blocks;

import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SunlightBlock extends LightBlock {
    public SunlightBlock(Properties p_153662_) {
        super(p_153662_);
    }

    @Override
    public VoxelShape getShape(BlockState p_153668_, BlockGetter p_153669_, BlockPos p_153670_, CollisionContext p_153671_) {
        return p_153671_.isHoldingItem(ItemRegistry.SUNFLOWER_POT.get()) ? Shapes.block() : Shapes.empty();
    }
}
