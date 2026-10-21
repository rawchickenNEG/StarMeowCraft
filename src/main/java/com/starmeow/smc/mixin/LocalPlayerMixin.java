package com.starmeow.smc.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.starmeow.smc.helper.CuriosHelper;
import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin {

    @ModifyExpressionValue(
            method = "aiStep",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;canElytraFly(Lnet/minecraft/world/entity/LivingEntity;)Z"
            )
    )
    private boolean smc$modifyCanFly(boolean original) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        return original || CuriosHelper.hasCharm(player, ItemRegistry.DIVINE_WING.get());
    }
}
