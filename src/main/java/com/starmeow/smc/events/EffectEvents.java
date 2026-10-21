package com.starmeow.smc.events;

import com.starmeow.smc.init.PotionEffectRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EffectEvents {
    @SubscribeEvent
    public static void onEffectEnd(MobEffectEvent.Expired event) {
        MobEffect mobeffect = event.getEffectInstance().getEffect();
        int effectLevel = event.getEffectInstance().getAmplifier() + 1;
        LivingEntity entity = event.getEntity();
        if(mobeffect == PotionEffectRegistry.FROST_BURST.get()){
            entity.invulnerableTime = 0;
            DamageSource source = entity.getLastAttacker() != null ? entity.level().damageSources().indirectMagic(entity.getLastAttacker(), null) : entity.level().damageSources().magic();
            entity.hurt(source, effectLevel * 6.0F + entity.getMaxHealth() * Math.min(effectLevel * 0.01F, 0.1F));
            entity.level().playSound((Player)null, entity.getOnPos(), SoundEvents.GLASS_BREAK, SoundSource.NEUTRAL, 1.0F, 1.0F);
            if(event.getEntity().level().isClientSide()){
                Minecraft.getInstance().particleEngine.createTrackingEmitter(entity, ParticleTypes.SNOWFLAKE, 1);
            }
        }
        if(mobeffect == PotionEffectRegistry.RAINBOW.get()){
            if(entity instanceof Player player && !player.getAbilities().instabuild){
                player.getAbilities().mayfly = false;
                player.getAbilities().flying = false;
                player.onUpdateAbilities();
            }
        }
        if(event.getEffectInstance().getDuration() <= 1){
            if(mobeffect == PotionEffectRegistry.YELLOW_LIGHT_SLOW.get()){
                entity.addEffect(new MobEffectInstance(PotionEffectRegistry.RED_LIGHT_STOP.get(), 600));
            }
            if(mobeffect == PotionEffectRegistry.RED_LIGHT_STOP.get()){
                entity.addEffect(new MobEffectInstance(PotionEffectRegistry.GREEN_LIGHT_GO.get(), 600));
            }
            if(mobeffect == PotionEffectRegistry.GREEN_LIGHT_GO.get()){
                entity.addEffect(new MobEffectInstance(PotionEffectRegistry.YELLOW_LIGHT_SLOW.get(), 60));
            }
        }
    }

    @SubscribeEvent
    public static void onApplyPotion(MobEffectEvent.Applicable event) {
        LivingEntity entity = event.getEntity();
        MobEffect effect = event.getEffectInstance().getEffect();
        if(entity.hasEffect(PotionEffectRegistry.FROST_RESISTANCE.get())){
            if (effect == MobEffects.MOVEMENT_SLOWDOWN || effect == PotionEffectRegistry.FROST.get() || effect == PotionEffectRegistry.FROST_BURST.get()){
                event.setResult(Event.Result.DENY);
            }
        }
        if(entity.hasEffect(PotionEffectRegistry.POISON_RESISTANCE.get())){
            if (effect == MobEffects.POISON || effect == MobEffects.WITHER){
                event.setResult(Event.Result.DENY);
            }
        }
        if(entity.hasEffect(PotionEffectRegistry.RED_LIGHT_STOP.get()) && entity.getEffect(PotionEffectRegistry.RED_LIGHT_STOP.get()).getDuration() > 5){
            if (effect == PotionEffectRegistry.YELLOW_LIGHT_SLOW.get() || effect == PotionEffectRegistry.GREEN_LIGHT_GO.get()){
                event.setResult(Event.Result.DENY);
            }
        }
    }
}
