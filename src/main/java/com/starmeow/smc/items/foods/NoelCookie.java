package com.starmeow.smc.items.foods;

import com.starmeow.smc.init.SoundRegistry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;

public class NoelCookie extends Item {
    public NoelCookie(Properties p_41383_) {
        super(p_41383_);
    }

    public SoundEvent getEatingSound() {
        return SoundRegistry.NOEL_EAT.get();
    }
}
