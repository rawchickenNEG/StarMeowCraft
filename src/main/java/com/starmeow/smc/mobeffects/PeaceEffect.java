package com.starmeow.smc.mobeffects;

import com.starmeow.smc.init.PotionEffectRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class PeaceEffect extends MobEffect {
    public PeaceEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
    }

    public void applyEffectTick(LivingEntity entity, int amplifier) {
        Level level = entity.level();
        if(level.getGameTime() % 20L == 0L){
            AABB box = entity.getBoundingBox().inflate(32);
            for (Mob mob : level.getEntitiesOfClass(Mob.class, box)) {
                /*
                //暂时弃用的方案
                if(mob.hasEffect(PotionEffectRegistry.NO_PEACE.get())) return;
                if (mob.getTarget() == entity && mob.canBeAffected(new MobEffectInstance(PotionEffectRegistry.NO_PEACE.get()))) {
                    mob.setTarget(null);
                    mob.setAggressive(false);
                    mob.getNavigation().stop();
                    if (mob instanceof NeutralMob neutral) neutral.stopBeingAngry();
                }
                */

                if (mob.getTarget() == entity && mob.canBeAffected(new MobEffectInstance(PotionEffectRegistry.PEACE.get()))) {
                    mob.setTarget(null);
                    mob.setAggressive(false);
                    mob.getNavigation().stop();
                    if (mob instanceof NeutralMob neutral) neutral.stopBeingAngry();
                }
            }
        }
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }
}