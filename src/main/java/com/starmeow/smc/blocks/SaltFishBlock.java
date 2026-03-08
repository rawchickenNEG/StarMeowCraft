package com.starmeow.smc.blocks;

import com.starmeow.smc.blockentities.SaltFishBlockEntity;
import com.starmeow.smc.init.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.Nullable;

public class SaltFishBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public SaltFishBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153094_, BlockState p_153095_, BlockEntityType<T> p_153096_) {
        return createTickerHelper(p_153096_, BlockEntityRegistry.SALT_FISH.get(), (p_153094_.isClientSide ? SaltFishBlockEntity::clientTick : SaltFishBlockEntity::serverTick));
    }

    @Override
    public boolean triggerEvent(BlockState p_49226_, Level p_49227_, BlockPos p_49228_, int p_49229_, int p_49230_) {
        super.triggerEvent(p_49226_, p_49227_, p_49228_, p_49229_, p_49230_);
        BlockEntity blockentity = p_49227_.getBlockEntity(p_49228_);
        return blockentity == null ? false : blockentity.triggerEvent(p_49229_, p_49230_);
    }

    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> p_152133_, BlockEntityType<E> p_152134_, BlockEntityTicker<? super E> p_152135_) {
        return p_152134_ == p_152133_ ? (BlockEntityTicker<A>)p_152135_ : null;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getHorizontalDirection();
        return this.defaultBlockState().setValue(FACING, direction);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState blockState) {
        return new SaltFishBlockEntity(pos, blockState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

}
