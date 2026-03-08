package com.starmeow.smc.events;

import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber
public class TradeEvents {
    @SubscribeEvent
    public static void addVillagerTrades(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfession.WEAPONSMITH) {
            List<VillagerTrades.ItemListing> trade5 = event.getTrades().get(5);
            trade5.add(new BasicItemListing(
                    new ItemStack(Items.EMERALD, 64),
                    new ItemStack(ItemRegistry.LUCKY_CLOVER.get(), 4),
                    new ItemStack(ItemRegistry.KNIFE.get(), 1),
                    1,
                    30,
                    0
            ));
        }



    }
}
