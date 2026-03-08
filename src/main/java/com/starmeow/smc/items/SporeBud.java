package com.starmeow.smc.items;

import com.starmeow.smc.entities.projectiles.ThrownSporeBud;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class SporeBud extends Item {
    public SporeBud(Item.Properties p_41126_) {
        super(p_41126_);
    }

    public InteractionResultHolder<ItemStack> use(Level p_41128_, Player p_41129_, InteractionHand p_41130_) {
        ItemStack $$3 = p_41129_.getItemInHand(p_41130_);
        p_41128_.playSound((Player)null, p_41129_.getX(), p_41129_.getY(), p_41129_.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (p_41128_.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!p_41128_.isClientSide) {
            ThrownSporeBud $$4 = new ThrownSporeBud(p_41128_, p_41129_);
            $$4.setItem($$3);
            $$4.shootFromRotation(p_41129_, p_41129_.getXRot(), p_41129_.getYRot(), 0.0F, 1.5F, 1.0F);
            p_41128_.addFreshEntity($$4);
        }

        p_41129_.awardStat(Stats.ITEM_USED.get(this));
        if (!p_41129_.getAbilities().instabuild) {
            $$3.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess($$3, p_41128_.isClientSide());
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        String string = "tooltip.smc." + stack.getItem();
        tooltip.add(Component.translatable(string).withStyle(ChatFormatting.BLUE));
    }
}