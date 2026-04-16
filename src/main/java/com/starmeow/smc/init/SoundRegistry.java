package com.starmeow.smc.init;

import com.starmeow.smc.StarMeowCraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, StarMeowCraft.MODID);

    public static final RegistryObject<SoundEvent> MUSIC_DISC_LAODA = registerSoundEvents("music_disc_laoda");
    public static final RegistryObject<SoundEvent> MUSIC_DISC_NYAN_CAT = registerSoundEvents("music_disc_nyan_cat");
    public static final RegistryObject<SoundEvent> MUSIC_DISC_BAD_APPLE = registerSoundEvents("music_disc_bad_apple");

    public static final RegistryObject<SoundEvent> ICE_TEA_DRUNK = registerSoundEvents("ice_tea_drunk");
    public static final RegistryObject<SoundEvent> ICE_TEA_KILL = registerSoundEvents("ice_tea_kill");
    public static final RegistryObject<SoundEvent> ICE_TEA_DEATH = registerSoundEvents("ice_tea_death");

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(StarMeowCraft.MODID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}