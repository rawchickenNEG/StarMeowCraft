package com.starmeow.smc.blockentities;

import com.starmeow.smc.init.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class SaltFishBlockEntity extends BlockEntity {
    private LivingEntity destroyTarget;
    public float activeRotation;
    @Nullable
    private UUID destroyTargetUUID;
    public long nextAmbientSoundActivation;
    public SaltFishBlockEntity(BlockPos p_155397_, BlockState p_155398_) {
        super(BlockEntityRegistry.SALT_FISH.get(), p_155397_, p_155398_);
    }

    public static void clientTick(Level p_155404_, BlockPos p_155405_, BlockState p_155406_, SaltFishBlockEntity p_155407_) {
        updateClientTarget(p_155404_, p_155405_, p_155407_);
        ++p_155407_.activeRotation;

    }

    public static void serverTick(Level p_155439_, BlockPos p_155440_, BlockState p_155441_, SaltFishBlockEntity p_155442_) {
        long i = p_155439_.getGameTime();
        if (i % 40L == 0L) {
            handleEffects(p_155439_, p_155440_);
            updateDestroyTarget(p_155439_, p_155440_, p_155441_, p_155442_);
        }

        if (i % 80L == 0L) {
            p_155439_.playSound(null, p_155440_, SoundEvents.CONDUIT_AMBIENT, SoundSource.BLOCKS, 1.0F, 1.0F);
        }

        if (i > p_155442_.nextAmbientSoundActivation) {
            p_155442_.nextAmbientSoundActivation = i + 60L + (long)p_155439_.getRandom().nextInt(40);
            p_155439_.playSound(null, p_155440_, SoundEvents.CONDUIT_AMBIENT_SHORT, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }


    public static void handleEffects(Level level, BlockPos p_155445_) {
        int w = 64;
        int x = p_155445_.getX();
        int y = p_155445_.getY();
        int z = p_155445_.getZ();
        AABB aabb = (new AABB(x, y, z, x + 1, y + 1, z + 1)).inflate(w).expandTowards(0.0D, level.getHeight(), 0.0D);
        List<Player> list = level.getEntitiesOfClass(Player.class, aabb);
        if (!list.isEmpty()) {
            for(Player player : list) {
                if (p_155445_.closerThan(player.blockPosition(), w) && player.isInWaterOrRain()) {
                    player.addEffect(new MobEffectInstance(MobEffects.CONDUIT_POWER, 260, 0, true, true));
                    if (player.hasEffect(MobEffects.DIG_SLOWDOWN)) {
                        player.removeEffect(MobEffects.DIG_SLOWDOWN);
                    }
                }
            }

        }
    }

    public static void updateDestroyTarget(Level p_155409_, BlockPos p_155410_, BlockState p_155411_, SaltFishBlockEntity p_155413_) {
        LivingEntity livingentity = p_155413_.destroyTarget;
        if (p_155413_.destroyTarget == null && p_155413_.destroyTargetUUID != null) {
            p_155413_.destroyTarget = findDestroyTarget(p_155409_, p_155410_, p_155413_.destroyTargetUUID);
            p_155413_.destroyTargetUUID = null;
        } else if (p_155413_.destroyTarget == null) {
            List<LivingEntity> list = p_155409_.getEntitiesOfClass(LivingEntity.class, getDestroyRangeAABB(p_155410_), (p_289511_) -> {
                return p_289511_ instanceof Enemy && p_289511_.isInWaterOrRain();
            });
            if (!list.isEmpty()) {
                p_155413_.destroyTarget = list.get(p_155409_.random.nextInt(list.size()));
            }
        } else if (!p_155413_.destroyTarget.isAlive() || !p_155410_.closerThan(p_155413_.destroyTarget.blockPosition(), 8.0D)) {
            p_155413_.destroyTarget = null;
        }

        if (p_155413_.destroyTarget != null) {
            p_155409_.playSound(null, p_155413_.destroyTarget.getX(), p_155413_.destroyTarget.getY(), p_155413_.destroyTarget.getZ(), SoundEvents.CONDUIT_ATTACK_TARGET, SoundSource.BLOCKS, 1.0F, 1.0F);
            p_155413_.destroyTarget.hurt(p_155409_.damageSources().magic(), 4.0F);
        }

        if (livingentity != p_155413_.destroyTarget) {
            p_155409_.sendBlockUpdated(p_155410_, p_155411_, p_155411_, 2);
        }

    }

    public static void updateClientTarget(Level p_155400_, BlockPos p_155401_, SaltFishBlockEntity p_155402_) {
        if (p_155402_.destroyTargetUUID == null) {
            p_155402_.destroyTarget = null;
        } else if (p_155402_.destroyTarget == null || !p_155402_.destroyTarget.getUUID().equals(p_155402_.destroyTargetUUID)) {
            p_155402_.destroyTarget = findDestroyTarget(p_155400_, p_155401_, p_155402_.destroyTargetUUID);
            if (p_155402_.destroyTarget == null) {
                p_155402_.destroyTargetUUID = null;
            }
        }

    }

    private static AABB getDestroyRangeAABB(BlockPos p_155432_) {
        int x = p_155432_.getX();
        int y = p_155432_.getY();
        int z = p_155432_.getZ();
        return (new AABB(x, y, z, x + 1, y + 1, z + 1)).inflate(16.0D);
    }

    @Nullable
    private static LivingEntity findDestroyTarget(Level p_155425_, BlockPos p_155426_, UUID p_155427_) {
        List<LivingEntity> list = p_155425_.getEntitiesOfClass(LivingEntity.class, getDestroyRangeAABB(p_155426_), (p_289510_) -> {
            return p_289510_.getUUID().equals(p_155427_);
        });
        return list.size() == 1 ? list.get(0) : null;
    }

    private static void animationTick(Level p_155419_, BlockPos p_155420_, List<BlockPos> p_155421_, @Nullable Entity p_155422_, int p_155423_) {
        RandomSource randomsource = p_155419_.random;
        double d0 = Mth.sin((float)(p_155423_ + 35) * 0.1F) / 2.0F + 0.5F;
        d0 = (d0 * d0 + d0) * (double)0.3F;
        Vec3 vec3 = new Vec3((double)p_155420_.getX() + 0.5D, (double)p_155420_.getY() + 1.5D + d0, (double)p_155420_.getZ() + 0.5D);

        for(BlockPos blockpos : p_155421_) {
            if (randomsource.nextInt(50) == 0) {
                BlockPos blockpos1 = blockpos.subtract(p_155420_);
                float f = -0.5F + randomsource.nextFloat() + (float)blockpos1.getX();
                float f1 = -2.0F + randomsource.nextFloat() + (float)blockpos1.getY();
                float f2 = -0.5F + randomsource.nextFloat() + (float)blockpos1.getZ();
                p_155419_.addParticle(ParticleTypes.NAUTILUS, vec3.x, vec3.y, vec3.z, (double)f, (double)f1, (double)f2);
            }
        }

        if (p_155422_ != null) {
            Vec3 vec31 = new Vec3(p_155422_.getX(), p_155422_.getEyeY(), p_155422_.getZ());
            float f3 = (-0.5F + randomsource.nextFloat()) * (3.0F + p_155422_.getBbWidth());
            float f4 = -1.0F + randomsource.nextFloat() * p_155422_.getBbHeight();
            float f5 = (-0.5F + randomsource.nextFloat()) * (3.0F + p_155422_.getBbWidth());
            Vec3 vec32 = new Vec3(f3, f4, f5);
            p_155419_.addParticle(ParticleTypes.NAUTILUS, vec31.x, vec31.y, vec31.z, vec32.x, vec32.y, vec32.z);
        }
    }
}
