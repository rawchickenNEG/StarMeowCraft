package com.starmeow.smc.mixin;

import com.starmeow.smc.helper.CuriosHelper;
import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin {
    @Inject(method = "tryToStartFallFlying", at = @At("HEAD"), cancellable = true)
    private void smc$changeFallFlyingState(CallbackInfoReturnable<Boolean> cir){
        Player player = (Player)(Object)this;
        if (!player.onGround() && !player.isFallFlying() && !player.isInWater() && !player.hasEffect(MobEffects.LEVITATION)) {
            if (CuriosHelper.hasCharm(player, ItemRegistry.DIVINE_WING.get())) {

                System.out.println("SMC: tryToStartFallFlying");

                player.startFallFlying();
                cir.setReturnValue(true);
            }
        }
    }
}
