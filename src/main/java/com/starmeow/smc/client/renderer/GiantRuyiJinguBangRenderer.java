package com.starmeow.smc.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.starmeow.smc.client.model.GiantRuyiJinguBangModel;
import com.starmeow.smc.entities.projectiles.GiantRuyiJinguBang;
import com.starmeow.smc.entities.projectiles.ThrownSpear;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GiantRuyiJinguBangRenderer extends EntityRenderer<GiantRuyiJinguBang> {
    public static final ResourceLocation LOCATION = new ResourceLocation("smc:textures/entity/giant_ruyi_jingu_bang.png");
    private final GiantRuyiJinguBangModel model;

    public GiantRuyiJinguBangRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new GiantRuyiJinguBangModel(context.bakeLayer(GiantRuyiJinguBangModel.LAYER_LOCATION));
    }


    @Override
    public void render(GiantRuyiJinguBang entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        float fallProgress = entity.getFallProgress(partialTicks);
        float retractProgress = entity.getRetractProgress(partialTicks);
        float fallEased = fallProgress * fallProgress;
        float startHeight = 20.0F;
        float yOffset = (1.0F - fallEased) * startHeight + 1;
        float retractEased = retractProgress * retractProgress;

        yOffset += retractEased * 12.0F;

        float scale = 1.0F - retractEased * 0.9F;
        poseStack.translate(0.0D, yOffset, 0.0D);

        poseStack.scale(3* scale, 3* scale, 3* scale);
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot())));
        poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));

        VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(buffer, this.model.renderType(this.getTextureLocation(entity)), false, false);

        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        poseStack.popPose();

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    public ResourceLocation getTextureLocation(GiantRuyiJinguBang p_116109_) {
        return LOCATION;
    }
}
