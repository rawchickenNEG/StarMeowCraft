package com.starmeow.smc.helper;

import com.starmeow.smc.init.ItemRegistry;
import com.starmeow.smc.items.DevourSword;
import net.minecraft.world.item.ItemStack;

public class WeaponHelper {

    public static boolean isValidWeapon(ItemStack stack) {
        return (stack.is(ItemRegistry.DEVOUR_SWORD.get()) && !DevourSword.hasZenishAbility(stack))
                || stack.is(ItemRegistry.EXCALIBUR.get())
                || stack.is(ItemRegistry.MEOWMERE.get());
    }
}
