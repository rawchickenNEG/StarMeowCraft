package com.starmeow.smc.mixin;

import com.starmeow.smc.config.Config;
import com.starmeow.smc.helper.CuriosHelper;
import com.starmeow.smc.init.EnchantmentRegistry;
import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Unique
    private boolean smc$shouldProtectHealth() {
        LivingEntity self = (LivingEntity)(Object)this;
        if (!(self instanceof ServerPlayer player)) return false;
        if (player.level().isClientSide()) return false;
        if (!player.isAddedToWorld() || player.tickCount < 20) return false;

        return player.getMainHandItem().is(ItemRegistry.MINI_BEDROCK.get()) && Config.ENABLE_BEDROCK.get();
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

    @Inject(
            method = "getArmorCoverPercentage",
            at = @At("HEAD"),
            cancellable = true
    )
    private void smc$changeCoverPercentage(CallbackInfoReturnable<Float> cir) {
        LivingEntity self = (LivingEntity)(Object)this;
        Iterable<ItemStack> iterable = self.getArmorSlots();
        int i = 0;
        int j = 0;
        boolean hasInvisibleArmor = false;
        for(ItemStack itemstack : iterable) {
            if (!itemstack.isEmpty()) {
                if(itemstack.getEnchantmentLevel(EnchantmentRegistry.INVISIBLE_ARMOR.get()) != 0){
                    hasInvisibleArmor = true;
                }else{
                    ++j;
                }
            }
            ++i;
        }
        if(hasInvisibleArmor){
            cir.setReturnValue(i > 0 ? (float)j / (float)i : 0.0F);
        }
    }

    @Inject(
            method = "updateFallFlying",
            at = @At("HEAD"),
            cancellable = true)
    private void smc$changeFallFlyingState(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity)(Object)this;
        if (!CuriosHelper.hasCharm(entity, ItemRegistry.DIVINE_WING.get())) return;
        boolean flag = entity.getSharedFlag(7);
        if (flag && !entity.onGround() && !entity.isPassenger() && !entity.hasEffect(MobEffects.LEVITATION)) {
            ItemStack itemstack = CuriosHelper.getCharm(entity, ItemRegistry.DIVINE_WING.get());
            flag = itemstack.elytraFlightTick(entity, entity.fallFlyTicks);
        } else {
            flag = false;
        }
        if (!entity.level().isClientSide) {
            entity.setSharedFlag(7, flag);
        }
        ci.cancel();
    }
}