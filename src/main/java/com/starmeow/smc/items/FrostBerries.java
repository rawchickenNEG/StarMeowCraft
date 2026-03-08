package com.starmeow.smc.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class FrostBerries extends Item {
    public FrostBerries(Properties p_41383_) {
        super(p_41383_);
    }

    public ItemStack finishUsingItem(ItemStack p_42923_, Level p_42924_, LivingEntity p_42925_) {
        final Vec3 center = new Vec3(p_42925_.getX(), p_42925_.getY(), p_42925_.getZ());
        AABB aabb = new AABB(center, center).inflate(32);
        List<LivingEntity> entities = p_42925_.level().getEntitiesOfClass(LivingEntity.class, aabb);
        for (LivingEntity target : entities) {
            target.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100, 0));
        }
        return p_42925_.eat(p_42924_, p_42923_);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        String string = "tooltip.smc." + stack.getItem();
        tooltip.add(Component.translatable(string).withStyle(ChatFormatting.BLUE));
    }
}
