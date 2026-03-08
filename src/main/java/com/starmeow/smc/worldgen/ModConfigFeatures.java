package com.starmeow.smc.worldgen;

import com.starmeow.smc.StarMeowCraft;
import com.starmeow.smc.init.BlockRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfigFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_FROSTIUM_ORE_KEY = registerKey("blue_frostium_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FROSTIUM_ORE_KEY = registerKey("frostium_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PERKIN_ORE_KEY = registerKey("perkin_ore");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest iceReplacables = new BlockMatchTest(Blocks.PACKED_ICE);
        RuleTest blueIceReplacables = new BlockMatchTest(Blocks.BLUE_ICE);

        register(context, BLUE_FROSTIUM_ORE_KEY, Feature.ORE, new OreConfiguration(blueIceReplacables,
                BlockRegistry.BLUE_ICE_FROSTIUM_ORE.get().defaultBlockState(), 8));

        register(context, FROSTIUM_ORE_KEY, Feature.ORE, new OreConfiguration(iceReplacables,
                BlockRegistry.FROSTIUM_ORE.get().defaultBlockState(), 8));

        List<OreConfiguration.TargetBlockState> overworldPerkinOres = List.of(OreConfiguration.target(stoneReplaceable,
                        BlockRegistry.PERKIN_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, BlockRegistry.DEEPSLATE_PERKIN_ORE.get().defaultBlockState()));
        register(context, PERKIN_ORE_KEY, Feature.ORE, new OreConfiguration(overworldPerkinOres, 8));

    }


    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(StarMeowCraft.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}