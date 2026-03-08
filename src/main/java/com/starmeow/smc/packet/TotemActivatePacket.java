package com.starmeow.smc.packet;


import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TotemActivatePacket {

    private final ItemStack stack;

    public TotemActivatePacket(ItemStack stack) {
        this.stack = stack;
    }

    public static void encode(TotemActivatePacket msg, FriendlyByteBuf buf) {
        buf.writeItem(msg.stack);
    }

    public static TotemActivatePacket decode(FriendlyByteBuf buf) {
        ItemStack stack = buf.readItem();
        return new TotemActivatePacket(stack);
    }

    public static void handle(TotemActivatePacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();

        if (ctx.getDirection().getReceptionSide().isClient()) {
            ctx.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null) {
                    mc.gameRenderer.displayItemActivation(msg.stack);
                }
            }));
        }
        ctx.setPacketHandled(true);
    }
}
