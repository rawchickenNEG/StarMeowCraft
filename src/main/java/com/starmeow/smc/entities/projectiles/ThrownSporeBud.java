package com.starmeow.smc.entities.projectiles;

import com.starmeow.smc.init.EntityTypeRegistry;
import com.starmeow.smc.init.ItemRegistry;
import com.starmeow.smc.init.PotionEffectRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ThrownSporeBud extends ThrowableItemProjectile {
    public ThrownSporeBud(EntityType<? extends ThrownSporeBud> entityType, Level level) {
        super(entityType, level);
    }
    public ThrownSporeBud(Level level, LivingEntity entity) {
        super(EntityTypeRegistry.THROWN_SPORE_BUD.get(), entity, level);
    }
    public ThrownSporeBud(Level level, double x, double y, double z) {
        super(EntityTypeRegistry.THROWN_SPORE_BUD.get(), x, y, z, level);
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

    protected void onHit(HitResult p_37488_) {
        super.onHit(p_37488_);
        if (!this.level().isClientSide) {
            final Vec3 center = new Vec3(this.getX(), this.getY(), this.getZ());
            int r = 3;
            AABB aabb = new AABB(center, center).inflate(r);
            List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class, aabb);
            for (LivingEntity target : entities) {
                if(target.hasEffect(PotionEffectRegistry.LOVE.get())){
                    target.addEffect(new MobEffectInstance(PotionEffectRegistry.LOVE.get(), 600, Math.min(target.getEffect(PotionEffectRegistry.LOVE.get()).getAmplifier() + 1, 4)));
                }
                target.addEffect(new MobEffectInstance(PotionEffectRegistry.LOVE.get(), 600, 0));
            }
            if(level() instanceof ServerLevel serverLevel){
                for (BlockPos tmpPos : BlockPos.withinManhattan(this.getOnPos(), r * 2, r * 2, r * 2)){
                    serverLevel.sendParticles(ParticleTypes.SPORE_BLOSSOM_AIR, tmpPos.getX(), tmpPos.getY(), tmpPos.getZ(), 3, 0.5D, 0.5D, 0.5D, 0.0D);
                }
            }
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }

    }

    protected Item getDefaultItem() {
        return ItemRegistry.SPORE_BUD.get();
    }
}
