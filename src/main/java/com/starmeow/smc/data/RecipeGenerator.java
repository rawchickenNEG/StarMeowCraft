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
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.FROST_BERRIES.get())
                .pattern("111")
                .pattern("121")
                .pattern("111")
                .define('1', ItemRegistry.FROSTIUM_NUGGET.get())
                .define('2', Items.SWEET_BERRIES)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.FROST_BERRIES.get().toString()));
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
        //西兰花工具
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.BROCCOLI_BOOTS.get())
                .pattern("1 1")
                .pattern("1 1")
                .define('1', ItemRegistry.BROCCOLI.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.BROCCOLI_BOOTS.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.BROCCOLI_FISHING_ROD.get())
                .pattern("  1")
                .pattern(" 12")
                .pattern("1 3")
                .define('3', ItemRegistry.BROCCOLI.get())
                .define('2', Items.VINE)
                .define('1', Items.BAMBOO)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.BROCCOLI_FISHING_ROD.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.BROCCOLI_HOE.get())
                .pattern("11")
                .pattern(" 2")
                .pattern(" 2")
                .define('1', ItemRegistry.BROCCOLI.get())
                .define('2', Items.BAMBOO)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.BROCCOLI_HOE.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.BROCCOLI_HOE.get())
                .pattern("11")
                .pattern("2 ")
                .pattern("2 ")
                .define('1', ItemRegistry.BROCCOLI.get())
                .define('2', Items.BAMBOO)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.BROCCOLI_HOE.get() + "_2"));

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

        //霜冻矿物块，锭，粒转化
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.FROSTIUM_BLOCK.get())
                .pattern("111")
                .pattern("111")
                .pattern("111")
                .define('1', ItemRegistry.FROSTIUM_INGOT.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.FROSTIUM_BLOCK.get().toString()));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.FROSTIUM_NUGGET.get(), 9)
                .requires(ItemRegistry.FROSTIUM_INGOT.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.FROSTIUM_NUGGET.get().toString()));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.FROSTIUM_INGOT.get())
                .pattern("111")
                .pattern("111")
                .pattern("111")
                .define('1', ItemRegistry.FROSTIUM_NUGGET.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.FROSTIUM_BLOCK.get() + "_from_nugget"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.FROSTIUM_INGOT.get(), 9)
                .requires(ItemRegistry.FROSTIUM_BLOCK.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.FROSTIUM_INGOT.get() + "_from_block"));
        //珀金矿物块，锭，粒转化
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.PERKIN_BLOCK.get())
                .pattern("111")
                .pattern("111")
                .pattern("111")
                .define('1', ItemRegistry.PERKIN_INGOT.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.PERKIN_BLOCK.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.PERKIN_NUGGET.get(), 9)
                .requires(ItemRegistry.PERKIN_INGOT.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.PERKIN_NUGGET.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.PERKIN_INGOT.get())
                .pattern("111")
                .pattern("111")
                .pattern("111")
                .define('1', ItemRegistry.PERKIN_NUGGET.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.PERKIN_BLOCK.get() + "_from_nugget"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.PERKIN_INGOT.get(), 9)
                .requires(ItemRegistry.PERKIN_BLOCK.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.PERKIN_INGOT.get() + "_from_block"));
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
        //矿物冶炼
        smeltingRecipes("blue_ice_frostium_ore_to_ingot", ItemRegistry.BLUE_ICE_FROSTIUM_ORE.get(), ItemRegistry.FROSTIUM_INGOT.get(), 0.35F, consumer);
        smeltingRecipes("frostium_ore_to_ingot", ItemRegistry.FROSTIUM_ORE.get(), ItemRegistry.FROSTIUM_INGOT.get(), 0.35F, consumer);
        smeltingRecipes("raw_frostium_to_ingot", ItemRegistry.RAW_FROSTIUM.get(), ItemRegistry.FROSTIUM_INGOT.get(), 0.35F, consumer);
        smeltingRecipes("perkin_ore_to_ingot", ItemRegistry.PERKIN_ORE.get(), ItemRegistry.PERKIN_INGOT.get(), 0.35F, consumer);
        smeltingRecipes("deepslate_perkin_ore_to_ingot", ItemRegistry.DEEPSLATE_PERKIN_ORE.get(), ItemRegistry.PERKIN_INGOT.get(), 0.35F, consumer);
        smeltingRecipes("raw_perkin_to_ingot", ItemRegistry.RAW_PERKIN.get(), ItemRegistry.PERKIN_INGOT.get(), 0.35F, consumer);
        //食物烘焙
        cookingRecipes("cooked_dirt", Items.DIRT, ItemRegistry.COOKED_DIRT.get(), 0.2F, consumer, true);
        cookingRecipes("spicy_bar", Items.ROTTEN_FLESH, ItemRegistry.SPICY_BAR.get(), 0.2F, consumer, false);
        cookingRecipes("jerky", Items.COOKED_BEEF, ItemRegistry.JERKY.get(), 0.2F, consumer, false);
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
                .requires(ItemRegistry.GRIMOIRE.get())
                .requires(ItemRegistry.KATANA.get())
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.DEVOUR_SWORD.get().toString()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.WATER_DISPENSER.get())
                .requires(Items.WATER_BUCKET)
                .requires(Items.GLASS)
                .requires(Items.LAVA_BUCKET)
                .requires(Items.HOPPER)
                .requires(Items.IRON_BLOCK)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.ICE_TEA.get()))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.WATER_DISPENSER.get().toString()));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.GOLDEN_BOAT.get())
                .pattern("121")
                .pattern("111")
                .define('1', Items.GOLD_INGOT)
                .define('2', Items.GOLDEN_SHOVEL)
                .unlockedBy("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GOLD_INGOT))
                .save(consumer, new ResourceLocation(StarMeowCraft.MODID, ItemRegistry.GOLDEN_BOAT.get().toString()));
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



}
