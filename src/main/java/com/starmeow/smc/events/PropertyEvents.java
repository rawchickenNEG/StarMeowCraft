package com.starmeow.smc.events;

import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber
public class PropertyEvents {
    @SubscribeEvent
    public static void addComposterItems(FMLCommonSetupEvent event) {
        ComposterBlock.COMPOSTABLES.put(ItemRegistry.BROCCOLI_SEED.get(), 0.3f);
        ComposterBlock.COMPOSTABLES.put(ItemRegistry.PEA.get(), 0.3f);
        ComposterBlock.COMPOSTABLES.put(ItemRegistry.BROCCOLI.get(), 0.65f);
        ComposterBlock.COMPOSTABLES.put(ItemRegistry.PEA_POD.get(), 0.65f);
    }
}
