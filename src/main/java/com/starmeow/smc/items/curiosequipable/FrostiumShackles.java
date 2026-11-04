package com.starmeow.smc.items.curiosequipable;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class FrostiumShackles extends Item implements BaseCurioItem {

    public FrostiumShackles(Properties p_41383_) {
        super(p_41383_);
    }
    
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attributeMap = ArrayListMultimap.create();
        LivingEntity entity = slotContext.entity();
        CompoundTag tag = stack.getOrCreateTag();
        int f = tag.getInt("SMCShacklesStored");
        if(entity != null){
            double step = entity.getAttributeValue(ForgeMod.STEP_HEIGHT_ADDITION.get());
            attributeMap.put(ForgeMod.STEP_HEIGHT_ADDITION.get(), new AttributeModifier(UUID.fromString("f544e399-eeee-4d1f-9b2a-88592d313f83"), "remove_all_step", 0.6 - step, AttributeModifier.Operation.ADDITION));
        }
        attributeMap.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("206f230d-e334-43dc-bb89-120b403854a4"), "lose_speed", -0.1 * f, AttributeModifier.Operation.MULTIPLY_BASE));
        attributeMap.put(Attributes.ARMOR, new AttributeModifier(UUID.fromString("1cfe638b-7915-4f71-ae9a-8432b267d813"), "add_armor", 2 * f, AttributeModifier.Operation.ADDITION));
        attributeMap.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.fromString("c35ee111-efc8-40f4-a879-bcf0fd5273aa"), "add_resist", 0.02 * f, AttributeModifier.Operation.ADDITION));
        return attributeMap;
    }

    public boolean overrideStackedOnOther(ItemStack itemStack, Slot p_150734_, ClickAction p_150735_, Player p_150736_) {
        if (itemStack.getCount() == 1 && p_150735_ == ClickAction.SECONDARY) {
            ItemStack anvil = p_150734_.getItem();
            CompoundTag tag = itemStack.getOrCreateTag();
            int stored = tag.getInt("SMCShacklesStored");
            if (anvil.is(Items.ANVIL)) {
                int insert = Math.min(anvil.getCount(), 64);
                p_150734_.safeTake(insert, 64, p_150736_);
                p_150736_.level().playSound(p_150736_, p_150736_.getOnPos(), SoundEvents.BUCKET_FILL_LAVA, SoundSource.PLAYERS);
                tag.putInt("SMCShacklesStored", stored + insert);
            } else if(anvil.isEmpty()){
                int export = Math.min(stored, 64);
                p_150734_.safeInsert(new ItemStack(Items.ANVIL, export));
                p_150736_.level().playSound(p_150736_, p_150736_.getOnPos(), SoundEvents.ANVIL_PLACE, SoundSource.PLAYERS);
                tag.putInt("SMCShacklesStored", stored - export);
            }
            return true;
        } else {
            return false;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        CompoundTag tag = stack.getOrCreateTag();
        int f = tag.getInt("SMCShacklesStored");
        tooltip.add(Component.translatable("tooltip.smc.frostium_shackles").withStyle(ChatFormatting.AQUA));
        tooltip.add(Component.translatable("tooltip.smc.frostium_shackles_0", f).withStyle(ChatFormatting.AQUA));
    }
}
