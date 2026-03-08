package com.starmeow.smc.entities;

import com.starmeow.smc.init.EntityTypeRegistry;
import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;


/**To extend TamableAnimal, SaltFish is not a type of AbstractFish like axolotl.*/
//TODO water breathing,
public class SaltFish extends TamableAnimal implements Bucketable {
    protected static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.defineId(SaltFish.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(SaltFish.class, EntityDataSerializers.BOOLEAN);
    public SaltFish(EntityType<SaltFish> p_28276_, Level p_28277_) {
        super(p_28276_, p_28277_);
        this.moveControl = new SaltFishMoveControl(this);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(1, new SaltFishFollowOwnerGoal(this));
        this.goalSelector.addGoal(2, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Player.class, 8.0F, 1.6D, 1.4D,
                e -> EntitySelector.NO_SPECTATORS.and(entity -> this.getOwner() != entity).test(e)));
        this.goalSelector.addGoal(4, new SaltFishSwimmingGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, this::isAngryAt));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return SaltFish.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40D)
                .add(Attributes.ATTACK_DAMAGE, 30D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob p_146744_) {
        SaltFish saltFish = EntityTypeRegistry.SALT_FISH.get().create(level);
        if (saltFish != null) {
            UUID uuid = this.getOwnerUUID();
            if (uuid != null) {
                saltFish.setOwnerUUID(uuid);
                saltFish.setTame(true);
            }
        }

        return saltFish;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(OWNER_UUID, Optional.empty());
        this.entityData.define(FROM_BUCKET, false);
    }
    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("FromBucket", this.fromBucket());
        tag.putInt("Order", this.getPose().ordinal());
    }
    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setFromBucket(tag.getBoolean("FromBucket"));
        int order = tag.getInt("Order");
        for (Pose pose : Pose.values()) {
            if (pose.ordinal() == order) {
                this.setPose(pose);
            }
        }
    }
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (! level().isClientSide) {
            if (this.isTame()) {
                InteractionResult result = super.mobInteract(player, hand);
                if (! result.consumesAction() && this.isOwnedBy(player)) {
                    Pose pose = this.getPose();
                    this.setPose(pose == Pose.SITTING ? Pose.SWIMMING : pose == Pose.SWIMMING ? Pose.STANDING : Pose.SITTING);
                    //STANDING for not following player, SWIMMING for following, SITTING for await.
                    if (this.getPose() == Pose.SITTING) {
                        this.jumping = false;
                        this.navigation.stop();
                        this.setTarget(null);
                    }
                    return InteractionResult.SUCCESS;
                }
                return result;
            } else if (itemStack.getItem() == ItemRegistry.CHOCOLATE.get()) {
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
                if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
                    this.tame(player);
                    this.navigation.stop();
                    this.setTarget(null);
                    this.setOrderedToSit(true);
                    this.level().broadcastEntityEvent(this, (byte)7);
                } else {
                    this.level().broadcastEntityEvent(this, (byte)6);
                }
                return InteractionResult.SUCCESS;
            }
            return super.mobInteract(player, hand);
        } else {
            boolean flag = this.isOwnedBy(player) || this.isTame() || (itemStack.is(ItemRegistry.CHOCOLATE.get()) && ! this.isTame());
            if (this.isTame()) {
                for (int i = 0; i < 5; i ++) {
                    level().addParticle(ParticleTypes.BUBBLE,
                            this.getX() - 0.3 + random.nextFloat() * 0.6,
                            this.getY() - 0.3 + random.nextFloat() * 0.6,
                            this.getZ() - 0.3 + random.nextFloat() * 0.6,
                            random.nextFloat() * 0.05 - 0.025, random.nextFloat() * 0.1, random.nextFloat() * 0.05 - 0.025);
                }
            }
            return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
        }
    }


    //Owning.
    @Nullable
    @Override
    public UUID getOwnerUUID() {
        Optional<UUID> opt = this.getEntityData().get(OWNER_UUID);
        return opt.orElse(null);
    }
    @Override
    public void setOwnerUUID(@javax.annotation.Nullable UUID p_21817_) {
        this.entityData.set(OWNER_UUID, Optional.ofNullable(p_21817_));
    }


    //Bucketing.
    @Override
    public void setFromBucket(boolean p_27498_) {
        this.entityData.set(FROM_BUCKET, p_27498_);
    }
    @Override
    public void saveToBucketTag(ItemStack itemStack) {
        Bucketable.saveDefaultDataToBucketTag(this, itemStack);
        CompoundTag tag = itemStack.getOrCreateTag();
        this.addAdditionalSaveData(tag);
    }
    @Override
    public void loadFromBucketTag(CompoundTag tag) {
        Bucketable.loadDefaultDataFromBucketTag(this, tag);
        this.readAdditionalSaveData(tag);
    }
    public ItemStack getBucketItemStack() {
        return new ItemStack(Items.COD_BUCKET);//TODO change this.
    }
    @Override
    public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_FILL_FISH;
    }
    @Override
    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }


    //Sounds.
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
    protected SoundEvent getSwimSound() {
        return SoundEvents.FISH_SWIM;
    }


    //Targeting.
    public boolean canAttack(LivingEntity p_21822_) {
        return !this.isOwnedBy(p_21822_) && super.canAttack(p_21822_);
    }
    public boolean wantsToAttack(LivingEntity target, LivingEntity owner) {
        if (!(target instanceof Creeper) && !(target instanceof Ghast)) {
            if (target instanceof Player && owner instanceof Player && !((Player)owner).canHarmPlayer((Player)target)) {
                return false;
            } else if (target instanceof AbstractHorse && ((AbstractHorse)target).isTamed()) {
                return false;
            } else if (target instanceof SaltFish && ((SaltFish)target).isTame()) {
                return false;
            } else {
                return !(target instanceof TamableAnimal) || !((TamableAnimal)target).isTame();
            }
        } else {
            return false;
        }
    }
    public boolean isAngryAt(LivingEntity living) {
        return living instanceof Enemy && living.isInWaterOrRain();
    }


    //Fish-Acting.
    protected float getStandingEyeHeight(Pose p_27474_, EntityDimensions p_27475_) {
        return p_27475_.height * 0.65F;
    }
    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.fromBucket() || this.getOwnerUUID() != null;
    }
    public boolean removeWhenFarAway(double p_27492_) {
        return !this.fromBucket() && !this.hasCustomName() && this.getOwnerUUID() == null;
    }
    public void travel(Vec3 p_27490_) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(0.01F, p_27490_);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(p_27490_);
        }
    }
    protected PathNavigation createNavigation(Level p_27480_) {
        return new WaterBoundPathNavigation(this, p_27480_);
    }
    public void aiStep() {
        if (!this.isInWater() && this.onGround() && this.verticalCollision) {
            this.setDeltaMovement(this.getDeltaMovement().add((double)((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F), (double)0.4F, (double)((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F)));
            this.setOnGround(false);
            this.hasImpulse = true;
            this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getVoicePitch());
        }

        super.aiStep();
    }
    public boolean canDrownInFluidType(FluidType type) {
        return super.canDrownInFluidType(type) && type != ForgeMod.WATER_TYPE.get();
    }
    protected void handleAirSupply(int p_30344_) {
        if (this.isAlive() && !this.isInWaterOrBubble()) {
            this.setAirSupply(p_30344_ - 1);
            if (this.getAirSupply() == -20) {
                this.setAirSupply(0);
                this.hurt(this.damageSources().drown(), 2.0F);
            }
        } else {
            this.setAirSupply(300);
        }

    }
    public void baseTick() {
        int i = this.getAirSupply();
        super.baseTick();
        this.handleAirSupply(i);
    }
    public boolean isPushedByFluid() {
        return false;
    }
    public boolean canBeLeashed(Player p_30346_) {
        return false;
    }
    public boolean checkSpawnObstruction(LevelReader p_30348_) {
        return p_30348_.isUnobstructed(this);
    }
    public MobType getMobType() {
        return MobType.WATER;
    }
    public boolean canBreatheUnderwater() {
        return true;
    }
    public int getAmbientSoundInterval() {
        return 120;
    }
    public int getExperienceReward() {
        return 1 + this.level().random.nextInt(3);
    }


    public static class SaltFishSwimmingGoal extends RandomSwimmingGoal {
        public final SaltFish fish;
        public SaltFishSwimmingGoal(SaltFish p_27505_) {
            super(p_27505_, 1.0D, 40);
            this.fish = p_27505_;
        }

        public void tick() {
            if (this.fish.getPose() == Pose.SITTING && ! this.fish.navigation.isDone()) {
                this.fish.navigation.stop();
                return;
            }
            super.tick();
        }
    }

    public static class SaltFishFollowOwnerGoal extends Goal {
        public final SaltFish fish;
        private int nextNavigateCount = 20;
        public SaltFishFollowOwnerGoal(SaltFish fish) {
            this.fish = fish;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (nextNavigateCount > 0) {
                nextNavigateCount--;
            } else if (fish.getPose() == Pose.SWIMMING) {
                nextNavigateCount = 20;
                LivingEntity owner = fish.getOwner();
                if (owner != null && owner.isInWaterOrBubble()) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public void tick() {
            LivingEntity owner = fish.getOwner();
            if (owner != null) {
                if (this.fish.distanceToSqr(owner) > 500) {
                    fish.moveTo(owner.position());
                    fish.navigation.moveTo(owner, 1);
                } else if (this.fish.distanceToSqr(owner) > 10) {
                    fish.navigation.moveTo(owner, 1);
                }
            }
        }
    }
    static class SaltFishMoveControl extends MoveControl {
        private final SaltFish fish;

        SaltFishMoveControl(SaltFish p_27501_) {
            super(p_27501_);
            this.fish = p_27501_;
        }

        public void tick() {
            if (this.fish.isEyeInFluid(FluidTags.WATER)) {
                this.fish.setDeltaMovement(this.fish.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
            }

            if (this.operation == Operation.MOVE_TO && !this.fish.getNavigation().isDone()) {
                float f = (float)(this.speedModifier * this.fish.getAttributeValue(Attributes.MOVEMENT_SPEED));
                this.fish.setSpeed(Mth.lerp(0.125F, this.fish.getSpeed(), f));
                double d0 = this.wantedX - this.fish.getX();
                double d1 = this.wantedY - this.fish.getY();
                double d2 = this.wantedZ - this.fish.getZ();
                if (d1 != 0.0D) {
                    double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                    this.fish.setDeltaMovement(this.fish.getDeltaMovement().add(0.0D, (double)this.fish.getSpeed() * (d1 / d3) * 0.1D, 0.0D));
                }

                if (d0 != 0.0D || d2 != 0.0D) {
                    float f1 = (float)(Mth.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                    this.fish.setYRot(this.rotlerp(this.fish.getYRot(), f1, 90.0F));
                    this.fish.yBodyRot = this.fish.getYRot();
                }

            } else {
                this.fish.setSpeed(0.0F);
            }
        }
    }
}
