package com.starmeow.smc.init;

import com.mojang.serialization.Codec;
import com.starmeow.smc.StarMeowCraft;
import com.starmeow.smc.loot.SMCAddLootModifier;
import com.starmeow.smc.loot.SMCLootModifier;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class LootModifierRegistry {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS , StarMeowCraft.MODID);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_ITEM = LOOT_MODIFIER.register("add_item", SMCLootModifier.CODEC);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_LOOT_TABLE = LOOT_MODIFIER.register("add_loot_table", SMCAddLootModifier.CODEC);
}
