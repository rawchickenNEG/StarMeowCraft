package com.starmeow.smc.enchantments;

import com.starmeow.smc.init.EnchantmentRegistry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class LaunchSelf extends Enchantment {
    public LaunchSelf(Rarity rarity) {
        super(rarity, EnchantmentRegistry.GRIMOIRE, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
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

    public boolean isTreasureOnly() {
        return true;
    }

    public boolean checkCompatibility(Enchantment p_45113_) {
        return super.checkCompatibility(p_45113_)
                && p_45113_ != EnchantmentRegistry.FIRE_SPELL.get()
                && p_45113_ != EnchantmentRegistry.LIGHTNING_SPELL.get()
                && p_45113_ != EnchantmentRegistry.FREEZE_SPELL.get()
                && p_45113_ != EnchantmentRegistry.EXPLODE_SPELL.get()
                && p_45113_ != EnchantmentRegistry.DAMAGE_SPELL.get();
    }
}
