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
        if(entity.level() instanceof ServerLevel level){
            float height = entity.getBbHeight();
            int step = 16;
            for(int i = 0; i < step; i++){
                float r = 0.5f * (float)Math.cos(i / (0.5f * step) * Math.PI) + 0.5f;
                float g = 0.5f * (float)Math.cos(i / (0.5f * step) * Math.PI - 2 * Math.PI / 3) + 0.5f;
                float b = 0.5f * (float)Math.cos(i / (0.5f * step) * Math.PI + 2 * Math.PI / 3) + 0.5f;
                DustParticleOptions options = new DustParticleOptions(new Vector3f(r, g, b), 1);
                level.sendParticles(options, entity.getX(), entity.getY() + height * (i / (float)step) , entity.getZ(), 1, 0, 0, 0, 0);
            }
        }
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }
}
