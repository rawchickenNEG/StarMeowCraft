package com.starmeow.smc.enchantments;

import com.starmeow.smc.init.EnchantmentRegistry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class ExplodeSpell extends Enchantment {
    public ExplodeSpell(Enchantment.Rarity rarity) {
        super(rarity, EnchantmentRegistry.GRIMOIRE, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    public int getMinCost(int p_44652_) {
        return 5 + (p_44652_ - 1) * 8;
    }

    public int getMaxCost(int p_44660_) {
        return super.getMinCost(p_44660_) + 50;
    }
}
