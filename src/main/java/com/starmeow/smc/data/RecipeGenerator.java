package com.starmeow.smc.data;

import com.starmeow.smc.StarMeowCraft;
import com.starmeow.smc.init.ItemRegistry;
import com.starmeow.smc.tags.ForgeTags;
import com.starmeow.smc.tags.SMCTags;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;

public class RecipeGenerator extends RecipeProvider {

    public RecipeGenerator(PackOutput p_125973_) {
        super(p_125973_);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        //好次滴
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.GOLDEN_BROCCOLI.get())
                .pattern("111")
                .pattern("121")
                .pattern("111")
                .define('1', Items.GOLD_INGOT)
                .define('2', ItemRegistry.BROCCOLI.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.GOLDEN_BROCCOLI.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.ASTERA_APPLE.get())
                .pattern("111")
                .pattern("121")
                .pattern("111")
                .define('1', ItemRegistry.STAR_DUST.get())
                .define('2', Items.APPLE)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.ASTERA_APPLE.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.ASTERA_JERKY.get())
                .pattern("111")
                .pattern("121")
                .pattern("111")
                .define('1', ItemRegistry.STAR_DUST.get())
                .define('2', ItemRegistry.JERKY.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.ASTERA_JERKY.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.ASTERA_PUDDING.get())
                .pattern("111")
                .pattern("121")
                .pattern("111")
                .define('1', ItemRegistry.STAR_DUST.get())
                .define('2', Items.HONEY_BLOCK)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.ASTERA_PUDDING.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.CHOCOLATE.get())
                .requires(Items.COCOA_BEANS)
                .requires(Items.COCOA_BEANS)
                .requires(Items.COCOA_BEANS)
                .requires(Items.SUGAR)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.CHOCOLATE.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.PEA.get(), 3)
                .requires(ItemRegistry.PEA_POD.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.PEA.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.CREAM_BROCCOLI.get())
                .requires(ItemRegistry.BROCCOLI.get())
                .requires(SMCTags.MILK_COMPAT)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.CREAM_BROCCOLI.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.BOILED_BROCCOLI.get())
                .requires(ItemRegistry.BROCCOLI.get())
                .requires(ItemRegistry.BROCCOLI.get())
                .requires(ItemRegistry.BROCCOLI.get())
                .requires(Items.WATER_BUCKET)
                .requires(Items.BOWL)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.BOILED_BROCCOLI.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.LIGHT_MEAL.get())
                .requires(ItemRegistry.BROCCOLI.get())
                .requires(ItemRegistry.BROCCOLI.get())
                .requires(SMCTags.CHICKEN_COMPAT)
                .requires(Items.EGG)
                .requires(Items.BOWL)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.LIGHT_MEAL.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.BRAISED_BEEF_WITH_PEAS.get())
                .requires(ItemRegistry.PEA.get())
                .requires(ItemRegistry.PEA.get())
                .requires(ItemRegistry.PEA.get())
                .requires(ItemRegistry.PEA.get())
                .requires(SMCTags.MEAT_COMPAT)
                .requires(SMCTags.MEAT_COMPAT)
                .requires(Items.BOWL)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.BRAISED_BEEF_WITH_PEAS.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.DANDELION_SALAD.get())
                .requires(Items.DANDELION)
                .requires(Items.DANDELION)
                .requires(Items.DANDELION)
                .requires(Items.BOWL)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.DANDELION_SALAD.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.PEA_SOUP.get())
                .requires(ItemRegistry.PEA.get())
                .requires(ItemRegistry.PEA.get())
                .requires(ItemRegistry.PEA.get())
                .requires(SMCTags.MILK_COMPAT)
                .requires(Items.BAKED_POTATO)
                .requires(Items.BOWL)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.PEA_SOUP.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.STEAMED_PEA_FLOUR_CAKE.get(), 2)
                .requires(ItemRegistry.PEA.get())
                .requires(ItemRegistry.PEA.get())
                .requires(ItemRegistry.PEA.get())
                .requires(SMCTags.MILK_COMPAT)
                .requires(Items.HONEY_BOTTLE)
                .requires(Items.SUGAR)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.STEAMED_PEA_FLOUR_CAKE.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.SPICY_STRIPS.get(),2)
                .requires(ItemRegistry.SPICY_BAR.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.SPICY_STRIPS.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.SPICY_STRIPS.get(),3)
                .requires(Items.ROTTEN_FLESH)
                .requires(Items.BLAZE_POWDER)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.SPICY_STRIPS.get() + "_from_blaze"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.SPICY_STRIPS.get(),2)
                .requires(Items.ROTTEN_FLESH)
                .requires(Items.GUNPOWDER)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.SPICY_STRIPS.get() + "_from_gunpowder"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.HIGH_CALCIUM_MILK.get())
                .requires(Items.BONE_MEAL)
                .requires(Items.PAPER)
                .requires(ForgeTags.MILK)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.HIGH_CALCIUM_MILK.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.ZONGZI.get())
                .requires(Items.BIG_DRIPLEAF)
                .requires(ForgeTags.SEEDS)
                .requires(ForgeTags.SEEDS)
                .requires(ForgeTags.SEEDS)
                .requires(ForgeTags.SEEDS)
                .requires(ForgeTags.SEEDS)
                .requires(ForgeTags.SEEDS)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.ZONGZI.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.BLOCKBENCH.get())
                .requires(ItemTags.WOODEN_STAIRS)
                .requires(Items.COOKIE)
                .requires(Items.BLUE_DYE)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.BLOCKBENCH.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.BROCCOLI_CRATE.get())
                .pattern("111")
                .pattern("111")
                .pattern("111")
                .define('1', ItemRegistry.BROCCOLI.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.BROCCOLI.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.BROCCOLI_CRATE.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.BROCCOLI.get(), 9)
                .requires(ItemRegistry.BROCCOLI_CRATE.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.BROCCOLI.get() + "_from_crate"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.SPICY_FIRED_RICE.get())
                .requires(Items.TORCHFLOWER_SEEDS)
                .requires(Items.BLAZE_POWDER)
                .requires(Items.TORCHFLOWER)
                .requires(ItemRegistry.SNIFFER_BEAK.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.SNIFFER_BEAK.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.SPICY_FIRED_RICE.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.SWEET_FRESH_SALAD.get())
                .requires(Items.PITCHER_POD)
                .requires(Items.HONEY_BOTTLE)
                .requires(Items.PITCHER_PLANT)
                .requires(ItemRegistry.SNIFFER_BEAK.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.SNIFFER_BEAK.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.SWEET_FRESH_SALAD.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.ASTERA_CAKE.get())
                .pattern("111")
                .pattern("121")
                .pattern("111")
                .define('1', ItemRegistry.STAR_DUST.get())
                .define('2', Items.CAKE)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.ASTERA_CAKE.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.FROST_CAKE.get())
                .pattern("151")
                .pattern("232")
                .pattern("454")
                .define('1', ForgeTags.MILK)
                .define('2', Items.SUGAR)
                .define('3', Items.EGG)
                .define('4', Items.WHEAT)
                .define('5', ItemRegistry.FROST_BERRIES.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FROST_BERRIES.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.FROST_CAKE.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.BROCCOLI_CAKE.get())
                .pattern("151")
                .pattern("232")
                .pattern("454")
                .define('1', ForgeTags.MILK)
                .define('2', Items.SUGAR)
                .define('3', Items.EGG)
                .define('4', Items.WHEAT)
                .define('5', ItemRegistry.BROCCOLI.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.CREAM_BROCCOLI.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.BROCCOLI_CAKE.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.RAINBOW_CAKE.get())
                .pattern("111")
                .pattern("232")
                .pattern("454")
                .define('1', ItemRegistry.CLOUD.get())
                .define('2', Items.SUGAR)
                .define('3', Items.EGG)
                .define('4', Items.WHEAT)
                .define('5', ItemRegistry.RAINBOW_CHIP.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.RAINBOW_CHIP.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.RAINBOW_CAKE.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.ONEO.get(), 8)
                .pattern("121")
                .pattern("343")
                .pattern("121")
                .define('1', Items.COCOA_BEANS)
                .define('2', Items.COOKIE)
                .define('3', Items.SUGAR)
                .define('4', ForgeTags.MILK)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.ONEO.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.TNET.get())
                .pattern(" 1 ")
                .pattern("121")
                .pattern(" 1 ")
                .define('1', Items.GUNPOWDER)
                .define('2', ItemRegistry.ONEO.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.ONEO.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.TNET.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.OREEPER.get(), 8)
                .pattern("111")
                .pattern("121")
                .pattern("111")
                .define('1', ItemRegistry.TNET.get())
                .define('2', Items.CREEPER_HEAD)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.TNET.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.OREEPER.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.EASTER_BUNNY_EGG_SANDWICH.get())
                .pattern("31")
                .pattern("42")
                .pattern("31")
                .define('1', Items.BREAD)
                .define('2', ItemRegistry.FRIED_EASTER_BUNNY_EGG.get())
                .define('3', Items.SUGAR)
                .define('4', ForgeTags.MILK)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.EASTER_BUNNY_EGG_SANDWICH.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.CHOCLIZ.get(), 2)
                .pattern(" 21")
                .pattern("342")
                .pattern("53 ")
                .define('1', Items.COCOA_BEANS)
                .define('2', ItemRegistry.CHOCOLATE.get())
                .define('3', Items.SNOWBALL)
                .define('4', ForgeTags.MILK)
                .define('5', Items.STICK)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.CHOCLIZ.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.CHOCLIZ_BERRY.get(), 2)
                .pattern(" 21")
                .pattern("342")
                .pattern("53 ")
                .define('1', Items.COCOA_BEANS)
                .define('2', ItemRegistry.CHOCOLATE.get())
                .define('3', Items.SNOWBALL)
                .define('4', ForgeTags.BERRIES)
                .define('5', Items.STICK)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.CHOCLIZ_BERRY.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.RAW_TOMAHAWK_STEAK.get())
                .pattern("11")
                .pattern("12")
                .pattern(" 2")
                .define('1', Items.BEEF)
                .define('2', Items.BONE)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.RAW_TOMAHAWK_STEAK.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.RAW_TOMAHAWK_STEAK.get())
                .pattern("11")
                .pattern("21")
                .pattern("2 ")
                .define('1', Items.BEEF)
                .define('2', Items.BONE)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.RAW_TOMAHAWK_STEAK.get() + "_2"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Items.MELON), RecipeCategory.FOOD, ItemRegistry.MELON_HALF.get(), 2)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.MELON))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.MELON_HALF.get().toString()));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Items.PUMPKIN), RecipeCategory.FOOD, ItemRegistry.PUMPKIN_PILE.get(), 2)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.PUMPKIN))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.PUMPKIN_PILE.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.CHICKEN_HARVESTER_EGG_SANDWICH.get())
                .requires(Items.BREAD)
                .requires(ItemRegistry.FRIED_CHICKEN_HARVESTER_EGG.get())
                .requires(ItemRegistry.FRIED_CHICKEN_HARVESTER_EGG.get())
                .requires(Items.BEETROOT)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.CHICKEN_HARVESTER_EGG_SANDWICH.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.BURGER.get())
                .requires(Items.BREAD)
                .requires(SMCTags.MEAT_COMPAT)
                .requires(Items.KELP)
                .requires(Items.BEETROOT)
                .requires(Items.CARROT)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.BURGER.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.MOONCAKE.get())
                .requires(Items.WHEAT)
                .requires(Items.SUGAR)
                .requires(Items.WHEAT)
                .requires(Items.SUGAR)
                .requires(ForgeTags.SEEDS)
                .requires(ForgeTags.SEEDS)
                .requires(ForgeTags.SEEDS)
                .requires(ForgeTags.SEEDS)
                .requires(ForgeTags.SEEDS)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.MOONCAKE.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.MOON_CAKE.get())
                .requires(Items.END_STONE)
                .requires(Items.SUGAR)
                .requires(Items.END_STONE)
                .requires(Items.SUGAR)
                .requires(Items.GOLDEN_APPLE)
                .requires(Items.SUGAR)
                .requires(Items.END_STONE)
                .requires(Items.SUGAR)
                .requires(Items.END_STONE)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.MOON_CAKE.get().toString()));
        //霜冻工具
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.FROSTIUM_PICKAXE.get())
                .pattern("111")
                .pattern(" 2 ")
                .pattern(" 2 ")
                .define('1', ItemRegistry.FROSTIUM_INGOT.get())
                .define('2', Items.STICK)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.FROSTIUM_PICKAXE.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.FROSTIUM_AXE.get())
                .pattern("11")
                .pattern("12")
                .pattern(" 2")
                .define('1', ItemRegistry.FROSTIUM_INGOT.get())
                .define('2', Items.STICK)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.FROSTIUM_AXE.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.FROSTIUM_AXE.get())
                .pattern("11")
                .pattern("21")
                .pattern("2 ")
                .define('1', ItemRegistry.FROSTIUM_INGOT.get())
                .define('2', Items.STICK)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.FROSTIUM_AXE.get() + "_2"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.FROSTIUM_SWORD.get())
                .pattern("1")
                .pattern("1")
                .pattern("2")
                .define('1', ItemRegistry.FROSTIUM_INGOT.get())
                .define('2', Items.STICK)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.FROSTIUM_SWORD.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.FROSTIUM_HELMET.get())
                .pattern("111")
                .pattern("1 1")
                .define('1', ItemRegistry.FROSTIUM_INGOT.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.FROSTIUM_HELMET.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.FROSTIUM_BOOTS.get())
                .pattern("1 1")
                .pattern("1 1")
                .define('1', ItemRegistry.FROSTIUM_INGOT.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.FROSTIUM_BOOTS.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.FROSTIUM_BOW.get())
                .pattern(" 12")
                .pattern("3 2")
                .pattern(" 12")
                .define('1', ItemRegistry.FROSTIUM_INGOT.get())
                .define('3', ItemRegistry.FROST_EYE.get())
                .define('2', Items.STRING)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.FROSTIUM_BOW.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.HANGER.get())
                .pattern(" 12")
                .pattern("3 2")
                .pattern(" 12")
                .define('1', Items.IRON_INGOT)
                .define('2', Items.IRON_NUGGET)
                .define('3', Items.TRIPWIRE_HOOK)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.HANGER.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.FROST_ARROW.get(), 2)
                .pattern(" 1 ")
                .pattern("121")
                .pattern(" 1 ")
                .define('1', ItemRegistry.FROSTIUM_NUGGET.get())
                .define('2', Items.ARROW)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.FROST_ARROW.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.FROST_EYE.get())
                .pattern("323")
                .pattern("212")
                .pattern("323")
                .define('1', Items.HEART_OF_THE_SEA)
                .define('3', ItemRegistry.FROSTIUM_NUGGET.get())
                .define('2', ItemRegistry.FROSTIUM_INGOT.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.FROST_EYE.get().toString()));
        //锌工具
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.ZINC_AXE.get())
                .pattern("11")
                .pattern("11")
                .pattern("22")
                .define('1', ItemRegistry.ZINC_INGOT.get())
                .define('2', Items.STICK)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.ZINC_AXE.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.ZINC_PICKAXE.get())
                .pattern("121")
                .pattern("121")
                .pattern(" 2 ")
                .define('1', ItemRegistry.ZINC_INGOT.get())
                .define('2', Items.STICK)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.ZINC_PICKAXE.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.ZINC_SHOVEL.get())
                .pattern(" 1 ")
                .pattern("121")
                .pattern(" 2 ")
                .define('1', ItemRegistry.ZINC_INGOT.get())
                .define('2', Items.STICK)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.ZINC_SHOVEL.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.ZINC_HOE.get())
                .pattern("111")
                .pattern("1 2")
                .pattern("  2")
                .define('1', ItemRegistry.ZINC_INGOT.get())
                .define('2', Items.STICK)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.ZINC_HOE.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.ZINC_SWORD.get())
                .pattern("1")
                .pattern("1")
                .pattern("2")
                .define('1', ItemRegistry.ZINC_INGOT.get())
                .define('2', Items.STICK)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.ZINC_SWORD.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.BATTERY.get())
                .pattern("121")
                .pattern("343")
                .pattern("343")
                .define('1', Items.GOLD_INGOT)
                .define('2', Items.IRON_INGOT)
                .define('3', Items.COAL)
                .define('4', ItemRegistry.ZINC_INGOT.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.BATTERY.get().toString()));
        //巧克力工具
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.CHOCOLATE_HELMET.get())
                .pattern("111")
                .pattern("1 1")
                .define('1', ItemRegistry.CHOCOLATE.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.CHOCOLATE_HELMET.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.CHOCOLATE_CHESTPLATE.get())
                .pattern("1 1")
                .pattern("111")
                .pattern("111")
                .define('1', ItemRegistry.CHOCOLATE.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.CHOCOLATE_CHESTPLATE.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.CHOCOLATE_LEGGINGS.get())
                .pattern("111")
                .pattern("1 1")
                .pattern("1 1")
                .define('1', ItemRegistry.CHOCOLATE.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.CHOCOLATE_LEGGINGS.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.CHOCOLATE_BOOTS.get())
                .pattern("1 1")
                .pattern("1 1")
                .define('1', ItemRegistry.CHOCOLATE.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.CHOCOLATE_BOOTS.get().toString()));
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(ItemRegistry.CHOCOLATE.get()), Ingredient.of(Items.WOODEN_SWORD), Ingredient.of(ItemRegistry.CHOCOLATE.get()), RecipeCategory.TOOLS, ItemRegistry.CHOCOLATE_SWORD.get() )
                .unlocks("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.CHOCOLATE.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.CHOCOLATE_SWORD.get() + "_from_smithing"));
        //狐狸套
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.SNOW_FOX_HELMET.get())
                .pattern("111")
                .pattern("1 1")
                .define('1', ItemRegistry.SNOW_FOX_FUR.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.SNOW_FOX_HELMET.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.SNOW_FOX_CHESTPLATE.get())
                .pattern("1 1")
                .pattern("111")
                .pattern("111")
                .define('1', ItemRegistry.SNOW_FOX_FUR.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.SNOW_FOX_CHESTPLATE.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.SNOW_FOX_LEGGINGS.get())
                .pattern("111")
                .pattern("1 1")
                .pattern("1 1")
                .define('1', ItemRegistry.SNOW_FOX_FUR.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.SNOW_FOX_LEGGINGS.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.SNOW_FOX_BOOTS.get())
                .pattern("1 1")
                .pattern("1 1")
                .define('1', ItemRegistry.SNOW_FOX_FUR.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.SNOW_FOX_BOOTS.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.FOX_HELMET.get())
                .pattern("111")
                .pattern("1 1")
                .define('1', ItemRegistry.FOX_FUR.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.FOX_HELMET.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.FOX_CHESTPLATE.get())
                .pattern("1 1")
                .pattern("111")
                .pattern("111")
                .define('1', ItemRegistry.FOX_FUR.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.FOX_CHESTPLATE.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.FOX_LEGGINGS.get())
                .pattern("111")
                .pattern("1 1")
                .pattern("1 1")
                .define('1', ItemRegistry.FOX_FUR.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.FOX_LEGGINGS.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.FOX_BOOTS.get())
                .pattern("1 1")
                .pattern("1 1")
                .define('1', ItemRegistry.FOX_FUR.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.FOX_BOOTS.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Items.ORANGE_WOOL)
                .pattern("11")
                .pattern("11")
                .define('1', ItemRegistry.FOX_FUR.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, Items.ORANGE_WOOL + "_from_fur"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Items.WHITE_WOOL)
                .pattern("11")
                .pattern("11")
                .define('1', ItemRegistry.SNOW_FOX_FUR.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, Items.WHITE_WOOL + "_from_fur"));
        //狐毛净化
        furPuring(Items.POISONOUS_POTATO, Items.POTATO, consumer);
        furPuring(Items.INK_SAC, Items.GLOW_INK_SAC, consumer);
        furPuring(Items.SWEET_BERRIES, Items.GLOW_BERRIES, consumer);
        furPuring(ItemRegistry.BAD_APPLE.get(), Items.APPLE, consumer);
        furPuring(ItemTags.WOOL, Items.WHITE_WOOL, consumer);
        furPuring(ItemTags.TERRACOTTA, Items.TERRACOTTA, consumer);
        furPuring(ItemTags.create(new ResourceLocation("forge:glass")), Items.GLASS, consumer);
        furPuring(ItemTags.create(new ResourceLocation("forge:glass_panes")), Items.GLASS_PANE, consumer);
        furPuring(ItemTags.WOOL_CARPETS, Items.WHITE_CARPET, consumer);
        furPuring(ItemTags.BANNERS, Items.WHITE_BANNER, consumer);
        furPuring(ItemTags.BEDS, Items.WHITE_BED, consumer);
        furPuring(Items.SUSPICIOUS_STEW, Items.MUSHROOM_STEW, consumer);
        furPuring(Items.FILLED_MAP, Items.MAP, consumer);
        furPuring(Items.WRITTEN_BOOK, Items.BOOK, consumer);
        furPuring(Items.MAGMA_CREAM, Items.SLIME_BALL, consumer);
        furPuring(Items.SOUL_SAND, Items.SAND, consumer);
        furPuring(Items.SOUL_SOIL, Items.DIRT, consumer);
        furPuring(Items.SOUL_CAMPFIRE, Items.CAMPFIRE, consumer);
        furPuring(Items.SOUL_TORCH, Items.TORCH, consumer);
        furPuring(Items.WARPED_FUNGUS, Items.BROWN_MUSHROOM, consumer);
        furPuring(Items.CRIMSON_FUNGUS, Items.RED_MUSHROOM, consumer);
        furPuring(Items.NETHER_BRICKS, Items.BRICKS, consumer);
        furPuring(Items.NETHER_GOLD_ORE, Items.GOLD_ORE, consumer);
        furPuring(Items.CRYING_OBSIDIAN, Items.OBSIDIAN, consumer);
        furPuring(Items.SCULK_VEIN, Items.GLOW_LICHEN, consumer);
        furPuring(Items.GUNPOWDER, Items.BLAZE_POWDER, consumer);
        furPuring(Items.CHARCOAL, Items.COAL, consumer);
        furPuring(Items.ROTTEN_FLESH, ItemRegistry.JERKY.get(), consumer);
        furPuring(ItemRegistry.COOKED_DIRT.get(), ItemRegistry.CHOCOLATE.get(), consumer);
        furPuring(Items.QUARTZ, Items.AMETHYST_SHARD, consumer);
        furPuring(Items.QUARTZ_BLOCK, Items.AMETHYST_BLOCK, consumer);
        furPuring(Items.WITHER_SKELETON_SKULL, Items.SKELETON_SKULL, consumer);
        furPuring(Items.ZOMBIE_HEAD, Items.PLAYER_HEAD, consumer);
        furPuring(Items.WITHER_ROSE, Items.POPPY, consumer);

        //西兰花工具
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.BROCCOLI_BOOTS.get())
                .pattern("1 1")
                .pattern("1 1")
                .define('1', ItemRegistry.BROCCOLI.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.BROCCOLI.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.BROCCOLI_BOOTS.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.BROCCOLI_FISHING_ROD.get())
                .pattern("  1")
                .pattern(" 12")
                .pattern("1 3")
                .define('3', ItemRegistry.BROCCOLI.get())
                .define('2', Items.VINE)
                .define('1', Items.BAMBOO)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.BROCCOLI.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.BROCCOLI_FISHING_ROD.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.BROCCOLI_HOE.get())
                .pattern("11")
                .pattern(" 2")
                .pattern(" 2")
                .define('1', ItemRegistry.BROCCOLI.get())
                .define('2', Items.BAMBOO)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.BROCCOLI.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.BROCCOLI_HOE.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.BROCCOLI_HOE.get())
                .pattern("11")
                .pattern("2 ")
                .pattern("2 ")
                .define('1', ItemRegistry.BROCCOLI.get())
                .define('2', Items.BAMBOO)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.BROCCOLI.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.BROCCOLI_HOE.get() + "_2"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.BROCCOLI_BOMB.get())
                .pattern("111")
                .pattern("121")
                .pattern("111")
                .define('1', ItemRegistry.BROCCOLI.get())
                .define('2', Items.TNT)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.BROCCOLI.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.BROCCOLI_BOMB.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.BROCCOLI_NUKE.get())
                .pattern("111")
                .pattern("121")
                .pattern("111")
                .define('1', ItemRegistry.BROCCOLI_BOMB.get())
                .define('2', Items.NETHER_STAR)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.BROCCOLI_BOMB.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.BROCCOLI_NUKE.get().toString()));

        //珀金工具
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.PERKIN_SHOVEL.get())
                .pattern("1")
                .pattern("2")
                .pattern("2")
                .define('1', ItemRegistry.PERKIN_INGOT.get())
                .define('2', Items.STICK)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.PERKIN_SHOVEL.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.PERKIN_HOE.get())
                .pattern("11")
                .pattern(" 2")
                .pattern(" 2")
                .define('1', ItemRegistry.PERKIN_INGOT.get())
                .define('2', Items.STICK)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.PERKIN_HOE.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.PERKIN_HOE.get())
                .pattern("11")
                .pattern("2 ")
                .pattern("2 ")
                .define('1', ItemRegistry.PERKIN_INGOT.get())
                .define('2', Items.STICK)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.PERKIN_HOE.get() + "_2"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.PERKIN_PICKAXE.get())
                .pattern("111")
                .pattern(" 2 ")
                .pattern(" 2 ")
                .define('1', ItemRegistry.PERKIN_INGOT.get())
                .define('2', Items.STICK)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.PERKIN_PICKAXE.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.PERKIN_AXE.get())
                .pattern("11")
                .pattern("12")
                .pattern(" 2")
                .define('1', ItemRegistry.PERKIN_INGOT.get())
                .define('2', Items.STICK)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.PERKIN_AXE.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.PERKIN_AXE.get())
                .pattern("11")
                .pattern("21")
                .pattern("2 ")
                .define('1', ItemRegistry.PERKIN_INGOT.get())
                .define('2', Items.STICK)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.PERKIN_AXE.get() + "_2"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.PERKIN_SPEAR.get())
                .pattern(" 11")
                .pattern(" 21")
                .pattern("2  ")
                .define('1', ItemRegistry.PERKIN_INGOT.get())
                .define('2', Items.STICK)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.PERKIN_SPEAR.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.GRIMOIRE.get())
                .pattern("1")
                .pattern("2")
                .pattern("3")
                .define('1', ItemRegistry.PERKIN_STAR.get())
                .define('2', Items.STICK)
                .define('3', ItemRegistry.PERKIN_INGOT.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.GRIMOIRE.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.PERKIN_STAR.get())
                .pattern("323")
                .pattern("212")
                .pattern("323")
                .define('1', ItemRegistry.PERKIN_INGOT.get())
                .define('3', ItemRegistry.PERKIN_NUGGET.get())
                .define('2', ItemRegistry.STAR_DUST.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.PERKIN_STAR.get().toString()));
        //霜珀工具
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(ItemRegistry.PERFROSTITE_UPGRADE_SCROLL.get()), Ingredient.of(Items.DIAMOND_AXE), Ingredient.of(ItemRegistry.PERFROSTITE_INGOT.get()), RecipeCategory.TOOLS, ItemRegistry.PERFROSTITE_AXE.get())
                .unlocks("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.NETHERITE_INGOT))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.PERFROSTITE_AXE.get() + "_from_smithing"));
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(ItemRegistry.PERFROSTITE_UPGRADE_SCROLL.get()), Ingredient.of(Items.DIAMOND_PICKAXE), Ingredient.of(ItemRegistry.PERFROSTITE_INGOT.get()), RecipeCategory.TOOLS, ItemRegistry.PERFROSTITE_PICKAXE.get())
                .unlocks("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.NETHERITE_INGOT))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.PERFROSTITE_PICKAXE.get() + "_from_smithing"));
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(ItemRegistry.PERFROSTITE_UPGRADE_SCROLL.get()), Ingredient.of(Items.DIAMOND_HOE), Ingredient.of(ItemRegistry.PERFROSTITE_INGOT.get()), RecipeCategory.TOOLS, ItemRegistry.PERFROSTITE_HOE.get())
                .unlocks("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.NETHERITE_INGOT))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.PERFROSTITE_HOE.get() + "_from_smithing"));
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(ItemRegistry.PERFROSTITE_UPGRADE_SCROLL.get()), Ingredient.of(Items.DIAMOND_SWORD), Ingredient.of(ItemRegistry.PERFROSTITE_INGOT.get()), RecipeCategory.TOOLS, ItemRegistry.PERFROSTITE_SWORD.get())
                .unlocks("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.NETHERITE_INGOT))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.PERFROSTITE_SWORD.get() + "_from_smithing"));
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(ItemRegistry.PERFROSTITE_UPGRADE_SCROLL.get()), Ingredient.of(Items.DIAMOND_SHOVEL), Ingredient.of(ItemRegistry.PERFROSTITE_INGOT.get()), RecipeCategory.TOOLS, ItemRegistry.PERFROSTITE_SHOVEL.get())
                .unlocks("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.NETHERITE_INGOT))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.PERFROSTITE_SHOVEL.get() + "_from_smithing"));
        /*
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(ItemRegistry.PERFROSTITE_UPGRADE_SCROLL.get()), Ingredient.of(ItemRegistry.FROSTIUM_BOW.get()), Ingredient.of(ItemRegistry.PERFROSTITE_INGOT.get()), RecipeCategory.TOOLS, ItemRegistry.PERFROSTITE_BOW.get())
                .unlocks("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.NETHERITE_INGOT))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.PERFROSTITE_BOW.get() + "_from_smithing"));
         */
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.PERFROSTITE_UPGRADE_SCROLL.get(), 2)
                .pattern("314")
                .pattern("324")
                .pattern("314")
                .define('1', ItemRegistry.GOLDEN_BROCCOLI.get())
                .define('2', ItemRegistry.PERFROSTITE_UPGRADE_SCROLL.get())
                .define('3', ItemRegistry.PERKIN_INGOT.get())
                .define('4', ItemRegistry.FROSTIUM_INGOT.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.PERFROSTITE_UPGRADE_SCROLL.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.PERFROSTITE_INGOT.get())
                .requires(ItemRegistry.PERKIN_INGOT.get())
                .requires(ItemRegistry.PERKIN_INGOT.get())
                .requires(ItemRegistry.PERKIN_INGOT.get())
                .requires(ItemRegistry.PERKIN_INGOT.get())
                .requires(ItemRegistry.FROSTIUM_INGOT.get())
                .requires(ItemRegistry.FROSTIUM_INGOT.get())
                .requires(ItemRegistry.FROSTIUM_INGOT.get())
                .requires(ItemRegistry.FROSTIUM_INGOT.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.PERFROSTITE_INGOT.get().toString()));
        //咸鱼
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE), Ingredient.of(Items.IRON_SWORD), Ingredient.of(ItemRegistry.SALT_FISH.get()), RecipeCategory.TOOLS, ItemRegistry.SALT_FISH_SWORD.get())
                .unlocks("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.SALT_FISH.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.SALT_FISH_SWORD.get() + "_from_smithing"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.SALT_FISH.get())
                .requires(Items.PRISMARINE_CRYSTALS)
                .requires(Items.COD)
                .requires(Items.PRISMARINE_CRYSTALS)
                .requires(Items.SALMON)
                .requires(Items.HEART_OF_THE_SEA)
                .requires(Items.TROPICAL_FISH)
                .requires(Items.PRISMARINE_CRYSTALS)
                .requires(Items.PUFFERFISH)
                .requires(Items.PRISMARINE_CRYSTALS)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.SALT_FISH.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.FISH_COOKIE.get(), 2)
                .requires(Items.WHEAT)
                .requires(Items.WHEAT)
                .requires(Items.WHEAT)
                .requires(ItemRegistry.SALT_FISH.get())
                .requires(ItemRegistry.SALT_FISH.get())
                .requires(ItemRegistry.SALT_FISH.get())
                .requires(Items.WHEAT)
                .requires(Items.WHEAT)
                .requires(Items.WHEAT)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.FISH_COOKIE.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.SALT_FISH_STEW.get())
                .requires(ItemRegistry.BOWL_OF_HOT_WATER.get())
                .requires(ItemRegistry.COOKED_SALT_FISH.get())
                .requires(SMCTags.MILK_COMPAT)
                .requires(Items.KELP)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.SALT_FISH_STEW.get().toString()));

        //棒棒糖
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.BROCCOLI_LOLLIPOP.get())
                .pattern("111")
                .pattern("121")
                .pattern("131")
                .define('1', Items.SUGAR)
                .define('2', ItemRegistry.BROCCOLI.get())
                .define('3', Items.STICK)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.BROCCOLI_LOLLIPOP.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.FROST_LOLLIPOP.get())
                .pattern("111")
                .pattern("121")
                .pattern("131")
                .define('1', Items.SUGAR)
                .define('2', ItemRegistry.FROSTIUM_INGOT.get())
                .define('3', Items.STICK)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.FROST_LOLLIPOP.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.PERKIN_LOLLIPOP.get())
                .pattern("111")
                .pattern("121")
                .pattern("131")
                .define('1', Items.SUGAR)
                .define('2', ItemRegistry.PERKIN_INGOT.get())
                .define('3', Items.STICK)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.PERKIN_LOLLIPOP.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.COLORFUL_ICE_CREAM.get())
                .requires(ItemRegistry.BROCCOLI_LOLLIPOP.get())
                .requires(ItemRegistry.PERKIN_LOLLIPOP.get())
                .requires(ItemRegistry.FROST_LOLLIPOP.get())
                .requires(SMCTags.MILK_COMPAT)
                .requires(Items.SNOW_BLOCK)
                .requires(Items.HAY_BLOCK)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.COLORFUL_ICE_CREAM.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.CHOP_KEBAB.get())
                .pattern("1")
                .pattern("1")
                .pattern("2")
                .define('2', Items.STICK)
                .define('1', ItemRegistry.CHICKEN_CHOP.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.CHOP_KEBAB.get().toString()));

        //矿物块，锭，粒转化
        blockIngotNuggetConvertingRecipes(ItemRegistry.FROSTIUM_BLOCK.get(), ItemRegistry.FROSTIUM_INGOT.get(), ItemRegistry.FROSTIUM_NUGGET.get(), consumer);
        blockIngotNuggetConvertingRecipes(ItemRegistry.PERKIN_BLOCK.get(), ItemRegistry.PERKIN_INGOT.get(), ItemRegistry.PERKIN_NUGGET.get(), consumer);
        blockIngotNuggetConvertingRecipes(ItemRegistry.SALT_FISH_BLOCK.get(), ItemRegistry.SALT_FISH.get(), ItemRegistry.SALT_FISH_NUGGET.get(), consumer);
        blockIngotNuggetConvertingRecipes(ItemRegistry.ZINC_BLOCK.get(), ItemRegistry.ZINC_INGOT.get(), ItemRegistry.ZINC_NUGGET.get(), consumer);

        //云系列建材
        stoneBricksRecipes(ItemRegistry.CLOUD.get(), ItemRegistry.CLOUD_STAIRS.get(), ItemRegistry.CLOUD_SLAB.get(), ItemRegistry.CLOUD_WALL.get(), consumer);
        stoneBricksRecipes(ItemRegistry.CLOUD_FLOOR.get(), ItemRegistry.CLOUD_FLOOR_STAIRS.get(), ItemRegistry.CLOUD_FLOOR_SLAB.get(), ItemRegistry.CLOUD_FLOOR_WALL.get(), consumer);
        stoneBricksRecipes(ItemRegistry.CLOUD_BRICKS.get(), ItemRegistry.CLOUD_BRICKS_STAIRS.get(), ItemRegistry.CLOUD_BRICKS_SLAB.get(), ItemRegistry.CLOUD_BRICKS_WALL.get(), consumer);
        stoneBricksCuttingRecipes(ItemRegistry.CLOUD.get(), ItemRegistry.CLOUD_STAIRS.get(), ItemRegistry.CLOUD_SLAB.get(), ItemRegistry.CLOUD_WALL.get(), "_from_cutting_cloud" ,consumer);
        stoneBricksCuttingRecipes(ItemRegistry.CLOUD_FLOOR.get(), ItemRegistry.CLOUD_FLOOR_STAIRS.get(), ItemRegistry.CLOUD_FLOOR_SLAB.get(), ItemRegistry.CLOUD_FLOOR_WALL.get(), "_from_cutting_cloud_floor", consumer);
        stoneBricksCuttingRecipes(ItemRegistry.CLOUD_BRICKS.get(), ItemRegistry.CLOUD_BRICKS_STAIRS.get(), ItemRegistry.CLOUD_BRICKS_SLAB.get(), ItemRegistry.CLOUD_BRICKS_WALL.get(), "_from_cutting_cloud_bricks", consumer);
        stoneBricksCuttingRecipes(ItemRegistry.CLOUD.get(), ItemRegistry.CLOUD_FLOOR_STAIRS.get(), ItemRegistry.CLOUD_FLOOR_SLAB.get(), ItemRegistry.CLOUD_FLOOR_WALL.get(), "_from_cutting_cloud", consumer);
        stoneBricksCuttingRecipes(ItemRegistry.CLOUD.get(), ItemRegistry.CLOUD_BRICKS_STAIRS.get(), ItemRegistry.CLOUD_BRICKS_SLAB.get(), ItemRegistry.CLOUD_BRICKS_WALL.get(), "_from_cutting_cloud", consumer);
        stoneBricksCuttingRecipes(ItemRegistry.CLOUD_FLOOR.get(), ItemRegistry.CLOUD_BRICKS_STAIRS.get(), ItemRegistry.CLOUD_BRICKS_SLAB.get(), ItemRegistry.CLOUD_BRICKS_WALL.get(), "_from_cutting_cloud_floor", consumer);
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.CLOUD.get()), RecipeCategory.DECORATIONS, ItemRegistry.CLOUD_FLOOR.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.CLOUD.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.CLOUD_FLOOR.get() + "_from_stone_cutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.CLOUD.get()), RecipeCategory.DECORATIONS, ItemRegistry.CLOUD_BRICKS.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.CLOUD.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.CLOUD_BRICKS.get() + "_from_stone_cutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.CLOUD.get()), RecipeCategory.DECORATIONS, ItemRegistry.CLOUD_PILLAR.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.CLOUD.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.CLOUD_PILLAR.get() + "_from_stone_cutting"));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ItemRegistry.CLOUD_BRICKS.get(), 4)
                .pattern("11")
                .pattern("11")
                .define('1', ItemRegistry.CLOUD.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.CLOUD.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.CLOUD_BRICKS.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ItemRegistry.CLOUD_PILLAR.get(), 2)
                .pattern("1")
                .pattern("1")
                .define('1', ItemRegistry.CLOUD.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.CLOUD.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.CLOUD_PILLAR.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ItemRegistry.CLOUD.get())
                .pattern("11")
                .pattern("11")
                .define('1', ItemRegistry.THIN_CLOUD.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.CLOUD.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.CLOUD.get() + "_from_thin_cloud"));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ItemRegistry.CIRRUS_CLOUD.get())
                .pattern("11")
                .pattern("11")
                .define('1', ItemRegistry.THIN_CIRRUS_CLOUD.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.CLOUD.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.CIRRUS_CLOUD.get() + "_from_thin_cirrus_cloud"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ItemRegistry.THIN_CIRRUS_CLOUD.get(), 4)
                .requires(ItemRegistry.CIRRUS_CLOUD.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.THIN_CIRRUS_CLOUD.get() + "_from_cirrus_cloud"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ItemRegistry.THIN_CIRRUS_CLOUD.get(), 4)
                .requires(ItemRegistry.CLOUD.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.THIN_CLOUD.get() + "_from_cloud"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.SNOWBALL), RecipeCategory.DECORATIONS, ItemRegistry.THIN_CLOUD.get(), 0.1F, 200)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, ItemRegistry.THIN_CLOUD.get() + "_from_smelting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.SNOW_BLOCK), RecipeCategory.DECORATIONS, ItemRegistry.CLOUD.get(), 0.1F, 200)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, ItemRegistry.CLOUD.get() + "_from_smelting");
        //彩虹
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.RAINBOW_FISHING_ROD.get())
                .pattern("  2")
                .pattern(" 23")
                .pattern("2 3")
                .define('2', ItemRegistry.RAINBOW_CHIP.get())
                .define('3', Items.STRING)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.RAINBOW_FISHING_ROD.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.RAINBOW_BOW.get())
                .pattern(" 23")
                .pattern("2 3")
                .pattern(" 23")
                .define('2', ItemRegistry.RAINBOW_CHIP.get())
                .define('3', Items.STRING)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.RAINBOW_BOW.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.MEOWMERE.get())
                .pattern("121")
                .pattern("131")
                .pattern("141")
                .define('1', ItemRegistry.RAINBOW_CHIP.get())
                .define('4', ItemRegistry.GRIMOIRE.get())
                .define('2', ItemRegistry.PERFROSTITE_SWORD.get())
                .define('3', Items.NETHER_STAR)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.MEOWMERE.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.GRAND_SWORD.get())
                .pattern("a1b")
                .pattern("c1d")
                .pattern("323")
                .define('1', Items.EMERALD)
                .define('2',Items.GOLDEN_SWORD)
                .define('3', Items.GOLD_NUGGET)
                .define('a', Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE)
                .define('b', Items.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE)
                .define('c', Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE)
                .define('d', Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.GRAND_SWORD.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.SAVAGE_SWORD.get())
                .pattern("a1a")
                .pattern("a1a")
                .pattern("323")
                .define('1', Items.NETHERITE_INGOT)
                .define('2',Items.GOLDEN_SWORD)
                .define('3', Items.GOLD_INGOT)
                .define('a', Ingredient.of(Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE, Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE,  Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE, Items.VEX_ARMOR_TRIM_SMITHING_TEMPLATE))
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.SAVAGE_SWORD.get().toString()));

        //矿物冶炼
        smeltingRecipes("blue_ice_frostium_ore_to_ingot", ItemRegistry.BLUE_ICE_FROSTIUM_ORE.get(), ItemRegistry.FROSTIUM_INGOT.get(), 0.35F, consumer);
        smeltingRecipes("frostium_ore_to_ingot", ItemRegistry.FROSTIUM_ORE.get(), ItemRegistry.FROSTIUM_INGOT.get(), 0.35F, consumer);
        smeltingRecipes("raw_frostium_to_ingot", ItemRegistry.RAW_FROSTIUM.get(), ItemRegistry.FROSTIUM_INGOT.get(), 0.35F, consumer);
        smeltingRecipes("perkin_ore_to_ingot", ItemRegistry.PERKIN_ORE.get(), ItemRegistry.PERKIN_INGOT.get(), 0.35F, consumer);
        smeltingRecipes("deepslate_perkin_ore_to_ingot", ItemRegistry.DEEPSLATE_PERKIN_ORE.get(), ItemRegistry.PERKIN_INGOT.get(), 0.35F, consumer);
        smeltingRecipes("raw_perkin_to_ingot", ItemRegistry.RAW_PERKIN.get(), ItemRegistry.PERKIN_INGOT.get(), 0.35F, consumer);
        smeltingRecipes("raw_zinc_to_ingot", ItemRegistry.RAW_ZINC.get(), ItemRegistry.ZINC_INGOT.get(), 0.35F, consumer);
        smeltingRecipes("zinc_ore_to_ingot", ItemRegistry.ZINC_ORE.get(), ItemRegistry.ZINC_INGOT.get(), 0.35F, consumer);
        smeltingRecipes("tuff_zinc_ore_to_ingot", ItemRegistry.TUFF_ZINC_ORE.get(), ItemRegistry.ZINC_INGOT.get(), 0.35F, consumer);
        smeltingRecipes("end_zinc_ore_to_ingot", ItemRegistry.END_ZINC_ORE.get(), ItemRegistry.ZINC_INGOT.get(), 0.35F, consumer);

        //食物烘焙
        cookingRecipes("cooked_dirt", Items.DIRT, ItemRegistry.COOKED_DIRT.get(), 0.2F, consumer, true);
        cookingRecipes("spicy_bar", Items.ROTTEN_FLESH, ItemRegistry.SPICY_BAR.get(), 0.2F, consumer, false);
        cookingRecipes("jerky", Items.COOKED_BEEF, ItemRegistry.JERKY.get(), 0.2F, consumer, false);
        cookingRecipes("jerky_2", Items.COOKED_PORKCHOP, ItemRegistry.JERKY.get(), 0.2F, consumer, false);
        cookingRecipes("cooked_salt_fish", ItemRegistry.SALT_FISH.get(), ItemRegistry.COOKED_SALT_FISH.get(), 1F, consumer, true);
        cookingRecipes("cooked_salt_fish_slice", ItemRegistry.RAW_SALT_FISH_SLICE.get(), ItemRegistry.COOKED_SALT_FISH_SLICE.get(), 1F, consumer, true);
        cookingRecipes("fried_chicken_harvester_egg", ItemRegistry.CHICKEN_HARVESTER_EGG.get(), ItemRegistry.FRIED_CHICKEN_HARVESTER_EGG.get(), 1F, consumer, true);
        cookingRecipes("fried_easter_bunny_egg", ItemRegistry.EASTER_BUNNY_EGG.get(), ItemRegistry.FRIED_EASTER_BUNNY_EGG.get(), 1F, consumer, true);
        cookingRecipes("tomahawk_steak", ItemRegistry.RAW_TOMAHAWK_STEAK.get(), ItemRegistry.TOMAHAWK_STEAK.get(), 1F, consumer, true);

        //杂项
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.MUSIC_DISC_LAODA.get())
                .pattern("111")
                .pattern("111")
                .pattern("111")
                .define('1', ItemRegistry.ICE_TEA.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.ICE_TEA.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.MUSIC_DISC_LAODA.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.MUSIC_DISC_NYAN_CAT.get())
                .pattern("111")
                .pattern("111")
                .pattern("111")
                .define('1', ItemRegistry.RAINBOW_COOKIE.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.RAINBOW_COOKIE.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.MUSIC_DISC_NYAN_CAT.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.MUSIC_DISC_BAD_APPLE.get())
                .pattern("111")
                .pattern("111")
                .pattern("111")
                .define('1', ItemRegistry.BAD_APPLE.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.BAD_APPLE.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.MUSIC_DISC_BAD_APPLE.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ItemRegistry.RAINBOW_COOKIE.get(), 8)
                .pattern("121")
                .define('1', Items.WHEAT)
                .define('2', ItemRegistry.RAINBOW_CHIP.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.RAINBOW_COOKIE.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.CAT_PAW.get())
                .pattern(" 1 ")
                .pattern("121")
                .pattern("232")
                .define('1', Items.IRON_NUGGET)
                .define('2', Items.WHITE_WOOL)
                .define('3', Items.PINK_WOOL)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.CAT_PAW.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.REDSTONE_ICE_CREAM.get())
                .pattern(" 1 ")
                .pattern("234")
                .pattern(" 5 ")
                .define('1', Items.REDSTONE)
                .define('2', Items.QUARTZ)
                .define('3', Items.SNOWBALL)
                .define('4', Items.REDSTONE_TORCH)
                .define('5', Items.HOPPER)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.REDSTONE_ICE_CREAM.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.END_ROD_CANDY.get())
                .pattern("1")
                .pattern("2")
                .pattern("3")
                .define('1', Items.SUGAR)
                .define('2', ForgeTags.MILK)
                .define('3', Items.END_ROD)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.END_ROD_CANDY.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.FIRE_EXTINGUISHER.get())
                .pattern("1")
                .pattern("2")
                .pattern("3")
                .define('1', Items.DRIED_KELP)
                .define('2', Items.IRON_INGOT)
                .define('3', Items.POWDER_SNOW_BUCKET)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.FIRE_EXTINGUISHER.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.ARCHAEOLOGICAL_SHOVEL.get())
                .pattern("1")
                .pattern("2")
                .pattern("2")
                .define('1', ItemTags.DECORATED_POT_SHERDS)
                .define('2', Items.STICK)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.ARCHAEOLOGICAL_SHOVEL.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.ORE_DETECTOR.get())
                .pattern("121")
                .pattern("131")
                .pattern("141")
                .define('1', ItemRegistry.PERKIN_INGOT.get())
                .define('2', Items.TINTED_GLASS)
                .define('3', Items.CALIBRATED_SCULK_SENSOR)
                .define('4', Items.REDSTONE)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.ORE_DETECTOR.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.TREASURE_DETECTOR.get())
                .pattern("121")
                .pattern("131")
                .pattern("141")
                .define('1', Items.GOLD_INGOT)
                .define('2', Items.TINTED_GLASS)
                .define('3', Items.EMERALD_BLOCK)
                .define('4', Items.REDSTONE)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.TREASURE_DETECTOR.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.VACUUM_SNIFFER.get())
                .pattern("12")
                .pattern("34")
                .define('1', Items.DRIED_KELP)
                .define('2', Items.MOSS_BLOCK)
                .define('3', ItemRegistry.SNIFFER_BEAK.get())
                .define('4', Items.BARREL)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.VACUUM_SNIFFER.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.WATERING_CAN.get())
                .pattern(" 1 ")
                .pattern("121")
                .pattern(" 11")
                .define('1', ItemRegistry.PERKIN_INGOT.get())
                .define('2', Items.WATER_BUCKET)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.WATERING_CAN.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.IRON_WATERING_CAN.get())
                .pattern(" 1 ")
                .pattern("121")
                .pattern(" 11")
                .define('1', Items.IRON_INGOT)
                .define('2', Items.WATER_BUCKET)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.IRON_WATERING_CAN.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.COPPER_WATERING_CAN.get())
                .pattern(" 1 ")
                .pattern("121")
                .pattern(" 11")
                .define('1', Items.COPPER_INGOT)
                .define('2', Items.WATER_BUCKET)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.COPPER_WATERING_CAN.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.SLINGSHOT.get())
                .pattern("121")
                .pattern(" 1 ")
                .define('1', Items.STICK)
                .define('2', Items.STRING)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.SLINGSHOT.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.CHOP_SHIELD.get())
                .pattern("131")
                .pattern("121")
                .pattern(" 1 ")
                .define('1', Items.QUARTZ)
                .define('2', ItemRegistry.CHICKEN_CHOP.get())
                .define('3', Items.IRON_INGOT)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.CHOP_SHIELD.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.BOT.get())
                .pattern("111")
                .pattern("232")
                .pattern("444")
                .define('1', Items.TINTED_GLASS)
                .define('2', Items.IRON_INGOT)
                .define('3', Items.DROPPER)
                .define('4', Items.EXPERIENCE_BOTTLE)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.BOT.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ItemRegistry.DELUXE_CAKE.get())
                .pattern("111")
                .pattern("121")
                .pattern("111")
                .define('1', ItemRegistry.COOKED_DIRT.get())
                .define('2', Items.CAKE)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CAKE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.DELUXE_CAKE.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ItemRegistry.DELUXE_CAKE.get())
                .requires(ItemRegistry.DELUXE_CAKE_SLICE.get())
                .requires(ItemRegistry.DELUXE_CAKE_SLICE.get())
                .requires(ItemRegistry.DELUXE_CAKE_SLICE.get())
                .requires(ItemRegistry.DELUXE_CAKE_SLICE.get())
                .requires(ItemRegistry.DELUXE_CAKE_SLICE.get())
                .requires(ItemRegistry.DELUXE_CAKE_SLICE.get())
                .requires(ItemRegistry.DELUXE_CAKE_SLICE.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.DELUXE_CAKE.get() + "_from_slice"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ItemRegistry.STAR_DUST.get(), 4)
                .requires(Items.GLOWSTONE_DUST)
                .requires(ItemRegistry.PERKIN_NUGGET.get())
                .requires(ItemRegistry.PERKIN_NUGGET.get())
                .requires(ItemRegistry.PERKIN_NUGGET.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.STAR_DUST.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ItemRegistry.GRASS_BLOCK_PIE.get())
                .requires(Items.SUGAR)
                .requires(Items.GRASS)
                .requires(ItemRegistry.COOKED_DIRT.get())
                .requires(ItemRegistry.COOKED_DIRT.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.GRASS_BLOCK_PIE.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ItemRegistry.SWISS_ARMY_KNIFE.get())
                .requires(Items.IRON_AXE)
                .requires(Items.IRON_PICKAXE)
                .requires(Items.IRON_SWORD)
                .requires(Items.IRON_SHOVEL)
                .requires(Items.IRON_HOE)
                .requires(Items.BRICK)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.SWISS_ARMY_KNIFE.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ItemRegistry.DIAMOND_SWISS_ARMY_KNIFE.get())
                .requires(Items.DIAMOND_AXE)
                .requires(Items.DIAMOND_PICKAXE)
                .requires(Items.DIAMOND_SWORD)
                .requires(Items.DIAMOND_SHOVEL)
                .requires(Items.DIAMOND_HOE)
                .requires(Items.GOLD_INGOT)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.DIAMOND_SWISS_ARMY_KNIFE.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.LUCKY_NUGGET.get(), 3)
                .requires(ItemRegistry.LUCKY_CLOVER.get())
                .requires(Items.COOKED_CHICKEN)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.LUCKY_NUGGET.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.SPORE_BUD.get(), 2)
                .requires(Items.SPORE_BLOSSOM)
                .requires(Items.GLISTERING_MELON_SLICE)
                .requires(ItemRegistry.SNIFFER_BEAK.get())
                .requires(Items.NETHER_WART)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.SPORE_BUD.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.DEVOUR_SWORD.get())
                .requires(Items.WOODEN_SWORD)
                .requires(Items.STONE_SWORD)
                .requires(Items.IRON_SWORD)
                .requires(Items.GOLDEN_SWORD)
                .requires(Items.DIAMOND_SWORD)
                .requires(Items.NETHERITE_SWORD)
                .requires(ItemRegistry.FROSTIUM_SWORD.get())
                .requires(ItemRegistry.ZINC_SWORD.get())
                .requires(ItemRegistry.KNIFE.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.DEVOUR_SWORD.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.ZENISH.get())
                .requires(ItemRegistry.MEOWMERE.get())
                .requires(ItemRegistry.DEVOUR_SWORD.get())
                .requires(ItemRegistry.EXCALIBUR.get())
                .requires(ItemRegistry.KATANA.get())
                .requires(ItemRegistry.SALT_FISH_SWORD.get())
                .requires(ItemRegistry.COLORFUL_ICE_CREAM.get())
                .requires(ItemRegistry.PERFROSTITE_SWORD.get())
                .requires(ItemRegistry.CHOP_KEBAB.get())
                .requires(ItemRegistry.CHOCOLATE_SWORD.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.ZENISH.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.RUYI_JINGU_BANG.get())
                .requires(ItemRegistry.ZINC_PICKAXE.get())
                .requires(ItemRegistry.CALIBUR.get())
                .requires(ItemRegistry.FROSTIUM_AXE.get())
                .requires(ItemRegistry.GOLDEN_HANGER.get())
                .requires(ItemRegistry.SUGARCANE_ROD.get())
                .requires(ItemRegistry.RAINBOW_BOW.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.RUYI_JINGU_BANG.get().toString()));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.WATER_DISPENSER.get())
                .requires(Items.WATER_BUCKET)
                .requires(Items.GLASS)
                .requires(Items.LAVA_BUCKET)
                .requires(Items.HOPPER)
                .requires(Items.IRON_BLOCK)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.WATER_DISPENSER.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.FRIDGE.get())
                .pattern("423")
                .pattern("411")
                .pattern("423")
                .define('1', Items.IRON_INGOT)
                .define('2', Items.CHEST)
                .define('3', Items.IRON_TRAPDOOR)
                .define('4', ItemRegistry.FROSTIUM_INGOT.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.FRIDGE.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.GOLDEN_BOAT.get())
                .pattern("121")
                .pattern("111")
                .define('1', Items.GOLD_INGOT)
                .define('2', Items.GOLDEN_SHOVEL)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GOLD_INGOT))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.GOLDEN_BOAT.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.GOLDEN_TRANSPARENT_BOAT.get())
                .pattern("121")
                .pattern("111")
                .pattern("343")
                .define('1', Items.GOLD_INGOT)
                .define('2', Items.GOLDEN_SHOVEL)
                .define('3', ItemRegistry.STAR_DUST.get())
                .define('4', Items.GLASS_PANE)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.GOLDEN_BOAT.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.GOLDEN_TRANSPARENT_BOAT.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.GOLDEN_TRANSPARENT_BOAT.get())
                .pattern(" 2 ")
                .pattern("343")
                .define('2', ItemRegistry.GOLDEN_BOAT.get())
                .define('3', ItemRegistry.STAR_DUST.get())
                .define('4', Items.GLASS_PANE)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.GOLDEN_BOAT.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.GOLDEN_TRANSPARENT_BOAT.get() + "_2"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.GOLDEN_CHEST_BOAT.get())
                .pattern("1")
                .pattern("2")
                .define('1', Items.CHEST)
                .define('2', ItemRegistry.GOLDEN_BOAT.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.GOLDEN_BOAT.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.GOLDEN_CHEST_BOAT.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.END_BOAT.get())
                .pattern("121")
                .pattern("111")
                .define('2', Items.END_ROD)
                .define('1', Items.SHULKER_SHELL)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.SHULKER_SHELL))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.END_BOAT.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.END_CHEST_BOAT.get())
                .pattern("1")
                .pattern("2")
                .define('1', Items.SHULKER_BOX)
                .define('2', ItemRegistry.END_BOAT.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.END_BOAT.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.END_CHEST_BOAT.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.TEMPLATE_SHROUD.get())
                .pattern("1 1")
                .pattern("111")
                .pattern("111")
                .define('1', Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.TEMPLATE_SHROUD.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.GRANITE_ANVIL.get())
                .pattern("111")
                .pattern(" 2 ")
                .pattern("333")
                .define('1', Items.POLISHED_GRANITE)
                .define('2', Items.GRANITE_WALL)
                .define('3', Items.POLISHED_GRANITE_SLAB)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.POLISHED_GRANITE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.GRANITE_ANVIL.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.ANCIENT_SMITHING_TABLE.get())
                .pattern("111")
                .pattern("323")
                .pattern("343")
                .define('1', Items.NETHERITE_INGOT)
                .define('2', Items.SMITHING_TABLE)
                .define('3', Items.GOLD_INGOT)
                .define('4', Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.SMITHING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.ANCIENT_SMITHING_TABLE.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.EASTER_BUNNY_EGG.get())
                .requires(SMCTags.EASTER_BUNNY_EGG_COMPONENTS)
                .requires(ItemRegistry.PERKIN_INGOT.get())
                .requires(SMCTags.EASTER_BUNNY_EGG_COMPONENTS)
                .requires(Items.TOTEM_OF_UNDYING)
                .requires(Items.RABBIT_SPAWN_EGG)
                .requires(Items.TOTEM_OF_UNDYING)
                .requires(ItemRegistry.BROCCOLI.get())
                .requires(SMCTags.EASTER_BUNNY_EGG_COMPONENTS)
                .requires(ItemRegistry.FROSTIUM_INGOT.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.EASTER_BUNNY_EGG.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.CHICKEN_HARVESTER_EGG.get())
                .pattern(" 1 ")
                .pattern("121")
                .pattern(" 1 ")
                .define('1', ItemRegistry.CHICKEN_CHOP.get())
                .define('2', Items.EGG)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.CHICKEN_HARVESTER_EGG.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.SALT_FISH_BUCKET.get())
                .requires(Items.TOTEM_OF_UNDYING)
                .requires(Items.WATER_BUCKET)
                .requires(ItemRegistry.SALT_FISH.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.SALT_FISH.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.SALT_FISH_BUCKET.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.SPEAR_GUN.get())
                .pattern("123")
                .pattern("45 ")
                .define('1', ItemTags.WOOL)
                .define('2', Items.CROSSBOW)
                .define('3', Items.ARROW)
                .define('4', ItemTags.PLANKS)
                .define('5', Items.COPPER_INGOT)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.ICE_TEA.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.SPEAR_GUN.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.ROD_SWORD.get())
                .pattern("1")
                .pattern("2")
                .define('1', Items.END_ROD)
                .define('2', Items.LIGHTNING_ROD)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.END_ROD))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.ROD_SWORD.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.SUGARCANE_ROD.get())
                .pattern("1")
                .pattern("1")
                .pattern("1")
                .define('1', Items.SUGAR_CANE)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.SUGAR_CANE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.SUGARCANE_ROD.get().toString()));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.INFINITE_FUEL.get())
                .requires(Items.COAL_BLOCK)
                .requires(Items.BLAZE_ROD)
                .requires(Items.LAVA_BUCKET)
                .requires(Items.DRIED_KELP_BLOCK)
                .requires(Items.TOTEM_OF_UNDYING)
                .requires(ItemRegistry.STAR_DUST.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.LAVA_BUCKET))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.INFINITE_FUEL.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.SUPER_BONEMEAL.get())
                .pattern("121")
                .pattern("212")
                .pattern("121")
                .define('1', Items.BONE_MEAL)
                .define('2', ItemRegistry.STAR_DUST.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.STAR_DUST.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.SUPER_BONEMEAL.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.CHAINSAW.get())
                .pattern(" 33")
                .pattern("413")
                .pattern("24 ")
                .define('1', ItemRegistry.ZINC_AXE.get())
                .define('2', Items.REDSTONE)
                .define('3', Items.CHAIN)
                .define('4', Items.IRON_BLOCK)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.ZINC_AXE.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.CHAINSAW.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.WANTED_LIST.get())
                .requires(Items.ROTTEN_FLESH)
                .requires(ItemRegistry.TODAYS_MENU.get())
                .requires(Items.IRON_SWORD)
                .requires(Items.BONE)
                .requires(Items.GUNPOWDER)
                .requires(Items.SPIDER_EYE)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.TODAYS_MENU.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.WANTED_LIST.get().toString()));

        /*
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ItemRegistry.NETHERITE_SWISS_ARMY_KNIFE.get())
                .requires(Items.NETHERITE_AXE)
                .requires(Items.NETHERITE_PICKAXE)
                .requires(Items.NETHERITE_SWORD)
                .requires(Items.NETHERITE_SHOVEL)
                .requires(Items.NETHERITE_HOE)
                .requires(Items.NETHER_BRICK)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.NETHERITE_SWISS_ARMY_KNIFE.get().toString()));
         */
        /*
        //我想用这个，但是咪不让，反正有矿透了估摸着也不缺合金
                 */
        //我赢了
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(ItemRegistry.DIAMOND_SWISS_ARMY_KNIFE.get()), Ingredient.of(Items.NETHERITE_INGOT), RecipeCategory.TOOLS, ItemRegistry.NETHERITE_SWISS_ARMY_KNIFE.get())
                .unlocks("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.NETHERITE_INGOT))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.NETHERITE_SWISS_ARMY_KNIFE.get() + "_from_smithing"));

        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.RABBIT_HIDE), Ingredient.of(ItemRegistry.CARROT_PICKAXE.get()), Ingredient.of(Items.GOLD_INGOT), RecipeCategory.TOOLS, ItemRegistry.GOLDEN_CARROT_PICKAXE.get() )
                .unlocks("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.CARROT_PICKAXE.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.GOLDEN_CARROT_PICKAXE.get() + "_from_smithing"));

        //纹饰
        smithingTrimRecipes(ItemRegistry.NETHERITE_ARMOR_TRIM_SMITHING_TEMPLATE.get(), consumer);
        smithingTrimRecipes(ItemRegistry.IRON_ARMOR_TRIM_SMITHING_TEMPLATE.get(), consumer);
        smithingTrimRecipes(ItemRegistry.CHAINMAIL_ARMOR_TRIM_SMITHING_TEMPLATE.get(), consumer);
        smithingTrimRecipes(ItemRegistry.CHOP_ARMOR_TRIM_SMITHING_TEMPLATE.get(), consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.IRON_ARMOR_TRIM_SMITHING_TEMPLATE.get(), 2)
                .pattern("121")
                .pattern("131")
                .pattern("111")
                .define('1', Items.DIAMOND)
                .define('2', ItemRegistry.IRON_ARMOR_TRIM_SMITHING_TEMPLATE.get())
                .define('3', Items.IRON_INGOT)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.IRON_ARMOR_TRIM_SMITHING_TEMPLATE.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.IRON_ARMOR_TRIM_SMITHING_TEMPLATE.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.NETHERITE_ARMOR_TRIM_SMITHING_TEMPLATE.get(), 2)
                .pattern("121")
                .pattern("131")
                .pattern("111")
                .define('1', Items.DIAMOND)
                .define('2', ItemRegistry.NETHERITE_ARMOR_TRIM_SMITHING_TEMPLATE.get())
                .define('3', Items.ANCIENT_DEBRIS)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.NETHERITE_ARMOR_TRIM_SMITHING_TEMPLATE.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.NETHERITE_ARMOR_TRIM_SMITHING_TEMPLATE.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.CHAINMAIL_ARMOR_TRIM_SMITHING_TEMPLATE.get(), 2)
                .pattern("121")
                .pattern("131")
                .pattern("111")
                .define('1', Items.DIAMOND)
                .define('2', ItemRegistry.CHAINMAIL_ARMOR_TRIM_SMITHING_TEMPLATE.get())
                .define('3', Items.CHAIN)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.CHAINMAIL_ARMOR_TRIM_SMITHING_TEMPLATE.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.CHAINMAIL_ARMOR_TRIM_SMITHING_TEMPLATE.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.CHOP_ARMOR_TRIM_SMITHING_TEMPLATE.get(), 2)
                .pattern("121")
                .pattern("131")
                .pattern("111")
                .define('1', Items.DIAMOND)
                .define('2', ItemRegistry.CHOP_ARMOR_TRIM_SMITHING_TEMPLATE.get())
                .define('3', ItemRegistry.CHICKEN_CHOP.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.CHOP_ARMOR_TRIM_SMITHING_TEMPLATE.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.CHOP_ARMOR_TRIM_SMITHING_TEMPLATE.get().toString()));

        //饰品
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.FROSTIUM_SHACKLES.get())
                .pattern(" 11")
                .pattern("232")
                .pattern("11 ")
                .define('1', ItemRegistry.FROSTIUM_INGOT.get())
                .define('2', ItemRegistry.FROSTIUM_NUGGET.get())
                .define('3', Items.CHAIN)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.FROSTIUM_SHACKLES.get().toString()));

    }

    private static void smeltingRecipes(String name, ItemLike ingredient, ItemLike result, float experience, Consumer<FinishedRecipe> consumer) {
        String namePrefix = new ResourceLocation(StarMeowCraft.MODID, name).toString();
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), RecipeCategory.MISC, result, experience, 200)
                .unlockedBy(name, InventoryChangeTrigger.TriggerInstance.hasItems(ingredient))
                .save(consumer, namePrefix + "_from_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ingredient), RecipeCategory.MISC, result, experience, 100)
                .unlockedBy(name, InventoryChangeTrigger.TriggerInstance.hasItems(ingredient))
                .save(consumer, namePrefix + "_from_blasting");
    }



    private static void cookingRecipes(String name, ItemLike ingredient, ItemLike result, float experience, Consumer<FinishedRecipe> consumer, Boolean allowsSmelting) {
        String namePrefix = new ResourceLocation(StarMeowCraft.MODID, name).toString();
        if(allowsSmelting){
            SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), RecipeCategory.MISC, result, experience, 200)
                    .unlockedBy(name, InventoryChangeTrigger.TriggerInstance.hasItems(ingredient))
                    .save(consumer, namePrefix + "_from_smelting");
        }
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(ingredient), RecipeCategory.MISC, result, experience, 100)
                .unlockedBy(name, InventoryChangeTrigger.TriggerInstance.hasItems(ingredient))
                .save(consumer, namePrefix + "_from_smoking");
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ingredient), RecipeCategory.MISC, result, 0, 600)
                .unlockedBy(name, InventoryChangeTrigger.TriggerInstance.hasItems(ingredient))
                .save(consumer, namePrefix + "_from_campfire");
    }

    private static void smithingTrimRecipes(ItemLike ingredient, Consumer<FinishedRecipe> consumer) {
        SmithingTrimRecipeBuilder.smithingTrim(Ingredient.of(ingredient), Ingredient.of(ItemTags.TRIMMABLE_ARMOR), Ingredient.of(ItemTags.TRIM_MATERIALS), RecipeCategory.TOOLS)
                .unlocks("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ingredient))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ingredient + "_smithing_trim"));
    }

    private static void blockIngotNuggetConvertingRecipes(ItemLike block, ItemLike ingot, ItemLike nugget, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, block)
                .pattern("111")
                .pattern("111")
                .pattern("111")
                .define('1', ingot)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, block.toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, nugget, 9)
                .requires(ingot)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, nugget.toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ingot)
                .pattern("111")
                .pattern("111")
                .pattern("111")
                .define('1', nugget)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ingot + "_from_nugget"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ingot, 9)
                .requires(block)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ingot + "_from_block"));

    }

    private static void stoneBricksRecipes(ItemLike block, ItemLike stairs, ItemLike slab, ItemLike wall, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, stairs, 4)
                .pattern("1  ")
                .pattern("11 ")
                .pattern("111")
                .define('1', block)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(block))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, stairs.toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, stairs, 4)
                .pattern("  1")
                .pattern(" 11")
                .pattern("111")
                .define('1', block)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(block))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, stairs + "_1"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, slab, 6)
                .pattern("111")
                .define('1', block)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(block))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, slab.toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, wall, 6)
                .pattern("111")
                .pattern("111")
                .define('1', block)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(block))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, wall.toString()));
    }

    private static void stoneBricksCuttingRecipes(ItemLike block, ItemLike stairs, ItemLike slab, ItemLike wall, String info, Consumer<FinishedRecipe> consumer) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(block), RecipeCategory.DECORATIONS, slab, 2)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(block))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, slab + info));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(block), RecipeCategory.DECORATIONS, stairs)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(block))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, stairs + info));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(block), RecipeCategory.DECORATIONS, wall)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(block))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, wall + info));
    }

    private static void furPuring(ItemLike input, ItemLike output, Consumer<FinishedRecipe> consumer){
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output)
                .requires(ItemRegistry.PURE_FOX_FUR.get())
                .requires(input)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, output + "_puring"));
    }

    private static void furPuring(TagKey<Item> input, ItemLike output, Consumer<FinishedRecipe> consumer){
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output)
                .requires(ItemRegistry.PURE_FOX_FUR.get())
                .requires(input)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, output + "_puring"));
    }
}
