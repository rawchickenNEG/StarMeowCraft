package com.starmeow.smc.mobeffects;

import com.starmeow.smc.helper.BlockHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BloomingEffect extends MobEffect {
    public BloomingEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
    }

    public void applyEffectTick(LivingEntity p_19467_, int p_19468_) {
        Level level = p_19467_.level();
        if(level instanceof ServerLevel serverLevel){
            BlockPos pos = p_19467_.getOnPos();
            int r = 3;
            for (BlockPos tmpPos : BlockPos.withinManhattan(pos, r * 2 + 1, r * 2 + 1, r * 2 + 1)){
                if(Math.round(BlockHelper.getBlockPosDistance(tmpPos, pos)) <= r){
                    BlockState state = serverLevel.getBlockState(tmpPos);
                    if(state.getBlock() instanceof BonemealableBlock plant && p_19467_.getRandom().nextInt(100) <= 50){
                        serverLevel.levelEvent(3009, tmpPos, 0);
                        plant.performBonemeal(serverLevel, p_19467_.getRandom(), tmpPos, serverLevel.getBlockState(tmpPos));
                    }
                }
            }
        }

    }
    public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
        int i;
        i = 50 >> p_19456_;
        if (i > 0) {
            return p_19455_ % i == 0;
        } else {
            return true;
        }
    }
}
