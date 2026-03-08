package com.starmeow.smc.items;

import com.starmeow.smc.init.ItemRegistry;
import com.starmeow.smc.init.PotionEffectRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
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
import java.util.List;

public class LollipopItem extends SwordItem {
    public LollipopItem(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
    }

    public ItemStack finishUsingItem(ItemStack p_42923_, Level p_42924_, LivingEntity p_42925_) {
        if (p_42925_ instanceof Player player){
            int foodLevel = 2;
            float satLevel = 2.5F;
            boolean flag = false;
            if(p_42923_.is(ItemRegistry.COLORFUL_ICE_CREAM.get())){
                flag = true;
            }
            player.getFoodData().setFoodLevel(player.getFoodData().getFoodLevel() + (flag ? 4 : 1) * foodLevel);
            player.getFoodData().setSaturation(player.getFoodData().getSaturationLevel() + satLevel);
            p_42924_.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_BURP, player.getSoundSource(), 0.5F, player.getVoicePitch());
            p_42923_.hurtAndBreak(p_42923_.getMaxDamage() / 10, player, (p_40665_) -> {
                p_40665_.broadcastBreakEvent(player.getUsedItemHand());
            });
            if(p_42923_.is(ItemRegistry.BROCCOLI_LOLLIPOP.get()) || flag){
                player.addEffect(new MobEffectInstance(PotionEffectRegistry.POISON_RESISTANCE.get(), 100));
            }
            if(p_42923_.is(ItemRegistry.FROST_LOLLIPOP.get()) || flag){
                player.addEffect(new MobEffectInstance(PotionEffectRegistry.FROST_RESISTANCE.get(), 100));
            }
            if(p_42923_.is(ItemRegistry.PERKIN_LOLLIPOP.get()) || flag){
                player.addEffect(new MobEffectInstance(PotionEffectRegistry.STAR_LIGHT.get(), 100));
            }
        }
        return p_42923_;
    }


    public int getUseDuration(ItemStack p_42933_) {
        return 32;
    }

    public UseAnim getUseAnimation(ItemStack p_42931_) {
        return UseAnim.EAT;
    }

    public InteractionResultHolder<ItemStack> use(Level p_42927_, Player p_42928_, InteractionHand p_42929_) {
        ItemStack itemstack = p_42928_.getItemInHand(p_42929_);
        if(!(p_42928_.getOffhandItem().getItem() instanceof ShieldItem) || p_42928_.isCrouching()){
            return ItemUtils.startUsingInstantly(p_42927_, p_42928_, p_42929_);
        }
        return InteractionResultHolder.pass(itemstack);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        String string = "tooltip.smc." + stack.getItem();
        tooltip.add(Component.translatable(string).withStyle(ChatFormatting.BLUE));
    }
}
