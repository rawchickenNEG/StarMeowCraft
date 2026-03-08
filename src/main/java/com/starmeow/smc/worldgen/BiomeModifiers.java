package com.starmeow.smc.worldgen;

import com.starmeow.smc.StarMeowCraft;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

public class BiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_BLUE_FROSTIUM_ORE = registerKey("add_blue_frostium_ore");
    public static final ResourceKey<BiomeModifier> ADD_FROSTIUM_ORE = registerKey("add_frostium_ore");
    public static final ResourceKey<BiomeModifier> ADD_PERKIN_ORE = registerKey("add_perkin_ore");

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_FROSTIUM_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OCEAN),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.FROSTIUM_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_BLUE_FROSTIUM_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OCEAN),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.BLUE_FROSTIUM_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_PERKIN_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_TAIGA),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.PERKIN_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(StarMeowCraft.MODID, name));
    }
}
