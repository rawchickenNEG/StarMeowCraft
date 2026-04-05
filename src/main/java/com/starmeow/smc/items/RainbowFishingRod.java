package com.starmeow.smc.items;

import com.starmeow.smc.entities.projectiles.RainbowFishingHook;
import com.starmeow.smc.helper.ItemHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class RainbowFishingRod extends FishingRodItem {
    public RainbowFishingRod(Properties p_41285_) {
        super(p_41285_);
    }

    public InteractionResultHolder<ItemStack> use(Level p_41290_, Player p_41291_, InteractionHand p_41292_) {
        ItemStack itemstack = p_41291_.getItemInHand(p_41292_);
        if (p_41291_.fishing != null) {
            if (!p_41290_.isClientSide) {
                int i = p_41291_.fishing.retrieve(itemstack);
                itemstack.hurtAndBreak(i, p_41291_, (p_41288_) -> {
                    p_41288_.broadcastBreakEvent(p_41292_);
                });
            }

            p_41290_.playSound((Player)null, p_41291_.getX(), p_41291_.getY(), p_41291_.getZ(), SoundEvents.FISHING_BOBBER_RETRIEVE, SoundSource.NEUTRAL, 1.0F, 0.4F / (p_41290_.getRandom().nextFloat() * 0.4F + 0.8F));
            p_41291_.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
        } else {
            p_41290_.playSound((Player)null, p_41291_.getX(), p_41291_.getY(), p_41291_.getZ(), SoundEvents.FISHING_BOBBER_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (p_41290_.getRandom().nextFloat() * 0.4F + 0.8F));
            if (!p_41290_.isClientSide) {
                int k = EnchantmentHelper.getFishingSpeedBonus(itemstack) + 1;
                int j = EnchantmentHelper.getFishingLuckBonus(itemstack) + 1;
                p_41290_.addFreshEntity(new RainbowFishingHook(p_41291_, p_41290_, j, k));
            }
            p_41291_.awardStat(Stats.ITEM_USED.get(this));
            p_41291_.gameEvent(GameEvent.ITEM_INTERACT_START);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, p_41290_.isClientSide());
    }

    @Override
    public Component getName(ItemStack stack) {
        return ItemHelper.rainbowColor(super.getName(stack), 20, 0);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        String string = "tooltip.smc." + stack.getItem();
        tooltip.add(Component.translatable(string).withStyle(ChatFormatting.BLUE));
    }
}
