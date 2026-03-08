package com.starmeow.smc.mixin;
import com.starmeow.smc.helper.EntityHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Boat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow @Nullable public abstract Entity getVehicle();

    @Inject(method = "fireImmune", at = @At("HEAD"), cancellable = true)
    private void smc$fireImmune(CallbackInfoReturnable<Boolean> cir) {
        if (this.getVehicle() instanceof Boat boat && EntityHelper.isFireProofBoat(boat.getVariant())) {
            cir.setReturnValue(true);
        }
    }
}
