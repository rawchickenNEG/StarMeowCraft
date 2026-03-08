package com.starmeow.smc.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.starmeow.smc.entities.ThrownSwordEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ThrownSwordRenderer extends EntityRenderer<ThrownSwordEntity> {
    private final ItemRenderer itemRenderer;
    private final float scale;
    private final boolean fullBright;

    public ThrownSwordRenderer(EntityRendererProvider.Context p_174416_, float p_174417_, boolean p_174418_) {
        super(p_174416_);
        this.itemRenderer = p_174416_.getItemRenderer();
        this.scale = p_174417_;
        this.fullBright = p_174418_;
    }

    public ThrownSwordRenderer(EntityRendererProvider.Context p_174414_) {
        this(p_174414_, 1.0F, false);
    }

    protected int getBlockLightLevel(ThrownSwordEntity p_116092_, BlockPos p_116093_) {
        return 15;
    }

    public void render(ThrownSwordEntity p_116085_, float p_116086_, float p_116087_, PoseStack p_116088_, MultiBufferSource p_116089_, int p_116090_) {
        if (p_116085_.tickCount >= 2 || !(this.entityRenderDispatcher.camera.getEntity().distanceToSqr(p_116085_) < 12.25)) {
            p_116088_.pushPose();
            p_116088_.scale(this.scale * 3, this.scale * 3, this.scale * 3);
            //From Goety SwordProjectileRenderer
            p_116088_.mulPose(Axis.YP.rotationDegrees(Mth.lerp(p_116087_, p_116085_.yRotO, p_116085_.getYRot()) - 90.0F));
            p_116088_.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(p_116087_, p_116085_.xRotO, p_116085_.getXRot()) - 45.0F));
            this.itemRenderer.renderStatic(((ItemSupplier)p_116085_).getItem(), ItemDisplayContext.GROUND, p_116090_, OverlayTexture.NO_OVERLAY, p_116088_, p_116089_, p_116085_.level(), p_116085_.getId());
            p_116088_.popPose();
            super.render(p_116085_, p_116086_, p_116087_, p_116088_, p_116089_, p_116090_);
        }
    }

    public ResourceLocation getTextureLocation(ThrownSwordEntity p_116083_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}