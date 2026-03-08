package com.starmeow.smc.worldgen;

import com.starmeow.smc.StarMeowCraft;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> BLUE_FROSTIUM_ORE_PLACED_KEY = registerKey("blue_frostium_ore_placed");
    public static final ResourceKey<PlacedFeature> FROSTIUM_ORE_PLACED_KEY = registerKey("frostium_ore_placed");
    public static final ResourceKey<PlacedFeature> PERKIN_ORE_PLACED_KEY = registerKey("perkin_ore_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, BLUE_FROSTIUM_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfigFeatures.BLUE_FROSTIUM_ORE_KEY),
                OrePlacement.commonOrePlacement(10,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(30), VerticalAnchor.absolute(62))));

        register(context, FROSTIUM_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfigFeatures.FROSTIUM_ORE_KEY),
                OrePlacement.commonOrePlacement(1,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(30), VerticalAnchor.absolute(62))));

        register(context, PERKIN_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfigFeatures.PERKIN_ORE_KEY),
                OrePlacement.commonOrePlacement(4,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(32))));
    }


    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(StarMeowCraft.MODID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}