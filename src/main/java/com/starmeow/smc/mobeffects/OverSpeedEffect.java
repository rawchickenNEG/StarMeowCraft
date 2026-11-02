package com.starmeow.smc.mobeffects;

import com.starmeow.smc.helper.DamageHelper;
import com.starmeow.smc.init.DamageTypeRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class OverSpeedEffect extends MobEffect {
    public OverSpeedEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, "debacf21-98c4-4abe-b799-2682f264488b", (double) 0.2F, AttributeModifier.Operation.MULTIPLY_BASE);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, "75dccef6-8b53-4245-ab68-ae1781baddb4", (double) 0.2F, AttributeModifier.Operation.MULTIPLY_BASE);
    }

    public void applyEffectTick(LivingEntity living, int amplifier) {
        int amp = amplifier - 4;
        if(amp >= 1){
            if(living.isSprinting()) {
                living.hurt(DamageHelper.customDamageSource(living, DamageTypeRegistry.CHOCLIZ_KEY), living.getMaxHealth() * 0.1F * amp);
            }
        }
   }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }
}
