package com.starmeow.smc.events;

import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

@Mod.EventBusSubscriber
public class HealEvents {
    @SubscribeEvent
    public static void onLivingHeal(LivingHealEvent event){
        LivingEntity entity = event.getEntity();
        Item mainhandItem = entity.getMainHandItem().getItem();
        Item offhandItem = entity.getMainHandItem().getItem();
        ArrayList<Item> items = new ArrayList<>();
        items.add(ItemRegistry.PERFROSTITE_AXE.get());
        items.add(ItemRegistry.PERFROSTITE_SHOVEL.get());
        items.add(ItemRegistry.PERFROSTITE_SWORD.get());
        items.add(ItemRegistry.PERFROSTITE_HOE.get());
        items.add(ItemRegistry.PERFROSTITE_PICKAXE.get());
        for(Item effectiveItem : items){
            if(mainhandItem == effectiveItem){
                entity.getMainHandItem().setDamageValue(entity.getMainHandItem().getDamageValue() - (int)Math.floor(event.getAmount() * 2));
            }
        }
        for(Item effectiveItem : items){
            if(offhandItem == effectiveItem){
                entity.getOffhandItem().setDamageValue(entity.getOffhandItem().getDamageValue() - (int)Math.floor(event.getAmount() * 2));
            }
        }
    }
}
