package com.starmeow.smc.items;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

public class MilkshakeItem extends TippedItems{
    public MilkshakeItem(Properties p_41383_) {
        super(p_41383_);
    }

    public ItemStack finishUsingItem(ItemStack p_41348_, Level p_41349_, LivingEntity p_41350_) {
        super.finishUsingItem(p_41348_, p_41349_, p_41350_);
        if (p_41350_ instanceof ServerPlayer $$3) {
            CriteriaTriggers.CONSUME_ITEM.trigger($$3, p_41348_);
            $$3.awardStat(Stats.ITEM_USED.get(this));
        }

        if (!p_41349_.isClientSide) {
            this.applyMilkshakeEffect(p_41349_, p_41350_);
        }

        if (p_41348_.isEmpty()) {
            return new ItemStack(Items.GLASS_BOTTLE);
        } else {
            if (p_41350_ instanceof Player && !((Player)p_41350_).getAbilities().instabuild) {
                ItemStack $$4 = new ItemStack(Items.GLASS_BOTTLE);
                Player $$5 = (Player)p_41350_;
                if (!$$5.getInventory().add($$4)) {
                    $$5.drop($$4, false);
                }
            }

            return p_41348_;
        }
    }

    public void applyMilkshakeEffect(Level level, LivingEntity living){
    }

    public int getUseDuration(ItemStack p_41360_) {
        return 40;
    }

    public UseAnim getUseAnimation(ItemStack p_41358_) {
        return UseAnim.DRINK;
    }

    public SoundEvent getDrinkingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    public SoundEvent getEatingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    public InteractionResultHolder<ItemStack> use(Level p_41352_, Player p_41353_, InteractionHand p_41354_) {
        return ItemUtils.startUsingInstantly(p_41352_, p_41353_, p_41354_);
    }
}
