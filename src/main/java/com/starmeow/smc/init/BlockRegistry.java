package com.starmeow.smc.init;

import com.starmeow.smc.StarMeowCraft;
import com.starmeow.smc.blocks.*;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, StarMeowCraft.MODID);

    public static final RegistryObject<Block> FROSTIUM_BLOCK = BLOCKS.register("frostium_block", () -> new CommonBlocks(Block.Properties.copy(Blocks.DIAMOND_BLOCK)));
    public static final RegistryObject<Block> FROSTIUM_ORE = BLOCKS.register("frostium_ore", () -> new DropExperienceBlock(Block.Properties.copy(Blocks.DIAMOND_ORE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> BLUE_ICE_FROSTIUM_ORE = BLOCKS.register("blue_ice_frostium_ore", () -> new DropExperienceBlock(Block.Properties.copy(Blocks.DIAMOND_ORE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> PERKIN_BLOCK = BLOCKS.register("perkin_block", () -> new CommonBlocks(Block.Properties.copy(Blocks.GOLD_BLOCK)));
    public static final RegistryObject<Block> PERKIN_ORE = BLOCKS.register("perkin_ore", () -> new DropExperienceBlock(Block.Properties.copy(Blocks.GOLD_ORE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DEEPSLATE_PERKIN_ORE = BLOCKS.register("deepslate_perkin_ore", () -> new DropExperienceBlock(Block.Properties.copy(Blocks.DEEPSLATE_GOLD_ORE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> BROCCOLI_PLANT_BLOCK = BLOCKS.register("broccoli_plant_block", () -> new BroccoliPlantBlock(Block.Properties.copy(Blocks.CARROTS)));
    public static final RegistryObject<Block> PEA_PLANT_BLOCK = BLOCKS.register("pea_plant_block", () -> new PeaPlantBlock(Block.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Block> SUNFLOWER_LIGHT_BLOCK = BLOCKS.register("sunflower_light_block", () -> new SunlightBlock(BlockBehaviour.Properties.of().replaceable().instabreak().noLootTable().noOcclusion().lightLevel((p_50856_) -> {
        return 15;
    })));
    public static final RegistryObject<Block> PEA_SHOOTER_POT_BLOCK = BLOCKS.register("pea_shooter_pot_block", () -> new PlantPotBlock(Block.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistryObject<Block> SUNFLOWER_POT_BLOCK = BLOCKS.register("sunflower_pot_block", () -> new PlantPotBlock(Block.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistryObject<Block> WALLNUT_POT_BLOCK = BLOCKS.register("wallnut_pot_block", () -> new PlantPotBlock(Block.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistryObject<Block> DELUXE_CAKE_BLOCK = BLOCKS.register("deluxe_cake_block", () -> new DeluxeCakeBlock(Block.Properties.copy(Blocks.CAKE)));

    public static final RegistryObject<Block> BROCCOLI_CRATE_BLOCK = BLOCKS.register("broccoli_crate", () -> new CommonBlocks(Block.Properties.copy(Blocks.OAK_WOOD)));

    public static final RegistryObject<Block> SALT_FISH_BLOCK = BLOCKS.register("salt_fish_block", () -> new SaltFishBlock(Block.Properties.copy(Blocks.NETHER_WART_BLOCK)));
    public static final RegistryObject<Block> DOLL_1 = BLOCKS.register("doll_bf_meow", () -> new DollBlocks(Block.Properties.copy(Blocks.WHITE_WOOL), SoundEvents.CAT_AMBIENT));
    public static final RegistryObject<Block> DOLL_2 = BLOCKS.register("doll_huacai", () -> new DollBlocks(Block.Properties.copy(Blocks.WHITE_WOOL), SoundEvents.FROG_AMBIENT));
    public static final RegistryObject<Block> DOLL_3 = BLOCKS.register("doll_raw_chicken", () -> new DollBlocks(Block.Properties.copy(Blocks.WHITE_WOOL), SoundEvents.PARROT_AMBIENT));
    public static final RegistryObject<Block> DOLL_4 = BLOCKS.register("doll_starjump", () -> new DollBlocks(Block.Properties.copy(Blocks.WHITE_WOOL), SoundEvents.FOX_AMBIENT));
    public static final RegistryObject<Block> WATER_DISPENSER = BLOCKS.register("water_dispenser", () -> new WaterDispenserBlock(Block.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> FRIDGE = BLOCKS.register("fridge", () -> new Fridge(Block.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> KNIFE = BLOCKS.register("knife", () -> new KnifeBlock(Block.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> GRANITE_ANVIL = BLOCKS.register("granite_anvil", () -> new GraniteAnvilBlock(Block.Properties.copy(Blocks.ANVIL).sound(SoundType.STONE)));
    public static final RegistryObject<Block> ANCIENT_SMITHING_TABLE = BLOCKS.register("ancient_smithing_table", () -> new AncientSmithingTable(Block.Properties.copy(Blocks.SMITHING_TABLE).sound(SoundType.NETHERITE_BLOCK)));
    public static final RegistryObject<Block> CALIBUR_BLOCK = BLOCKS.register("calibur_block", () -> new CaliburBlock(Block.Properties.copy(Blocks.BEDROCK).sound(SoundType.METAL)));
    
    public static final RegistryObject<Block> POTTED_BROCCOLI = BLOCKS.register("potted_broccoli", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, BlockRegistry.BROCCOLI_PLANT_BLOCK, BlockBehaviour.Properties.copy(Blocks.POTTED_DANDELION)));
    //public static final RegistryObject<Block> POTTED_LUCKY_CLOVER = BLOCKS.register("potted_broccoli", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, BlockRegistry.BROCCOLI_PLANT_BLOCK, BlockBehaviour.Properties.copy(Blocks.POTTED_DANDELION)));

    public static final RegistryObject<Block> FROST_PIE_BLOCK = BLOCKS.register("frost_pie_block", () -> new BasePieBlock(BlockBehaviour.Properties.copy(Blocks.CAKE), ItemRegistry.FROST_PIE_SLICE));

}
