package com.starmeow.smc.mixin;

import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {

    @ModifyVariable(
            method = "turnPlayer",
            ordinal = 3,
            at = @At(
                    value = "STORE"
            )
    )
    private double adjustSensitivity(double sensitivity) {

        Player player = Minecraft.getInstance().player;
        if (player == null) {
            return sensitivity;
        }
        if(player.getMainHandItem().is(ItemRegistry.MINI_BEDROCK.get())){
            return sensitivity * 0;
        }
        return sensitivity;
    }

}
