package com.starmeow.smc.items;

import com.starmeow.smc.entities.projectiles.FrostArrow;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FrostArrowItem extends ArrowItem {
    public FrostArrowItem(Item.Properties p_43235_) {
        super(p_43235_);
    }

    public AbstractArrow createArrow(Level p_43237_, ItemStack p_43238_, LivingEntity p_43239_) {
        return new FrostArrow(p_43237_, p_43239_);
    }
}