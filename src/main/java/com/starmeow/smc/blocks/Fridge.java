package com.starmeow.smc.blocks;

import com.starmeow.smc.blockentities.FridgeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class Fridge extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty IS_UPPER = BooleanProperty.create("upper");
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    public Fridge(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(IS_UPPER, false).setValue(OPEN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_152043_) {
        p_152043_.add(FACING, IS_UPPER, OPEN);
    }

    public InteractionResult use(BlockState p_49069_, Level p_49070_, BlockPos p_49071_, Player p_49072_, InteractionHand p_49073_, BlockHitResult p_49074_) {
        if (p_49070_.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity $$6 = p_49070_.getBlockEntity(p_49071_);
            if ($$6 instanceof FridgeBlockEntity) {
                p_49072_.openMenu((FridgeBlockEntity)$$6);
                p_49072_.awardStat(Stats.OPEN_BARREL);
            }

            return InteractionResult.CONSUME;
        }
    }

    public boolean isRandomlyTicking(BlockState p_52288_) {
        return !p_52288_.getValue(IS_UPPER);
    }

    public void randomTick(BlockState p_221050_, ServerLevel p_221051_, BlockPos p_221052_, RandomSource p_221053_) {
        if (p_221051_.isAreaLoaded(p_221052_, 1)) {
            if(p_221053_.nextInt(25) == 0){
                BlockEntity $$6 = p_221051_.getBlockEntity(p_221052_);
                if ($$6 instanceof FridgeBlockEntity blockEntity && !p_221050_.getValue(OPEN)) {
                    ResourceLocation res = new ResourceLocation("smc:chests/fridge");
                    blockEntity.unpackLootTable(null);
                    blockEntity.setLootTable(res, 0);
                    blockEntity.freezeWater();
                }
            }
        }
    }

    public void onRemove(BlockState p_49076_, Level p_49077_, BlockPos p_49078_, BlockState p_49079_, boolean p_49080_) {
        if (!p_49076_.is(p_49079_.getBlock())) {
            BlockEntity $$5 = p_49077_.getBlockEntity(p_49078_);
            if ($$5 instanceof Container) {
                Containers.dropContents(p_49077_, p_49078_, (Container)$$5);
                p_49077_.updateNeighbourForOutputSignal(p_49078_, this);
            }
            super.onRemove(p_49076_, p_49077_, p_49078_, p_49079_, p_49080_);
        }
    }

    public void tick(BlockState p_220758_, ServerLevel p_220759_, BlockPos p_220760_, RandomSource p_220761_) {
        BlockEntity $$4 = p_220759_.getBlockEntity(p_220760_);
        if ($$4 instanceof FridgeBlockEntity) {
            ((FridgeBlockEntity)$$4).recheckOpen();
        }

    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos p_152102_, BlockState p_152103_) {
        return new FridgeBlockEntity(p_152102_, p_152103_);
    }


    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext p_152019_) {
        BlockPos pos = p_152019_.getClickedPos();
        Level level = p_152019_.getLevel();
        if (pos.getY() >= level.getMaxBuildHeight() - 1 || !level.getBlockState(pos.above()).canBeReplaced(p_152019_)){
            return null;
        } else {
            Direction dir = p_152019_.getHorizontalDirection().getOpposite();
            return this.defaultBlockState()
                    .setValue(FACING, dir)
                    .setValue(IS_UPPER, false)
                    .setValue(OPEN, false);
        }
    }

    public BlockState rotate(BlockState p_152033_, Rotation p_152034_) {
        return p_152033_.setValue(FACING, p_152034_.rotate(p_152033_.getValue(FACING)));
    }

    public BlockState mirror(BlockState p_152030_, Mirror p_152031_) {
        return p_152030_.rotate(p_152031_.getRotation(p_152030_.getValue(FACING)));
    }

    @Override
    public void setPlacedBy(Level p_49499_, BlockPos p_49500_, BlockState p_49501_, @Nullable LivingEntity p_49502_, ItemStack p_49503_) {
        super.setPlacedBy(p_49499_, p_49500_, p_49501_, p_49502_, p_49503_);
        if (!p_49499_.isClientSide) {
            BlockPos upper = p_49500_.above();
            p_49499_.setBlock(upper, (BlockState)p_49501_.setValue(IS_UPPER, true), 3);
            p_49499_.blockUpdated(p_49500_, Blocks.AIR);
            p_49501_.updateNeighbourShapes(p_49499_, p_49500_, 3);
        }
        if (p_49503_.hasCustomHoverName()) {
            BlockEntity $$5 = p_49499_.getBlockEntity(p_49500_);
            if ($$5 instanceof FridgeBlockEntity) {
                ((FridgeBlockEntity)$$5).setCustomName(p_49503_.getHoverName());
            }
        }
    }

    public boolean hasAnalogOutputSignal(BlockState p_49058_) {
        return true;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction dir, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(IS_UPPER) && dir == Direction.DOWN) {
            if (!neighborState.is(this) || neighborState.getValue(IS_UPPER)) {
                return Blocks.AIR.defaultBlockState();
            }
        }
        if (!state.getValue(IS_UPPER) && dir == Direction.UP){
            if (!neighborState.is(this) || !neighborState.getValue(IS_UPPER)) {
                return Blocks.AIR.defaultBlockState();
            }
        }
        return super.updateShape(state, dir, neighborState, level, pos, neighborPos);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide) {
            boolean upper = state.getValue(IS_UPPER);
            BlockPos otherPos = upper ? pos.below() : pos.above();
            BlockState other = level.getBlockState(otherPos);

            if (other.is(this) && other.getValue(IS_UPPER) != upper) {
                level.destroyBlock(otherPos, false, player);
            }
        }
        super.playerWillDestroy(level, pos, state, player);
    }

    public RenderShape getRenderShape(BlockState p_49090_) {
        return RenderShape.MODEL;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        return Collections.singletonList(new ItemStack(this, 1));
    }
}
