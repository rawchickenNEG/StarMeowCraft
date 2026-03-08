package com.starmeow.smc.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class HighCalciumMilk extends TippedItems {
    public HighCalciumMilk(Properties p_41383_) {
        super(p_41383_);
    }

    public ItemStack finishUsingItem(ItemStack p_42923_, Level p_42924_, LivingEntity p_42925_) {
        List<MobEffectInstance> list = new ArrayList<>(p_42925_.getActiveEffects());
        for (MobEffectInstance ins : list) {
            p_42925_.removeEffect(ins.getEffect());
            if (p_42925_.hasEffect(ins.getEffect())) {
                p_42925_.getActiveEffectsMap().remove(ins.getEffect());
            }
        }
        if (p_42925_ instanceof Player && !((Player)p_42925_).getAbilities().instabuild) {
            p_42923_.shrink(1);
        }
        return p_42923_;
    }

    public int getUseDuration(ItemStack p_42933_) {
        return 32;
    }

    public UseAnim getUseAnimation(ItemStack p_42931_) {
        return UseAnim.DRINK;
    }

    public InteractionResultHolder<ItemStack> use(Level p_42927_, Player p_42928_, InteractionHand p_42929_) {
        return ItemUtils.startUsingInstantly(p_42927_, p_42928_, p_42929_);
    }
}
