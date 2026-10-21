package com.starmeow.smc.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MultiPlayerGameMode.class)
public abstract class MultiPlayerGameModeMixin {
    @Redirect(
            method = "handleCreativeModeItemAdd",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/GameType;isCreative()Z"
            )
    )
    private boolean smc$allowInstabuildItemAdd(GameType gameType) {
        LocalPlayer player = Minecraft.getInstance().player;
        return gameType.isCreative() || (player != null && player.getAbilities().instabuild);
    }
}
