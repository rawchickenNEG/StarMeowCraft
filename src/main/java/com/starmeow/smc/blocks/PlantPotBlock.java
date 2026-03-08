package com.starmeow.smc.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class PlantPotBlock extends Block {
    protected static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 8.0, 12.0);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public PlantPotBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    public VoxelShape getShape(BlockState p_53556_, BlockGetter p_53557_, BlockPos p_53558_, CollisionContext p_53559_) {
        return SHAPE;
    }

    public BlockState updateShape(BlockState p_152036_, Direction p_152037_, BlockState p_152038_, LevelAccessor p_152039_, BlockPos p_152040_, BlockPos p_152041_) {
        return p_152037_ == p_152036_.getValue(FACING).getOpposite() && !p_152036_.canSurvive(p_152039_, p_152040_) ? Blocks.AIR.defaultBlockState() : super.updateShape(p_152036_, p_152037_, p_152038_, p_152039_, p_152040_, p_152041_);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext p_152019_) {
        return this.defaultBlockState().setValue(FACING, p_152019_.getClickedFace());
    }

    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).isSolid();
    }

    public BlockState rotate(BlockState p_152033_, Rotation p_152034_) {
        return p_152033_.setValue(FACING, p_152034_.rotate(p_152033_.getValue(FACING)));
    }

    public BlockState mirror(BlockState p_152030_, Mirror p_152031_) {
        return p_152030_.rotate(p_152031_.getRotation(p_152030_.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_152043_) {
        p_152043_.add(FACING);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        return Collections.singletonList(new ItemStack(Items.FLOWER_POT, 1));
    }
}
