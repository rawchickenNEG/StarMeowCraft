package com.starmeow.smc.init;


import com.starmeow.smc.StarMeowCraft;
import com.starmeow.smc.recipe.ChangingWeaponSkinRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeSerializerRegistry {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, StarMeowCraft.MODID);

    public static final RegistryObject<RecipeSerializer<ChangingWeaponSkinRecipe>> CHANGING_WEAPON_SKIN =
            SERIALIZERS.register("changing_weapon_skin", () -> ChangingWeaponSkinRecipe.SERIALIZER);

    public static void register(IEventBus bus) {
        SERIALIZERS.register(bus);
    }
}