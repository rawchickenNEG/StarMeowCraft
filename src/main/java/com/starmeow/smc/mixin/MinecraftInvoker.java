package com.starmeow.smc.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Minecraft.class)
public interface MinecraftInvoker {

    @Invoker("startAttack")
    boolean smc$startAttack();
}