package com.starmeow.smc.packet;

import com.starmeow.smc.helper.EntityHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SwordSlashC2S {

    public static void encode(SwordSlashC2S msg, FriendlyByteBuf buf) {}
    public static SwordSlashC2S decode(FriendlyByteBuf buf) { return new SwordSlashC2S(); }

    public static void handle(SwordSlashC2S msg, Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ServerPlayer player = ctx.getSender();
        if (player == null) return;

        ctx.enqueueWork(() -> {
            EntityHelper.shootSwordProjectileByPlayer(player);
        });

        ctx.setPacketHandled(true);
    }
}