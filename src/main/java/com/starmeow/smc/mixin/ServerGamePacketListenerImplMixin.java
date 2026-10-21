package com.starmeow.smc.mixin;

import com.starmeow.smc.helper.CuriosHelper;
import com.starmeow.smc.init.EnchantmentRegistry;
import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {

    @Inject(method = "handlePlayerAction", at = @At("HEAD"), cancellable = true)
    private void smc$isInWaterWhenHasEffect(ServerboundPlayerActionPacket packet, CallbackInfo cir) {
        ServerboundPlayerActionPacket.Action action = packet.getAction();
        ServerGamePacketListenerImpl listener = (ServerGamePacketListenerImpl)(Object)this;
        Player player = listener.player;
        ItemStack stack = player.getMainHandItem();
        if(action == ServerboundPlayerActionPacket.Action.DROP_ITEM){
            if(EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.ANCHOR_HOLD.get(), stack) > 0){
                cir.cancel();
                listener.player.initMenu(listener.player.inventoryMenu);
            }
        }
    }

    @Shadow
    public ServerPlayer player;
    @Redirect(
            method = "handleSetCreativeModeSlot",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerPlayerGameMode;isCreative()Z"
            )
    )
    private boolean smc$allowInstabuildCurio(ServerPlayerGameMode gameMode) {
        return gameMode.isCreative() || this.player.getAbilities().instabuild;
    }
}
