package com.starmeow.smc.events;

import com.mojang.logging.LogUtils;
import com.starmeow.smc.StarMeowCraft;
import com.starmeow.smc.client.renderer.item.DivineHaloCuriosRenderer;
import com.starmeow.smc.client.renderer.item.DivineWingCuriosRenderer;
import com.starmeow.smc.client.renderer.item.FoxTailCuriosRenderer;
import com.starmeow.smc.client.renderer.item.RedFrameGlassesCuriosRenderer;
import com.starmeow.smc.config.Config;
import com.starmeow.smc.init.BlockRegistry;
import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@Mod.EventBusSubscriber(modid = StarMeowCraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        LogUtils.getLogger().info("Meow~");

        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.POTTED_BROCCOLI.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.CALIBUR_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.FROST_BERRY_BUSH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.THIN_CLOUD.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.THIN_CIRRUS_CLOUD.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.SALT_FISH_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.SALT_FISH_PIE_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.KNIFE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.CLEAVER.get(), RenderType.cutout());

        CuriosRendererRegistry.register(ItemRegistry.DIVINE_WING.get(), DivineWingCuriosRenderer::new);
        CuriosRendererRegistry.register(ItemRegistry.DIVINE_HALO.get(), DivineHaloCuriosRenderer::new);
        CuriosRendererRegistry.register(ItemRegistry.FOX_TAIL.get(), FoxTailCuriosRenderer::new);
        CuriosRendererRegistry.register(ItemRegistry.RED_FRAME_GLASSES.get(), RedFrameGlassesCuriosRenderer::new);
        CuriosRendererRegistry.register(ItemRegistry.CREATIVE_HALO.get(), DivineHaloCuriosRenderer::new);

        if(Config.LAVA_TRANSPARENT.get()){
            ItemBlockRenderTypes.setRenderLayer(Fluids.LAVA, RenderType.translucent());
        }
    }
}
