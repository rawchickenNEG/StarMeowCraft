package com.starmeow.smc.entities;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.ForgeHooks;

public abstract class AntiInstantKillMobs extends PathfinderMob {
    protected AntiInstantKillMobs(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
    }

    @Override
    protected void actuallyHurt(DamageSource source, float damage) {
        if (!this.isInvulnerableTo(source)) {
            damage = ForgeHooks.onLivingHurt(this, source, damage);
            if (damage <= 0.0F) {
                return;
            }
            damage = this.getDamageAfterArmorAbsorb(source, damage);
            damage = this.getDamageAfterMagicAbsorb(source, damage);
            float f1 = Math.max(damage - this.getAbsorptionAmount(), 0.0F);
            this.setAbsorptionAmount(this.getAbsorptionAmount() - (damage - f1));
            float f = damage - f1;
            if (f > 0.0F && f < 3.4028235E37F) {
                Entity entity = source.getEntity();
                if (entity instanceof ServerPlayer) {
                    ServerPlayer serverplayer = (ServerPlayer)entity;
                    serverplayer.awardStat(Stats.DAMAGE_DEALT_ABSORBED, Math.round(f * 10.0F));
                }
            }

            f1 = ForgeHooks.onLivingDamage(this, source, f1);
            //Decrease Damage above 10
            if(f1 > 10.0F){
                f1 = (float)(Math.log10(f1) * 20 - 10);
            }
            if (f1 != 0.0F) {
                this.getCombatTracker().recordDamage(source, f1);
                this.setHealth(this.getHealth() - f1);
                this.setAbsorptionAmount(this.getAbsorptionAmount() - f1);
                this.gameEvent(GameEvent.ENTITY_DAMAGE);
            }
        }
    }
}
