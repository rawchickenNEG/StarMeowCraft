package com.starmeow.smc.items;

import com.starmeow.smc.entities.projectiles.CobbleBullet;
import com.starmeow.smc.init.EnchantmentRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

import java.util.function.Predicate;

public class Slingshot extends ProjectileWeaponItem {
    public Slingshot(Properties p_43009_) {
        super(p_43009_);
    }

    public void releaseUsing(ItemStack itemstack, Level level, LivingEntity p_40669_, int p_40670_) {
        if (p_40669_ instanceof Player player) {
            int i = this.getUseDuration(itemstack) - p_40670_;
            float f = getPowerForTime(i);
            if (level instanceof ServerLevel serverLevel) {
                Item bulletItem = Items.COBBLESTONE;
                if(EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.OBSIDIAN_HITTING.get(), itemstack) != 0){
                    bulletItem = Items.OBSIDIAN;
                } else if (EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.ENDER_FLOATING.get(), itemstack) != 0){
                    bulletItem = Items.END_STONE;
                } else if (EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.INFERNO_HITTING.get(), itemstack) != 0){
                    bulletItem = Items.NETHERRACK;
                }
                CobbleBullet bullet = new CobbleBullet(level, player);
                bullet.setItem(bulletItem.getDefaultInstance());
                bullet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5f * f, 1.0F);
                serverLevel.addFreshEntity(bullet);
                serverLevel.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (player.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                player.awardStat(Stats.ITEM_USED.get(this));
                if (!player.getAbilities().instabuild) {
                    Item finalBulletItem = bulletItem;
                    player.getInventory().clearOrCountMatchingItems(p -> new ItemStack(finalBulletItem).getItem() == p.getItem(), 1, player.inventoryMenu.getCraftSlots());
                }
                itemstack.hurtAndBreak(1, player, (p_40665_) -> {
                    p_40665_.broadcastBreakEvent(player.getUsedItemHand());
                });
            }
        }
    }

    public static float getPowerForTime(int p_40662_) {
        float f = (float)p_40662_ / 10.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }
        return f;
    }

    public int getUseDuration(ItemStack p_40680_) {
        return 72000;
    }

    public UseAnim getUseAnimation(ItemStack p_40678_) {
        return UseAnim.BOW;
    }

    public InteractionResultHolder<ItemStack> use(Level p_40672_, Player player, InteractionHand p_40674_) {
        ItemStack itemstack = player.getItemInHand(p_40674_);
        int oh = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.OBSIDIAN_HITTING.get(), itemstack);
        int ef = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.ENDER_FLOATING.get(), itemstack);
        int ih = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.INFERNO_HITTING.get(), itemstack);
        if(player.getAbilities().instabuild
                || player.getInventory().contains(Items.COBBLESTONE.getDefaultInstance()) && oh + ef + ih == 0
                || (player.getInventory().contains(Items.OBSIDIAN.getDefaultInstance()) && oh != 0)
                || (player.getInventory().contains(Items.END_STONE.getDefaultInstance()) && ef != 0)
                || (player.getInventory().contains(Items.NETHERRACK.getDefaultInstance()) && ih != 0)){
            player.startUsingItem(p_40674_);
            return InteractionResultHolder.success(itemstack);
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }

    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return ARROW_ONLY;
    }

    public int getDefaultProjectileRange() {
        return 15;
    }
}
