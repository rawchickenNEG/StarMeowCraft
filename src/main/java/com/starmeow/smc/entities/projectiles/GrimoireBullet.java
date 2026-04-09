package com.starmeow.smc.entities.projectiles;

import com.starmeow.smc.config.Config;
import com.starmeow.smc.init.EntityTypeRegistry;
import com.starmeow.smc.init.PotionEffectRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class GrimoireBullet extends ThrowableItemProjectile {
    public GrimoireBullet(EntityType<? extends GrimoireBullet> entityType, Level level) {
        super(entityType, level);
    }
    public GrimoireBullet(Level level, LivingEntity entity) {
        super(EntityTypeRegistry.GRIMOIRE_BULLET.get(), entity, level);
    }
    public GrimoireBullet(Level level, double x, double y, double z) {
        super(EntityTypeRegistry.GRIMOIRE_BULLET.get(), x, y, z, level);
    }
    public int damage_spell = 0;
    public int explode_spell = 0;
    public int fire_spell = 0;
    public int freeze_spell = 0;
    public int launch_spell = 0;
    public boolean lightning_spell = false;

    public void setDamage(int damage) {
        this.damage_spell = damage;
    }
    public void setExplode(int explode) {
        this.explode_spell = explode;
    }
    public void setFire(int fire) {
        this.fire_spell = fire;
    }
    public void setFreeze(int freeze) {
        this.freeze_spell = freeze;
    }
    public void setLaunch(int launch) {
        this.launch_spell = launch;
    }
    public void isLightning(boolean lightning) {
        this.lightning_spell = lightning;
    }

    public void handleEntityEvent(byte p_37484_) {
    }

    public void tick() {
        super.tick();
        this.setNoGravity(true);
        if(level().isClientSide() && Config.MAGIC_BULLET_PARTICLE.get()){
            this.level().addParticle(ParticleTypes.WAX_OFF, this.getRandomX(0.6D), this.getRandomY(), this.getRandomZ(0.6D), 0.0D, 0.0D, 0.0D);
        }
        if (Math.abs(this.getDeltaMovement().x + this.getDeltaMovement().y + this.getDeltaMovement().z)< 0.01){
            this.discard();
        }
    }

    protected void onHitEntity(EntityHitResult p_37486_) {
        super.onHitEntity(p_37486_);
        p_37486_.getEntity().hurt(this.damageSources().indirectMagic(this, this.getOwner()), (float)(Config.BULLET_DAMAGE.get() + damage_spell * Config.BULLET_DAMAGE_ENCHANTMENT.get()));
        if (fire_spell != 0){
            p_37486_.getEntity().setSecondsOnFire(fire_spell * 5);
        }
        if (freeze_spell != 0){
            if(p_37486_.getEntity() instanceof LivingEntity entity){
                entity.addEffect(new MobEffectInstance(PotionEffectRegistry.FROST.get(), freeze_spell * 60, 0));
            };
        }
        if (launch_spell != 0){
            double d0 = p_37486_.getEntity().getX() - this.getX();
            double d1 = p_37486_.getEntity().getZ() - this.getZ();
            double d2 = Math.max(d0 * d0 + d1 * d1, 0.001D);
            p_37486_.getEntity().push(d0 / d2 * launch_spell, 0.3D, d1 / d2 * launch_spell);
        }
    }

    protected void onHitBlock(BlockHitResult p_37384_) {
        super.onHitBlock(p_37384_);
        if (!this.level().isClientSide && fire_spell != 0) {
            BlockPos blockpos = p_37384_.getBlockPos().relative(p_37384_.getDirection());
            if (this.level().isEmptyBlock(blockpos)) {
                this.level().setBlockAndUpdate(blockpos, BaseFireBlock.getState(this.level(), blockpos));
            }
        }
    }

    protected void onHit(HitResult p_37488_) {
        super.onHit(p_37488_);
        if (!this.level().isClientSide) {
            if (explode_spell != 0){
                this.level().explode(this, this.getX(), this.getY(), this.getZ(), (float)explode_spell, this.fire_spell != 0, Level.ExplosionInteraction.NONE);
            }
            if (lightning_spell){
                LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(this.level());
                if (bolt != null) {
                    bolt.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                    this.level().addFreshEntity(bolt);
                    this.discard();
                }
            }
            this.playSound(SoundEvents.ENDER_EYE_DEATH);
            this.level().levelEvent(3004, this.blockPosition(), this.isSilent() ? -1 : 1);
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }

    protected Item getDefaultItem() {
        return Items.AIR;
    }
}
