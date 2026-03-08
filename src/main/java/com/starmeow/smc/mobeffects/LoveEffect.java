package com.starmeow.smc.mobeffects;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;

public class LoveEffect extends MobEffect {
    public LoveEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
        this.addAttributeModifier(Attributes.FOLLOW_RANGE, "7107DE5E-1235-1934-6242-294572013847", (double) 4.0F, AttributeModifier.Operation.ADDITION);
    }

    public void applyEffectTick(LivingEntity entity, int amplifier) {
        double r0 = 0.8;
        double p0 = Math.random();
        double sin = r0 * Math.sin((0.5 * p0) * 180D * Math.PI);
        double cos = r0 * Math.cos((0.5 * p0) * 180D * Math.PI);
        if(entity.level() instanceof ServerLevel serverLevel){
            if(entity.getRandom().nextInt(6) == 1){
                serverLevel.sendParticles(ParticleTypes.HEART, entity.getX() + sin, entity.getY() + entity.getBbHeight(), entity.getZ() + cos, 1, 0, 0, 0, 0);
            }
        }
        if(entity instanceof Animal animal && animal.getInLoveTime() < 1){
            if(animal.getAge() >= 0){
                animal.setAge(Math.max(animal.getAge() - (int) Math.pow(2, amplifier), 0));
                animal.setInLoveTime(600);
            }

        }

    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }
}
