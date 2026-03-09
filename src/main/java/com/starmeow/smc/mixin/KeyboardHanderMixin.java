package com.starmeow.smc.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import com.starmeow.smc.init.NetworkRegistry;
import com.starmeow.smc.packet.CoffeeGetC2S;
import net.minecraft.Util;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class KeyboardHanderMixin {
    @Unique
    private boolean amd$holdingF3C = false;
    @Unique private long amd$f3cStartMs = 0L;

    @Unique private static final long AMD_CRASH_MS = 10000L;
    @Unique private static final long AMD_TRIGGER_MIN_MS = 9000L;

    @Inject(method = "tick", at = @At("TAIL"))
    private void amd$tick(CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        long window = mc.getWindow().getWindow();
        boolean f3 = InputConstants.isKeyDown(window, GLFW.GLFW_KEY_F3);
        boolean c  = InputConstants.isKeyDown(window, GLFW.GLFW_KEY_C);

        boolean holdingNow = f3 && c;
        long time = Util.getMillis();

        if (holdingNow) {
            if (!amd$holdingF3C) {
                amd$holdingF3C = true;
                amd$f3cStartMs = time;
            }
            return;
        }

        if (amd$holdingF3C) {
            amd$holdingF3C = false;
            long held = time - amd$f3cStartMs;

            if (held >= AMD_TRIGGER_MIN_MS && held < AMD_CRASH_MS) {
                NetworkRegistry.CHANNEL.sendToServer(new CoffeeGetC2S());
            }
        }
    }
}
