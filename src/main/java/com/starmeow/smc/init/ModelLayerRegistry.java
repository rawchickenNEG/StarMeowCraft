package com.starmeow.smc.init;

import com.starmeow.smc.client.model.ChickenHarvesterModel;
import com.starmeow.smc.client.model.CloudianModel;
import com.starmeow.smc.client.model.SpearModel;
import com.starmeow.smc.client.model.SwordAuraModel;
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
    }
}
