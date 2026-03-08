package com.starmeow.smc.client.renderer;

import com.starmeow.smc.entities.projectiles.RainbowArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RainbowArrowRenderer extends ArrowRenderer<RainbowArrow> {
    public static final ResourceLocation LOCATION = new ResourceLocation("smc:textures/entity/projectiles/rainbow_arrow.png");

    public RainbowArrowRenderer(EntityRendererProvider.Context p_174399_) {
        super(p_174399_);
    }

    public ResourceLocation getTextureLocation(RainbowArrow p_116001_) {
        return LOCATION;
    }

    protected int getBlockLightLevel(RainbowArrow p_234560_, BlockPos p_234561_) {
        return 15;
    }
}