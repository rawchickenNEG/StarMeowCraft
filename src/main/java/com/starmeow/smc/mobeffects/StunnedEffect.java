package com.starmeow.smc.mobeffects;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;

public class StunnedEffect extends MobEffect {
    public StunnedEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, "7107DE5E-2346-4030-5654-514C1F160892", (double) -1.0F, AttributeModifier.Operation.MULTIPLY_BASE);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, "7107DE5E-2134-4030-940E-114553364086", (double) -1.0F, AttributeModifier.Operation.MULTIPLY_BASE);
    }


    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.getDeltaMovement().y > 0) {
            entity.setDeltaMovement(entity.getDeltaMovement().multiply(1, 0.1D, 1));
        }
        double r0 = 0.5;
        double p0 = Math.random();
        double sin = r0 * Math.sin((0.5 * p0) * 180D * Math.PI);
        double cos = r0 * Math.cos((0.5 * p0) * 180D * Math.PI);
        if(entity.level() instanceof ServerLevel serverLevel){
            serverLevel.sendParticles(ParticleTypes.WAX_OFF, entity.getX() + sin, entity.getY() + entity.getBbHeight(), entity.getZ() + cos, 1, 0, 0, 0, 0);
        }
        if (entity instanceof Mob mob) {
            if (!mob.level().isClientSide) {
                mob.goalSelector.setControlFlag(Goal.Flag.MOVE, false);
                mob.goalSelector.setControlFlag(Goal.Flag.JUMP, false);
                mob.goalSelector.setControlFlag(Goal.Flag.LOOK, false);
                mob.goalSelector.setControlFlag(Goal.Flag.TARGET, false);
            }
        }
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }
}
