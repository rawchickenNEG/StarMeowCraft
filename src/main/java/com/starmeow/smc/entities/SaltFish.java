package com.starmeow.smc.entities;

import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Predicate;

public class SaltFish extends AbstractSchoolingFish {

    public UUID ownerUUID = null;
    public LivingEntity currentTarget;
    public SaltFish(EntityType<? extends SaltFish> p_28276_, Level p_28277_) {
        super(p_28276_, p_28277_);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2D, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, new SaltFish.FishAttackSelector(this)));
    }

    public ItemStack getBucketItemStack() {
        return new ItemStack(ItemRegistry.SALT_FISH_BUCKET.get());
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.COD_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.COD_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource p_28281_) {
        return SoundEvents.COD_HURT;
    }

    protected SoundEvent getFlopSound() {
        return SoundEvents.COD_FLOP;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 40.0).add(Attributes.MOVEMENT_SPEED, 1.0).add(Attributes.ATTACK_DAMAGE, 8.0);
    }

    public boolean doHurtTarget(Entity p_149201_) {
        boolean $$1 = p_149201_.hurt(this.damageSources().mobAttack(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if ($$1) {
            this.doEnchantDamageEffects(this, p_149201_);
            this.playSound(SoundEvents.PLAYER_HURT_DROWN, 1.0F, 1.0F);
        }
        return $$1;
    }

    public void saveToBucketTag(ItemStack p_149187_) {
        Bucketable.saveDefaultDataToBucketTag(this, p_149187_);
        CompoundTag tag = p_149187_.getOrCreateTag();
        if (this.ownerUUID != null) {
            tag.putString("OwnerUUID", this.ownerUUID.toString());
        } else {
            tag.remove("OwnerUUID");
        }
    }

    public void loadFromBucketTag(CompoundTag p_149163_) {
        Bucketable.loadDefaultDataFromBucketTag(this, p_149163_);
        this.loadOwnerUUID(p_149163_);
    }

    public void addAdditionalSaveData(CompoundTag p_30418_) {
        super.addAdditionalSaveData(p_30418_);
        if (this.ownerUUID != null) {
            p_30418_.putString("OwnerUUID", this.ownerUUID.toString());
        }
    }

    public void readAdditionalSaveData(CompoundTag p_30402_) {
        super.readAdditionalSaveData(p_30402_);
        this.loadOwnerUUID(p_30402_);
    }

    private void loadOwnerUUID(CompoundTag tag) {
        if (tag.contains("OwnerUUID", Tag.TAG_STRING)) {
            String uuidStr = tag.getString("OwnerUUID");
            if (!uuidStr.isEmpty()) {
                try {
                    this.ownerUUID = UUID.fromString(uuidStr);
                } catch (IllegalArgumentException e) {
                    this.ownerUUID = null;
                }
            } else {
                this.ownerUUID = null;
            }
        } else {
            this.ownerUUID = null;
        }
    }

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if(player.getItemInHand(hand).is(ItemRegistry.CHOCOLATE.get())){
            if(this.ownerUUID == null){
                this.ownerUUID = player.getUUID();
                if(this.level() instanceof ServerLevel serverLevel){
                    serverLevel.sendParticles(ParticleTypes.HEART, this.getX(), this.getY() + this.getBbHeight(), this.getZ(), 5, 0.25D, 0.1D, 0.25D, 0.0D);
                }
            }
            this.heal(6);
            this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1));
            this.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 2400, 0));
            if(this.hasEffect(MobEffects.DAMAGE_BOOST)){
                int amp = this.getEffect(MobEffects.DAMAGE_BOOST).getAmplifier();
                this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 2400, Math.min(amp + 1, 4)));
            }else{
                this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 2400, 0));
            }
            this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 2.0F);
        }
        return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
    }

    private static class FishAttackSelector implements Predicate<LivingEntity> {
        private final SaltFish fish;

        public FishAttackSelector(SaltFish p_32879_) {
            this.fish = p_32879_;
        }

        public boolean test(@Nullable LivingEntity living) {
            if(fish.ownerUUID != null){
                if(fish.level().getPlayerByUUID(fish.ownerUUID) != null){
                    Player owner = fish.level().getPlayerByUUID(fish.ownerUUID);
                    if(owner != null && living != null && living.distanceToSqr(this.fish) > 16.0){
                        if(owner.getLastAttacker() != null && owner.getLastAttacker().is(living) && fish.currentTarget == null){
                            fish.currentTarget = living;
                            return true;
                        }
                        if(living.getLastAttacker() != null && living.getLastAttacker().is(owner)){
                            fish.currentTarget = living;
                            return true;
                        }
                    }
                }
            }
            return fish.currentTarget != null && living != null && fish.currentTarget.is(living);
        }
    }

}
