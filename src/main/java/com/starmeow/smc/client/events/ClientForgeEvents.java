package com.starmeow.smc.client.events;


import com.starmeow.smc.StarMeowCraft;
import com.starmeow.smc.init.ItemRegistry;
import com.starmeow.smc.init.NetworkRegistry;
import com.starmeow.smc.packet.SwordSlashC2S;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = StarMeowCraft.MODID, value = Dist.CLIENT)
public class ClientForgeEvents {

    private static int lastSentTick = -999999;

    public static boolean isValidWeapon(ItemStack stack) {
        return stack.is(ItemRegistry.DEVOUR_SWORD.get());
    }

    public static void sendOncePerTick(Player player) {
        int now = player.tickCount;
        if (now == lastSentTick) return;
        lastSentTick = now;

        NetworkRegistry.sendToServer(new SwordSlashC2S());
    }

    @SubscribeEvent
    public static void onLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
        Player player = event.getEntity();
        if (player == null) return;

        ItemStack stack = player.getMainHandItem();
        if (!isValidWeapon(stack)) return;

        if (Minecraft.getInstance().player != player) return;

        sendOncePerTick(player);
    }
}
