package com.starmeow.smc.items;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ChocolateArmorItems extends SMCArmorItems{
    public ChocolateArmorItems(ArmorMaterial p_40386_, Type p_266831_, Properties p_40388_) {
        super(p_40386_, p_266831_, p_40388_);
    }

    @Override
    public void onArmorTick(ItemStack itemstack, Level level, Player player) {
        if(!level.isClientSide()) {
            CompoundTag tag = itemstack.getOrCreateTag();
            int damage = itemstack.getDamageValue();
            if(tag.contains("SMCChocolateArmorDamage")){
                if(damage > tag.getInt("SMCChocolateArmorDamage")){
                    player.getFoodData().eat(damage - tag.getInt("SMCChocolateArmorDamage"), 0.5f);
                }
            }
            tag.putInt("SMCChocolateArmorDamage", damage);
        }
    }


    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.translatable("tooltip.smc.chocolate_armor").withStyle(ChatFormatting.BLUE));
    }
}
