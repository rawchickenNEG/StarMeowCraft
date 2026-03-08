package com.starmeow.smc.items;

import com.starmeow.smc.config.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class LuckyNugget extends Item {
    public LuckyNugget(Properties p_41383_) {
        super(p_41383_);
    }

    public ItemStack finishUsingItem(ItemStack p_42923_, Level p_42924_, LivingEntity p_42925_) {
        Random random = new Random();
        List<MobEffect> effects = BuiltInRegistries.MOB_EFFECT.stream().filter(effect ->
                !effect.isInstantenous() && !effect.equals(MobEffects.SATURATION)
        ).toList();
        MobEffect randomEffect = effects.get(random.nextInt(effects.size()));
        if(!p_42924_.isClientSide()){
            int duration = Config.LUCKY_NUGGET_DURATION.get();
            if(Config.LUCKY_NUGGET_INFINITE.get()){
                duration = -1;
            }
            p_42925_.addEffect(new MobEffectInstance(randomEffect, duration, 0));
        }
        return p_42925_.eat(p_42924_, p_42923_);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        String string = "tooltip.smc.lucky_clover";
        tooltip.add(Component.translatable(string).withStyle(ChatFormatting.BLUE));
    }
}
