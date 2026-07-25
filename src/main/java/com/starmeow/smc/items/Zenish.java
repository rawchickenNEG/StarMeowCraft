package com.starmeow.smc.items;

import com.starmeow.smc.helper.ItemHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class Zenish extends SwordItem {
    public Zenish(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
    }
    @Override
    public Component getName(ItemStack stack) {
        int[] colors = {
                ItemHelper.colorToInt(86, 105, 181),
                ItemHelper.colorToInt(111, 203, 190),
                ItemHelper.colorToInt(225, 238, 159),
                ItemHelper.colorToInt(20, 120, 118),
                ItemHelper.colorToInt(47, 46, 112)
        };
        return ItemHelper.customRainbowColor(super.getName(stack), 100, true, 0.16f, colors);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        String string = "tooltip.smc." + stack.getItem();
        Style style = Style.EMPTY.withColor(ItemHelper.colorToInt(125, 89, 225));
        tooltip.add(Component.translatable(string).withStyle(style));
        tooltip.add(Component.translatable(string +"_1").withStyle(ChatFormatting.BLUE));

    }
}
