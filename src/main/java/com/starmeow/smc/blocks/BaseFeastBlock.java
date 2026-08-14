package com.starmeow.smc.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;
//From FD feast block
public class BaseFeastBlock extends Block {
    public static final DirectionProperty FACING;
    public static final IntegerProperty SERVINGS;
    public final Supplier<Item> servingItem;
    public final boolean hasLeftovers;
    public final boolean hasServingParticles;
    protected static final VoxelShape[] SHAPES;

    public BaseFeastBlock(BlockBehaviour.Properties properties, Supplier<Item> servingItem, boolean hasLeftovers, boolean hasServingParticles) {
        super(properties);
        this.servingItem = servingItem;
        this.hasLeftovers = hasLeftovers;
        this.hasServingParticles = hasServingParticles;
        this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH)).setValue(this.getServingsProperty(), this.getMaxServings()));
    }

    public BaseFeastBlock(BlockBehaviour.Properties properties, Supplier<Item> servingItem, boolean hasLeftovers) {
        this(properties, servingItem, hasLeftovers, true);
    }

    public IntegerProperty getServingsProperty() {
        return SERVINGS;
    }

    public int getMaxServings() {
        return 4;
    }

    public ItemStack getServingItem(BlockState state) {
        return new ItemStack((ItemLike)this.servingItem.get());
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES[(Integer)state.getValue(SERVINGS)];
    }

    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        return level.isClientSide && this.takeServing(level, pos, state, player, hand).consumesAction() ? InteractionResult.SUCCESS : this.takeServing(level, pos, state, player, hand);
    }

    protected InteractionResult takeServing(Level level, BlockPos pos, BlockState state, Player player, InteractionHand hand) {
        int servings = (Integer)state.getValue(this.getServingsProperty());
        if (servings == 0) {
            level.playSound((Player)null, pos, SoundEvents.WOOD_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);
            level.destroyBlock(pos, true);
            return InteractionResult.SUCCESS;
        } else {
            ItemStack serving = this.getServingItem(state);
            ItemStack heldStack = player.getItemInHand(hand);
            if (servings > 0) {
                if (!serving.hasCraftingRemainingItem() || ItemStack.isSameItem(heldStack, serving.getCraftingRemainingItem())) {
                    level.setBlock(pos, (BlockState)state.setValue(this.getServingsProperty(), servings - 1), 3);
                    player.awardStat(Stats.ITEM_USED.get(heldStack.getItem()));
                    if (!player.getAbilities().instabuild && serving.hasCraftingRemainingItem()) {
                        heldStack.shrink(1);
                    }

                    if (!player.getInventory().add(serving)) {
                        player.drop(serving, false);
                    }

                    if ((Integer)level.getBlockState(pos).getValue(this.getServingsProperty()) == 0 && !this.hasLeftovers) {
                        level.destroyBlock(pos, true);
                    }

                    level.playSound((Player)null, pos, SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.BLOCKS, 1.0F, 1.0F);
                    if (this.hasServingParticles && level instanceof ServerLevel) {
                        ServerLevel serverLevel = (ServerLevel)level;
                        serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), (double)pos.getX() + (double)0.5F, (double)pos.getY() + (double)0.5F, (double)pos.getZ() + (double)0.5F, 3, 0.1, 0.1, 0.1, 0.001);
                    }

                    return InteractionResult.SUCCESS;
                }

                player.displayClientMessage(Component.translatable("message.smc.take_serving", serving.getCraftingRemainingItem().getHoverName()), true);
            }

            return InteractionResult.PASS;
        }
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        return facing == Direction.DOWN && !state.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return canSupportRigidBlock(level, pos.below());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, SERVINGS);
    }

    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return state.getValue(this.getServingsProperty());
    }

    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
    }

    static {
        FACING = BlockStateProperties.HORIZONTAL_FACING;
        SERVINGS = IntegerProperty.create("servings", 0, 4);
        SHAPES = new VoxelShape[]{Block.box((double)2.0F, (double)0.0F, (double)2.0F, (double)14.0F, (double)2.0F, (double)14.0F), Block.box((double)2.0F, (double)0.0F, (double)2.0F, (double)14.0F, (double)2.0F, (double)14.0F), Block.box((double)2.0F, (double)0.0F, (double)2.0F, (double)14.0F, (double)4.0F, (double)14.0F), Block.box((double)2.0F, (double)0.0F, (double)2.0F, (double)14.0F, (double)6.0F, (double)14.0F), Block.box((double)2.0F, (double)0.0F, (double)2.0F, (double)14.0F, (double)8.0F, (double)14.0F)};
    }
}