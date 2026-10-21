package com.starmeow.smc.mixin;
import com.mojang.blaze3d.shaders.Effect;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.starmeow.smc.helper.CuriosHelper;
import com.starmeow.smc.helper.EntityHelper;
import com.starmeow.smc.init.ItemRegistry;
import com.starmeow.smc.init.PotionEffectRegistry;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.Curios;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {
    @Inject(method = "isEntityUpsideDown", at = @At("HEAD"), cancellable = true)
    private static void smc$shouldUpsideDown(LivingEntity living, CallbackInfoReturnable<Boolean> cir) {
        if(CuriosHelper.hasCharm(living, ItemRegistry.GLITCH_PARTICLE.get())){
            cir.setReturnValue(true);
        }
    }

    @Inject(
            method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At("HEAD")
    )
    private void smc$rootAnimation(LivingEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
        float time = entity.level().getGameTime() + partialTicks;
        boolean affected = !entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(16), nearby -> nearby.hasEffect(PotionEffectRegistry.OHH_YE_YE.get())).isEmpty();
        if (!affected) return;

        float progress = time / 20.0F * ((float) Math.PI * 2.0F);

        float sin = Mth.sin(2.0F * progress);

        float yOffset = (float) (Math.tanh(2 * sin) / Math.tanh(2));

        poseStack.scale(1.0F - yOffset * 0.25F, 1.0F + yOffset * 0.25F, 1.0F - yOffset * 0.25F);

        float rotation = progress * 90;

        poseStack.mulPose(Axis.YP.rotationDegrees(rotation));
    }
}
