package com.starmeow.smc.recipe;

import com.starmeow.smc.init.ItemRegistry;
import com.starmeow.smc.init.RecipeSerializerRegistry;
import com.starmeow.smc.items.DevourSword;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ChangingWeaponSkinRecipe extends CustomRecipe {
    public static final SimpleCraftingRecipeSerializer<ChangingWeaponSkinRecipe> SERIALIZER = new SimpleCraftingRecipeSerializer<>(ChangingWeaponSkinRecipe::new);
    public static final Item CONSUME_ITEM = ItemRegistry.STAR_DUST.get();

    public ChangingWeaponSkinRecipe(ResourceLocation id, CraftingBookCategory ctg) {
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

        if (stackList.size() == 3)
            if (stackList.get(0).getItem() instanceof DevourSword || stackList.get(1).getItem() instanceof DevourSword || stackList.get(2).getItem() instanceof DevourSword)
                if (stackList.get(0).getItem() == CONSUME_ITEM || stackList.get(1).getItem() == CONSUME_ITEM || stackList.get(2).getItem() == CONSUME_ITEM)
                    if(!stackList.get(0).isEmpty() || !stackList.get(1).isEmpty() || !stackList.get(2).isEmpty()){
                        ItemStack sword = stackList.get(0).getItem() instanceof DevourSword ? stackList.get(0).copy() : (stackList.get(1).getItem() instanceof DevourSword ? stackList.get(1).copy() : stackList.get(2).copy());

                        ItemStack skin = getItemStack(stackList);
                        if(skin != null) DevourSword.setSkinItem(sword, skin);

                    return sword;
                }

        return ItemStack.EMPTY;
    }

    private static @Nullable ItemStack getItemStack(List<ItemStack> stackList) {
        ItemStack skin = null;
        if(stackList.get(0).getItem() instanceof DevourSword && stackList.get(1).getItem() == CONSUME_ITEM){
            skin = stackList.get(2);
        }
        else if(stackList.get(1).getItem() instanceof DevourSword && stackList.get(0).getItem() == CONSUME_ITEM){
            skin = stackList.get(2);
        }
        else if(stackList.get(1).getItem() instanceof DevourSword && stackList.get(2).getItem() == CONSUME_ITEM){
            skin = stackList.get(0);
        }
        else if(stackList.get(2).getItem() instanceof DevourSword && stackList.get(1).getItem() == CONSUME_ITEM){
            skin = stackList.get(0);
        }
        else if(stackList.get(0).getItem() instanceof DevourSword && stackList.get(2).getItem() == CONSUME_ITEM){
            skin = stackList.get(1);
        }
        else if(stackList.get(2).getItem() instanceof DevourSword && stackList.get(0).getItem() == CONSUME_ITEM){
            skin = stackList.get(1);
        }
        return skin == null ? new ItemStack(ItemRegistry.DEVOUR_SWORD.get()) : skin;
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

        if (stackList.size() == 3)
            if (stackList.get(0).getItem() instanceof DevourSword || stackList.get(1).getItem() instanceof DevourSword || stackList.get(2).getItem() instanceof DevourSword)
                if (stackList.get(0).getItem() == CONSUME_ITEM || stackList.get(1).getItem() == CONSUME_ITEM || stackList.get(2).getItem() == CONSUME_ITEM)
                    return true;

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