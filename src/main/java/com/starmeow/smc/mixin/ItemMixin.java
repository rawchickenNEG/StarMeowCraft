package com.starmeow.smc.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(method = "isFoil", at = @At("HEAD"), cancellable = true)
    private void smc$changeFoilState(ItemStack stack, CallbackInfoReturnable<Boolean> cir){
        if(stack.getTag() != null){
            CompoundTag tag = stack.getTag();
            if(tag.contains("SMCShouldGlow")){
                cir.setReturnValue(tag.getBoolean("SMCShouldGlow"));
            }
        }
    }
}
