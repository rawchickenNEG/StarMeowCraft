package com.starmeow.smc.client.renderer;

import com.starmeow.smc.client.model.ChickenHarvesterModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChickenHarvesterRenderer extends MobRenderer<Chicken, ChickenHarvesterModel<Chicken>> {
    private static final ResourceLocation LOCATION = new ResourceLocation("smc:textures/entity/chicken_harvester.png");

    public ChickenHarvesterRenderer(EntityRendererProvider.Context p_173952_) {
        super(p_173952_, new ChickenHarvesterModel<>(p_173952_.bakeLayer(ChickenHarvesterModel.LAYER_LOCATION)), 0.3F);
    }

    public ResourceLocation getTextureLocation(Chicken p_113998_) {
        return LOCATION;
    }

    protected float getBob(Chicken p_114000_, float p_114001_) {
        float $$2 = Mth.lerp(p_114001_, p_114000_.oFlap, p_114000_.flap);
        float $$3 = Mth.lerp(p_114001_, p_114000_.oFlapSpeed, p_114000_.flapSpeed);
        return (Mth.sin($$2) + 1.0F) * $$3;
    }
}
