package com.starmeow.smc.mixin;

import com.starmeow.smc.helper.CuriosHelper;
import com.starmeow.smc.init.ItemRegistry;
import com.starmeow.smc.items.FoxArmorItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

@Mixin(Fox.class)
public class FoxMixin {
    @Shadow
    @Final
    @Mutable
    private static Predicate<Player> AVOID_PLAYERS;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void modifyAvoidPlayers(CallbackInfo ci) {

        Predicate<Player> original = AVOID_PLAYERS;

        AVOID_PLAYERS = player -> {
            boolean flag = player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof FoxArmorItems
                    && player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof FoxArmorItems
                    && player.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof FoxArmorItems
                    && player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof FoxArmorItems;
            if(flag || CuriosHelper.hasCharm(player, ItemRegistry.FOX_TAIL.get())) {
                return false;
            }

            return original.test(player);
        };
    }
}