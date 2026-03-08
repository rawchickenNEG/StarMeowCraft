package com.starmeow.smc.events;

import com.starmeow.smc.init.ItemRegistry;
import com.starmeow.smc.items.DevourSword;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = "smc", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MenuEvents {

    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        if (event.getPlayer().level().isClientSide) return;

        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();
        ItemStack out = left.copy();

        if (right.isEmpty()) return;

        if(left.is(ItemRegistry.SWISS_ARMY_KNIFE.get()) || left.is(ItemRegistry.DIAMOND_SWISS_ARMY_KNIFE.get()) || left.is(ItemRegistry.NETHERITE_SWISS_ARMY_KNIFE.get())){
            CompoundTag tag = out.getOrCreateTag();
            boolean hasShears = tag.getBoolean("SMCSwissKnifeShears");
            boolean hasBrush = tag.getBoolean("SMCSwissKnifeBrush");
            if(right.is(Items.SHEARS) && !hasShears){
                tag.putBoolean("SMCSwissKnifeShears", true);
            }
            if(right.is(Items.BRUSH) && !hasBrush){
                tag.putBoolean("SMCSwissKnifeBrush", true);
            }
            int cost = 1;
            String name = event.getName();
            if (name != null) {
                if (name.isEmpty()){
                    out.resetHoverName();
                }
                else {
                    out.setHoverName(Component.literal(name));
                    cost = 2;
                }
            }
            event.setCost(cost);
            event.setMaterialCost(1);
            event.setOutput(out);
        }



    }

}
