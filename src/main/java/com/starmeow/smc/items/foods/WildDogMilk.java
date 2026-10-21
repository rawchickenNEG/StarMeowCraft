package com.starmeow.smc.items.foods;

import com.starmeow.smc.helper.ItemHelper;
import com.starmeow.smc.items.TippedItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class WildDogMilk extends Item {
    public WildDogMilk(Properties p_41383_) {
        super(p_41383_);
    }

    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity livingEntity) {
        ItemStack itemstack = super.finishUsingItem(pStack, pLevel, livingEntity);
        List<MobEffectInstance> list = new ArrayList<>(livingEntity.getActiveEffects());
        for (MobEffectInstance ins : list) {
            livingEntity.addEffect(new MobEffectInstance(ins.getEffect(), -1, ins.getAmplifier(), true, ins.isVisible()));
        }
        return itemstack;
    }

    public int getUseDuration(ItemStack p_42933_) {
        return 32;
    }

    public UseAnim getUseAnimation(ItemStack p_42931_) {
        return UseAnim.DRINK;
    }

    @Override
    public Component getName(ItemStack stack) {
        return ItemHelper.rainbowColor(super.getName(stack), 20, false);
    }

    public InteractionResultHolder<ItemStack> use(Level p_42927_, Player p_42928_, InteractionHand p_42929_) {
        return ItemUtils.startUsingInstantly(p_42927_, p_42928_, p_42929_);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        int[] colors = {
                ItemHelper.colorToInt(155, 155, 155),
                ItemHelper.colorToInt(85, 85, 85)
        };
        String string = "tooltip.smc." + stack.getItem();
        Component colorfulTip = ItemHelper.rainbowColor(Component.translatable(string + "_1"), 50, false);
        Component colorfulTip2 = ItemHelper.customRainbowColor(Component.translatable(string + "_2"), 50, false, 0.16f, colors);
        tooltip.add(Component.translatable(string).withStyle(ChatFormatting.BLUE).append(colorfulTip));
        tooltip.add(colorfulTip2);
    }
}
