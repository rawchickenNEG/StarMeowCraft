package com.starmeow.smc.client.renderer;

import com.starmeow.smc.entities.projectiles.MagicArrow;
import com.starmeow.smc.entities.projectiles.RainbowArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MagicArrowRenderer extends ArrowRenderer<MagicArrow> {
    public static final ResourceLocation LOCATION = new ResourceLocation("smc:textures/entity/projectiles/rainbow_arrow.png");

    public MagicArrowRenderer(EntityRendererProvider.Context p_174399_) {
        super(p_174399_);
    }

    public ResourceLocation getTextureLocation(MagicArrow p_116001_) {
        return LOCATION;
    }

    protected int getBlockLightLevel(MagicArrow p_234560_, BlockPos p_234561_) {
        return 15;
    }
}