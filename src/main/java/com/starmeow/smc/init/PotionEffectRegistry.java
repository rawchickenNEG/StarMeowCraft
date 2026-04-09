package com.starmeow.smc.init;

import com.starmeow.smc.StarMeowCraft;
import com.starmeow.smc.mobeffects.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PotionEffectRegistry {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, StarMeowCraft.MODID);
    public static final DeferredRegister<Potion> POTION = DeferredRegister.create(ForgeRegistries.POTIONS, StarMeowCraft.MODID);
    public static final RegistryObject<MobEffect> STUNNED = MOB_EFFECTS.register("stunned", () -> new StunnedEffect(MobEffectCategory.HARMFUL, -3355648));
    public static final RegistryObject<MobEffect> LOVE = MOB_EFFECTS.register("love", () -> new LoveEffect(MobEffectCategory.BENEFICIAL, -26113));
    public static final RegistryObject<MobEffect> FROST = MOB_EFFECTS.register("frost", () -> new FrostEffect(MobEffectCategory.HARMFUL, -10053121));
    public static final RegistryObject<MobEffect> FROST_BURST = MOB_EFFECTS.register("frost_burst", () -> new FrostEffect(MobEffectCategory.HARMFUL, -100661778));
    public static final RegistryObject<MobEffect> EXTENSION = MOB_EFFECTS.register("extension", () -> new ExtensionEffect(MobEffectCategory.BENEFICIAL, -3407872));
    public static final RegistryObject<MobEffect> RAINBOW = MOB_EFFECTS.register("rainbow", () -> new RainbowEffect(MobEffectCategory.BENEFICIAL, 16262179));
    public static final RegistryObject<MobEffect> FROST_RESISTANCE = MOB_EFFECTS.register("frost_resistance", () -> new FrostResistanceEffect(MobEffectCategory.BENEFICIAL, -6684673));
    public static final RegistryObject<MobEffect> POISON_RESISTANCE = MOB_EFFECTS.register("poison_resistance", () -> new PoisonResistanceEffect(MobEffectCategory.BENEFICIAL, -6684775));
    public static final RegistryObject<MobEffect> STAR_LIGHT = MOB_EFFECTS.register("star_light", () -> new NoAttributeEffects(MobEffectCategory.BENEFICIAL, -13159));
    public static final RegistryObject<MobEffect> CHOP_PROTECTION = MOB_EFFECTS.register("chop_protection", () -> new NoAttributeEffects(MobEffectCategory.BENEFICIAL, -13159));
    public static final RegistryObject<MobEffect> PEACE = MOB_EFFECTS.register("peace", () -> new PeaceEffect(MobEffectCategory.BENEFICIAL, -13159));
    public static final RegistryObject<MobEffect> NO_PEACE = MOB_EFFECTS.register("no_peace", () -> new NoAttributeEffects(MobEffectCategory.NEUTRAL, -13159));
    public static final RegistryObject<MobEffect> ELBOWING = MOB_EFFECTS.register("elbow_strike", () -> new ElbowingEffect(MobEffectCategory.BENEFICIAL, 16762624));


}
