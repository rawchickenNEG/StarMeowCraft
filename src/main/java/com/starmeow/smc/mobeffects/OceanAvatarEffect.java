package com.starmeow.smc.mobeffects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class OceanAvatarEffect extends MobEffect {
    public OceanAvatarEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
    }

    public void applyEffectTick(LivingEntity entity, int amplifier) {
        entity.fallDistance = 0;
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }
}
