package com.starmeow.smc.items;

import com.starmeow.smc.init.ItemRegistry;
import com.starmeow.smc.init.SoundRegistry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

public class WaterBowlItem extends BowlFoodItem {
    public WaterBowlItem(Item.Properties p_40682_) {
        super(p_40682_);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack p_40684_, Level p_40685_, LivingEntity p_40686_) {
        if(p_40684_.is(ItemRegistry.BOWL_OF_HOT_WATER.get())){
            p_40686_.hurt(p_40686_.damageSources().onFire() , 5);
        }
        ItemStack $$3 = super.finishUsingItem(p_40684_, p_40685_, p_40686_);
        return p_40686_ instanceof Player && ((Player)p_40686_).getAbilities().instabuild ? $$3 : new ItemStack(Items.BOWL);
    }

    public UseAnim getUseAnimation(ItemStack p_41358_) {
        return UseAnim.DRINK;
    }

    public SoundEvent getEatingSound() {
        return SoundEvents.GENERIC_DRINK;
    }
}