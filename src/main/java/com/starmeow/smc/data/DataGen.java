package com.starmeow.smc.data;

import com.starmeow.smc.StarMeowCraft;
import com.starmeow.smc.init.BlockRegistry;
import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen {

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        generator.addProvider(event.includeServer(), new RecipeGenerator(output));
        generator.addProvider(event.includeClient(), new ModelProvider(generator, helper));
        generator.addProvider(event.includeClient(), new StateProvider(generator, helper));
        BlocktagProvider blockTagGenerator =generator.addProvider(event.includeClient(), new BlocktagProvider(output, lookupProvider, helper));
        generator.addProvider(event.includeClient(), new ItemtagProvider(output, lookupProvider, blockTagGenerator.contentsGetter(), helper));
        generator.addProvider(event.includeServer(), new WorldgenProvider(output, lookupProvider));
    }

    private static LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();
    static {
        trimMaterials.put(TrimMaterials.QUARTZ, 0.1F);
        trimMaterials.put(TrimMaterials.IRON, 0.2F);
        trimMaterials.put(TrimMaterials.NETHERITE, 0.3F);
        trimMaterials.put(TrimMaterials.REDSTONE, 0.4F);
        trimMaterials.put(TrimMaterials.COPPER, 0.5F);
        trimMaterials.put(TrimMaterials.GOLD, 0.6F);
        trimMaterials.put(TrimMaterials.EMERALD, 0.7F);
        trimMaterials.put(TrimMaterials.DIAMOND, 0.8F);
        trimMaterials.put(TrimMaterials.LAPIS, 0.9F);
        trimMaterials.put(TrimMaterials.AMETHYST, 1.0F);
    }

    public static class ModelProvider extends ItemModelProvider {
        public ModelProvider(DataGenerator gen, ExistingFileHelper helper) {
            super(gen.getPackOutput(), StarMeowCraft.MODID, helper);
        }
        @Override
        protected void registerModels() {
            ArrayList<Object> list = new ArrayList<>();
            list.add(String.valueOf(ItemRegistry.BROCCOLI.get()));
            list.add(String.valueOf(ItemRegistry.CHOCOLATE.get()));
            list.add(String.valueOf(ItemRegistry.GOLDEN_BROCCOLI.get()));
            list.add(String.valueOf(ItemRegistry.FROSTIUM_INGOT.get()));
            list.add(String.valueOf(ItemRegistry.FROSTIUM_BOOTS.get()));
            list.add(String.valueOf(ItemRegistry.FROSTIUM_HELMET.get()));
            list.add(String.valueOf(ItemRegistry.STAR_DUST.get()));
            list.add(String.valueOf(ItemRegistry.BROCCOLI_BOOTS.get()));
            list.add(String.valueOf(ItemRegistry.ICE_TEA.get()));
            list.add(String.valueOf(ItemRegistry.MUSIC_DISC_LAODA.get()));
            list.add(String.valueOf(ItemRegistry.FROSTIUM_NUGGET.get()));
            list.add(String.valueOf(ItemRegistry.PERKIN_INGOT.get()));
            list.add(String.valueOf(ItemRegistry.PERKIN_NUGGET.get()));
            list.add(String.valueOf(ItemRegistry.PEA.get()));
            list.add(String.valueOf(ItemRegistry.PEA_POD.get()));
            list.add(String.valueOf(ItemRegistry.RAW_PERKIN.get()));
            list.add(String.valueOf(ItemRegistry.RAW_FROSTIUM.get()));
            list.add(String.valueOf(ItemRegistry.PEA_SOUP.get()));
            list.add(String.valueOf(ItemRegistry.BRAISED_BEEF_WITH_PEAS.get()));
            list.add(String.valueOf(ItemRegistry.STEAMED_PEA_FLOUR_CAKE.get()));
            list.add(String.valueOf(ItemRegistry.BOILED_BROCCOLI.get()));
            list.add(String.valueOf(ItemRegistry.LIGHT_MEAL.get()));
            list.add(String.valueOf(ItemRegistry.CREAM_BROCCOLI.get()));
            list.add(String.valueOf(ItemRegistry.CHICKEN_CHOP.get()));
            list.add(String.valueOf(ItemRegistry.BROCCOLI_SEED.get()));
            list.add(String.valueOf(ItemRegistry.SNIFFER_BEAK.get()));
            list.add(String.valueOf(ItemRegistry.SALT_FISH.get()));
            list.add(String.valueOf(ItemRegistry.SALT_FISH_NUGGET.get()));
            list.add(String.valueOf(ItemRegistry.FIRE_EXTINGUISHER.get()));
            list.add(String.valueOf(ItemRegistry.VACUUM_SNIFFER.get()));
            list.add(String.valueOf(ItemRegistry.GRASS_BLOCK_PIE.get()));
            list.add(String.valueOf(ItemRegistry.COOKED_DIRT.get()));
            list.add(String.valueOf(ItemRegistry.ASTERA_APPLE.get()));
            list.add(String.valueOf(ItemRegistry.ASTERA_JERKY.get()));
            list.add(String.valueOf(ItemRegistry.ASTERA_PUDDING.get()));
            list.add(String.valueOf(ItemRegistry.FROST_BERRIES.get()));
            list.add(String.valueOf(ItemRegistry.LUCKY_CLOVER.get()));
            list.add(String.valueOf(ItemRegistry.CREATIVE_CLOVER.get()));
            list.add(String.valueOf(ItemRegistry.ORE_DETECTOR.get()));
            list.add(String.valueOf(ItemRegistry.JERKY.get()));
            list.add(String.valueOf(ItemRegistry.SPICY_STRIPS.get()));
            list.add(String.valueOf(ItemRegistry.SPICY_BAR.get()));
            list.add(String.valueOf(ItemRegistry.PERFROSTITE_INGOT.get()));
            list.add(String.valueOf(ItemRegistry.PERFROSTITE_UPGRADE_SCROLL.get()));
            list.add(String.valueOf(ItemRegistry.SPORE_BUD.get()));
            list.add(String.valueOf(ItemRegistry.LUCKY_NUGGET.get()));
            list.add(String.valueOf(ItemRegistry.FROST_ARROW.get()));
            list.add(String.valueOf(ItemRegistry.FROST_EYE.get()));
            list.add(String.valueOf(ItemRegistry.PERKIN_STAR.get()));
            list.add(String.valueOf(ItemRegistry.MINI_BEDROCK.get()));
            list.add(String.valueOf(ItemRegistry.BOT.get()));
            list.add(String.valueOf(ItemRegistry.REDSTONE_ICE_CREAM.get()));
            list.add(String.valueOf(ItemRegistry.DELUXE_CAKE.get()));
            list.add(String.valueOf(ItemRegistry.RAINBOW_CHIP.get()));
            list.add(String.valueOf(ItemRegistry.RAINBOW_COOKIE.get()));
            list.add(String.valueOf(ItemRegistry.HIGH_CALCIUM_MILK.get()));
            list.add(String.valueOf(ItemRegistry.GOOGOO_STEW.get()));
            list.add(String.valueOf(ItemRegistry.GUAGUA_JELLY.get()));
            list.add(String.valueOf(ItemRegistry.MUSIC_DISC_NYAN_CAT.get()));
            list.add(String.valueOf(ItemRegistry.CHOCOLATE_HELMET.get()));
            list.add(String.valueOf(ItemRegistry.CHOCOLATE_CHESTPLATE.get()));
            list.add(String.valueOf(ItemRegistry.CHOCOLATE_LEGGINGS.get()));
            list.add(String.valueOf(ItemRegistry.CHOCOLATE_BOOTS.get()));
            list.add(String.valueOf(ItemRegistry.DELUXE_CAKE_SLICE.get()));
            list.add(String.valueOf(ItemRegistry.FROST_PIE_SLICE.get()));
            list.add(String.valueOf(ItemRegistry.STAR_CANDY.get()));
            list.add(String.valueOf(ItemRegistry.FROST_CANDY.get()));
            list.add(String.valueOf(ItemRegistry.BROCCOLI_CANDY.get()));
            list.add(String.valueOf(ItemRegistry.ZONGZI.get()));
            list.add(String.valueOf(ItemRegistry.BOWL_OF_HOT_WATER.get()));
            list.add(String.valueOf(ItemRegistry.BOWL_OF_WATER.get()));
            list.add(String.valueOf(ItemRegistry.GOLDEN_BOAT.get()));
            list.add(String.valueOf(ItemRegistry.GOLDEN_TRANSPARENT_BOAT.get()));
            list.add(String.valueOf(ItemRegistry.GOLDEN_CHEST_BOAT.get()));
            list.add(String.valueOf(ItemRegistry.END_BOAT.get()));
            list.add(String.valueOf(ItemRegistry.END_CHEST_BOAT.get()));
            list.add(String.valueOf(ItemRegistry.COFFEE.get()));
            list.add(String.valueOf(ItemRegistry.TEMPLATE_SHROUD.get()));
            list.add(String.valueOf(ItemRegistry.IRON_ARMOR_TRIM_SMITHING_TEMPLATE.get()));
            list.add(String.valueOf(ItemRegistry.NETHERITE_ARMOR_TRIM_SMITHING_TEMPLATE.get()));
            list.add(String.valueOf(ItemRegistry.CHAINMAIL_ARMOR_TRIM_SMITHING_TEMPLATE.get()));
            list.add(String.valueOf(ItemRegistry.DIVINE_WING.get()));
            list.add(String.valueOf(ItemRegistry.BAD_APPLE.get()));
            list.add(String.valueOf(ItemRegistry.MUSIC_DISC_BAD_APPLE.get()));
            list.add(String.valueOf(ItemRegistry.SPICY_FIRED_RICE.get()));
            list.add(String.valueOf(ItemRegistry.SWEET_FRESH_SALAD.get()));
            list.add(String.valueOf(ItemRegistry.TREASURE_DETECTOR.get()));
            list.add(String.valueOf(ItemRegistry.MUSIC_DISC_GOD_PICKAXE.get()));
            list.add(String.valueOf(ItemRegistry.ASTERA_CAKE.get()));
            list.add(String.valueOf(ItemRegistry.ASTERA_CAKE_SLICE.get()));
            list.add(String.valueOf(ItemRegistry.BROCCOLI_CAKE.get()));
            list.add(String.valueOf(ItemRegistry.BROCCOLI_CAKE_SLICE.get()));
            list.add(String.valueOf(ItemRegistry.FROST_CAKE.get()));
            list.add(String.valueOf(ItemRegistry.FROST_CAKE_SLICE.get()));
            list.add(String.valueOf(ItemRegistry.RAINBOW_CAKE.get()));
            list.add(String.valueOf(ItemRegistry.RAINBOW_CAKE_SLICE.get()));
            list.add(String.valueOf(ItemRegistry.ASTERA_ICE_CREAM.get()));
            list.add(String.valueOf(ItemRegistry.BROCCOLI_ICE_CREAM.get()));
            list.add(String.valueOf(ItemRegistry.FROST_ICE_CREAM.get()));
            list.add(String.valueOf(ItemRegistry.RAINBOW_ICE_CREAM.get()));
            list.add(String.valueOf(ItemRegistry.SMC_ICE_CREAM.get()));
            list.add(String.valueOf(ItemRegistry.ASTERA_MILKSHAKE.get()));
            list.add(String.valueOf(ItemRegistry.BROCCOLI_MILKSHAKE.get()));
            list.add(String.valueOf(ItemRegistry.FROST_MILKSHAKE.get()));
            list.add(String.valueOf(ItemRegistry.RAINBOW_MILKSHAKE.get()));
            list.add(String.valueOf(ItemRegistry.ONEO.get()));
            list.add(String.valueOf(ItemRegistry.TNET.get()));
            list.add(String.valueOf(ItemRegistry.OREEPER.get()));
            list.add(String.valueOf(ItemRegistry.BROCCOLI_BOMB.get()));
            list.add(String.valueOf(ItemRegistry.ICON_FOOD.get()));
            list.add(String.valueOf(ItemRegistry.ICON_BLOCK.get()));
            list.add(String.valueOf(ItemRegistry.ICON_COMPAT.get()));
            list.add(String.valueOf(ItemRegistry.ICON_GEAR.get()));
            list.add(String.valueOf(ItemRegistry.SALT_FISH_ROLL.get()));
            list.add(String.valueOf(ItemRegistry.SALT_FISH_PIE_SLICE.get()));
            list.add(String.valueOf(ItemRegistry.COOKED_SALT_FISH.get()));
            list.add(String.valueOf(ItemRegistry.RAW_SALT_FISH_SLICE.get()));
            list.add(String.valueOf(ItemRegistry.COOKED_SALT_FISH_SLICE.get()));
            list.add(String.valueOf(ItemRegistry.SALT_FISH_STEW.get()));
            list.add(String.valueOf(ItemRegistry.FISH_COOKIE.get()));
            list.add(String.valueOf(ItemRegistry.EASTER_BUNNY_EGG.get()));
            list.add(String.valueOf(ItemRegistry.CHICKEN_HARVESTER_EGG.get()));
            list.add(String.valueOf(ItemRegistry.FRIED_EASTER_BUNNY_EGG.get()));
            list.add(String.valueOf(ItemRegistry.FRIED_CHICKEN_HARVESTER_EGG.get()));
            list.add(String.valueOf(ItemRegistry.EASTER_BUNNY_EGG_SANDWICH.get()));
            list.add(String.valueOf(ItemRegistry.CHICKEN_HARVESTER_EGG_SANDWICH.get()));
            list.add(String.valueOf(ItemRegistry.SALT_FISH_BUCKET.get()));
            list.add(String.valueOf(ItemRegistry.CHICKEN_HARVESTER_SPAWN_EGG.get()));
            list.add(String.valueOf(ItemRegistry.CLOUDIAN_SPAWN_EGG.get()));
            list.add(String.valueOf(ItemRegistry.EASTER_BUNNY_SPAWN_EGG.get()));
            list.add(String.valueOf(ItemRegistry.SALT_FISH_SPAWN_EGG.get()));
            list.add(String.valueOf(ItemRegistry.GIANT_CARROT.get()));
            list.add(String.valueOf(ItemRegistry.DIVINE_HALO.get()));
            list.add(String.valueOf(ItemRegistry.DIVINE_SHARD.get()));
            list.add(String.valueOf(ItemRegistry.CHICKEN_TAIL_FEATHER.get()));
            list.add(String.valueOf(ItemRegistry.DANDELION_SALAD.get()));
            list.add(String.valueOf(ItemRegistry.SNOW_GOLEM_ICE_CREAM.get()));
            list.add(String.valueOf(ItemRegistry.RAW_ZINC.get()));
            list.add(String.valueOf(ItemRegistry.ZINC_INGOT.get()));
            list.add(String.valueOf(ItemRegistry.ZINC_NUGGET.get()));
            list.add(String.valueOf(ItemRegistry.FOX_BOOTS.get()));
            list.add(String.valueOf(ItemRegistry.FOX_HELMET.get()));
            list.add(String.valueOf(ItemRegistry.FOX_LEGGINGS.get()));
            list.add(String.valueOf(ItemRegistry.FOX_CHESTPLATE.get()));
            list.add(String.valueOf(ItemRegistry.SNOW_FOX_BOOTS.get()));
            list.add(String.valueOf(ItemRegistry.SNOW_FOX_HELMET.get()));
            list.add(String.valueOf(ItemRegistry.SNOW_FOX_LEGGINGS.get()));
            list.add(String.valueOf(ItemRegistry.SNOW_FOX_CHESTPLATE.get()));
            list.add(String.valueOf(ItemRegistry.CHOP_ARMOR_TRIM_SMITHING_TEMPLATE.get()));
            list.add(String.valueOf(ItemRegistry.BATTERY.get()));
            list.add(String.valueOf(ItemRegistry.SUPER_BONEMEAL.get()));
            list.add(String.valueOf(ItemRegistry.FROSTIUM_SHACKLES.get()));
            list.add(String.valueOf(ItemRegistry.FOX_TAIL.get()));
            list.add(String.valueOf(ItemRegistry.BLOOM_CAKE.get()));
            list.add(String.valueOf(ItemRegistry.FOX_FUR.get()));
            list.add(String.valueOf(ItemRegistry.SNOW_FOX_FUR.get()));
            list.add(String.valueOf(ItemRegistry.PURE_FOX_FUR.get()));
            list.add(String.valueOf(ItemRegistry.INFINITE_FUEL.get()));
            list.add(String.valueOf(ItemRegistry.RED_FRAME_GLASSES.get()));
            list.add(String.valueOf(ItemRegistry.PUMPKIN_PILE.get()));
            list.add(String.valueOf(ItemRegistry.MELON_HALF.get()));
            list.add(String.valueOf(ItemRegistry.BOWL_OF_GQKJ_CHAR_SIU_PORK_KNUCKLE_RICE.get()));
            list.add(String.valueOf(ItemRegistry.GQKJ_CHAR_SIU_PORK_KNUCKLE_RICE.get()));
            for (Object o : list) {
                this.singleTexture(o.toString(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(StarMeowCraft.MODID, "item/" + o));
            }

            ArrayList<Object> listtools = new ArrayList<>();
            listtools.add(String.valueOf(ItemRegistry.FROSTIUM_AXE.get()));
            listtools.add(String.valueOf(ItemRegistry.FROSTIUM_SWORD.get()));
            listtools.add(String.valueOf(ItemRegistry.FROSTIUM_PICKAXE.get()));
            listtools.add(String.valueOf(ItemRegistry.GRIMOIRE.get()));
            listtools.add(String.valueOf(ItemRegistry.BROCCOLI_HOE.get()));
            listtools.add(String.valueOf(ItemRegistry.ARCHAEOLOGICAL_SHOVEL.get()));
            listtools.add(String.valueOf(ItemRegistry.PERFROSTITE_AXE.get()));
            listtools.add(String.valueOf(ItemRegistry.PERFROSTITE_SWORD.get()));
            listtools.add(String.valueOf(ItemRegistry.PERFROSTITE_PICKAXE.get()));
            listtools.add(String.valueOf(ItemRegistry.PERFROSTITE_SHOVEL.get()));
            listtools.add(String.valueOf(ItemRegistry.PERFROSTITE_HOE.get()));
            listtools.add(String.valueOf(ItemRegistry.PERKIN_AXE.get()));
            listtools.add(String.valueOf(ItemRegistry.PERKIN_PICKAXE.get()));
            listtools.add(String.valueOf(ItemRegistry.PERKIN_SHOVEL.get()));
            listtools.add(String.valueOf(ItemRegistry.PERKIN_HOE.get()));
            listtools.add(String.valueOf(ItemRegistry.MCR_SWORD.get()));
            listtools.add(String.valueOf(ItemRegistry.CARROT_PICKAXE.get()));
            listtools.add(String.valueOf(ItemRegistry.GOLDEN_CARROT_PICKAXE.get()));
            listtools.add(String.valueOf(ItemRegistry.CHOCOLATE_SWORD.get()));
            listtools.add(String.valueOf(ItemRegistry.END_ROD_CANDY.get()));
            listtools.add(String.valueOf(ItemRegistry.COMMAND_BLOCK_WAND.get()));
            listtools.add(String.valueOf(ItemRegistry.GOD_PICKAXE.get()));
            listtools.add(String.valueOf(ItemRegistry.ZINC_AXE.get()));
            listtools.add(String.valueOf(ItemRegistry.ZINC_SWORD.get()));
            listtools.add(String.valueOf(ItemRegistry.ZINC_PICKAXE.get()));
            listtools.add(String.valueOf(ItemRegistry.ZINC_SHOVEL.get()));
            listtools.add(String.valueOf(ItemRegistry.ZINC_HOE.get()));
            listtools.add(String.valueOf(ItemRegistry.CHOCLIZ.get()));
            listtools.add(String.valueOf(ItemRegistry.CHOCLIZ_BERRY.get()));
            listtools.add(String.valueOf(ItemRegistry.RAW_TOMAHAWK_STEAK.get()));
            listtools.add(String.valueOf(ItemRegistry.RAW_MINT_TOMAHAWK_STEAK.get()));
            listtools.add(String.valueOf(ItemRegistry.TOMAHAWK_STEAK.get()));
            listtools.add(String.valueOf(ItemRegistry.MINT_TOMAHAWK_STEAK.get()));

            for (Object o : listtools) {
                this.singleTexture(o.toString(), new ResourceLocation("item/handheld"), "layer0", new ResourceLocation(StarMeowCraft.MODID, "item/" + o));
            }

            trimmedArmorItem(ItemRegistry.BROCCOLI_BOOTS);
            trimmedArmorItem(ItemRegistry.FROSTIUM_HELMET);
            trimmedArmorItem(ItemRegistry.FROSTIUM_BOOTS);
            trimmedArmorItem(ItemRegistry.CHOCOLATE_BOOTS);
            trimmedArmorItem(ItemRegistry.CHOCOLATE_CHESTPLATE);
            trimmedArmorItem(ItemRegistry.CHOCOLATE_LEGGINGS);
            trimmedArmorItem(ItemRegistry.CHOCOLATE_HELMET);
            //this.withExistingParent(ItemRegistry.EASTER_BUNNY_SPAWN_EGG.get().toString(), mcLoc("item/template_spawn_egg"));
            //this.withExistingParent(ItemRegistry.CLOUDIAN_SPAWN_EGG.get().toString(), mcLoc("item/template_spawn_egg"));
            //this.withExistingParent(ItemRegistry.CHICKEN_HARVESTER_SPAWN_EGG.get().toString(), mcLoc("item/template_spawn_egg"));
        }

        // Shoutout to El_Redstoniano for making this
        private void trimmedArmorItem(RegistryObject<Item> itemRegistryObject) {
            final String MOD_ID = StarMeowCraft.MODID; // Change this to your mod id

            if(itemRegistryObject.get() instanceof ArmorItem armorItem) {
                trimMaterials.entrySet().forEach(entry -> {

                    ResourceKey<TrimMaterial> trimMaterial = entry.getKey();
                    float trimValue = entry.getValue();

                    String armorType = switch (armorItem.getEquipmentSlot()) {
                        case HEAD -> "helmet";
                        case CHEST -> "chestplate";
                        case LEGS -> "leggings";
                        case FEET -> "boots";
                        default -> "";
                    };

                    String armorItemPath = "item/" + armorItem;
                    String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
                    String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                    ResourceLocation armorItemResLoc = new ResourceLocation(MOD_ID, armorItemPath);
                    ResourceLocation trimResLoc = new ResourceLocation(trimPath); // minecraft namespace
                    ResourceLocation trimNameResLoc = new ResourceLocation(MOD_ID, currentTrimName);

                    // This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will
                    // avoid an IllegalArgumentException
                    existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                    // Trimmed armorItem files
                    getBuilder(currentTrimName)
                            .parent(new ModelFile.UncheckedModelFile("item/generated"))
                            .texture("layer0", armorItemResLoc)
                            .texture("layer1", trimResLoc);

                    // Non-trimmed armorItem file (normal variant)
                    this.withExistingParent(itemRegistryObject.getId().getPath(),
                                    mcLoc("item/generated"))
                            .override()
                            .model(new ModelFile.UncheckedModelFile(trimNameResLoc))
                            .predicate(mcLoc("trim_type"), trimValue).end()
                            .texture("layer0",
                                    new ResourceLocation(MOD_ID,
                                            "item/" + itemRegistryObject.getId().getPath()));
                });
            }
        }
    }



    public static class StateProvider extends BlockStateProvider {
        public StateProvider(DataGenerator gen, ExistingFileHelper helper) {
            super(gen.getPackOutput(), StarMeowCraft.MODID, helper);
        }
        @Override
        protected void registerStatesAndModels() {
            ArrayList<Block> listblock = new ArrayList<>();
            listblock.add(BlockRegistry.FROSTIUM_BLOCK.get());
            listblock.add(BlockRegistry.PERKIN_BLOCK.get());
            listblock.add(BlockRegistry.CLOUD.get());
            listblock.add(BlockRegistry.CIRRUS_CLOUD.get());
            listblock.add(BlockRegistry.THIN_CLOUD.get());
            listblock.add(BlockRegistry.THIN_CIRRUS_CLOUD.get());
            listblock.add(BlockRegistry.CLOUD_FLOOR.get());
            listblock.add(BlockRegistry.CLOUD_BRICKS.get());
            listblock.add(BlockRegistry.ZINC_BLOCK.get());
            listblock.add(BlockRegistry.ZINC_ORE.get());
            listblock.add(BlockRegistry.TUFF_ZINC_ORE.get());
            listblock.add(BlockRegistry.END_ZINC_ORE.get());
            for (Block o : listblock) {
                this.simpleBlockWithItem(o, this.cubeAll(o));
            }
            this.crateBlock(BlockRegistry.BROCCOLI_CRATE_BLOCK.get(), this.itemName(ItemRegistry.BROCCOLI.get()));
            this.rotatePillarBlock((RotatedPillarBlock) BlockRegistry.CLOUD_PILLAR.get());
            this.stairBlock((StairBlock) BlockRegistry.CLOUD_STAIRS.get(), BlockRegistry.CLOUD.get());
            this.slabBlock((SlabBlock) BlockRegistry.CLOUD_SLAB.get(), BlockRegistry.CLOUD.get());
            this.wallBlock((WallBlock) BlockRegistry.CLOUD_WALL.get(), BlockRegistry.CLOUD.get());
            this.stairBlock((StairBlock) BlockRegistry.CLOUD_FLOOR_STAIRS.get(), BlockRegistry.CLOUD_FLOOR.get());
            this.slabBlock((SlabBlock) BlockRegistry.CLOUD_FLOOR_SLAB.get(), BlockRegistry.CLOUD_FLOOR.get());
            this.wallBlock((WallBlock) BlockRegistry.CLOUD_FLOOR_WALL.get(), BlockRegistry.CLOUD_FLOOR.get());
            this.stairBlock((StairBlock) BlockRegistry.CLOUD_BRICKS_STAIRS.get(), BlockRegistry.CLOUD_BRICKS.get());
            this.slabBlock((SlabBlock) BlockRegistry.CLOUD_BRICKS_SLAB.get(), BlockRegistry.CLOUD_BRICKS.get());
            this.wallBlock((WallBlock) BlockRegistry.CLOUD_BRICKS_WALL.get(), BlockRegistry.CLOUD_BRICKS.get());
            this.topSideBottomBlock(BlockRegistry.BROCCOLI_NUKE.get());

        }

        public void crateBlock(Block block, String cropName) {
            this.simpleBlockWithItem(block, this.models().cubeBottomTop(this.blockName(block), this.resourceBlock(cropName + "_crate_side"), this.resourceBlock(cropName + "_crate_bottom"), this.resourceBlock(cropName + "_crate_top")));
        }

        public void topSideBottomBlock(Block block) {
            this.simpleBlockWithItem(block, this.models().cubeBottomTop(this.blockName(block), this.resourceBlock(this.blockName(block) + "_side"), this.resourceBlock(this.blockName(block) + "_bottom"), this.resourceBlock(this.blockName(block) + "_top")));
        }

        public void rotatePillarBlock(RotatedPillarBlock pillar) {
            String pillarName = this.blockName(pillar);
            this.logBlock(pillar);
            this.simpleBlockItem(pillar, this.models().cubeColumn(pillarName, this.resourceBlock(pillarName), this.resourceBlock(pillarName + "_top")));
        }

        public void stairBlock(StairBlock stair, Block originBlock) {
            String blockName = this.blockName(originBlock);
            this.stairsBlock(stair, this.resourceBlock(blockName));
            this.simpleBlockItem(stair, this.models().stairs(this.blockName(stair), this.resourceBlock(blockName), this.resourceBlock(blockName), this.resourceBlock(blockName)));
        }

        public void slabBlock(SlabBlock slab, Block originBlock) {
            String blockName = this.blockName(originBlock);
            this.slabBlock(slab, this.resourceBlock(blockName), this.resourceBlock(blockName));
            this.simpleBlockItem(slab, this.models().slab(this.blockName(slab), this.resourceBlock(blockName), this.resourceBlock(blockName), this.resourceBlock(blockName)));
        }

        public void wallBlock(WallBlock wall, Block originBlock) {
            String blockName = this.blockName(originBlock);
            this.wallBlock(wall, blockName, this.resourceBlock(blockName));
            this.simpleBlockItem(wall, this.models().wallInventory(this.blockName(wall), this.resourceBlock(blockName)));
        }

        private String blockName(Block block) {
            return ForgeRegistries.BLOCKS.getKey(block).getPath();
        }

        private String itemName(Item item) {
            return ForgeRegistries.ITEMS.getKey(item).getPath();
        }


        public ResourceLocation resourceBlock(String path) {
            return new ResourceLocation(StarMeowCraft.MODID, "block/" + path);
        }
    }

}
