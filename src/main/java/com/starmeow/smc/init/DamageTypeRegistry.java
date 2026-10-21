package com.starmeow.smc.init;

import com.starmeow.smc.StarMeowCraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class DamageTypeRegistry {
    public static final ResourceKey<DamageType> CHOCLIZ_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(StarMeowCraft.MODID ,"chocliz"));
    public static final ResourceKey<DamageType> BANG_SMASH = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(StarMeowCraft.MODID ,"bang_smash"));

}
