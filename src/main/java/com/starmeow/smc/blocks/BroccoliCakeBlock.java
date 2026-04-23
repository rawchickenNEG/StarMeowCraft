package com.starmeow.smc.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class BroccoliCakeBlock extends CommonCakeBlock{
    public BroccoliCakeBlock(Properties p_51184_, Supplier<Item> slice) {
        super(p_51184_, slice);
    }

    public boolean isRandomlyTicking(BlockState p_52288_) {
        return p_52288_.getValue(BITES) != 0;
    }

    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.isAreaLoaded(pos, 1)) {
            if(random.nextInt(20) == 0){
                BlockState newState = state.setValue(BITES, Mth.clamp(state.getValue(BITES) - 1, 0, MAX_BITES));
                level.setBlock(pos, newState, Block.UPDATE_ALL);
                level.playSound((Player)null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        }
    }
}
