package com.starmeow.smc.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.starmeow.smc.init.EnchantmentRegistry;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin {

    @Inject(
            method = "renderArmorPiece",
            at = @At("HEAD"),
            cancellable = true
    )
    private void lmd$hideEnchantedArmor(PoseStack poseStack, MultiBufferSource buffer, LivingEntity entity, EquipmentSlot slot, int packedLight, HumanoidModel<?> model, CallbackInfo ci) {
        ItemStack stack = entity.getItemBySlot(slot);
        if (stack.getEnchantmentLevel(EnchantmentRegistry.INVISIBLE_ARMOR.get()) > 0) {
            ci.cancel();
        }
    }
}