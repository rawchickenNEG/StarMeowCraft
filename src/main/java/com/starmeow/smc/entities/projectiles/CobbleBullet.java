package com.starmeow.smc.entities.projectiles;

import com.starmeow.smc.init.EntityTypeRegistry;
import com.starmeow.smc.init.PotionEffectRegistry;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class CobbleBullet extends ThrowableItemProjectile {
    public CobbleBullet(EntityType<? extends CobbleBullet> entityType, Level level) {
        super(entityType, level);
    }
    public CobbleBullet(Level level, LivingEntity entity) {
        super(EntityTypeRegistry.COBBLE_BULLET.get(), entity, level);
    }
    public CobbleBullet(Level level, double x, double y, double z) {
        super(EntityTypeRegistry.COBBLE_BULLET.get(), x, y, z, level);
    }
    public boolean ride = false;
    public void setRide(boolean ride) {
        this.ride = ride;
    }
    //幻翼坠机
    public void tick() {
        super.tick();
        if(this.ride){
            if(this.getVehicle() instanceof Phantom phantom){
                phantom.setDeltaMovement(phantom.getDeltaMovement().get(Direction.Axis.X), phantom.getDeltaMovement().get(Direction.Axis.Y) - 0.5,phantom.getDeltaMovement().get(Direction.Axis.Z));
                if (phantom.onGround()){
                    phantom.hurt(this.damageSources().thrown(this, this.getOwner()), 6.0F);
                }
            }
            if(this.getVehicle() == null){
                this.setRide(false);
            }
        }
        if(this.getItem().is(Items.NETHERRACK)){
            this.setRemainingFireTicks(10);
        }
        if(this.getItem().is(Items.END_STONE)){
            this.setNoGravity(true);
            if (Math.abs(this.getDeltaMovement().x + this.getDeltaMovement().y + this.getDeltaMovement().z)< 0.01){
                this.discard();
            }
        }
    }
    //击中时粒子
    public void handleEntityEvent(byte p_37484_) {
        if (p_37484_ == 3) {
            double d0 = 0.08D;
            for(int i = 0; i < 8; ++i) {
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - 0.5D) * d0, ((double)this.random.nextFloat() - 0.5D) * d0, ((double)this.random.nextFloat() - 0.5D) * d0);
            }
        }
    }

    protected void onHitEntity(EntityHitResult p_37486_) {
        super.onHitEntity(p_37486_);
        p_37486_.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 3.0f);
        if(p_37486_.getEntity() instanceof Phantom phantom){
            CobbleBullet bullet = new CobbleBullet(phantom.level(), (LivingEntity) this.getOwner());
            bullet.setRide(true);
            phantom.level().addFreshEntity(bullet);
            bullet.startRiding(phantom);
        }
        if(this.getItem().is(Items.OBSIDIAN)){
            if(p_37486_.getEntity() instanceof LivingEntity living && !this.level().isClientSide){
                living.addEffect(new MobEffectInstance(PotionEffectRegistry.STUNNED.get(), 20));
            }
        }
    }

    protected void onHit(HitResult p_37488_) {
        super.onHit(p_37488_);
        if (!this.level().isClientSide) {
            if(!this.ride){
                if(this.getItem().is(Items.NETHERRACK)){
                    this.level().explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 1, false, Level.ExplosionInteraction.NONE);
                }
                this.level().broadcastEntityEvent(this, (byte)3);
                this.playSound(SoundEvents.POINTED_DRIPSTONE_HIT);
                this.discard();
            }
        }

    }

    protected Item getDefaultItem() {
        return Items.COBBLESTONE;
    }
}
