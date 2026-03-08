package com.starmeow.smc.mobeffects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeMod;

public class ElbowingEffect extends MobEffect {
    public ElbowingEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
        this.addAttributeModifier(Attributes.ATTACK_KNOCKBACK, "7107DE5E-1472-9872-6231-639485149237", (double) 1.0F, AttributeModifier.Operation.ADDITION);
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }
}
