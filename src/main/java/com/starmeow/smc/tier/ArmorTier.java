package com.starmeow.smc.tier;

import com.starmeow.smc.StarMeowCraft;
import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;

@SuppressWarnings("NullableProblems")
public enum ArmorTier implements ArmorMaterial {
    FROSTIUM("frostium", 30, Util.make(new EnumMap<>(ArmorItem.Type.class), armor -> {
        armor.put(ArmorItem.Type.BOOTS, 3);
        armor.put(ArmorItem.Type.LEGGINGS, 6);
        armor.put(ArmorItem.Type.CHESTPLATE, 8);
        armor.put(ArmorItem.Type.HELMET, 3);
    }), 18, SoundEvents.ARMOR_EQUIP_IRON, 2.0f, 0.0f, Ingredient.of(ItemRegistry.FROSTIUM_INGOT.get())),
    BROCCOLI("broccoli", 15, Util.make(new EnumMap<>(ArmorItem.Type.class), armor -> {
        armor.put(ArmorItem.Type.BOOTS, 2);
        armor.put(ArmorItem.Type.LEGGINGS, 5);
        armor.put(ArmorItem.Type.CHESTPLATE, 6);
        armor.put(ArmorItem.Type.HELMET, 2);
    }), 10, SoundEvents.ARMOR_EQUIP_LEATHER, 0, 0.0f, Ingredient.of(ItemRegistry.BROCCOLI.get())),
    CHOCOLATE("chocolate", 12, Util.make(new EnumMap<>(ArmorItem.Type.class), armor -> {
        armor.put(ArmorItem.Type.BOOTS, 1);
        armor.put(ArmorItem.Type.LEGGINGS, 3);
        armor.put(ArmorItem.Type.CHESTPLATE, 6);
        armor.put(ArmorItem.Type.HELMET, 1);
    }), 12, SoundEvents.ARMOR_EQUIP_LEATHER, 0, 0.0f, Ingredient.of(ItemRegistry.CHOCOLATE.get())),
    PERFROSTITE("perfrostite", 55, Util.make(new EnumMap<>(ArmorItem.Type.class), armor -> {
        armor.put(ArmorItem.Type.BOOTS, 4);
        armor.put(ArmorItem.Type.LEGGINGS, 9);
        armor.put(ArmorItem.Type.CHESTPLATE, 11);
        armor.put(ArmorItem.Type.HELMET, 6);
    }), 24, SoundEvents.ARMOR_EQUIP_NETHERITE, 5.0f, 0.0f, Ingredient.of(ItemRegistry.FROSTIUM_INGOT.get())),
    TEMPLATE("template", 30, Util.make(new EnumMap<>(ArmorItem.Type.class), armor -> {
        armor.put(ArmorItem.Type.BOOTS, 0);
        armor.put(ArmorItem.Type.LEGGINGS, 0);
        armor.put(ArmorItem.Type.CHESTPLATE, 0);
        armor.put(ArmorItem.Type.HELMET, 0);
    }), 10, SoundEvents.ARMOR_EQUIP_NETHERITE, 1.0f, 0.0f, Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE));

    private final String name;
    private final int durabilityMultiplier;
    private final EnumMap<ArmorItem.Type, Integer> protectionFunctionForType;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final Ingredient repairIngredient;
    private static final EnumMap<ArmorItem.Type, Integer> DURABILITY_FOR_TYPE = Util.make(new EnumMap<>(ArmorItem.Type.class), (durabilityValue) -> {
        durabilityValue.put(ArmorItem.Type.BOOTS, 13);
        durabilityValue.put(ArmorItem.Type.LEGGINGS, 15);
        durabilityValue.put(ArmorItem.Type.CHESTPLATE, 16);
        durabilityValue.put(ArmorItem.Type.HELMET, 11);
    });

    ArmorTier(String name, int durability, EnumMap<ArmorItem.Type, Integer> protection, int enchantmentValue, SoundEvent sound, float toughness, float knockbackResistance, Ingredient repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durability;
        this.protectionFunctionForType = protection;
        this.enchantmentValue = enchantmentValue;
        this.sound = sound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return DURABILITY_FOR_TYPE.get(type) * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return this.protectionFunctionForType.get(type);
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.sound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient;
    }

    @Override
    public String getName() {
        return StarMeowCraft.MODID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}