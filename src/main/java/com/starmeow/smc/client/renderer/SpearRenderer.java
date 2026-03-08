package com.starmeow.smc.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.starmeow.smc.client.model.SpearModel;
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
public class SpearRenderer extends EntityRenderer<ThrownSpear> {
    public static final ResourceLocation LOCATION = new ResourceLocation("smc:textures/entity/perkin_spear.png");
    private final SpearModel model;

    public SpearRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new SpearModel(context.bakeLayer(SpearModel.LAYER_LOCATION));
    }


    public void render(ThrownSpear p_116111_, float p_116112_, float p_116113_, PoseStack p_116114_, MultiBufferSource p_116115_, int p_116116_) {
        p_116114_.pushPose();
        p_116114_.mulPose(Axis.YP.rotationDegrees(Mth.lerp(p_116113_, p_116111_.yRotO, p_116111_.getYRot()) - 90.0F));
        p_116114_.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(p_116113_, p_116111_.xRotO, p_116111_.getXRot()) + 90.0F));
        VertexConsumer $$6 = ItemRenderer.getFoilBufferDirect(p_116115_, this.model.renderType(this.getTextureLocation(p_116111_)), false, p_116111_.isFoil());
        this.model.renderToBuffer(p_116114_, $$6, p_116116_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        p_116114_.popPose();
        super.render(p_116111_, p_116112_, p_116113_, p_116114_, p_116115_, p_116116_);
    }

    public ResourceLocation getTextureLocation(ThrownSpear p_116109_) {
        return LOCATION;
    }
}
