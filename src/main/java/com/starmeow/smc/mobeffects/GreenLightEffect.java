package com.starmeow.smc.mobeffects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class GreenLightEffect extends MobEffect {
    public GreenLightEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, "b8705675-96ff-4a00-94d8-54354668d844", 0.2F, AttributeModifier.Operation.MULTIPLY_BASE);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, "59387078-f879-4274-8c98-3dec1dc88b42", 0.3F, AttributeModifier.Operation.MULTIPLY_BASE);
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }
}
