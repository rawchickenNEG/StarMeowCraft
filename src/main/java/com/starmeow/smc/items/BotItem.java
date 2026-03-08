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

public class BotItem extends Item {
    public BotItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);
        if (!(entity instanceof Player player) || level.isClientSide) return;

        CompoundTag tag = stack.getOrCreateTag();
        int xp = tag.getInt("SMCBotXp");
        boolean absorbing = tag.getBoolean("SMCBotAbsorbing");
        boolean releasing = tag.getBoolean("SMCBotReleasing");
        boolean fixing = tag.getBoolean("SMCBotFixing");

        int playerXp = PlayerHelper.getPlayerXP(player);
        int change = Math.max(playerXp / ((player.experienceLevel + 1) * 3), 1);

        if (absorbing && playerXp > 0 && xp + change < Integer.MAX_VALUE) {
            player.giveExperiencePoints(-change);
            tag.putInt("SMCBotXp", xp + change);
        }

        if (releasing && xp > 0) {
            int limit = ItemHelper.extractNumberFromItemName(stack);
            if(limit == -1 || limit > PlayerHelper.getLevelForExperience(playerXp)){
                int give = Math.min(change, xp);
                player.giveExperiencePoints(give);
                tag.putInt("SMCBotXp", xp - give);
            }
        }

        if (fixing && xp > 0 && ItemHelper.hasRepairableEquipment(player)) {
            ExperienceOrb xpOrb = new ExperienceOrb(level, player.getX(), player.getY(), player.getZ(), 5);
            if(level.getGameTime() % 5L == 0L){
                tag.putInt("SMCBotXp", xp - 5);
                level.addFreshEntity(xpOrb);
            }
        }
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        CompoundTag tag = itemstack.getOrCreateTag();
        boolean absorbing = tag.getBoolean("SMCBotAbsorbing");
        boolean releasing = tag.getBoolean("SMCBotReleasing");
        boolean fixing = tag.getBoolean("SMCBotFixing");
        if(player.isCrouching()){
            if(!absorbing && !releasing){
                tag.putBoolean("SMCBotAbsorbing", true);
            } else if (absorbing && !releasing){
                tag.putBoolean("SMCBotAbsorbing", false);
                tag.putBoolean("SMCBotReleasing", true);
            } else {
                tag.putBoolean("SMCBotReleasing", false);
            }
            level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);

        } else {
            tag.putBoolean("SMCBotFixing", !fixing);
            level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);

        }
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    public boolean isFoil(ItemStack p_41453_) {
        CompoundTag tag = p_41453_.getOrCreateTag();
        return tag.getBoolean("SMCBotFixing");
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        CompoundTag tag = stack.getOrCreateTag();
        int xp = tag.getInt("SMCBotXp");
        int xpLevel = PlayerHelper.getLevelForExperience(xp);
        boolean absorbing = tag.getBoolean("SMCBotAbsorbing");
        boolean releasing = tag.getBoolean("SMCBotReleasing");
        boolean fixing = tag.getBoolean("SMCBotFixing");
        MutableComponent mode = (fixing ? Component.translatable("tooltip.smc.bot_mode_1") : Component.translatable("tooltip.smc.bot_mode_0"));
        MutableComponent XpMode = (!absorbing && !releasing ? Component.translatable("tooltip.smc.bot_mode_0") : (absorbing ? Component.translatable("tooltip.smc.bot_mode_2") : Component.translatable("tooltip.smc.bot_mode_3")));
        tooltip.add(Component.translatable("tooltip.smc.bot").withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.translatable("tooltip.smc.bot_1").withStyle(ChatFormatting.DARK_AQUA).append(mode.withStyle(ChatFormatting.YELLOW)));
        tooltip.add(Component.translatable("tooltip.smc.bot_2").withStyle(ChatFormatting.DARK_AQUA).append(XpMode.withStyle(ChatFormatting.YELLOW)));
        tooltip.add(Component.translatable("tooltip.smc.bot_3", xp, xpLevel).withStyle(ChatFormatting.GREEN));
    }
}
