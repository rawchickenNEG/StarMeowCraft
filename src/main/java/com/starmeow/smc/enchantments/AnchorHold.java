package com.starmeow.smc.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class AnchorHold extends Enchantment {
    public AnchorHold(Rarity rarity) {
        super(rarity, EnchantmentCategory.BREAKABLE, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
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
}