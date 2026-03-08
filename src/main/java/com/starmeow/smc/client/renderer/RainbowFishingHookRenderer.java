package com.starmeow.smc.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.starmeow.smc.entities.projectiles.RainbowFishingHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolActions;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.awt.*;

@OnlyIn(Dist.CLIENT)
public class RainbowFishingHookRenderer extends EntityRenderer<RainbowFishingHook> {
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("smc:textures/entity/rainbow_fishing_hook.png");
    private static final RenderType RENDER_TYPE;

    public RainbowFishingHookRenderer(EntityRendererProvider.Context p_174117_) {
        super(p_174117_);
    }

    public void render(RainbowFishingHook p_114705_, float p_114706_, float p_114707_, PoseStack p_114708_, MultiBufferSource p_114709_, int p_114710_) {
        Player player = p_114705_.getPlayerOwner();
        if (player != null) {
            p_114708_.pushPose();
            p_114708_.pushPose();
            p_114708_.scale(0.5F, 0.5F, 0.5F);
            p_114708_.mulPose(this.entityRenderDispatcher.cameraOrientation());
            p_114708_.mulPose(Axis.YP.rotationDegrees(180.0F));
            PoseStack.Pose posestack$pose = p_114708_.last();
            Matrix4f matrix4f = posestack$pose.pose();
            Matrix3f matrix3f = posestack$pose.normal();
            VertexConsumer vertexconsumer = p_114709_.getBuffer(RENDER_TYPE);
            vertex(vertexconsumer, matrix4f, matrix3f, p_114710_, 0.0F, 0, 0, 1);
            vertex(vertexconsumer, matrix4f, matrix3f, p_114710_, 1.0F, 0, 1, 1);
            vertex(vertexconsumer, matrix4f, matrix3f, p_114710_, 1.0F, 1, 1, 0);
            vertex(vertexconsumer, matrix4f, matrix3f, p_114710_, 0.0F, 1, 0, 0);
            p_114708_.popPose();
            int i = player.getMainArm() == HumanoidArm.RIGHT ? 1 : -1;
            ItemStack itemstack = player.getMainHandItem();
            if (!itemstack.canPerformAction(ToolActions.FISHING_ROD_CAST)) {
                i = -i;
            }

            float f = player.getAttackAnim(p_114707_);
            float f1 = Mth.sin(Mth.sqrt(f) * 3.1415927F);
            float f2 = Mth.lerp(p_114707_, player.yBodyRotO, player.yBodyRot) * 0.017453292F;
            double d0 = (double)Mth.sin(f2);
            double d1 = (double)Mth.cos(f2);
            double d2 = (double)i * 0.35;
            double d3 = 0.8;
            double d4;
            double d5;
            double d6;
            float f3;
            double d9;
            if ((this.entityRenderDispatcher.options == null || this.entityRenderDispatcher.options.getCameraType().isFirstPerson()) && player == Minecraft.getInstance().player) {
                d9 = 960.0 / (double)(Integer)this.entityRenderDispatcher.options.fov().get();
                Vec3 vec3 = this.entityRenderDispatcher.camera.getNearPlane().getPointOnPlane((float)i * 0.525F, -0.1F);
                vec3 = vec3.scale(d9);
                vec3 = vec3.yRot(f1 * 0.5F);
                vec3 = vec3.xRot(-f1 * 0.7F);
                d4 = Mth.lerp((double)p_114707_, player.xo, player.getX()) + vec3.x;
                d5 = Mth.lerp((double)p_114707_, player.yo, player.getY()) + vec3.y;
                d6 = Mth.lerp((double)p_114707_, player.zo, player.getZ()) + vec3.z;
                f3 = player.getEyeHeight();
            } else {
                d4 = Mth.lerp((double)p_114707_, player.xo, player.getX()) - d1 * d2 - d0 * 0.8;
                d5 = player.yo + (double)player.getEyeHeight() + (player.getY() - player.yo) * (double)p_114707_ - 0.45;
                d6 = Mth.lerp((double)p_114707_, player.zo, player.getZ()) - d0 * d2 + d1 * 0.8;
                f3 = player.isCrouching() ? -0.1875F : 0.0F;
            }

            d9 = Mth.lerp((double)p_114707_, p_114705_.xo, p_114705_.getX());
            double d10 = Mth.lerp((double)p_114707_, p_114705_.yo, p_114705_.getY()) + 0.25;
            double d8 = Mth.lerp((double)p_114707_, p_114705_.zo, p_114705_.getZ());
            float f4 = (float)(d4 - d9);
            float f5 = (float)(d5 - d10) + f3;
            float f6 = (float)(d6 - d8);
            VertexConsumer vertexconsumer1 = p_114709_.getBuffer(RenderType.lineStrip());
            PoseStack.Pose posestack$pose1 = p_114708_.last();

            for (int k = 0; k < 16; ++k) {
                float t0 = fraction(k, 16);
                float t1 = fraction(k + 1, 16);

                Color color0 = Color.getHSBColor(t0, 1.0f, 1.0f);
                Color color1 = Color.getHSBColor(t1, 1.0f, 1.0f);

                stringVertex(
                        f4, f5, f6,
                        vertexconsumer1,
                        posestack$pose1,
                        t0, t1,
                        color0.getRed(), color0.getGreen(), color0.getBlue(),
                        color1.getRed(), color1.getGreen(), color1.getBlue()
                );
            }

            p_114708_.popPose();
            super.render(p_114705_, p_114706_, p_114707_, p_114708_, p_114709_, p_114710_);
        }

    }

    private static float fraction(int p_114691_, int p_114692_) {
        return (float)p_114691_ / (float)p_114692_;
    }

    private static void vertex(VertexConsumer p_254464_, Matrix4f p_254085_, Matrix3f p_253962_, int p_254296_, float p_253632_, int p_254132_, int p_254171_, int p_254026_) {
        p_254464_.vertex(p_254085_, p_253632_ - 0.5F, (float)p_254132_ - 0.5F, 0.0F).color(255, 255, 255, 255).uv((float)p_254171_, (float)p_254026_).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(p_254296_).normal(p_253962_, 0.0F, 1.0F, 0.0F).endVertex();
    }

    private static void stringVertex(float dx, float dy, float dz, VertexConsumer consumer, PoseStack.Pose pose, float t0, float t1, int r0, int g0, int b0, int r1, int g1, int b1) {
        float x0 = dx * t0;
        float y0 = dy * (t0 * t0 + t0) * 0.5F + 0.25F;
        float z0 = dz * t0;

        float x1 = dx * t1;
        float y1 = dy * (t1 * t1 + t1) * 0.5F + 0.25F;
        float z1 = dz * t1;

        float nx = x1 - x0;
        float ny = y1 - y0;
        float nz = z1 - z0;
        float len = Mth.sqrt(nx * nx + ny * ny + nz * nz);
        if (len != 0) {
            nx /= len;
            ny /= len;
            nz /= len;
        }

        consumer.vertex(pose.pose(), x0, y0, z0)
                .color(r0, g0, b0, 255)
                .normal(pose.normal(), nx, ny, nz)
                .endVertex();

        consumer.vertex(pose.pose(), x1, y1, z1)
                .color(r1, g1, b1, 255)
                .normal(pose.normal(), nx, ny, nz)
                .endVertex();
    }

    public ResourceLocation getTextureLocation(RainbowFishingHook p_114703_) {
        return TEXTURE_LOCATION;
    }

    static {
        RENDER_TYPE = RenderType.entityCutout(TEXTURE_LOCATION);
    }
}