package com.starmeow.smc.items.curiosequipable;

import com.starmeow.smc.helper.CuriosHelper;
import com.starmeow.smc.items.TippedItems;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public interface BaseCurioItem extends ICurioItem {
    default boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return !CuriosHelper.hasCharm(slotContext.entity(), stack.getItem());
    }
}
