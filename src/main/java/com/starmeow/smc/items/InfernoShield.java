package com.starmeow.smc.items;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class InfernoShield extends ShieldItem {
    public InfernoShield(Properties p_43089_) {
        super(p_43089_);
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack item, int count) {
        if (entity instanceof Player player && player.isCrouching()) {
            for (int s = 1; s <= 5; ++s){
                double sx = s * entity.getLookAngle().x + entity.getX();
                double sy = s * entity.getLookAngle().y + entity.getY() + entity.getEyeHeight();
                double sz = s * entity.getLookAngle().z + entity.getZ();
                Vec3 svec3 = new Vec3(sx,sy,sz);
                if(s == 1){
                    if(count % 3 == 0) {
                        level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 1F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
                    }
                    for(int m = 1; m <= 10; ++m){
                    level.addParticle(ParticleTypes.FLAME, sx, sy, sz, 0.3 * entity.getLookAngle().x + 0.3 * (entity.getRandom().nextFloat() - 0.5), 0.3 * entity.getLookAngle().y + 0.3 * (entity.getRandom().nextFloat() - 0.5), 0.3 * entity.getLookAngle().z + 0.3 * (entity.getRandom().nextFloat() - 0.5));
                    }
                }
                AABB aabb = new AABB(svec3, svec3).inflate(1 + 0.2 * s);
                List<LivingEntity> entities = entity.level().getEntitiesOfClass(LivingEntity.class, aabb);
                for (Entity target : entities) {
                    if (target != entity){
                        target.hurt(level.damageSources().onFire(), 8 - s);
                        target.setSecondsOnFire(5);
                    }
                }
            }
        }
    }


}
