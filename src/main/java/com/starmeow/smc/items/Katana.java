package com.starmeow.smc.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;

public class Katana extends SwordItem {

    public Katana(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack)
    {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(this.getDefaultAttributeModifiers(slot));
        double addition = getCount(stack) * 0.1;
        if (slot == EquipmentSlot.MAINHAND) {
            builder.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(UUID.fromString("68024817-4632-8753-1084-367085658492"), "atk_range", 4.0D, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("68024817-4632-8753-1084-367954234584"), "atk_damage", addition, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
        return builder.build();
    }

    public static int getCount(ItemStack itemStack) {
        CompoundTag tag = itemStack.getTag();
        if (tag == null || !tag.contains("SMCKatanaLevel"))
            return 0;
        return tag.getInt("SMCKatanaLevel");
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);
        CompoundTag tag = stack.getOrCreateTag();
        if(tag.getInt("SMCKatanaLevel") == 3){
            tag.putInt("SMCKatanaPower", Math.max(tag.getInt("SMCKatanaPower") - 1, 0));
            if(tag.getInt("SMCKatanaPower") == 0){
                tag.putInt("SMCKatanaLevel", 2);
            }
        }
    }

    @Override
    public boolean isBarVisible(ItemStack p_150899_) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack p_150900_) {
        CompoundTag tag = p_150900_.getOrCreateTag();
        return Math.round((float)tag.getInt("SMCKatanaPower") * 13.0F / 400.0F);
    }

    public int getBarColor(ItemStack p_150901_) {
        int color;
        CompoundTag tag = p_150901_.getOrCreateTag();
        switch (tag.getInt("SMCKatanaLevel")) {
            case 1 -> color =Mth.color(1.0F, 1.0F, 1.0F);
            case 2 -> color =Mth.color(1.0F, 1.0F, 0);
            case 3 -> color =Mth.color(1.0F, 0, 0);
            default -> color =Mth.color(0.5F, 0.5F, 0.5F);
        }
        return color;
    }

    @Override
    public boolean hurtEnemy(ItemStack p_43278_, LivingEntity p_43279_, LivingEntity p_43280_) {
        CompoundTag tag = p_43278_.getOrCreateTag();
        tag.putInt("SMCKatanaPower", Math.min(tag.getInt("SMCKatanaPower") + 40, 400));
        if(tag.getInt("SMCKatanaPower") == 400){
            tag.putInt("SMCKatanaLevel", Math.min(tag.getInt("SMCKatanaLevel") + 1, 3));
            if(tag.getInt("SMCKatanaLevel") != 3){
                tag.putInt("SMCKatanaPower", 0);
            }
        }
        return true;
    }

    @Override
    public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack) {
        return oldStack.getItem() == newStack.getItem();
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged || newStack.getItem() != oldStack.getItem();
    }

    @Override
    public void setDamage(ItemStack stack, int damage){
        super.setDamage(stack, 0);
    }

    @Override
    public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStackMaterial) {
        return false;
    }

    @Override
    public int getEnchantmentValue() {
        return 10;
    }
}
