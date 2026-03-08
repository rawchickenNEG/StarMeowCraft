package com.starmeow.smc.items;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class CarrotPickaxe extends PickaxeItem {
    private final boolean hasNightVision;
    public CarrotPickaxe(Tier p_42961_, int p_42962_, float p_42963_, Properties p_42964_, boolean hasNightVision) {
        super(p_42961_, p_42962_, p_42963_, p_42964_);
        this.hasNightVision = hasNightVision;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if(!level.isClientSide() && hasNightVision && entity instanceof LivingEntity living) {
            if(living.getItemBySlot(EquipmentSlot.MAINHAND) == stack || living.getItemBySlot(EquipmentSlot.OFFHAND) == stack) {
                living.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 310, 0, true, true, true));
            }
        }
    }

    public boolean mineBlock(ItemStack p_40998_, Level p_40999_, BlockState p_41000_, BlockPos p_41001_, LivingEntity p_41002_) {
        super.mineBlock(p_40998_, p_40999_, p_41000_, p_41001_, p_41002_);
        if(p_41002_ instanceof Player player){
            if(player.getRandom().nextInt(5) == 0){
                player.getFoodData().setFoodLevel(player.getFoodData().getFoodLevel() + 2);
            }
        }
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        String string = "tooltip.smc." + stack.getItem();
        tooltip.add(Component.translatable(string).withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.translatable(string + "_1").withStyle(ChatFormatting.BLUE));
    }
}
