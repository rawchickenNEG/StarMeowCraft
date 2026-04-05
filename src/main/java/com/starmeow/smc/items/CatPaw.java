package com.starmeow.smc.items;

import com.starmeow.smc.helper.ItemHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class CatPaw extends SwordItem {
    public CatPaw(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);
        if(!level.isClientSide() && entity instanceof LivingEntity player) {
            final Vec3 center = new Vec3(player.getX(), player.getY(), player.getZ());
            AABB aabb = new AABB(center, center).inflate(16);
            List<LivingEntity> entities = player.level().getEntitiesOfClass(LivingEntity.class, aabb);
            for (LivingEntity target : entities) {
                if (target instanceof Creeper creeper) {
                    if (creeper.getTarget() != null && creeper.getTarget().is(player)) {
                        creeper.setTarget(null);
                    }
                    creeper.goalSelector.addGoal(5, new AvoidEntityGoal<>(creeper, LivingEntity.class, 6.0F, 1.0D, 1.2D,
                            p -> p.getMainHandItem().is(this)||p.getOffhandItem().is(this)));
                }
            }
        }
    }

    @Override
    public Component getName(ItemStack stack) {
        return ItemHelper.customColor(super.getName(stack), 255, 150, 255, false);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        String string = "tooltip.smc." + stack.getItem();
        tooltip.add(Component.translatable(string).withStyle(ChatFormatting.BLUE));
    }
}
