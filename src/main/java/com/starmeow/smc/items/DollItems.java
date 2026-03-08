package com.starmeow.smc.items;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.Block;

public class DollItems extends ItemNameBlockItem implements Equipable {
    public DollItems(Block p_41579_, Properties p_41580_) {
        super(p_41579_, p_41580_);
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }
}
