package com.starmeow.smc.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EndBoatControlPacket {
    private final boolean up;
    private final boolean down;

    public EndBoatControlPacket(boolean up, boolean down) {
        this.up = up;
        this.down = down;
    }

    public static void encode(EndBoatControlPacket msg, FriendlyByteBuf buf) {
        buf.writeBoolean(msg.up);
        buf.writeBoolean(msg.down);
    }

    public static EndBoatControlPacket decode(FriendlyByteBuf buf) {
        return new EndBoatControlPacket(buf.readBoolean(), buf.readBoolean());
    }

    public static void handle(EndBoatControlPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;

            if (!(player.getVehicle() instanceof Boat boat)) return;

            if (boat.getVariant() != Boat.Type.byName("end")) return;

            Vec3 v = boat.getDeltaMovement();

            if (msg.up && !msg.down) {
                boat.setDeltaMovement(v.x, 0.45, v.z);
            }
            else if (msg.down && !msg.up) {
                boat.setDeltaMovement(v.x, -0.45, v.z);
            }
            else {
                boat.setDeltaMovement(v.x, v.y * 0.9, v.z);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}