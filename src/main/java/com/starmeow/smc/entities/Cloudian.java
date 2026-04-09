package com.starmeow.smc.entities;

import com.starmeow.smc.helper.EntityHelper;
import com.starmeow.smc.init.ItemRegistry;
import com.starmeow.smc.init.ParticleRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.function.Predicate;

public class Cloudian extends Monster {
    protected static final int ATTACK_TIME = 20;
    private static final EntityDataAccessor<Boolean> DATA_ID_MOVING;
    private static final EntityDataAccessor<Integer> DATA_ID_ATTACK_TARGET;
    private float clientSideTailAnimation;
    private float clientSideTailAnimationO;
    private float clientSideTailAnimationSpeed;
    private float clientSideSpikesAnimation;
    private float clientSideSpikesAnimationO;
    @Nullable
    private LivingEntity clientSideCachedAttackTarget;
    private int clientSideAttackTime;
    private boolean clientSideTouchedGround;
    @Nullable
    protected RandomStrollGoal randomStrollGoal;

    public Cloudian(EntityType<? extends Cloudian> p_32810_, Level p_32811_) {
        super(p_32810_, p_32811_);
        this.xpReward = 10;
        //this.setPathfindingMalus(BlockPathTypes.OPEN, 0.0F);
        this.moveControl = new Cloudian.GuardianMoveControl(this);
        this.clientSideTailAnimation = this.random.nextFloat();
        this.clientSideTailAnimationO = this.clientSideTailAnimation;
    }

