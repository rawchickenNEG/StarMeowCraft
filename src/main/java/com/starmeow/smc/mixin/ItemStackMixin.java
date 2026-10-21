package com.starmeow.smc.mixin;
import com.starmeow.smc.init.EnchantmentRegistry;
import com.starmeow.smc.init.ItemRegistry;
import com.starmeow.smc.init.SoundRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Inject(method = "hurtAndBreak", at = @At("HEAD"))
    private <T extends LivingEntity> void smc$hurtAndBreakAndGive(int damage, T p_41624_, Consumer<T> p_41625_, CallbackInfo ci) {
        ItemStack itemStack = (ItemStack)(Object)this;
        if (!p_41624_.level().isClientSide && (p_41624_ instanceof Player player) && damage >= itemStack.getMaxDamage() - itemStack.getDamageValue()) {
            if(itemStack.is(ItemRegistry.CALIBUR.get())){
                ItemStack spawnedItem = new ItemStack(ItemRegistry.EXCALIBUR.get());
                if (itemStack.hasTag()){
                    spawnedItem.setTag(itemStack.getTag().copy());
                }
                spawnedItem.setDamageValue(0);
                if (!player.getInventory().add(spawnedItem)) {
                    player.drop(spawnedItem, false);
                }
                if(p_41624_.level() instanceof ServerLevel serverLevel){
                    serverLevel.sendParticles(ParticleTypes.WAX_OFF, player.getX(), player.getY()+1, player.getZ(), 30, 2D, 2D, 2D, 1D);
                }
                p_41624_.level().playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
                itemStack.shrink(1);
            }
            if(itemStack.is(ItemRegistry.GOD_PICKAXE.get())){
                ItemStack spawnedItem = new ItemStack(ItemRegistry.MUSIC_DISC_GOD_PICKAXE.get());
                if (!player.getInventory().add(spawnedItem)) {
                    player.drop(spawnedItem, false);
                }
                p_41624_.level().playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundRegistry.MUSIC_DISC_GOD_PICKAXE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                itemStack.shrink(1);
            }
        }
    }
}
