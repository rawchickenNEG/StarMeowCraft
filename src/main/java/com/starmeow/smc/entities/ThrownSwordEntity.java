package com.starmeow.smc.entities;

import com.starmeow.smc.config.Config;
import com.starmeow.smc.helper.EntityHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class ThrownSwordEntity extends PathfinderMob implements ItemSupplier {
    private int life;
    private Entity owner;
    private Entity target;

    public ThrownSwordEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
        this.setNoAi(true);
        this.setNoGravity(true);
        this.setInvulnerable(true);
        this.noPhysics = true;
        this.life = 50;
    }

    public void setOwner(@Nullable LivingEntity owner) {
        this.owner = owner;
    }

    @Nullable
    public LivingEntity getOwner() {
        if (owner == null) return null;
        return owner instanceof LivingEntity living ? living : null;
    }

    @Override
    protected void registerGoals() {}

    @Override
    public boolean hurt(DamageSource p_32013_, float p_32014_) {return false;}
    @Override
    public boolean isPickable() {return false;}
    @Override
    public boolean isAttackable() { return false; }
    @Override
    public boolean isPushable() { return false; }
    @Override
    public boolean canBeCollidedWith() { return false; }


    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide && --life < 0) {
            discard();
            return;
        }
        if(level().isClientSide && Config.FLYING_SWORD_PARTICLE.get()){
            this.level().addParticle(ParticleTypes.WAX_OFF, this.getRandomX(0.6D), this.getRandomY(), this.getRandomZ(0.6D), 0.0D, 0.0D, 0.0D);
        }
        this.checkInsideBlocks();
        if(this.target != null){
            Vec3 targetPos = EntityHelper.getVec3(this.target);
            Vec3 pos = this.position();
            Vec3 toTarget = targetPos.subtract(pos);

            if (toTarget.lengthSqr() > 0.2 * 0.2){
                Vec3 vel = this.getDeltaMovement();
                double speed = Math.max(vel.length(), 1F);

                Vec3 curDir = vel.normalize();
                Vec3 desiredDir = toTarget.normalize();

                // 每秒最大转向角
                double turnDegPerSec = 240;
                double maxTurnRadPerTick = Math.toRadians(turnDegPerSec) / 20.0;

                Vec3 newDir = followingTarget(curDir, desiredDir, maxTurnRadPerTick);

                this.setDeltaMovement(newDir.scale(speed));
                this.hasImpulse = true;
            }

        }else{
            Vec3 vec3 = this.getDeltaMovement();
            double d2 = this.getX() + vec3.x;
            double d0 = this.getY() + vec3.y;
            double d1 = this.getZ() + vec3.z;
            this.updateRotation();
            float f = 1F;
            this.setDeltaMovement(vec3.scale(f));
            this.setPos(d2, d0, d1);
        }


        this.move(MoverType.SELF, this.getDeltaMovement());
        if (!level().isClientSide) {
            trySettingTarget();
            tryHitSomething();
        }
    }

    private void trySettingTarget() {
        if(this.target != null) return;
        LivingEntity owner = this.getOwner();
        AABB box = this.getBoundingBox().inflate(life / 10.0);
        List<Entity> targets = level().getEntities(this, box, e -> e instanceof LivingEntity && e.isAlive() && e != owner && !(e instanceof ThrownSwordEntity));
        if (!targets.isEmpty()) {
            this.target = targets.get(this.getRandom().nextInt(targets.size()));
        };
    }

    private Vec3 followingTarget(Vec3 curDir, Vec3 desiredDir, double maxTurnRad) {
        curDir = curDir.normalize();
        desiredDir = desiredDir.normalize();

        double dot = Mth.clamp(curDir.dot(desiredDir), -1.0, 1.0);
        double angle = Math.acos(dot);

        if (angle < 1.0e-6) return desiredDir;

        double t = Math.min(1.0, maxTurnRad / angle);

        double sin = Math.sin(angle);
        double w1 = Math.sin((1.0 - t) * angle) / sin;
        double w2 = Math.sin(t * angle) / sin;

        return curDir.scale(w1).add(desiredDir.scale(w2)).normalize();
    }

    private void tryHitSomething() {
        LivingEntity owner = getOwner();

        AABB box = this.getBoundingBox().inflate(0.25);
        List<Entity> hits = level().getEntities(this, box, e -> e instanceof LivingEntity && e.isAlive() && !(e instanceof ThrownSwordEntity));
        if (!hits.isEmpty()) {
            LivingEntity target = (LivingEntity) hits.get(0);
            if(target != owner){
                if(this.target == owner) return;
                target.invulnerableTime = 0;
                boolean success = this.doHurtTarget(target);
                ItemStack weapon = this.getMainHandItem();
                if(this.getMainHandItem().getItem() instanceof SwordItem sword){
                    sword.hurtEnemy(weapon, target, getOwner() != null ? getOwner() : this);
                    if(getOwner() instanceof Player player){
                        sword.onLeftClickEntity(weapon, player, target);
                    }
                }

                if (success && owner instanceof Player player) {
                    target.setLastHurtByPlayer(player);
                }
                this.target = owner;
            } else {
                this.discard();
            }
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1.0)
                .add(Attributes.MOVEMENT_SPEED, 0.0)
                .add(Attributes.ATTACK_DAMAGE, 1.0);
    }

    public void shoot(double p_37266_, double p_37267_, double p_37268_, float p_37269_, float p_37270_) {
        Vec3 vec3 = (new Vec3(p_37266_, p_37267_, p_37268_)).normalize().add(this.random.triangle(0.0, 0.0172275 * (double)p_37270_), this.random.triangle(0.0, 0.0172275 * (double)p_37270_), this.random.triangle(0.0, 0.0172275 * (double)p_37270_)).scale((double)p_37269_);
        this.setDeltaMovement(vec3);
        double d0 = vec3.horizontalDistance();
        this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * 57.2957763671875));
        this.setXRot((float)(Mth.atan2(vec3.y, d0) * 57.2957763671875));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
    }

    public void shootFromRotation(Entity p_37252_, float p_37253_, float p_37254_, float p_37255_, float p_37256_, float p_37257_) {
        float f = -Mth.sin(p_37254_ * 0.017453292F) * Mth.cos(p_37253_ * 0.017453292F);
        float f1 = -Mth.sin((p_37253_ + p_37255_) * 0.017453292F);
        float f2 = Mth.cos(p_37254_ * 0.017453292F) * Mth.cos(p_37253_ * 0.017453292F);
        this.shoot((double)f, (double)f1, (double)f2, p_37256_, p_37257_);
        Vec3 vec3 = p_37252_.getDeltaMovement();
        this.setDeltaMovement(this.getDeltaMovement().add(vec3.x, p_37252_.onGround() ? 0.0 : vec3.y, vec3.z));
    }

    protected void updateRotation() {
        Vec3 vec3 = this.getDeltaMovement();
        double d0 = vec3.horizontalDistance();
        this.setXRot((float)(Mth.atan2(vec3.y, d0) * 57.2957763671875));
        this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * 57.2957763671875));
    }

    protected static float lerpRotation(float p_37274_, float p_37275_) {
        while(p_37275_ - p_37274_ < -180.0F) {
            p_37274_ -= 360.0F;
        }

        while(p_37275_ - p_37274_ >= 180.0F) {
            p_37274_ += 360.0F;
        }

        return Mth.lerp(0.2F, p_37274_, p_37275_);
    }

    @Override
    public ItemStack getItem() {
        return this.getMainHandItem();
    }
}