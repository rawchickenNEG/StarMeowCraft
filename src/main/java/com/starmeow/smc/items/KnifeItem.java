package com.starmeow.smc.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class KnifeItem extends SwordItem {
    public KnifeItem(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
    }

    @Override
    public void setDamage(ItemStack stack, int damage){
        super.setDamage(stack, 0);
    }

    @Override
    public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStackMaterial) {
        return false;
    }

    @Override
    public boolean isEnchantable(ItemStack p_41456_) {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        String string = "tooltip.smc." + stack.getItem();
        tooltip.add(Component.translatable(string).withStyle(ChatFormatting.DARK_AQUA));
        tooltip.add(Component.translatable(string + "_1").withStyle(ChatFormatting.DARK_AQUA));
        tooltip.add(Component.translatable(string + "_2").withStyle(ChatFormatting.DARK_AQUA));
        tooltip.add(Component.translatable(string + "_3").withStyle(ChatFormatting.DARK_AQUA));
    }
}
