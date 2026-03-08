package com.starmeow.smc.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class WallnutPotShield extends ShieldItem {
    public WallnutPotShield(Properties p_43089_) {
        super(p_43089_);
    }

    public InteractionResultHolder<ItemStack> use(Level p_43099_, Player p_43100_, InteractionHand p_43101_) {
        ItemStack itemstack = p_43100_.getItemInHand(p_43101_);
        p_43100_.startUsingItem(p_43101_);
        Vec3 center = new Vec3(p_43100_.getX(), p_43100_.getY(), p_43100_.getZ());
        AABB aabb = new AABB(center, center).inflate(16);
        List<LivingEntity> entities = p_43099_.getEntitiesOfClass(LivingEntity.class, aabb);
        for (LivingEntity target : entities) {
            if(target instanceof Mob mob){
                mob.setTarget(p_43100_);
            }
        }
        return InteractionResultHolder.consume(itemstack);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        String string = "tooltip.smc." + stack.getItem();
        tooltip.add(Component.translatable(string).withStyle(ChatFormatting.BLUE));
    }

    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }

    public UseAnim getUseAnimation(ItemStack p_42931_) {
        return UseAnim.BOW;
    }
}
