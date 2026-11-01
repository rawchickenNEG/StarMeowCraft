package com.starmeow.smc.entities.projectiles;

import com.starmeow.smc.entities.ChickenHarvester;
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

public class ThrownChickenHarvesterEgg extends ThrowableItemProjectile {
    public ThrownChickenHarvesterEgg(EntityType<? extends ThrownChickenHarvesterEgg> p_37473_, Level p_37474_) {
        super(p_37473_, p_37474_);
    }

    public ThrownChickenHarvesterEgg(Level p_37481_, LivingEntity p_37482_) {
        super(EntityType.EGG, p_37482_, p_37481_);
    }

    public ThrownChickenHarvesterEgg(Level p_37476_, double p_37477_, double p_37478_, double p_37479_) {
        super(EntityType.EGG, p_37477_, p_37478_, p_37479_, p_37476_);
    }

    public void handleEntityEvent(byte p_37484_) {
        if (p_37484_ == 3) {
            double $$1 = 0.08;

            for(int $$2 = 0; $$2 < 8; ++$$2) {
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, ItemRegistry.FRIED_CHICKEN_HARVESTER_EGG.get().getDefaultInstance()), this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - 0.5) * 0.08, ((double)this.random.nextFloat() - 0.5) * 0.08, ((double)this.random.nextFloat() - 0.5) * 0.08);
            }
        }

    }

    protected void onHitEntity(EntityHitResult p_37486_) {
        super.onHitEntity(p_37486_);
        p_37486_.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 0.0F);
    }

    protected void onHit(HitResult p_37488_) {
        super.onHit(p_37488_);
        if (!this.level().isClientSide) {
            ChickenHarvester $$3 = EntityTypeRegistry.CHICKEN_HARVESTER.get().create(this.level());
            if ($$3 != null) {
                $$3.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                this.level().addFreshEntity($$3);
            }

            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }

    }

    protected Item getDefaultItem() {
        return ItemRegistry.CHICKEN_HARVESTER_EGG.get();
    }
}
