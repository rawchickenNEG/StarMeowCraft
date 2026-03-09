package com.starmeow.smc.packet;

import com.starmeow.smc.helper.EntityHelper;
import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CoffeeGetC2S {

    public static void encode(CoffeeGetC2S msg, FriendlyByteBuf buf) {}
    public static CoffeeGetC2S decode(FriendlyByteBuf buf) { return new CoffeeGetC2S(); }

    public static void handle(CoffeeGetC2S msg, Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ServerPlayer player = ctx.getSender();
        if (player == null) return;

        ctx.enqueueWork(() -> {
            ItemStack item = new ItemStack(ItemRegistry.COFFEE.get());
            if (!player.getInventory().add(item)) {
                player.drop(item, false);
            }
        });

        ctx.setPacketHandled(true);
    }
}