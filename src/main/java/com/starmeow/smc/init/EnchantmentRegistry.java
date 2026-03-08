package com.starmeow.smc.init;

import com.starmeow.smc.StarMeowCraft;
import com.starmeow.smc.enchantments.*;
import com.starmeow.smc.items.Grimoire;
import com.starmeow.smc.items.Slingshot;
import com.starmeow.smc.items.SwissArmyKnife;
import com.starmeow.smc.items.WateringCan;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EnchantmentRegistry {
    public static final DeferredRegister<Enchantment> ENCHANTMENT = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, StarMeowCraft.MODID);
    public static final RegistryObject<Enchantment> DAMAGE_SPELL = ENCHANTMENT.register("damage_spell", () -> new DamageSpell(Enchantment.Rarity.RARE));
    public static final RegistryObject<Enchantment> EXPLODE_SPELL = ENCHANTMENT.register("explode_spell", () -> new ExplodeSpell(Enchantment.Rarity.VERY_RARE));
    public static final RegistryObject<Enchantment> FIRE_SPELL = ENCHANTMENT.register("fire_spell", () -> new FireSpell(Enchantment.Rarity.RARE));
    public static final RegistryObject<Enchantment> FREEZE_SPELL = ENCHANTMENT.register("freeze_spell", () -> new FreezeSpell(Enchantment.Rarity.VERY_RARE));
    public static final RegistryObject<Enchantment> HEAL_SPELL = ENCHANTMENT.register("heal_spell", () -> new HealSpell(Enchantment.Rarity.RARE));
    public static final RegistryObject<Enchantment> INFINITE_SPELL = ENCHANTMENT.register("infinite_spell", () -> new InfiniteSpell(Enchantment.Rarity.VERY_RARE));
    public static final RegistryObject<Enchantment> LAUNCH_SPELL = ENCHANTMENT.register("launch_spell", () -> new LaunchSpell(Enchantment.Rarity.RARE));
    public static final RegistryObject<Enchantment> LIGHTNING_SPELL = ENCHANTMENT.register("lightning_spell", () -> new LightningSpell(Enchantment.Rarity.VERY_RARE));
    public static final RegistryObject<Enchantment> PROTECTION_SPELL = ENCHANTMENT.register("protection_spell", () -> new ProtectionSpell(Enchantment.Rarity.VERY_RARE));
    public static final RegistryObject<Enchantment> RAPID_COOLING = ENCHANTMENT.register("rapid_cooling", () -> new RapidCooling(Enchantment.Rarity.RARE));
    public static final RegistryObject<Enchantment> STRENGTH_SPELL = ENCHANTMENT.register("strength_spell", () -> new StrengthSpell(Enchantment.Rarity.RARE));
    public static final RegistryObject<Enchantment> EXTENSION_SPELL = ENCHANTMENT.register("extension_spell", () -> new ExtensionSpell(Enchantment.Rarity.RARE));
    public static final RegistryObject<Enchantment> OBSIDIAN_HITTING = ENCHANTMENT.register("obsidian_hitting", () -> new ObsidianHitting(Enchantment.Rarity.VERY_RARE));
    public static final RegistryObject<Enchantment> INFERNO_HITTING = ENCHANTMENT.register("inferno_hitting", () -> new InfernoHitting(Enchantment.Rarity.RARE));
    public static final RegistryObject<Enchantment> ENDER_FLOATING = ENCHANTMENT.register("ender_floating", () -> new InfernoHitting(Enchantment.Rarity.VERY_RARE));
    public static final RegistryObject<Enchantment> COMPOSITE_FERTILIZER = ENCHANTMENT.register("composite_fertilizer", () -> new CompositeFertilizer(Enchantment.Rarity.VERY_RARE));
    public static final RegistryObject<Enchantment> LIFE_CYCLE = ENCHANTMENT.register("life_cycle", () -> new LifeCycle(Enchantment.Rarity.VERY_RARE));
    public static final RegistryObject<Enchantment> STRONG_FERTILIZER = ENCHANTMENT.register("strong_fertilizer", () -> new StrongFertilizer(Enchantment.Rarity.RARE));
    public static final RegistryObject<Enchantment> WIDE_RANGE = ENCHANTMENT.register("wide_range", () -> new WideRange(Enchantment.Rarity.RARE));
    public static final RegistryObject<Enchantment> ADAPTION = ENCHANTMENT.register("adaption", () -> new Adaption(Enchantment.Rarity.RARE));

    public static final EnchantmentCategory GRIMOIRE = EnchantmentCategory.create("grimoire", item -> item instanceof Grimoire);
    public static final EnchantmentCategory SLINGSHOT = EnchantmentCategory.create("slingshot", item -> item instanceof Slingshot);
    public static final EnchantmentCategory WATERING_CAN = EnchantmentCategory.create("watering_can", item -> item instanceof WateringCan);
    public static final EnchantmentCategory SWISS_ARMY_KNIFE = EnchantmentCategory.create("swiss_army_knife", item -> item instanceof SwissArmyKnife);
}
