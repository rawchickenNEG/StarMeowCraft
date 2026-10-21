package com.starmeow.smc.items.foods;

import com.starmeow.smc.items.TippedItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MoonCake extends TippedItems {
    public MoonCake(Properties p_41383_) {
        super(p_41383_);
    }

    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity living) {
        if(level.isNight()){
            switch (level.getMoonPhase()){
                case 0 -> applyMoonCakeEffects(living, 4);
                case 1, 7 -> applyMoonCakeEffects(living, 3);
                case 2, 6 -> applyMoonCakeEffects(living, 2);
                case 3, 5 -> applyMoonCakeEffects(living, 1);
                case 4 -> applyMoonCakeEffects(living, 0);
            }
        }
        return living.eat(level, stack);
    }

    public void applyMoonCakeEffects(LivingEntity living, int power){
        switch (power){
            case 1 -> {
                living.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1));
                living.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 2400));
            }
            case 2 -> {
                living.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 400, 1));
                living.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 2400, 3));
                living.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000));
            }
            case 3 -> {
                living.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 400, 1));
                living.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 2400, 3));
                living.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000));
                living.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000));
            }
            case 4 -> {
                living.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 600, 4));
                living.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 2400, 3));
                living.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000));
                living.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000));
            }
            default -> {
                living.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 2400));
            }
        }
    }
}