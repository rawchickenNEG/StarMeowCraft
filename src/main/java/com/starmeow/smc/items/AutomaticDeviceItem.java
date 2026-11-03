package com.starmeow.smc.items;

import com.starmeow.smc.helper.ItemHelper;
import com.starmeow.smc.helper.PlayerHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class AutomaticDeviceItem extends Item {
    public AutomaticDeviceItem(Properties p_41383_) {
        super(p_41383_);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        CompoundTag tag = itemstack.getOrCreateTag();
        boolean auto = tag.getBoolean("SMCAutoAttack");
        boolean fullAuto = tag.getBoolean("SMCFullyAutoAttack");
        if(!auto && !fullAuto){
            tag.putBoolean("SMCAutoAttack", true);
        } else if (auto && !fullAuto){
            tag.putBoolean("SMCAutoAttack", false);
            tag.putBoolean("SMCFullyAutoAttack", true);
        } else {
            tag.putBoolean("SMCFullyAutoAttack", false);
        }
        level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    public boolean isFoil(ItemStack p_41453_) {
        CompoundTag tag = p_41453_.getOrCreateTag();
        return tag.getBoolean("SMCAutoAttack") || tag.getBoolean("SMCFullyAutoAttack");
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        CompoundTag tag = stack.getOrCreateTag();
        boolean auto = tag.getBoolean("SMCAutoAttack");
        boolean fullAuto = tag.getBoolean("SMCFullyAutoAttack");
        MutableComponent mode = (!auto && !fullAuto ? Component.translatable("tooltip.smc.automatic_device_mode_0") : (auto ? Component.translatable("tooltip.smc.automatic_device_mode_1") : Component.translatable("tooltip.smc.automatic_device_mode_2")));
        tooltip.add(Component.translatable("tooltip.smc.automatic_device").withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.translatable("tooltip.smc.automatic_device_1").withStyle(ChatFormatting.DARK_AQUA).append(mode.withStyle(ChatFormatting.YELLOW)));
    }
}
