package com.starmeow.smc.items;

import com.starmeow.smc.entities.projectiles.ThrownBroccoliBoom;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class BroccoliBoom extends Item {
    public BroccoliBoom(Properties p_41126_) {
        super(p_41126_);
    }

    public void releaseUsing(ItemStack itemstack, Level level, LivingEntity living, int p_40670_) {
        int i = this.getUseDuration(itemstack) - p_40670_;
        if (i >= 20) {
            level.playSound((Player)null, living.getX(), living.getY(), living.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
            if (!level.isClientSide) {
                ThrownBroccoliBoom $$4 = new ThrownBroccoliBoom(level, living);
                $$4.setItem(itemstack);
                $$4.shootFromRotation(living, living.getXRot(), living.getYRot(), 0.0F, 1.5F, 1.0F);
                level.addFreshEntity($$4);
            }

            if(living instanceof Player player){
                player.awardStat(Stats.ITEM_USED.get(this));
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
            }
        }
    }

    public InteractionResultHolder<ItemStack> use(Level p_40672_, Player player, InteractionHand p_40674_) {
        ItemStack itemstack = player.getItemInHand(p_40674_);
        player.startUsingItem(p_40674_);
        return InteractionResultHolder.success(itemstack);
    }

    public int getUseDuration(ItemStack p_40680_) {
        return 72000;
    }

    public UseAnim getUseAnimation(ItemStack p_40678_) {
        return UseAnim.BOW;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        String string = "tooltip.smc." + stack.getItem();
        tooltip.add(Component.translatable(string).withStyle(ChatFormatting.YELLOW));
    }
}