package com.starmeow.smc.items;

import com.starmeow.smc.entities.projectiles.PeaBullet;
import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class PeaShooterPot extends BowItem implements Equipable {

    public PeaShooterPot(Item.Properties p_40660_) {
        super(p_40660_);
    }
    
    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack item, int count) {
        if (count > 10 && count % 10 == 0) {
            if (entity instanceof Player player) {
                boolean flag = player.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, item) > 0;
                ItemStack itemstack = new ItemStack(ItemRegistry.PEA.get());
                if (player.getInventory().contains(itemstack) || flag) {
                    boolean flag1 = player.getAbilities().instabuild;
                    if (!level.isClientSide) {
                        //子弹实体
                        PeaBullet bullet = new PeaBullet(level, entity);
                        bullet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.0F, 1.0F);

                        int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, item);
                        if (j > 0) {
                            bullet.setDamage(bullet.damage + j * 0.5f + 0.5f);
                        }

                        int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, item);
                        if (k > 0) {
                            bullet.setBack(k);
                        }

                        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, item) > 0) {
                            bullet.setSecondsOnFire(100);
                        }

                        level.addFreshEntity(bullet);
                    }

                    level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F));
                    if (!flag1 && !player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            player.getInventory().removeItem(itemstack);
                        }
                    }
                    item.hurtAndBreak(1, player, (p_40665_) -> {
                        p_40665_.broadcastBreakEvent(player.getUsedItemHand());
                    });
                    if (!flag1) {
                        player.getInventory().clearOrCountMatchingItems(p -> new ItemStack(ItemRegistry.PEA.get()).getItem() == p.getItem(), 1, player.inventoryMenu.getCraftSlots());
                    }
                }
            }
        }
    }

    @Override
    public void releaseUsing(ItemStack p_40667_, Level p_40668_, LivingEntity p_40669_, int p_40670_) {
        if (p_40669_ instanceof Player player) {
            player.awardStat(Stats.ITEM_USED.get(this));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_40672_, Player p_40673_, InteractionHand p_40674_) {
        ItemStack itemstack = p_40673_.getItemInHand(p_40674_);
        p_40673_.startUsingItem(p_40674_);
        return InteractionResultHolder.success(itemstack);
    }

    @Override
    public boolean isEnchantable(ItemStack p_41456_) {
        return true;
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return false;
    }

    @Override
    public int getUseDuration(ItemStack p_40680_) {
        return 72000;
    }

    @Override
    public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack) {
        return oldStack.getItem() == newStack.getItem();
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged || newStack.getItem() != oldStack.getItem();
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_40678_) {
        return UseAnim.BOW;
    }

    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }
}