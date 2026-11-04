package com.starmeow.smc.items.curiosequipable;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.UUID;

public class DockingStation extends Item implements BaseCurioItem {

    public DockingStation(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attributeMap = ArrayListMultimap.create();
        CuriosApi.addSlotModifier(attributeMap, "charm", UUID.fromString("55e5887f-58f7-4b00-8e51-47e86d0e3468"), 3, AttributeModifier.Operation.ADDITION);
        return attributeMap;
    }
}
