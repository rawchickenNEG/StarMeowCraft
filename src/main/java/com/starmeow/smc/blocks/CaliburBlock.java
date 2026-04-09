package com.starmeow.smc.blocks;

import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class CaliburBlock extends Block {
    protected static final VoxelShape SHAPE_UPPER_Z = Block.box(7.0, 0.0, 5.0, 9.0, 16.0, 11.0);
    protected static final VoxelShape SHAPE_Z = Shapes.joinUnoptimized(
            Block.box(7.0, 0.0, 5.0, 9.0, 16.0, 11.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
            BooleanOp.OR);
    protected static final VoxelShape SHAPE_UPPER = Block.box(5.0, 0.0, 7.0, 11.0, 16.0, 9.0);
    protected static final VoxelShape SHAPE = Shapes.joinUnoptimized(
            Block.box(5.0, 0.0, 7.0, 11.0, 16.0, 9.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
            BooleanOp.OR);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty IS_UPPER = BooleanProperty.create("upper");

    public CaliburBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(IS_UPPER, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_152043_) {
        p_152043_.add(FACING, IS_UPPER);
    }

    public VoxelShape getShape(BlockState p_53556_, BlockGetter p_53557_, BlockPos p_53558_, CollisionContext p_53559_) {
        if(p_53556_.getValue(FACING) == Direction.WEST || p_53556_.getValue(FACING) == Direction.EAST){
            return p_53556_.getValue(IS_UPPER) ? SHAPE_UPPER_Z : SHAPE_Z;
        }
        return p_53556_.getValue(IS_UPPER) ? SHAPE_UPPER : SHAPE;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) return InteractionResult.SUCCESS;
        if (player.getEffect(MobEffects.DAMAGE_BOOST) != null && player.getEffect(MobEffects.DAMAGE_BOOST).getAmplifier() >= 4) {
            ItemStack spawnedItem = new ItemStack(ItemRegistry.CALIBUR.get());
            if (!player.getInventory().add(spawnedItem)) {
                player.drop(spawnedItem, false);
            }
            LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(level);
            if (bolt != null) {
                bolt.setVisualOnly(true);
                bolt.moveTo(pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5);
                level.addFreshEntity(bolt);
            }
            level.destroyBlock(pos, false);
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.WAX_OFF, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 15, 0.5D, 2D, 0.5D, 0.1D);
            }
            level.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.TOTEM_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
        } else {
            player.displayClientMessage(Component.translatable("message.smc.need_strength").withStyle(ChatFormatting.RED), true);
            level.playSound((Player)null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ANVIL_PLACE, SoundSource.BLOCKS, 1.0F, 2.0F);
        }

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
                    .setValue(IS_UPPER, false);
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
