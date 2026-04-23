package com.starmeow.smc.items;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class AsteraMilkshakeItem extends MilkshakeItem{
    public AsteraMilkshakeItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void applyMilkshakeEffect(Level level, LivingEntity living){
        living.heal(living.getActiveEffects().size() * 4);
    }
}
