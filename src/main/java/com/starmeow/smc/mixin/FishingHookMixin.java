package com.starmeow.smc.mixin;

import com.starmeow.smc.entities.projectiles.SpearHook;
import net.minecraft.world.entity.projectile.FishingHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(FishingHook.class)
public class FishingHookMixin {
    @ModifyConstant(
            method = "shouldStopFishing",
            constant = @Constant(doubleValue = 1024.0D)
    )
    private double smc$extendSpearRange(double original) {
        return (Object)this instanceof SpearHook ? 4096.0D : original;
    }
}
