package com.starmeow.smc.init;

import com.starmeow.smc.StarMeowCraft;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ParticleRegistry {
    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, StarMeowCraft.MODID);

    public static final RegistryObject<SimpleParticleType> DETECTOR_MARK = PARTICLES.register("detector_mark", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SUNLIGHT = PARTICLES.register("sunlight", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SWORD_AURA = PARTICLES.register("sword_aura", () -> new SimpleParticleType(false));

}
