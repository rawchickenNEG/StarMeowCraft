package com.starmeow.smc.mobeffects;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class FrostEffect extends MobEffect {
    public FrostEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, "7107DE5E-2346-4030-5654-634532179442", (double) -0.2F, AttributeModifier.Operation.MULTIPLY_BASE);
    }

    public void applyEffectTick(LivingEntity entity, int amplifier) {
        int frozen = entity.getTicksFrozen();
        entity.setIsInPowderSnow(true);
        entity.setTicksFrozen(frozen + 1);

        double r0 = 0.5;
        double p0 = Math.random();
        double sin = r0 * Math.sin((0.5 * p0) * 180D * Math.PI);
        double cos = r0 * Math.cos((0.5 * p0) * 180D * Math.PI);
        if(entity.level() instanceof ServerLevel serverLevel){
            serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, entity.getX() + sin, entity.getY() + entity.getBbHeight(), entity.getZ() + cos, 1, 0, 0, 0, 0);
        }
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }
}
