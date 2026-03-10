package com.starmeow.smc.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GraniteAnvilBlock extends AnvilBlock {

    public static final DirectionProperty FACING;
    private static final VoxelShape BASE;
    private static final VoxelShape X_LEG1;
    private static final VoxelShape X_TOP;
    private static final VoxelShape Z_LEG1;
    private static final VoxelShape Z_TOP;
    private static final VoxelShape X_AXIS_AABB;
    private static final VoxelShape Z_AXIS_AABB;

    public GraniteAnvilBlock(Properties p_48777_) {
        super(p_48777_);
    }

    public VoxelShape getShape(BlockState p_48816_, BlockGetter p_48817_, BlockPos p_48818_, CollisionContext p_48819_) {
        Direction $$4 = (Direction)p_48816_.getValue(FACING);
        return $$4.getAxis() == Direction.Axis.X ? X_AXIS_AABB : Z_AXIS_AABB;
    }

    @Override
    public void onLand(Level p_48793_, BlockPos p_48794_, BlockState p_48795_, BlockState p_48796_, FallingBlockEntity p_48797_) {
        if (!p_48797_.isSilent()) {
            p_48793_.playSound((Player)null, p_48794_.getX(), p_48794_.getY(), p_48794_.getZ(), SoundEvents.DRIPSTONE_BLOCK_HIT, SoundSource.BLOCKS, 1.0F, 1.0F);
        }

    }

    @Override
    public void onBrokenAfterFall(Level p_152053_, BlockPos p_152054_, FallingBlockEntity p_152055_) {
        if (!p_152055_.isSilent()) {
            p_152053_.levelEvent(2001, p_152054_, Block.getId(this.defaultBlockState()));
            p_152053_.playSound((Player)null, p_152054_.getX(), p_152054_.getY(), p_152054_.getZ(), SoundEvents.DRIPSTONE_BLOCK_BREAK, SoundSource.BLOCKS, 1.0F, 0.6F);
        }

    }

    static {
        FACING = HorizontalDirectionalBlock.FACING;
        BASE = Block.box(2.0, 0.0, 2.0, 14.0, 4.0, 14.0);
        Z_LEG1 = Block.box(5.0, 4.0, 4.0, 11.0, 9.0, 12.0);
        X_TOP = Block.box(0.0, 9.0, 2.0, 16.0, 16.0, 14.0);
        X_LEG1 = Block.box(4.0, 4.0, 5.0, 12.0, 9.0, 11.0);
        Z_TOP = Block.box(2.0, 9.0, 0.0, 14.0, 16.0, 16.0);
        X_AXIS_AABB = Shapes.or(BASE, X_LEG1, X_TOP);
        Z_AXIS_AABB = Shapes.or(BASE, Z_LEG1, Z_TOP);
    }
}
