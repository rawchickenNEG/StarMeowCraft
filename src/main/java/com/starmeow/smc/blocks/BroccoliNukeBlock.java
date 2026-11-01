package com.starmeow.smc.blocks;

import com.starmeow.smc.entities.PrimedBroccoliBomb;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;

public class BroccoliNukeBlock extends TntBlock {
    public BroccoliNukeBlock(Properties p_57422_) {
        super(p_57422_);
    }

    @Override
    public void onCaughtFire(BlockState state, Level world, BlockPos pos, @Nullable Direction face, @Nullable LivingEntity igniter) {
        explode(world, pos, igniter);
    }

    private static void explode(Level p_57437_, BlockPos p_57438_, @Nullable LivingEntity p_57439_) {
        if (!p_57437_.isClientSide) {
            PrimedBroccoliBomb primedtnt = new PrimedBroccoliBomb(p_57437_, (double)p_57438_.getX() + 0.5, (double)p_57438_.getY(), (double)p_57438_.getZ() + 0.5, p_57439_);
            p_57437_.addFreshEntity(primedtnt);
            p_57437_.playSound((Player)null, primedtnt.getX(), primedtnt.getY(), primedtnt.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
            p_57437_.gameEvent(p_57439_, GameEvent.PRIME_FUSE, p_57438_);
        }
    }

    @Override
    public void wasExploded(Level p_57441_, BlockPos p_57442_, Explosion p_57443_) {
        if (!p_57441_.isClientSide) {
            PrimedBroccoliBomb primedtnt = new PrimedBroccoliBomb(p_57441_, (double)p_57442_.getX() + 0.5, (double)p_57442_.getY(), (double)p_57442_.getZ() + 0.5, p_57443_.getIndirectSourceEntity());
            int i = primedtnt.getFuse();
            primedtnt.setFuse((short)(p_57441_.random.nextInt(i / 4) + i / 8));
            p_57441_.addFreshEntity(primedtnt);
        }

    }
}
