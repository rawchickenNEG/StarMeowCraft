package com.starmeow.smc.blocks;

import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class DeluxeCakeBlock extends CakeBlock {
    public DeluxeCakeBlock(Properties p_51184_) {
        super(p_51184_);
    }

    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack heldStack = player.getItemInHand(hand);
        if (level.isClientSide) {
            if (heldStack.is(ItemTags.create(new ResourceLocation("farmersdelight:tools/knives")))
                    ||heldStack.is(ItemTags.create(new ResourceLocation("forge:tools/knives")))) {
                return this.cutSlice(level, pos, state, player);
            }

            if (super.use(state, level, pos, player, hand, hit) == InteractionResult.SUCCESS) {
                return InteractionResult.SUCCESS;
            }

            if (heldStack.isEmpty()) {
                return InteractionResult.CONSUME;
            }
        }

        return heldStack.is(ItemTags.create(new ResourceLocation("farmersdelight:tools/knives")))
                ||heldStack.is(ItemTags.create(new ResourceLocation("forge:tools/knives")))
                ? this.cutSlice(level, pos, state, player) : super.use(state, level, pos, player, hand, hit);
    }

    protected InteractionResult cutSlice(Level level, BlockPos pos, BlockState state, Player player) {
        int bites = (Integer)state.getValue(BITES);
        if (bites < 6) {
            level.setBlock(pos, (BlockState)state.setValue(BITES, bites + 1), 3);
        } else {
            level.removeBlock(pos, false);
        }

        Direction direction = player.getDirection().getOpposite();
        ItemStack slice = new ItemStack(ItemRegistry.DELUXE_CAKE_SLICE.get());
        ItemEntity entityToSpawn = new ItemEntity(level, (double)pos.getX() + (double)bites * 0.1, (double)pos.getY() + 0.2, (double)pos.getZ() + 0.5, slice);
        entityToSpawn.setDeltaMovement(-0.05, 0.0, 0.0);
        entityToSpawn.setPickUpDelay(10);
        level.addFreshEntity(entityToSpawn);

        level.playSound((Player)null, pos, SoundEvents.WOOL_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);
        return InteractionResult.SUCCESS;
    }
}
