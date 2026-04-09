package com.starmeow.smc.entities.projectiles;

import com.starmeow.smc.config.Config;
import com.starmeow.smc.init.EntityTypeRegistry;
import com.starmeow.smc.init.ItemRegistry;
import com.starmeow.smc.init.PotionEffectRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FrostArrow extends AbstractArrow {
    private int duration = 200;

    public FrostArrow(EntityType<? extends FrostArrow> p_37411_, Level p_37412_) {
        super(p_37411_, p_37412_);
    }

    public FrostArrow(Level p_37419_, LivingEntity p_37420_) {
        super(EntityTypeRegistry.FROST_ARROW.get(), p_37420_, p_37419_);
    }

    public FrostArrow(Level p_37414_, double p_37415_, double p_37416_, double p_37417_) {
        super(EntityTypeRegistry.FROST_ARROW.get(), p_37415_, p_37416_, p_37417_, p_37414_);
    }

    public void tick() {
        super.tick();
        if (this.level().isClientSide && !this.inGround && Config.FROST_ARROW_PARTICLE.get()) {
            this.level().addParticle(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
        }
    }

    protected ItemStack getPickupItem() {
        return new ItemStack(ItemRegistry.FROST_ARROW.get());
    }

    protected void doPostHurtEffects(LivingEntity p_37422_) {
        super.doPostHurtEffects(p_37422_);
        MobEffectInstance $$1 = new MobEffectInstance(PotionEffectRegistry.FROST.get(), this.duration, 0);
        p_37422_.addEffect($$1, this.getEffectSource());
    }

    public void readAdditionalSaveData(CompoundTag p_37424_) {
        super.readAdditionalSaveData(p_37424_);
        if (p_37424_.contains("Duration")) {
            this.duration = p_37424_.getInt("Duration");
        }

    }

    public void addAdditionalSaveData(CompoundTag p_37426_) {
        super.addAdditionalSaveData(p_37426_);
        p_37426_.putInt("Duration", this.duration);
    }
}
