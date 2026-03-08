package com.starmeow.smc.entities.projectiles;

import com.starmeow.smc.init.EntityTypeRegistry;
import com.starmeow.smc.init.PotionEffectRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class MagicArrow extends AbstractArrow {

    public MagicArrow(EntityType<? extends MagicArrow> p_37411_, Level p_37412_) {
        super(p_37411_, p_37412_);
        this.setPierceLevel((byte)99);
    }

    public MagicArrow(Level p_37419_, LivingEntity p_37420_) {
        super(EntityTypeRegistry.MAGIC_ARROW.get(), p_37420_, p_37419_);
        this.setPierceLevel((byte)99);
    }

    public MagicArrow(Level p_37414_, double p_37415_, double p_37416_, double p_37417_) {
        super(EntityTypeRegistry.MAGIC_ARROW.get(), p_37415_, p_37416_, p_37417_, p_37414_);
        this.setPierceLevel((byte)99);
    }

    protected void onHit(HitResult p_37488_) {
        super.onHit(p_37488_);
        Level level = this.level();
        if (!level.isClientSide) {
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }

    protected void onHitEntity(EntityHitResult p_36757_) {
        super.onHitEntity(p_36757_);
        if(!this.level().isClientSide && p_36757_.getEntity() instanceof LivingEntity living){
            living.invulnerableTime = 0;
        }
    }

    protected ItemStack getPickupItem() {
        return new ItemStack(Items.AIR);
    }
}
