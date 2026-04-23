package com.starmeow.smc.items;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class BroccoliMilkshakeItem extends MilkshakeItem{
    public BroccoliMilkshakeItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void applyMilkshakeEffect(Level level, LivingEntity living){
        removeAllEffects(living);
        final Vec3 center = new Vec3(living.getX(), living.getY(), living.getZ());
        AABB aabb = new AABB(center, center).inflate(8);
        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, aabb);
        for (LivingEntity target : targets) {
            removeAllEffects(target);
        }
    }

    public void removeAllEffects(LivingEntity living){
        List<MobEffectInstance> list = new ArrayList<>(living.getActiveEffects());
        for (MobEffectInstance ins : list) {
            living.removeEffect(ins.getEffect());
            if (living.hasEffect(ins.getEffect())) {
                living.getActiveEffectsMap().remove(ins.getEffect());
            }
        }
    }
}
