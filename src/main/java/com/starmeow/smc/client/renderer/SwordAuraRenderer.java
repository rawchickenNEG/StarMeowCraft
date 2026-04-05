package com.starmeow.smc.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.starmeow.smc.client.model.SpearModel;
import com.starmeow.smc.client.model.SwordAuraModel;
import com.starmeow.smc.entities.ThrownSwordEntity;
import com.starmeow.smc.entities.projectiles.SwordAura;
import com.starmeow.smc.entities.projectiles.ThrownSpear;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SwordAuraRenderer extends EntityRenderer<SwordAura> {
    public static final ResourceLocation LOCATION = new ResourceLocation("smc:textures/entity/projectiles/sword_aura.png");
    private final SwordAuraModel model;

    public SwordAuraRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new SwordAuraModel(context.bakeLayer(SwordAuraModel.LAYER_LOCATION));
    }

    protected int getBlockLightLevel(SwordAura p_116092_, BlockPos p_116093_) {
        return 15;
    }

    public void render(SwordAura p_116111_, float p_116112_, float p_116113_, PoseStack p_116114_, MultiBufferSource p_116115_, int p_116116_) {
        p_116114_.pushPose();
        p_116114_.mulPose(Axis.YP.rotationDegrees(Mth.lerp(p_116113_, p_116111_.yRotO, p_116111_.getYRot()) - 180F));
        p_116114_.mulPose(Axis.XP.rotationDegrees(Mth.lerp(p_116113_, p_116111_.xRotO, p_116111_.getXRot())));
        this.model.renderToBuffer(p_116114_, p_116115_.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(p_116111_))), p_116116_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        p_116114_.popPose();
        super.render(p_116111_, p_116112_, p_116113_, p_116114_, p_116115_, p_116116_);
    }

    public ResourceLocation getTextureLocation(SwordAura p_116109_) {
        return LOCATION;
    }
}
