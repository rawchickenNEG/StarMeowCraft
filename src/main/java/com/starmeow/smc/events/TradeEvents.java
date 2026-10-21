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
        if (event.getType() == VillagerProfession.BUTCHER) {
            List<VillagerTrades.ItemListing> trade5 = event.getTrades().get(5);
            trade5.add(new BasicItemListing(
                    new ItemStack(Items.EMERALD, 48),
                    new ItemStack(ItemRegistry.LUCKY_CLOVER.get(), 4),
                    new ItemStack(ItemRegistry.CLEAVER.get(), 1),
                    1,
                    30,
                    0
            ));
        }
        if (event.getType() == VillagerProfession.LIBRARIAN) {
            List<VillagerTrades.ItemListing> trade5 = event.getTrades().get(5);
            trade5.add(new BasicItemListing(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(ItemRegistry.BAD_APPLE.get(), 4),
                    new ItemStack(ItemRegistry.RED_FRAME_GLASSES.get(), 1),
                    1,
                    30,
                    0
            ));
        }
        if (event.getType() == VillagerProfession.FARMER) {
            List<VillagerTrades.ItemListing> trade5 = event.getTrades().get(5);
            trade5.add(new BasicItemListing(
                    new ItemStack(Items.EMERALD, 11),
                    new ItemStack(ItemRegistry.BROCCOLI.get(), 4),
                    new ItemStack(ItemRegistry.GLITCH_PARTICLE.get(), 1),
                    1,
                    30,
                    0
            ));
        }
        if (event.getType() == VillagerProfession.BUTCHER) {
            List<VillagerTrades.ItemListing> trade5 = event.getTrades().get(5);
            trade5.add(new BasicItemListing(
                    new ItemStack(Items.EMERALD, 32),
                    new ItemStack(ItemRegistry.GIANT_CARROT.get(), 1),
                    new ItemStack(ItemRegistry.TODAYS_MENU.get(), 1),
                    1,
                    30,
                    0
            ));
        }
    }
}
