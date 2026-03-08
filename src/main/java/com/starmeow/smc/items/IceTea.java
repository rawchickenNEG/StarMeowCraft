package com.starmeow.smc.items;

import com.starmeow.smc.init.SoundRegistry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;

public class IceTea extends Item {
    public IceTea(Properties p_41383_) {
        super(p_41383_);
    }

    public UseAnim getUseAnimation(ItemStack p_41358_) {
        return UseAnim.DRINK;
    }

    public SoundEvent getDrinkingSound() {
        return SoundRegistry.ICE_TEA_DRUNK.get();
    }

    public SoundEvent getEatingSound() {
        return SoundRegistry.ICE_TEA_DRUNK.get();
    }
}
