package com.starmeow.smc.items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class IceCreamItem extends BowlFoodItem {
    public IceCreamItem(Properties p_40682_) {
        super(p_40682_);
    }
    //From neapolitan mod
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        entity.setTicksFrozen(entity.getTicksFrozen() + 200);
        return super.finishUsingItem(stack, level, entity);
    }
}