    protected void registerGoals() {
        MoveTowardsRestrictionGoal $$0 = new MoveTowardsRestrictionGoal(this, 1.0);
        this.randomStrollGoal = new RandomStrollGoal(this, 1.0, ATTACK_TIME);
        this.goalSelector.addGoal(4, new Cloudian.GuardianAttackGoal(this));
        this.goalSelector.addGoal(5, $$0);
        this.goalSelector.addGoal(7, this.randomStrollGoal);
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Cloudian.class, 12.0F, 0.01F));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
        this.randomStrollGoal.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        $$0.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, LivingEntity.class, 10, true, false, new Cloudian.GuardianAttackSelector(this)));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.ATTACK_DAMAGE, 6.0).add(Attributes.MOVEMENT_SPEED, 0.5).add(Attributes.FOLLOW_RANGE, 32.0).add(Attributes.MAX_HEALTH, 80.0);
    }

    protected PathNavigation createNavigation(Level p_32846_) {
        FlyingPathNavigation nav = new FlyingPathNavigation(this, p_32846_);
        nav.setCanOpenDoors(false);
        nav.setCanFloat(true);
        nav.setCanPassDoors(false);
        return nav;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_MOVING, false);
        this.entityData.define(DATA_ID_ATTACK_TARGET, 0);
    }

    public boolean isMoving() {
        return (Boolean)this.entityData.get(DATA_ID_MOVING);
    }

    void setMoving(boolean p_32862_) {
        this.entityData.set(DATA_ID_MOVING, p_32862_);
    }

    public int getAttackDuration() {
        return ATTACK_TIME;
    }

    void setActiveAttackTarget(int p_32818_) {
        this.entityData.set(DATA_ID_ATTACK_TARGET, p_32818_);
    }

    public boolean hasActiveAttackTarget() {
        return this.entityData.get(DATA_ID_ATTACK_TARGET) != 0;
    }

    @Nullable
    public LivingEntity getActiveAttackTarget() {
        if (!this.hasActiveAttackTarget()) {
            return null;
        } else if (this.level().isClientSide) {
            if (this.clientSideCachedAttackTarget != null) {
                return this.clientSideCachedAttackTarget;
            } else {
                Entity $$0 = this.level().getEntity((Integer)this.entityData.get(DATA_ID_ATTACK_TARGET));
                if ($$0 instanceof LivingEntity) {
                    this.clientSideCachedAttackTarget = (LivingEntity)$$0;
                    return this.clientSideCachedAttackTarget;
                } else {
                    return null;
                }
            }
        } else {
            return this.getTarget();
        }
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> p_32834_) {
        super.onSyncedDataUpdated(p_32834_);
        if (DATA_ID_ATTACK_TARGET.equals(p_32834_)) {
            this.clientSideAttackTime = 0;
            this.clientSideCachedAttackTarget = null;
        }

    }

    public int getAmbientSoundInterval() {
        return 160;
    }

    public float getClientSideAttackTime() {
        return (float)this.clientSideAttackTime;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.GUARDIAN_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource p_32852_) {
        return SoundEvents.GUARDIAN_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.GUARDIAN_DEATH;
    }

    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.EVENTS;
    }

    protected float getStandingEyeHeight(Pose p_32843_, EntityDimensions p_32844_) {
        return p_32844_.height * 0.5F;
    }

    public float getWalkTargetValue(BlockPos p_32831_, LevelReader p_32832_) {
        return p_32832_.getBlockState(p_32831_).is(Blocks.AIR) ? 10.0F + p_32832_.getPathfindingCostFromLightLevels(p_32831_) : super.getWalkTargetValue(p_32831_, p_32832_);
    }

    public void aiStep() {
        if (this.isAlive()) {
            if (this.level().isClientSide) {
                this.clientSideTailAnimationO = this.clientSideTailAnimation;
                Vec3 $$1;
                if (this.isMoving()) {
                    if (this.clientSideTailAnimationSpeed < 0.5F) {
                        this.clientSideTailAnimationSpeed = 4.0F;
                    } else {
                        this.clientSideTailAnimationSpeed += (0.5F - this.clientSideTailAnimationSpeed) * 0.1F;
                    }
                } else {
                    this.clientSideTailAnimationSpeed += (0.125F - this.clientSideTailAnimationSpeed) * 0.2F;
                }

                this.clientSideTailAnimation += this.clientSideTailAnimationSpeed;
                this.clientSideSpikesAnimationO = this.clientSideSpikesAnimation;
                if (this.isMoving()) {
                    this.clientSideSpikesAnimation += (0.0F - this.clientSideSpikesAnimation) * 0.25F;
                } else {
                    this.clientSideSpikesAnimation += (1.0F - this.clientSideSpikesAnimation) * 0.06F;
                }

                if (this.isMoving()) {
                    $$1 = this.getViewVector(0.0F);

                    for(int $$2 = 0; $$2 < 2; ++$$2) {
                        this.level().addParticle(ParticleRegistry.SWORD_AURA.get(), this.getRandomX(0.5) - $$1.x * 1.5, this.getRandomY() - $$1.y * 1.5, this.getRandomZ(0.5) - $$1.z * 1.5, 0.0, 0.0, 0.0);
                    }
                }

                if (this.hasActiveAttackTarget()) {
                    if (this.clientSideAttackTime < this.getAttackDuration()) {
                        ++this.clientSideAttackTime;
                    }
                }
            }
            this.setAirSupply(300);
            this.fallDistance = 0;
            if (this.hasActiveAttackTarget()) {
                this.setYRot(this.yHeadRot);
            }
        }

        super.aiStep();
    }

    protected SoundEvent getFlopSound() {
        return SoundEvents.GUARDIAN_FLOP;
    }

    public float getTailAnimation(float p_32864_) {
        return Mth.lerp(p_32864_, this.clientSideTailAnimationO, this.clientSideTailAnimation);
    }

    public float getSpikesAnimation(float p_32866_) {
        return Mth.lerp(p_32866_, this.clientSideSpikesAnimationO, this.clientSideSpikesAnimation);
    }

    public float getAttackAnimationScale(float p_32813_) {
        return ((float)this.clientSideAttackTime + p_32813_) / (float)this.getAttackDuration();
    }

    public boolean hurt(DamageSource p_32820_, float p_32821_) {
        if (this.level().isClientSide) {
            return false;
        } else {
            if (!this.isMoving() && !p_32820_.is(DamageTypeTags.AVOIDS_GUARDIAN_THORNS) && !p_32820_.is(DamageTypes.THORNS)) {
                Entity var4 = p_32820_.getDirectEntity();
                if (var4 instanceof LivingEntity) {
                    LivingEntity $$2 = (LivingEntity)var4;
                    $$2.hurt(this.damageSources().thorns(this), 2.0F);
                }
            }

            if (this.randomStrollGoal != null) {
                this.randomStrollGoal.trigger();
            }

            return super.hurt(p_32820_, p_32821_);
        }
    }

    public int getMaxHeadXRot() {
        return 180;
    }

    public void travel(Vec3 p_32858_) {
        if (this.isControlledByLocalInstance()) {
            this.moveRelative(0.1F, p_32858_);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
            if (!this.isMoving() && this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.002, 0.0));
            }
        } else {
            super.travel(p_32858_);
        }

    }

    static {
        DATA_ID_MOVING = SynchedEntityData.defineId(Cloudian.class, EntityDataSerializers.BOOLEAN);
        DATA_ID_ATTACK_TARGET = SynchedEntityData.defineId(Cloudian.class, EntityDataSerializers.INT);
    }

    private static class GuardianMoveControl extends MoveControl {
        private final Cloudian guardian;

        public GuardianMoveControl(Cloudian p_32886_) {
            super(p_32886_);
            this.guardian = p_32886_;
        }

        public void tick() {
            if (this.operation == Operation.MOVE_TO && !this.guardian.getNavigation().isDone()) {
                Vec3 $$0 = new Vec3(this.wantedX - this.guardian.getX(), this.wantedY - this.guardian.getY(), this.wantedZ - this.guardian.getZ());
                double $$1 = $$0.length();
                double $$2 = $$0.x / $$1;
                double $$3 = $$0.y / $$1;
                double $$4 = $$0.z / $$1;
                float $$5 = (float)(Mth.atan2($$0.z, $$0.x) * 57.2957763671875) - 90.0F;
                this.guardian.setYRot(this.rotlerp(this.guardian.getYRot(), $$5, 90.0F));
                this.guardian.yBodyRot = this.guardian.getYRot();
                float $$6 = (float)(this.speedModifier * this.guardian.getAttributeValue(Attributes.MOVEMENT_SPEED));
                float $$7 = Mth.lerp(0.125F, this.guardian.getSpeed(), $$6);
                this.guardian.setSpeed($$7);
                double $$8 = Math.sin((double)(this.guardian.tickCount + this.guardian.getId()) * 0.5) * 0.05;
                double $$9 = Math.cos((double)(this.guardian.getYRot() * 0.017453292F));
                double $$10 = Math.sin((double)(this.guardian.getYRot() * 0.017453292F));
                double $$11 = Math.sin((double)(this.guardian.tickCount + this.guardian.getId()) * 0.75) * 0.05;
                this.guardian.setDeltaMovement(this.guardian.getDeltaMovement().add($$8 * $$9, $$11 * ($$10 + $$9) * 0.25 + (double)$$7 * $$3 * 0.1, $$8 * $$10));
                LookControl $$12 = this.guardian.getLookControl();
                double $$13 = this.guardian.getX() + $$2 * 2.0;
                double $$14 = this.guardian.getEyeY() + $$3 / $$1;
                double $$15 = this.guardian.getZ() + $$4 * 2.0;
                double $$16 = $$12.getWantedX();
                double $$17 = $$12.getWantedY();
                double $$18 = $$12.getWantedZ();
                if (!$$12.isLookingAtTarget()) {
                    $$16 = $$13;
                    $$17 = $$14;
                    $$18 = $$15;
                }

                this.guardian.getLookControl().setLookAt(Mth.lerp(0.125, $$16, $$13), Mth.lerp(0.125, $$17, $$14), Mth.lerp(0.125, $$18, $$15), 10.0F, 40.0F);
                this.guardian.setMoving(true);
            } else {
                this.guardian.setSpeed(0.0F);
                this.guardian.setMoving(false);
            }
        }
    }

    static class GuardianAttackGoal extends Goal {
        private final Cloudian guardian;
        private int attackTime;

        public GuardianAttackGoal(Cloudian p_32871_) {
            this.guardian = p_32871_;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        public boolean canUse() {
            LivingEntity $$0 = this.guardian.getTarget();
            return $$0 != null && $$0.isAlive();
        }

        public boolean canContinueToUse() {
            return super.canContinueToUse() && (this.guardian.getTarget() != null && this.guardian.distanceToSqr(this.guardian.getTarget()) > 9.0);
        }

        public void start() {
            this.attackTime = -10;
            this.guardian.getNavigation().stop();
            LivingEntity $$0 = this.guardian.getTarget();
            if ($$0 != null) {
                this.guardian.getLookControl().setLookAt($$0, 90.0F, 90.0F);
            }

            this.guardian.hasImpulse = true;
        }

        public void stop() {
            this.guardian.setActiveAttackTarget(0);
            this.guardian.setTarget((LivingEntity)null);
            this.guardian.randomStrollGoal.trigger();
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            LivingEntity $$0 = this.guardian.getTarget();
            if ($$0 != null) {
                this.guardian.getNavigation().stop();
                this.guardian.getLookControl().setLookAt($$0, 90.0F, 90.0F);
                if (!this.guardian.hasLineOfSight($$0)) {
                    this.guardian.setTarget(null);
                } else {
                    ++this.attackTime;
                    if (this.attackTime == 0) {
                        this.guardian.setActiveAttackTarget($$0.getId());
                        if (!this.guardian.isSilent()) {
                            this.guardian.level().broadcastEntityEvent(this.guardian, (byte)21);
                        }
                    } else if (this.attackTime >= this.guardian.getAttackDuration()) {
                        ItemStack stack = new ItemStack(ItemRegistry.EXCALIBUR.get());
                        if(!this.guardian.level().isClientSide()){
                            EntityHelper.shootExcaliburSwordProjectile(stack, this.guardian);
                        }
                        this.guardian.setTarget(null);
                    }

                    super.tick();
                }
            }
        }
    }

    private static class GuardianAttackSelector implements Predicate<LivingEntity> {
        private final Cloudian guardian;

        public GuardianAttackSelector(Cloudian p_32879_) {
            this.guardian = p_32879_;
        }

        public boolean test(@Nullable LivingEntity p_32881_) {
            return (p_32881_ instanceof Player || p_32881_.getMobType() == MobType.UNDEAD) && p_32881_.distanceToSqr(this.guardian) > 9.0;
        }
    }
}
