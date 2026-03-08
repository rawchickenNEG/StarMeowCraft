package com.starmeow.smc.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class PerfrostiteHoe extends HoeItem {
    public PerfrostiteHoe(Tier p_41336_, int p_41337_, float p_41338_, Properties p_41339_) {
        super(p_41336_, p_41337_, p_41338_, p_41339_);
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return this.canApplyEnchantment(EnchantmentHelper.getEnchantments(stack).keySet().toArray(new Enchantment[0])) || super.isBookEnchantable(stack, book);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return this.canApplyEnchantment(enchantment) || super.canApplyAtEnchantingTable(stack, enchantment);
    }

    private boolean canApplyEnchantment(Enchantment... enchantments) {
        for (Enchantment enchantment : enchantments) {
            if (enchantment.category == EnchantmentCategory.WEAPON || enchantment.category == EnchantmentCategory.DIGGER && enchantment != Enchantments.SWEEPING_EDGE)
                return true;
        }
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        tooltip.add(Component.translatable("tooltip.smc.perfrostite_tools").withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.translatable("tooltip.smc.perfrostite_tools_1").withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.translatable("tooltip.smc.perfrostite_tools_2").withStyle(ChatFormatting.BLUE));
    }
}
