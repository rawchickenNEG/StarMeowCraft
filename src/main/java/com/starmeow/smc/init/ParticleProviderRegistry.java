package com.starmeow.smc.init;

import com.starmeow.smc.StarMeowCraft;
import com.starmeow.smc.client.particles.DetectorMarkParticle;
import com.starmeow.smc.client.particles.SunlightParticle;
import com.starmeow.smc.client.particles.SwordAuraParticle;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ParticleProviderRegistry {
    @Mod.EventBusSubscriber(modid = StarMeowCraft.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onRegisterParticleProviders(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(ParticleRegistry.DETECTOR_MARK.get(), DetectorMarkParticle.Provider::new);
            event.registerSpriteSet(ParticleRegistry.SUNLIGHT.get(), SunlightParticle.Provider::new);
            event.registerSpriteSet(ParticleRegistry.SWORD_AURA.get(), SwordAuraParticle.Provider::new);

        }
    }
}
