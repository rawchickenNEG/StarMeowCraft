package com.starmeow.smc.entities.projectiles;

import com.starmeow.smc.config.Config;
import com.starmeow.smc.entities.ThrownSwordEntity;
import com.starmeow.smc.init.EntityTypeRegistry;
import com.starmeow.smc.init.ParticleRegistry;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MeowBall extends ThrowableProjectile {
    private int life = 0;
    private Entity owner;
    private final List<UUID> attackedEntityUUID = new ArrayList<>();
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(MeowBall.class, EntityDataSerializers.INT);
    public MeowBall(EntityType<? extends MeowBall> p_37411_, Level p_37412_) {
        super(p_37411_, p_37412_);
    }

    public MeowBall(Level level, double x, double y, double z) {
        super(EntityTypeRegistry.MEOW_BALL.get(), x, y, z, level);
    }

    public void handleEntityEvent(byte p_37484_) {
    }

    public void setOwner(@Nullable LivingEntity owner) {
        this.owner = owner;
    }

    @Nullable
    public LivingEntity getOwner() {
        if (this.owner == null) return null;
        return this.owner instanceof LivingEntity living ? living : null;
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult result) {
        Vec3 normal = Vec3.atLowerCornerOf(result.getDirection().getNormal());
        Vec3 velocity = this.getDeltaMovement();
        this.attackedEntityUUID.clear();

        double dotProduct = velocity.dot(normal);
        Vec3 newVelocity = velocity.subtract(normal.scale(2 * dotProduct));

        Vec3 position = this.position();
        Vec3 pushBack = normal.scale(0.05);
        this.setPos(position.x + pushBack.x, position.y + pushBack.y, position.z + pushBack.z);

        this.setDeltaMovement(newVelocity);
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.CAT_AMBIENT, SoundSource.NEUTRAL, 1.0F, 1.5F);

    }

    public void tick() {
        super.tick();
        if(level().isClientSide() && Config.SWORD_AURA_PARTICLE.get()){
            this.level().addParticle(ParticleRegistry.SWORD_AURA.get(), this.getRandomX(0.6D), this.getRandomY(), this.getRandomZ(0.6D), 0.0D, 0.0D, 0.0D);
        }
        if (Math.abs(this.getDeltaMovement().x + this.getDeltaMovement().y + this.getDeltaMovement().z)< 0.01){
            this.discard();
        }
        if(life >= 80){
            this.discard();
        }else{
            life++;
        }
        if (!level().isClientSide) {
            tryHitSomething();
        }
    }

    private void tryHitSomething() {
        LivingEntity owner = getOwner();
        List<Entity> hits = level().getEntities(this, this.getBoundingBox(), e -> e instanceof LivingEntity && e.isAlive() && !(e instanceof ThrownSwordEntity) && e != owner);
        if (!hits.isEmpty()) {
            for(Entity e : hits){
                if(e instanceof LivingEntity living && !attackedEntityUUID.contains(living.getUUID())){
                    living.invulnerableTime = 0;
                    float extraDamage = 10;
                    boolean hurt = living.hurt(this.damageSources().indirectMagic(this, this.getOwner()), (float) (Config.AURA_BASE_DAMAGE.get() + extraDamage));
                    if (hurt) {
                        attackedEntityUUID.add(living.getUUID());
                    }
                }
            }
        }
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(VARIANT, 0);
    }

    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, Mth.clamp(variant, 0, 5));
    }

    protected Item getDefaultItem() {
        return Items.AIR;
    }
}
