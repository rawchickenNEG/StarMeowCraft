package com.starmeow.smc.items;

import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class FrostiumBow extends BowItem {
    public FrostiumBow(Properties p_40660_) {
        super(p_40660_);
    }

    public static final String KEY = "SMC_FROM_FROSTIUM_BOW";

    @Override
    public void releaseUsing(ItemStack p_40667_, Level p_40668_, LivingEntity p_40669_, int p_40670_) {
        if (p_40669_ instanceof Player player) {
            boolean flag = player.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, p_40667_) > 0;
            ItemStack itemstack = player.getProjectile(p_40667_);

            int i = this.getUseDuration(p_40667_) - p_40670_;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(p_40667_, p_40668_, player, i, !itemstack.isEmpty() || flag);
            if (i < 0) return;

            if (!itemstack.isEmpty() || flag) {
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(Items.ARROW);
                }

                float f = getPowerForTime(i);
                if (!((double)f < 0.1D)) {
                    boolean flag1 = player.getAbilities().instabuild || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, p_40667_, player));
                    if (!p_40668_.isClientSide) {
                        ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                        ArrayList<AbstractArrow> arrows = new ArrayList<AbstractArrow>();
                        AbstractArrow abstractarrow = arrowitem.createArrow(p_40668_, itemstack, player);
                        AbstractArrow abstractarrow1 = arrowitem.createArrow(p_40668_, itemstack, player);
                        AbstractArrow abstractarrow2 = arrowitem.createArrow(p_40668_, itemstack, player);
                        arrows.add(abstractarrow);
                        arrows.add(abstractarrow1);
                        arrows.add(abstractarrow2);
                        int s = 0;
                        for (AbstractArrow arrow : arrows){
                            arrow = customArrow(arrow);
                            if (f == 1.0F) {
                                arrow.setCritArrow(true);
                            }

                            int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, p_40667_);
                            if (j > 0) {
                                arrow.setBaseDamage(arrow.getBaseDamage() + (double)j * 0.5D + 0.5D);
                            }

                            int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, p_40667_);
                            if (k > 0) {
                                arrow.setKnockback(k);
                            }

                            if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, p_40667_) > 0) {
                                arrow.setSecondsOnFire(100);
                            }
                            if (flag1 || player.getAbilities().instabuild && (itemstack.is(Items.SPECTRAL_ARROW) || itemstack.is(Items.TIPPED_ARROW))) {
                                arrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                            }
                            if (flag && itemstack.is(ItemRegistry.FROST_ARROW.get())){
                                arrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                            }
                            if(s > 0){
                                arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
                            } else {
                                p_40667_.hurtAndBreak(1, player, (p_289501_) -> {
                                    p_289501_.broadcastBreakEvent(player.getUsedItemHand());
                                });
                            }
                            arrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, f * 3.0F - 0.3F * s, 1.0F);
                            p_40668_.addFreshEntity(arrow);
                            s++;
                        }
                    }

                    p_40668_.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (p_40668_.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    if (!flag1 && !player.getAbilities().instabuild && !(flag && itemstack.is(ItemRegistry.FROST_ARROW.get()))) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            player.getInventory().removeItem(itemstack);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    @Override
    public AbstractArrow customArrow(AbstractArrow arrow) {
        arrow.getPersistentData().putBoolean(KEY, true);
        return arrow;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        String string = "tooltip.smc." + stack.getItem();
        tooltip.add(Component.translatable(string).withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.translatable(string + "_1").withStyle(ChatFormatting.AQUA));
    }
}
