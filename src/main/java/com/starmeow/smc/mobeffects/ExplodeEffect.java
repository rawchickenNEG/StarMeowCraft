package com.starmeow.smc.mobeffects;

import com.starmeow.smc.helper.EntityHelper;
import com.starmeow.smc.init.PotionEffectRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.level.Level;

public class ExplodeEffect extends MobEffect {
    public ExplodeEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
    }

    public void removeAttributeModifiers(LivingEntity living, AttributeMap p_19418_, int amp) {
        Level level = living.level();
        if(!level.isClientSide()){
            if(living.hasEffect(PotionEffectRegistry.FUZED.get()) && living.getEffect(PotionEffectRegistry.FUZED.get()).getDuration() > 0) return;
            EntityHelper.spawnLingeringCloud(living, amp);
            level.explode(living, living.getX(), living.getY(), living.getZ(), (amp + 1) * 3, Level.ExplosionInteraction.NONE);
        }
        super.removeAttributeModifiers(living, p_19418_, amp);
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }
}
