package com.starmeow.smc.init;

import com.starmeow.smc.StarMeowCraft;
import com.starmeow.smc.blockentities.FridgeBlockEntity;
import com.starmeow.smc.blockentities.SaltFishBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, StarMeowCraft.MODID);

    public static final RegistryObject<BlockEntityType<SaltFishBlockEntity>> SALT_FISH = BLOCK_ENTITIES.register("salt_fish",
            () -> BlockEntityType.Builder.of(SaltFishBlockEntity::new, BlockRegistry.SALT_FISH_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<FridgeBlockEntity>> FRIDGE = BLOCK_ENTITIES.register("fridge",
            () -> BlockEntityType.Builder.of(FridgeBlockEntity::new, BlockRegistry.FRIDGE.get()).build(null));
}
