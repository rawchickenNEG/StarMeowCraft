package com.starmeow.smc.mixin;
import com.starmeow.smc.helper.EntityHelper;
import com.starmeow.smc.init.PotionEffectRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.Objects;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow @Nullable public abstract Entity getVehicle();

    @Shadow public abstract Vec3 trackingPosition();

    @Inject(method = "fireImmune", at = @At("HEAD"), cancellable = true)
    private void smc$fireImmune(CallbackInfoReturnable<Boolean> cir) {
        if (this.getVehicle() instanceof Boat boat && EntityHelper.isFireProofBoat(boat.getVariant())) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "isInWater", at = @At("HEAD"), cancellable = true)
    private void smc$isInWaterWhenHasEffect(CallbackInfoReturnable<Boolean> cir) {
      final Entity entity = (Entity)(Object)this;
      if (!(entity instanceof LivingEntity living)) {
        return;
      }
      
      // HACK illaujaq: Fix #8.
      final boolean hasEffect = Objects.nonNull(living.getActiveEffectsMap()) &&
          living.hasEffect(PotionEffectRegistry.OCEAN_AVATAR.get());
      if (!hasEffect) {
        return;
      }
      
      if(living instanceof Player player){
        if(player.isSwimming()){
          cir.setReturnValue(true);
        }
      } else {
        cir.setReturnValue(true);
      }
    }

    @Inject(method = "isInRain", at = @At("HEAD"), cancellable = true)
    private void smc$isInRainWhenHasEffect(CallbackInfoReturnable<Boolean> cir) {
        Entity entity = (Entity)(Object)this;
        if(entity instanceof LivingEntity living && living.hasEffect(PotionEffectRegistry.OCEAN_AVATAR.get())){
            cir.setReturnValue(true);
        }
    }
}
