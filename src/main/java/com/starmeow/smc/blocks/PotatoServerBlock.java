package com.starmeow.smc.blocks;

import com.starmeow.smc.blockentities.PotatoServerBlockEntity;
import com.starmeow.smc.helper.BlockHelper;
import com.starmeow.smc.init.BlockEntityRegistry;
import com.starmeow.smc.init.EnchantmentRegistry;
import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PotatoServerBlock extends BaseEntityBlock {

    protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
    protected static final VoxelShape SHAPE_POTATO = Block.box(5.0, 11.0, 4.0, 11.0, 18.0, 12.0);
    protected static final VoxelShape SHAPE_POTATO_A = Block.box(4.0, 11.0, 5.0, 12.0, 18.0, 11.0);
    protected static final VoxelShape SHAPE_STICK = Block.box(7.0, 8.0, 7.0, 9.0, 23.0, 9.0);
    protected static final VoxelShape X_AXIS_AABB = Shapes.or(SHAPE, SHAPE_POTATO, SHAPE_STICK);
    protected static final VoxelShape Z_AXIS_AABB = Shapes.or(SHAPE, SHAPE_POTATO_A, SHAPE_STICK);

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    public static final IntegerProperty POTATO_STATE = IntegerProperty.create("potato_state", 0, 2);

    public PotatoServerBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(ACTIVE, false).setValue(POTATO_STATE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, ACTIVE, POTATO_STATE);
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos p_152102_, BlockState p_152103_) {
        return new PotatoServerBlockEntity(p_152102_, p_152103_);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) return InteractionResult.SUCCESS;
        boolean active = !state.getValue(ACTIVE);
        BlockState newState = state.setValue(ACTIVE, active);
        level.setBlock(pos, newState, Block.UPDATE_ALL);
        level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.WOODEN_BUTTON_CLICK_ON, SoundSource.BLOCKS, 1.0F, 1.0F);

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof PotatoServerBlockEntity loader) {
            if (active) {
                loader.enableChunkLoading();
            } else {
                loader.disableChunkLoading();
            }
        }
        return InteractionResult.CONSUME;
    }

    public RenderShape getRenderShape(BlockState p_49090_) {
        return RenderShape.MODEL;
    }


    public BlockState rotate(BlockState p_152033_, Rotation p_152034_) {
        return p_152033_.setValue(FACING, p_152034_.rotate(p_152033_.getValue(FACING)));
    }

    public BlockState mirror(BlockState p_152030_, Mirror p_152031_) {
        return p_152030_.rotate(p_152031_.getRotation(p_152030_.getValue(FACING)));
    }

    public VoxelShape getShape(BlockState p_48816_, BlockGetter p_48817_, BlockPos p_48818_, CollisionContext p_48819_) {
        Direction $$4 = p_48816_.getValue(FACING);
        return $$4.getAxis() == Direction.Axis.X ? X_AXIS_AABB : Z_AXIS_AABB;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);

            if (blockEntity instanceof PotatoServerBlockEntity loader) {
                loader.disableChunkLoading();
            }
        }
        super.onRemove(state, level, pos, newState, moving);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext p_152019_) {
        Direction dir = p_152019_.getHorizontalDirection().getOpposite();
        return this.defaultBlockState()
                .setValue(FACING, dir)
                .setValue(ACTIVE, false)
                .setValue(POTATO_STATE, 0);
    }

    public boolean isRandomlyTicking(BlockState p_55486_) {
        return p_55486_.getValue(ACTIVE);
    }

    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(ACTIVE) && random.nextInt(100) < 20) {
            int r = 7;
            ArrayList<BlockPos> potatoPoses = new ArrayList<>();
            for (BlockPos tmpPos : BlockPos.withinManhattan(pos, r, r, r)){
                BlockState blockstate = level.getBlockState(tmpPos);
                if(blockstate.is(Blocks.POTATOES) && BlockHelper.isMaxAgedBlock(blockstate)){
                    potatoPoses.add(tmpPos.immutable());
                }
            }
            if(potatoPoses.isEmpty()){
                level.setBlock(pos, state.setValue(ACTIVE, Boolean.FALSE), 3);
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (blockEntity instanceof PotatoServerBlockEntity loader) {
                    loader.disableChunkLoading();
                }
            }else{
                BlockPos selectedPos = potatoPoses.get(random.nextInt(potatoPoses.size()));
                BlockState potatoState = level.getBlockState(selectedPos);
                if (!potatoState.is(Blocks.POTATOES)) return;
                level.levelEvent(2001, selectedPos, Block.getId(potatoState));
                BlockState newState = potatoState.setValue(PotatoBlock.AGE, 0);
                level.setBlock(selectedPos, newState, Block.UPDATE_ALL);
            }
        }
    }

    public void animateTick(BlockState p_221913_, Level p_221914_, BlockPos p_221915_, RandomSource p_221916_) {
        if (p_221913_.getValue(ACTIVE)) {
            spawnParticles(p_221914_, p_221915_);
        }

    }

    private static void spawnParticles(Level p_55455_, BlockPos p_55456_) {
        double d0 = 0.5625D;
        RandomSource randomsource = p_55455_.random;

        for(Direction direction : Direction.values()) {
            BlockPos blockpos = p_55456_.relative(direction);
            if (!p_55455_.getBlockState(blockpos).isSolidRender(p_55455_, blockpos)) {
                Direction.Axis direction$axis = direction.getAxis();
                double d1 = direction$axis == Direction.Axis.X ? 0.5D + d0 * (double)direction.getStepX() : (double)randomsource.nextFloat();
                double d2 = direction$axis == Direction.Axis.Y ? 0.5D + d0 * (double)direction.getStepY() : (double)randomsource.nextFloat();
                double d3 = direction$axis == Direction.Axis.Z ? 0.5D + d0 * (double)direction.getStepZ() : (double)randomsource.nextFloat();
                p_55455_.addParticle(DustParticleOptions.REDSTONE, (double)p_55456_.getX() + d1, (double)p_55456_.getY() + d2, (double)p_55456_.getZ() + d3, 0.0D, 0.0D, 0.0D);
            }
        }

    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        return Collections.singletonList(new ItemStack(ItemRegistry.POTATO_SERVER.get(), 1));
    }
}
