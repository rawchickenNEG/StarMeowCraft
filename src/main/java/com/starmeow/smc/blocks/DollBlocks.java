package com.starmeow.smc.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class DollBlocks extends CommonBlocks {
    protected static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 14.0, 12.0);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty STAND = BooleanProperty.create("stand");
    private final SoundEvent actSound;

    public DollBlocks(Properties p_49795_, SoundEvent interactSound) {
        super(p_49795_);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(STAND, false));
        this.actSound = interactSound;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_152043_) {
        p_152043_.add(FACING, STAND);
    }

    public VoxelShape getShape(BlockState p_53556_, BlockGetter p_53557_, BlockPos p_53558_, CollisionContext p_53559_) {
        return SHAPE;
    }

    public BlockState updateShape(BlockState p_152036_, Direction p_152037_, BlockState p_152038_, LevelAccessor p_152039_, BlockPos p_152040_, BlockPos p_152041_) {
        return p_152037_ == p_152036_.getValue(FACING).getOpposite() && !p_152036_.canSurvive(p_152039_, p_152040_) ? Blocks.AIR.defaultBlockState() : super.updateShape(p_152036_, p_152037_, p_152038_, p_152039_, p_152040_, p_152041_);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) return InteractionResult.SUCCESS;
        if(player.isCrouching()){
            boolean stand = !state.getValue(STAND);
            BlockState newState = state.setValue(STAND, stand);
            level.setBlock(pos, newState, Block.UPDATE_ALL);
            level.playSound((Player)null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
        }else{
            level.playSound((Player)null, pos.getX(), pos.getY(), pos.getZ(), actSound, SoundSource.BLOCKS, 1.0F, 1.0F);
            if(level instanceof ServerLevel serverLevel){
                serverLevel.sendParticles(ParticleTypes.HEART, pos.getX()+0.5, pos.getY()+0.8, pos.getZ()+0.5, 2, 0.25D, 0.1D, 0.25D, 0.0D);
            }
        }
        return InteractionResult.CONSUME;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext p_152019_) {
        Direction dir = p_152019_.getHorizontalDirection().getOpposite();
        return this.defaultBlockState()
                .setValue(FACING, dir)
                .setValue(STAND, false);
    }

    public BlockState rotate(BlockState p_152033_, Rotation p_152034_) {
        return p_152033_.setValue(FACING, p_152034_.rotate(p_152033_.getValue(FACING)));
    }

    public BlockState mirror(BlockState p_152030_, Mirror p_152031_) {
        return p_152030_.rotate(p_152031_.getRotation(p_152030_.getValue(FACING)));
    }
}
