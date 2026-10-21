package com.starmeow.smc.packet;

import com.starmeow.smc.helper.CuriosHelper;
import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class WingBoostC2S {

    public static void encode(WingBoostC2S msg, FriendlyByteBuf buf) {}
    public static WingBoostC2S decode(FriendlyByteBuf buf) { return new WingBoostC2S(); }

    public static void handle(WingBoostC2S msg, Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ServerPlayer player = ctx.getSender();
        if (player == null) return;

        ctx.enqueueWork(() -> {
            if(player.getFallFlyingTicks() > 10 && player.getFoodData().getFoodLevel() > 6){
                player.setDeltaMovement(player.getLookAngle().x() * 1.5F, player.getLookAngle().y() * 1.5F, player.getLookAngle().z() * 1.5F);
                player.hurtMarked = true;
                if(!player.getAbilities().instabuild && !CuriosHelper.hasCharm(player, ItemRegistry.DIVINE_HALO.get()) && !player.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.DIVINE_HALO.get())){
                    player.getFoodData().addExhaustion(4.0f);
                }
                player.level().playSound(null, player, SoundEvents.SNOW_PLACE, SoundSource.PLAYERS, 1.0F, 1.0F);
                if(player.level() instanceof ServerLevel serverLevel){
                    serverLevel.sendParticles(ParticleTypes.CLOUD, player.getX(), player.getY() + player.getEyeHeight(), player.getZ(), 25, 0.0D, 0.0D, 0.0D, 0.3D);
                }
                player.getCooldowns().addCooldown(ItemRegistry.DIVINE_WING.get(), 20);
            }
        });

        ctx.setPacketHandled(true);
    }
}