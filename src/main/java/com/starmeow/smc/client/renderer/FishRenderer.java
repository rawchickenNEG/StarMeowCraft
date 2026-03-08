package com.starmeow.smc.client.renderer;

import com.starmeow.smc.entities.SaltFish;
import net.minecraft.client.model.CodModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FishRenderer extends MobRenderer<SaltFish, CodModel<SaltFish>> {

    public FishRenderer(EntityRendererProvider.Context context) {
        super(context, new CodModel<>(context.bakeLayer(ModelLayers.COD)), 0.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(SaltFish p_114482_) {
        return new ResourceLocation("smc:textures/entity/fish_entity.png");
    }
}