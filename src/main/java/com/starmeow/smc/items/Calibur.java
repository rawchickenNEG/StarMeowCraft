package com.starmeow.smc.items;

import com.starmeow.smc.config.Config;
import com.starmeow.smc.helper.ItemHelper;
import com.starmeow.smc.init.ItemRegistry;
import com.starmeow.smc.init.ParticleRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;

public class Calibur extends SwordItem {
    public Calibur(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);
        if(!level.isClientSide() && entity instanceof LivingEntity living && selected) {
            int i = 0;
            if(living.hasEffect(MobEffects.DAMAGE_BOOST)){
                i = Math.min(1 + living.getEffect(MobEffects.DAMAGE_BOOST).getAmplifier(), 5);
            }
            if(i != 5){
                living.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 1, 4 - i));
                living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 1, 4 - i));
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        String string = "tooltip.smc." + stack.getItem();
        tooltip.add(Component.translatable(string).withStyle(ChatFormatting.BLUE));
    }
}
