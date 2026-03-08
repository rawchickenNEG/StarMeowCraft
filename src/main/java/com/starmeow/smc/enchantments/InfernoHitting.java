package com.starmeow.smc.enchantments;

import com.starmeow.smc.init.EnchantmentRegistry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class InfernoHitting extends Enchantment {
    public InfernoHitting(Rarity rarity) {
        super(rarity, EnchantmentRegistry.SLINGSHOT, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    public int getMinCost(int p_44652_) {
        return 5 + (p_44652_ - 1) * 8;
    }

    public int getMaxCost(int p_44660_) {
        return super.getMinCost(p_44660_) + 50;
    }

    public boolean checkCompatibility(Enchantment p_44590_) {
        return !(p_44590_ instanceof ObsidianHitting) && !(p_44590_ instanceof EnderFloating) && super.checkCompatibility(p_44590_);
    }
}
