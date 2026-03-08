package com.starmeow.smc.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ChopShield extends ShieldItem {
    public ChopShield(Properties p_43089_) {
        super(p_43089_);
    }

    @Override
    public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        if (entity instanceof Player player && player.getCooldowns().isOnCooldown(itemstack.getItem()) && player.getFoodData().getFoodLevel() > 0) {
            player.getCooldowns().removeCooldown(itemstack.getItem());
            player.getFoodData().setFoodLevel(Math.max(player.getFoodData().getFoodLevel() - 2, 0));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        String string = "tooltip.smc." + stack.getItem();
        tooltip.add(Component.translatable(string).withStyle(ChatFormatting.BLUE));
    }

    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }
}
