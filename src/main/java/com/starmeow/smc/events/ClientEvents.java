package com.starmeow.smc.events;

import com.mojang.logging.LogUtils;
import com.starmeow.smc.StarMeowCraft;
import com.starmeow.smc.config.Config;
import com.starmeow.smc.init.BlockRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = StarMeowCraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        LogUtils.getLogger().info("Meow~");

        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.POTTED_BROCCOLI.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.CALIBUR_BLOCK.get(), RenderType.cutout());
        if(Config.LAVA_TRANSPARENT.get()){
            ItemBlockRenderTypes.setRenderLayer(Fluids.LAVA, RenderType.translucent());
        }
    }
}
