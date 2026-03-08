package com.starmeow.smc.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.starmeow.smc.entities.EasterBunny;
import net.minecraft.client.model.RabbitModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class EasterBunnyRenderer extends MobRenderer<EasterBunny, RabbitModel<EasterBunny>> {

    public EasterBunnyRenderer(EntityRendererProvider.Context p_174360_) {
        super(p_174360_, new RabbitModel(p_174360_.bakeLayer(ModelLayers.RABBIT)), 0.6F);
    }

    @Override
    public void render(EasterBunny entity, float entityYaw, float partialTicks,
                       PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.scale(2.0F, 2.0F, 2.0F);
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(EasterBunny p_114482_) {
        return new ResourceLocation("smc:textures/entity/easter_bunny.png");
    }
}
