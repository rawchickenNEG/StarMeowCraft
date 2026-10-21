package com.starmeow.smc.init;

import com.starmeow.smc.client.model.*;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class ModelLayerRegistry {
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(SpearModel.LAYER_LOCATION, SpearModel::createBodyLayer);
        event.registerLayerDefinition(ChickenHarvesterModel.LAYER_LOCATION, ChickenHarvesterModel::createBodyLayer);
        event.registerLayerDefinition(SwordAuraModel.LAYER_LOCATION, SwordAuraModel::createBodyLayer);
        event.registerLayerDefinition(CloudianModel.LAYER_LOCATION, CloudianModel::createBodyLayer);
        event.registerLayerDefinition(DivineWingModel.LAYER_LOCATION, DivineWingModel::createBodyLayer);
        event.registerLayerDefinition(FoxTailModel.LAYER_LOCATION, FoxTailModel::createBodyLayer);
        event.registerLayerDefinition(RedFrameGlassesModel.LAYER_LOCATION, RedFrameGlassesModel::createArmorLayer);
        event.registerLayerDefinition(DivineHaloModel.LAYER_LOCATION, DivineHaloModel::createArmorLayer);
        event.registerLayerDefinition(SpearHookModel.LAYER_LOCATION, SpearHookModel::createBodyLayer);
        event.registerLayerDefinition(GiantRuyiJinguBangModel.LAYER_LOCATION, GiantRuyiJinguBangModel::createBodyLayer);

    }
}
