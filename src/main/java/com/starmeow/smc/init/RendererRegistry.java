package com.starmeow.smc.init;

import com.starmeow.smc.client.renderer.*;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RendererRegistry {

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityTypeRegistry.GRIMOIRE_BULLET.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.PEA_BULLET.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.COBBLE_BULLET.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.DETECTOR_MARK.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.THROWN_SPORE_BUD.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.THROWN_SPEAR.get(), SpearRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.FROST_ARROW.get(), FrostArrowRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.RAINBOW_ARROW.get(), RainbowArrowRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.MAGIC_ARROW.get(), MagicArrowRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.SALT_FISH.get(), FishRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.EASTER_BUNNY.get(), EasterBunnyRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.BROCCOLI_FISHING_HOOK.get(), BroccoliFishingHookRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.RAINBOW_FISHING_HOOK.get(), RainbowFishingHookRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.SPEAR_HOOK.get(), SpearHookRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.THROWN_SWORD.get(), ThrownSwordRenderer::new);
    }

}