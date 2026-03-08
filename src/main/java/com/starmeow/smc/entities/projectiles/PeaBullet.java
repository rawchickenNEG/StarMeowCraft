package com.starmeow.smc.entities.projectiles;

import com.starmeow.smc.init.EntityTypeRegistry;
import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class PeaBullet extends ThrowableItemProjectile {
    public PeaBullet(EntityType<? extends PeaBullet> entityType, Level level) {
        super(entityType, level);
    }
    public PeaBullet(Level level, LivingEntity entity) {
        super(EntityTypeRegistry.PEA_BULLET.get(), entity, level);
    }
    public PeaBullet(Level level, double x, double y, double z) {
        super(EntityTypeRegistry.PEA_BULLET.get(), x, y, z, level);
    }
    public float damage = 0;
    public double back = 0;
    public void setDamage(float damage) {
        this.damage = damage;
    }
    public void setBack(double back) {
        this.back = back;
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
        p_37486_.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 4.0f + 2.0f * damage);
    }

    protected void onHit(HitResult p_37488_) {
        super.onHit(p_37488_);
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }

    }

    protected Item getDefaultItem() {
        return ItemRegistry.PEA.get();
    }
}
