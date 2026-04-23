package com.starmeow.smc.items;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class FrostMilkshakeItem extends MilkshakeItem{
    public FrostMilkshakeItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void applyMilkshakeEffect(Level level, LivingEntity living){
        List<MobEffectInstance> list = new ArrayList<>(living.getActiveEffects());
        final Vec3 center = new Vec3(living.getX(), living.getY(), living.getZ());
        AABB aabb = new AABB(center, center).inflate(8);
        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, aabb);
        for (LivingEntity target : targets) {
            for (MobEffectInstance ins : list) {
                target.addEffect(ins);
            }
        }
    }
}
