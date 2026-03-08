package com.starmeow.smc.mixin;

import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Unique
    private boolean smc$shouldProtectHealth() {
        LivingEntity self = (LivingEntity)(Object)this;
        if (!(self instanceof ServerPlayer player)) return false;
        if (player.level().isClientSide()) return false;
        if (!player.isAddedToWorld() || player.tickCount < 20) return false;

        return player.getMainHandItem().is(ItemRegistry.MINI_BEDROCK.get());
    }

    @ModifyVariable(
            method = "setHealth(F)V",
            at = @At("HEAD"),
            argsOnly = true,
            require = 0
    )
    private float smc$clampHealth(float newHealth) {
        if (!smc$shouldProtectHealth()) return newHealth;

        float cur = ((LivingEntity)(Object)this).getHealth();
        return Math.max(newHealth, cur);
    }

}