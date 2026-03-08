package com.starmeow.smc.mobeffects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.ForgeMod;

public class ExtensionEffect extends MobEffect {
    public ExtensionEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
        this.addAttributeModifier(ForgeMod.BLOCK_REACH.get(), "7107DE5E-1235-1934-6242-901385140412", (double) 1.0F, AttributeModifier.Operation.ADDITION);
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }
}
