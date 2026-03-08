package com.starmeow.smc.client.renderer;

import com.starmeow.smc.entities.projectiles.FrostArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FrostArrowRenderer extends ArrowRenderer<FrostArrow> {
    public static final ResourceLocation LOCATION = new ResourceLocation("smc:textures/entity/projectiles/frost_arrow.png");

    public FrostArrowRenderer(EntityRendererProvider.Context p_174399_) {
        super(p_174399_);
    }

    public ResourceLocation getTextureLocation(FrostArrow p_116001_) {
        return LOCATION;
    }
}