package com.starmeow.smc.entities.projectiles;

import com.starmeow.smc.client.renderer.SwordAuraRenderer;
import com.starmeow.smc.config.Config;
import com.starmeow.smc.entities.ThrownSwordEntity;
import com.starmeow.smc.init.EntityTypeRegistry;
import com.starmeow.smc.init.ParticleRegistry;
import com.starmeow.smc.init.PotionEffectRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;

import javax.annotation.Nullable;
import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SwordAura extends ThrowableItemProjectile {
    private int life = 0;
    private Entity owner;
    private int divine;
    private final List<UUID> attackedEntityUUID = new ArrayList<>();
    public SwordAura(EntityType<? extends SwordAura> p_37411_, Level p_37412_) {
        super(p_37411_, p_37412_);
    }
    public SwordAura(Level level, double x, double y, double z) {
        super(EntityTypeRegistry.SWORD_AURA.get(), x, y, z, level);
    }

    public void handleEntityEvent(byte p_37484_) {
    }

    public void setOwner(@Nullable LivingEntity owner) {
        this.owner = owner;
    }

    public void setDivineLevel(int level){
        this.divine = level;
    }

    @Nullable
    public LivingEntity getOwner() {
        if (this.owner == null) return null;
        return this.owner instanceof LivingEntity living ? living : null;
    }

    public void tick() {
        super.tick();
        this.setNoGravity(true);
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
                    float extraDamage = (float) Math.max(Config.AURA_BASE_DAMAGE.get() * this.divine * 0.3f, living.getHealth() * Config.AURA_EXTRA_DAMAGE.get() * 0.01f);
                    living.hurt(this.damageSources().indirectMagic(this, this.getOwner()), (float) (Config.AURA_BASE_DAMAGE.get() + extraDamage));
                    living.invulnerableTime = 0;
                    attackedEntityUUID.add(living.getUUID());
                }
            }
        }
    }

    protected Item getDefaultItem() {
        return Items.AIR;
    }
}
