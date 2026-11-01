package com.starmeow.smc.items;

import com.starmeow.smc.helper.ItemHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class GodPickaxe extends PickaxeItem {
    public GodPickaxe(Tier p_42961_, int p_42962_, float p_42963_, Properties p_42964_) {
        super(p_42961_, p_42962_, p_42963_, p_42964_);
    }

    @Override
    public Component getName(ItemStack stack) {
        int[] colors = {
                ItemHelper.colorToInt(53, 143, 41),
                ItemHelper.colorToInt(119, 184, 73),
                ItemHelper.colorToInt(232, 230, 160),
                ItemHelper.colorToInt(119, 184, 73)
        };
        return ItemHelper.customRainbowColor(super.getName(stack), 100, true, 0.16f, colors);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        String string = "tooltip.smc." + stack.getItem();
        tooltip.add(Component.translatable(string).withStyle(ChatFormatting.YELLOW));
        tooltip.add(Component.translatable(string + "_1").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable(string + "_2").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable(string + "_3").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable(string + "_4").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable(string + "_5").withStyle(ChatFormatting.GRAY));
    }
}
