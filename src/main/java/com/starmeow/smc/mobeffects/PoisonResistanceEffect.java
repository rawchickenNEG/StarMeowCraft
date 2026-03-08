package com.starmeow.smc.mobeffects;

import com.starmeow.smc.init.PotionEffectRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class PoisonResistanceEffect extends MobEffect {
    public PoisonResistanceEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
    }

    public void applyEffectTick(LivingEntity entity, int amplifier) {
        entity.removeEffect(MobEffects.WITHER);
        entity.removeEffect(MobEffects.POISON);
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }
}
