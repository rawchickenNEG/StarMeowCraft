package com.starmeow.smc.recipe;

import com.starmeow.smc.init.ItemRegistry;
import com.starmeow.smc.init.RecipeSerializerRegistry;
import com.starmeow.smc.items.SwissArmyKnife;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SwitchEnchantGlowRecipe extends CustomRecipe {
    public static final SimpleCraftingRecipeSerializer<SwitchEnchantGlowRecipe> SERIALIZER = new SimpleCraftingRecipeSerializer<>(SwitchEnchantGlowRecipe::new);

    public SwitchEnchantGlowRecipe(ResourceLocation id, CraftingBookCategory ctg) {
        super(id, ctg);
    }

    @Override
    public ItemStack assemble(CraftingContainer inv, RegistryAccess access) {
        List<ItemStack> stackList = new ArrayList<ItemStack>();

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack slotStack = inv.getItem(i);

            if (!slotStack.isEmpty()) {
                stackList.add(slotStack);
            }
        }

        if (stackList.size() == 2){
            if(getMagicItem(stackList) != null && getItemStack(stackList) != null && getMagicItem(stackList).is(ItemRegistry.PURE_FOX_FUR.get())){
                if(getItemStack(stackList).isEnchantable() || getItemStack(stackList).isEnchanted()){
                    ItemStack appliedItem = stackList.get(0).is(ItemRegistry.PURE_FOX_FUR.get()) ? stackList.get(1).copy() : stackList.get(0).copy();
                    CompoundTag tag = appliedItem.getOrCreateTag();
                    if(tag.contains("SMCShouldGlow")){
                        tag.putBoolean("SMCShouldGlow", !tag.getBoolean("SMCShouldGlow"));
                    }else{
                        tag.putBoolean("SMCShouldGlow", false);
                    }
                    return appliedItem;
                }
            }
        }

        return ItemStack.EMPTY;
    }

    private static @Nullable ItemStack getItemStack(List<ItemStack> stackList) {
        if (stackList.get(0).is(ItemRegistry.PURE_FOX_FUR.get())) {
            return stackList.get(1);
        }
        else if (stackList.get(1).is(ItemRegistry.PURE_FOX_FUR.get())) {
            return stackList.get(0);
        }

        return null;
    }

    private static @Nullable ItemStack getMagicItem(List<ItemStack> stackList) {
        if (stackList.get(0).is(ItemRegistry.PURE_FOX_FUR.get())) {
            return stackList.get(0);
        }
        else if (stackList.get(1).is(ItemRegistry.PURE_FOX_FUR.get())) {
            return stackList.get(1);
        }

        return null;
    }

    @Override
    public boolean matches(CraftingContainer inv, Level world) {
        List<ItemStack> stackList = new ArrayList<>();

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack slotStack = inv.getItem(i);

            if (!slotStack.isEmpty()) {
                stackList.add(slotStack);
            }
        }

        if (stackList.size() == 2){
            if(getMagicItem(stackList) != null && getItemStack(stackList) != null && getMagicItem(stackList).is(ItemRegistry.PURE_FOX_FUR.get())){
                return getItemStack(stackList).isEnchantable() || getItemStack(stackList).isEnchanted();
            }
        }
        return false;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv) {
        NonNullList<ItemStack> remainingItems = NonNullList.withSize(inv.getContainerSize(), ItemStack.EMPTY);
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (stack.is(ItemRegistry.PURE_FOX_FUR.get())) {
                if (stack.hasCraftingRemainingItem()) {
                    remainingItems.set(i, stack.getCraftingRemainingItem());
                }
            }
        }
        return remainingItems;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializerRegistry.SWITCH_GLOWING.get();
    }
}