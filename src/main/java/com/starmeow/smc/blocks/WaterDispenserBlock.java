package com.starmeow.smc.blocks;

import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;

public class WaterDispenserBlock extends CommonBlocks {
    protected static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);
    protected static final VoxelShape SHAPE_UPPER = Shapes.joinUnoptimized(
            Block.box(2.0, 2.0, 2.0, 14.0, 16.0, 14.0),
            Block.box(5.0, 0.0, 5.0, 11.0, 2.0, 11.0),
            BooleanOp.OR);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty IS_UPPER = BooleanProperty.create("upper");
    public static final BooleanProperty IS_HOT = BooleanProperty.create("hot");

    public WaterDispenserBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(IS_UPPER, false).setValue(IS_HOT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_152043_) {
        p_152043_.add(FACING, IS_UPPER, IS_HOT);
    }

    public VoxelShape getShape(BlockState p_53556_, BlockGetter p_53557_, BlockPos p_53558_, CollisionContext p_53559_) {
        return p_53556_.getValue(IS_UPPER) ?SHAPE_UPPER : SHAPE;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) return InteractionResult.SUCCESS;
        if(!state.getValue(IS_UPPER)){
            if(player.isCrouching()){
                boolean hot = !state.getValue(IS_HOT);
                BlockState newState = state.setValue(IS_HOT, hot);
                level.setBlock(pos, newState, Block.UPDATE_ALL);
                level.playSound((Player)null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 1.0F, 1.0F);
            }else{
                if(player.getMainHandItem().is(Items.BUCKET)){
                    if (!player.getAbilities().instabuild) {
                        player.getMainHandItem().shrink(1);
                    }
                    ItemStack itemStack = new ItemStack(state.getValue(IS_HOT) ? Items.LAVA_BUCKET : Items.WATER_BUCKET);
                    ItemHandlerHelper.giveItemToPlayer(player, itemStack);
                    level.playSound((Player)null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.COW_MILK, SoundSource.BLOCKS, 1.0F, 1.0F);
                }
                if(player.getMainHandItem().is(Items.GLASS_BOTTLE)){
                    if (!player.getAbilities().instabuild) {
                        player.getMainHandItem().shrink(1);
                    }
                    ItemStack itemStack = state.getValue(IS_HOT) ? PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD) : PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER);
                    ItemHandlerHelper.giveItemToPlayer(player, itemStack);
                    level.playSound((Player)null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.COW_MILK, SoundSource.BLOCKS, 1.0F, 1.0F);
                }
                if(player.getMainHandItem().is(Items.BOWL)){
                    if (!player.getAbilities().instabuild) {
                        player.getMainHandItem().shrink(1);
                    }
                    ItemStack itemStack = new ItemStack(state.getValue(IS_HOT) ? ItemRegistry.BOWL_OF_HOT_WATER.get() : ItemRegistry.BOWL_OF_WATER.get());
                    ItemHandlerHelper.giveItemToPlayer(player, itemStack);
                    level.playSound((Player)null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.COW_MILK, SoundSource.BLOCKS, 1.0F, 1.0F);
                }
            }}
        return InteractionResult.CONSUME;
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
                    .setValue(IS_HOT, false);
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
}
