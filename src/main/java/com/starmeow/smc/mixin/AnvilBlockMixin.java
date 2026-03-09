package com.starmeow.smc.mixin;

import com.starmeow.smc.init.BlockRegistry;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilBlock.class)
public class AnvilBlockMixin {
    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private static void smc$specialAnvil(BlockState p_48825_, CallbackInfoReturnable<Boolean> cir) {
        if (p_48825_.is(BlockRegistry.GRANITE_ANVIL.get())) {
            cir.setReturnValue(null);
        }
    }
}
