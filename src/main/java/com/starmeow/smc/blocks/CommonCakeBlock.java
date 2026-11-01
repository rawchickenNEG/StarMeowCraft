package com.starmeow.smc.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

import java.util.function.Supplier;

public class CommonCakeBlock extends CakeBlock {
    public final Supplier<Item> sliceItem;
    public CommonCakeBlock(Properties p_51184_, Supplier<Item> slice) {
        super(p_51184_);
        this.sliceItem = slice;
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
                ? this.cutSlice(level, pos, state, player) : this.useWithEatSlice(state, level, pos, player, hand, hit);
    }

    public InteractionResult useWithEatSlice(BlockState p_51202_, Level p_51203_, BlockPos p_51204_, Player p_51205_, InteractionHand p_51206_, BlockHitResult p_51207_) {
        ItemStack $$6 = p_51205_.getItemInHand(p_51206_);
        Item $$7 = $$6.getItem();
        if ($$6.is(ItemTags.CANDLES) && (Integer)p_51202_.getValue(BITES) == 0) {
            Block $$8 = Block.byItem($$7);
            if ($$8 instanceof CandleBlock) {
                if (!p_51205_.isCreative()) {
                    $$6.shrink(1);
                }

                p_51203_.playSound((Player)null, p_51204_, SoundEvents.CAKE_ADD_CANDLE, SoundSource.BLOCKS, 1.0F, 1.0F);
                p_51203_.setBlockAndUpdate(p_51204_, CandleCakeBlock.byCandle($$8));
                p_51203_.gameEvent(p_51205_, GameEvent.BLOCK_CHANGE, p_51204_);
                p_51205_.awardStat(Stats.ITEM_USED.get($$7));
                return InteractionResult.SUCCESS;
            }
        }

        if (p_51203_.isClientSide) {
            if (eat(p_51203_, p_51204_, p_51202_, p_51205_).consumesAction()) {
                return InteractionResult.SUCCESS;
            }

            if ($$6.isEmpty()) {
                return InteractionResult.CONSUME;
            }
        }

        return this.eatSlice(p_51203_, p_51204_, p_51202_, p_51205_);
    }

    protected InteractionResult eatSlice(LevelAccessor p_51186_, BlockPos p_51187_, BlockState p_51188_, Player p_51189_) {
        if (!p_51189_.canEat(false)) {
            return InteractionResult.PASS;
        } else {
            p_51189_.awardStat(Stats.EAT_CAKE_SLICE);
            Item sliceItem = this.sliceItem.get();
            ItemStack sliceStack = new ItemStack(sliceItem);
            sliceItem.finishUsingItem(sliceStack, (Level) p_51186_, p_51189_);
            int $$4 = (Integer)p_51188_.getValue(BITES);
            p_51186_.gameEvent(p_51189_, GameEvent.EAT, p_51187_);
            if ($$4 < 6) {
                p_51186_.setBlock(p_51187_, (BlockState)p_51188_.setValue(BITES, $$4 + 1), 3);
            } else {
                p_51186_.removeBlock(p_51187_, false);
                p_51186_.gameEvent(p_51189_, GameEvent.BLOCK_DESTROY, p_51187_);
            }

            return InteractionResult.SUCCESS;
        }
    }

    protected InteractionResult cutSlice(Level level, BlockPos pos, BlockState state, Player player) {
        int bites = (Integer)state.getValue(BITES);
        if (bites < 6) {
            level.setBlock(pos, (BlockState)state.setValue(BITES, bites + 1), 3);
        } else {
            level.removeBlock(pos, false);
        }
        ItemStack slice = new ItemStack(sliceItem.get());
        ItemEntity entityToSpawn = new ItemEntity(level, (double)pos.getX() + (double)bites * 0.1, (double)pos.getY() + 0.2, (double)pos.getZ() + 0.5, slice);
        entityToSpawn.setDeltaMovement(-0.05, 0.0, 0.0);
        entityToSpawn.setPickUpDelay(10);
        level.addFreshEntity(entityToSpawn);

        level.playSound((Player)null, pos, SoundEvents.WOOL_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);
        return InteractionResult.SUCCESS;
    }
}
