package com.starmeow.smc.init;

import com.starmeow.smc.StarMeowCraft;
import com.starmeow.smc.packet.SwordSlashC2S;
import com.starmeow.smc.packet.TotemActivatePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkRegistry {
    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel CHANNEL = net.minecraftforge.network.NetworkRegistry.newSimpleChannel(
            new ResourceLocation(StarMeowCraft.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int id = 0;

    public static void register() {
        CHANNEL.registerMessage(
                id++,
                TotemActivatePacket.class,
                TotemActivatePacket::encode,
                TotemActivatePacket::decode,
                TotemActivatePacket::handle
        );
        CHANNEL.messageBuilder(SwordSlashC2S.class, id++)
                .encoder(SwordSlashC2S::encode)
                .decoder(SwordSlashC2S::decode)
                .consumerMainThread(SwordSlashC2S::handle)
                .add();
    }

    public static void sendTotemActivate(ServerPlayer player, ItemStack stack) {
        CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> player),
                new TotemActivatePacket(stack)
        );
    }

    public static void sendToServer(Object msg) {
        CHANNEL.sendToServer(msg);
    }
}