package com.starmeow.smc.mobeffects;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.joml.Vector3f;

public class RainbowEffect extends MobEffect {
    public RainbowEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, "7107DE5E-1415-8327-1836-660908659023", (double) +0.2F, AttributeModifier.Operation.MULTIPLY_BASE);
    }

    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if(entity instanceof Player player){
            player.getAbilities().mayfly = true;
        }
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }
}
