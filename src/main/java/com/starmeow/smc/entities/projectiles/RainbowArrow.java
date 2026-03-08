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

public class RainbowArrow extends AbstractArrow {
    private final boolean canSummon;

    public RainbowArrow(EntityType<? extends RainbowArrow> p_37411_, Level p_37412_) {
        super(p_37411_, p_37412_);
        this.canSummon = false;
    }

    public RainbowArrow(Level p_37419_, LivingEntity p_37420_) {
        super(EntityTypeRegistry.RAINBOW_ARROW.get(), p_37420_, p_37419_);
        this.canSummon = true;
    }

    public RainbowArrow(Level p_37414_, double p_37415_, double p_37416_, double p_37417_) {
        super(EntityTypeRegistry.RAINBOW_ARROW.get(), p_37415_, p_37416_, p_37417_, p_37414_);
        this.canSummon = false;
    }

    public void tick() {
        super.tick();
        if (this.level().isClientSide && !this.inGround) {
            this.level().addParticle(ParticleTypes.END_ROD, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
        }
    }

    protected void onHit(HitResult p_37488_) {
        super.onHit(p_37488_);
        Level level = this.level();
        if (!level.isClientSide) {
            if (level.isThundering()){
                LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(this.level());
                if (bolt != null) {
                    bolt.moveTo(this.getX(), this.getY(), this.getZ());
                    level.addFreshEntity(bolt);
                }
            }
            if(level.isRaining() && canSummon){
                //无尽贪婪天堂陨落长弓同款
                int number = level.isThundering() ? 15 : 10;
                for(int i = 0; i < number; i++)
                {
                    double angle = level.getRandom().nextDouble() * 2 * Math.PI;
                    double dist = level.getRandom().nextGaussian() * 0.5;
                    double x = Math.sin(angle) * dist + this.getX();
                    double z = Math.cos(angle) * dist + this.getZ();
                    double y = this.getY() + 25.0;
                    double dangle = level.getRandom().nextDouble() * 2 * Math.PI;
                    double ddist = level.getRandom().nextDouble() * 0.35;
                    double dx = Math.sin(dangle) * ddist;
                    double dz = Math.cos(dangle) * ddist;
                    RainbowArrow arrow = new RainbowArrow(level, x, y, z);
                    arrow.setDeltaMovement(dx, -(level.getRandom().nextDouble() * 1.85 + 0.15), dz);
                    arrow.setBaseDamage(this.getBaseDamage());
                    arrow.setOwner(this.getOwner());
                    level.addFreshEntity(arrow);
                }
            }
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }

    protected void onHitEntity(EntityHitResult p_36757_) {
        super.onHitEntity(p_36757_);
        if(!this.level().isClientSide && p_36757_.getEntity() instanceof LivingEntity living && living.level().isThundering()){
            living.addEffect(new MobEffectInstance(PotionEffectRegistry.STUNNED.get(), 20));
        }
    }

    protected ItemStack getPickupItem() {
        return new ItemStack(Items.AIR);
    }
}
