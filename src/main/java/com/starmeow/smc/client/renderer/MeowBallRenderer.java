package com.starmeow.smc.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.starmeow.smc.client.model.CatBallModel;
import com.starmeow.smc.entities.projectiles.MeowBall;
import com.starmeow.smc.entities.projectiles.SwordAura;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class MeowBallRenderer extends EntityRenderer<MeowBall> {
    public static final List<ResourceLocation> LOCATIONS = List.of(
            new ResourceLocation("smc:textures/entity/meow_balls/cat_ball_bf_meow.png"),
            new ResourceLocation("smc:textures/entity/meow_balls/cat_ball_denpc.png"),
            new ResourceLocation("smc:textures/entity/meow_balls/cat_ball_huacai.png"),
            new ResourceLocation("smc:textures/entity/meow_balls/cat_ball_starjump.png"),
            new ResourceLocation("smc:textures/entity/meow_balls/cat_ball_zinc.png"),
            new ResourceLocation("smc:textures/entity/meow_balls/cat_ball_raw_chicken.png")
    );

    private final CatBallModel model;

    public MeowBallRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new CatBallModel(context.bakeLayer(CatBallModel.LAYER_LOCATION));
    }

    protected int getBlockLightLevel(MeowBall p_116092_, BlockPos p_116093_) {
        return 15;
    }

    public void render(MeowBall p_116111_, float p_116112_, float p_116113_, PoseStack p_116114_, MultiBufferSource p_116115_, int p_116116_) {
        p_116114_.pushPose();
        p_116114_.mulPose(Axis.YP.rotationDegrees(Mth.lerp(p_116113_, p_116111_.yRotO, p_116111_.getYRot()) - 180F));
        p_116114_.mulPose(Axis.XP.rotationDegrees(Mth.lerp(p_116113_, p_116111_.xRotO, p_116111_.getXRot())));
        p_116114_.mulPose(Axis.ZP.rotationDegrees(180.0F));
        p_116114_.translate(0, -2f, 0);
        p_116114_.scale(1.2f, 1.2f, 1.2f);

        this.model.renderToBuffer(p_116114_, p_116115_.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(p_116111_))), p_116116_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        p_116114_.popPose();
        super.render(p_116111_, p_116112_, p_116113_, p_116114_, p_116115_, p_116116_);
    }

    public ResourceLocation getTextureLocation(MeowBall p_116109_) {
        return LOCATIONS.get(p_116109_.getVariant());
    }
}
