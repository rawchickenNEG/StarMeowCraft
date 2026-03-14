package com.starmeow.smc.recipe;

import com.starmeow.smc.init.ItemRegistry;
import com.starmeow.smc.init.RecipeSerializerRegistry;
import com.starmeow.smc.items.DevourSword;
import com.starmeow.smc.items.SwissArmyKnife;
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

public class AddKnifeToolsRecipe extends CustomRecipe {
    public static final SimpleCraftingRecipeSerializer<AddKnifeToolsRecipe> SERIALIZER = new SimpleCraftingRecipeSerializer<>(AddKnifeToolsRecipe::new);
    public static final List<Item> CONSUME_ITEMS = Arrays.asList(Items.SHEARS, Items.BRUSH);

    public AddKnifeToolsRecipe(ResourceLocation id, CraftingBookCategory ctg) {
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

        if (stackList.size() == 2)
            if (stackList.get(0).getItem() instanceof SwissArmyKnife || stackList.get(1).getItem() instanceof SwissArmyKnife)
                if (CONSUME_ITEMS.contains(stackList.get(0).getItem()) || CONSUME_ITEMS.contains(stackList.get(1).getItem())){
                    ItemStack sword = stackList.get(0).getItem() instanceof SwissArmyKnife ? stackList.get(0).copy() :stackList.get(1).copy();
                    ItemStack newTool = getItemStack(stackList);
                    if(newTool != null){
                        CompoundTag tag = sword.getOrCreateTag();
                        boolean hasShears = tag.getBoolean("SMCSwissKnifeShears");
                        boolean hasBrush = tag.getBoolean("SMCSwissKnifeBrush");
                        if(newTool.is(Items.SHEARS) && !hasShears){
                            tag.putBoolean("SMCSwissKnifeShears", true);
                        }
                        if(newTool.is(Items.BRUSH) && !hasBrush){
                            tag.putBoolean("SMCSwissKnifeBrush", true);
                        }
                    }
                    return sword;
                }

        return ItemStack.EMPTY;
    }

    private static @Nullable ItemStack getItemStack(List<ItemStack> stackList) {
        return stackList.get(0).getItem() instanceof SwissArmyKnife ? stackList.get(1) : stackList.get(0);
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

        if (stackList.size() == 2)
            if (stackList.get(0).getItem() instanceof SwissArmyKnife || stackList.get(1).getItem() instanceof SwissArmyKnife)
                return CONSUME_ITEMS.contains(stackList.get(0).getItem()) || CONSUME_ITEMS.contains(stackList.get(1).getItem());

        return false;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializerRegistry.CHANGING_WEAPON_SKIN.get();
    }
}