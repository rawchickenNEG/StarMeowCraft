package com.starmeow.smc.mobeffects;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class SnowStepEffect extends MobEffect {
    public SnowStepEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
    }

    public void applyEffectTick(LivingEntity entity, int amplifier) {
        BlockState blockstate = Blocks.SNOW.defaultBlockState();

        for(int i = 0; i < 4; ++i) {
            int j = Mth.floor(entity.getX() + (double)((float)(i % 2 * 2 - 1) * 0.25F));
            int k = Mth.floor(entity.getY());
            int l = Mth.floor(entity.getZ() + (double)((float)(i / 2 % 2 * 2 - 1) * 0.25F));
            BlockPos blockpos = new BlockPos(j, k, l);
            if (entity.level().isEmptyBlock(blockpos) && blockstate.canSurvive(entity.level(), blockpos)) {
                entity.level().setBlockAndUpdate(blockpos, blockstate);
                entity.level().gameEvent(GameEvent.BLOCK_PLACE, blockpos, GameEvent.Context.of(entity, blockstate));
            }
        }
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }
}
