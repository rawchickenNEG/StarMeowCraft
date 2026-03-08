package com.starmeow.smc.items;

import com.starmeow.smc.entities.projectiles.MagicArrow;
import com.starmeow.smc.entities.projectiles.RainbowArrow;
import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CommandBlockWand extends Item {
    public CommandBlockWand(Properties p_40660_) {
        super(p_40660_);
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack item, int count) {
        if (!level.isClientSide) {
            AbstractArrow abstractarrow = new MagicArrow(level, entity);
            abstractarrow.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 1 * 3.0F, 1.0F);

            int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, item);
            if (j > 0) {
                abstractarrow.setBaseDamage(abstractarrow.getBaseDamage() + (double)j * 0.5 + 0.5);
            }

            int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, item);
            if (k > 0) {
                abstractarrow.setKnockback(k);
            }

            if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, item) > 0) {
                abstractarrow.setSecondsOnFire(100);
            }
            level.addFreshEntity(abstractarrow);
        }
        level.playSound((Player)null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (entity.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
        if(entity instanceof Player player) player.awardStat(Stats.ITEM_USED.get(this));
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

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        String string = "tooltip.smc." + stack.getItem();
        tooltip.add(Component.translatable(string).withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.translatable(string + "_1").withStyle(ChatFormatting.AQUA));
    }
}
