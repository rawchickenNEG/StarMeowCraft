package com.starmeow.smc.init;

import com.starmeow.smc.StarMeowCraft;
import com.starmeow.smc.entities.ChickenHarvester;
import com.starmeow.smc.entities.EasterBunny;
import com.starmeow.smc.entities.SaltFish;
import com.starmeow.smc.entities.ThrownSwordEntity;
import com.starmeow.smc.entities.projectiles.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = StarMeowCraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityTypeRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, StarMeowCraft.MODID);

    public static final RegistryObject<EntityType<GrimoireBullet>> GRIMOIRE_BULLET = throwableItem("grimoire_bullet", GrimoireBullet::new);

    public static final RegistryObject<EntityType<PeaBullet>> PEA_BULLET = throwableItem("pea_bullet", PeaBullet::new);
    public static final RegistryObject<EntityType<ThrownSporeBud>> THROWN_SPORE_BUD = throwableItem("thrown_spore_bud", ThrownSporeBud::new);
    public static final RegistryObject<EntityType<CobbleBullet>> COBBLE_BULLET = throwableItem("cobble_bullet", CobbleBullet::new);
    public static final RegistryObject<EntityType<DetectorMark>> DETECTOR_MARK = throwableItem("detector_mark", DetectorMark::new);
    public static final RegistryObject<EntityType<ThrownSpear>> THROWN_SPEAR = abstractArrow("thrown_spear", ThrownSpear::new);
    public static final RegistryObject<EntityType<FrostArrow>> FROST_ARROW = abstractArrow("frost_arrow", FrostArrow::new);
    public static final RegistryObject<EntityType<RainbowArrow>> RAINBOW_ARROW = abstractArrow("rainbow_arrow", RainbowArrow::new);
    public static final RegistryObject<EntityType<ThrownSwordEntity>> THROWN_SWORD = abstractArrow("thrown_sword", ThrownSwordEntity::new);
    public static final RegistryObject<EntityType<MagicArrow>> MAGIC_ARROW = abstractArrow("magic_arrow", MagicArrow::new);
    public static final RegistryObject<EntityType<SwordAura>> SWORD_AURA = swordAura("sword_aura", SwordAura::new);

    public static final RegistryObject<EntityType<BroccoliFishingHook>> BROCCOLI_FISHING_HOOK = throwableItem("broccoli_fishing_hook", BroccoliFishingHook::new);
    public static final RegistryObject<EntityType<RainbowFishingHook>> RAINBOW_FISHING_HOOK = throwableItem("rainbow_fishing_hook", RainbowFishingHook::new);
    public static final RegistryObject<EntityType<SpearHook>> SPEAR_HOOK = throwableItem("spear_hook", SpearHook::new);

    public static final RegistryObject<EntityType<SaltFish>> SALT_FISH = register("salt_fish", EntityType.Builder.of(SaltFish::new, MobCategory.WATER_AMBIENT).sized(0.5F, 0.3F).clientTrackingRange(4));
    public static final RegistryObject<EntityType<EasterBunny>> EASTER_BUNNY = register("easter_bunny", EntityType.Builder.of(EasterBunny::new, MobCategory.CREATURE).sized(0.8F, 1).clientTrackingRange(12));
    public static final RegistryObject<EntityType<ChickenHarvester>> CHICKEN_HARVESTER = register("chicken_harvester", EntityType.Builder.of(ChickenHarvester::new, MobCategory.CREATURE).sized(0.8F, 1).clientTrackingRange(12));


    private static <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.Builder<T> entityTypeBuilder) {
        return ENTITY_TYPES.register(name, () -> entityTypeBuilder.build(name));
    }

    private static <T extends Entity> RegistryObject<EntityType<T>> throwableItem(String name, EntityType.EntityFactory<T> factory) {
        return ENTITY_TYPES.register(name, () -> (EntityType.Builder.of(factory, MobCategory.MISC).sized(0.25F, 0.25F)
                .clientTrackingRange(4).updateInterval(10).build(name)));
    }

    private static <T extends Entity> RegistryObject<EntityType<T>> swordAura(String name, EntityType.EntityFactory<T> factory) {
        return ENTITY_TYPES.register(name, () -> (EntityType.Builder.of(factory, MobCategory.MISC).sized(1.5F, 1.5F)
                .clientTrackingRange(4).updateInterval(10).build(name)));
    }

    private static <T extends Entity> RegistryObject<EntityType<T>> abstractArrow(String name, EntityType.EntityFactory<T> factory) {
        return ENTITY_TYPES.register(name, () -> (EntityType.Builder.of(factory, MobCategory.MISC).sized(0.5F, 0.5F)
                .clientTrackingRange(4).updateInterval(20).build(name)));
    }

    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent e) {
        e.put(SALT_FISH.get(), SaltFish.createAttributes().add(Attributes.MOVEMENT_SPEED, 2).add(Attributes.MAX_HEALTH, 40).build());
        e.put(EASTER_BUNNY.get(), Rabbit.createAttributes().build());
        e.put(CHICKEN_HARVESTER.get(), ChickenHarvester.createAttributes().build());
        e.put(THROWN_SWORD.get(), ThrownSwordEntity.createAttributes().build());
    }
}
