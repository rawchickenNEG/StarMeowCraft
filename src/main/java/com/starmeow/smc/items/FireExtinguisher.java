package com.starmeow.smc.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class FireExtinguisher extends Item {
    public FireExtinguisher(Properties p_41383_) {
        super(p_41383_);
    }

    public ItemStack finishUsingItem(ItemStack p_42923_, Level p_42924_, LivingEntity p_42925_) {
        if (p_42925_ instanceof Player player){
            BlockPos pos = player.getOnPos().offset(0,1,0);
            int r = 15;
            for (BlockPos tmpPos : BlockPos.withinManhattan(pos, r, r, r)) {
                if(p_42924_.getBlockState(tmpPos).is(Blocks.FIRE) || p_42924_.getBlockState(tmpPos).is(Blocks.SOUL_FIRE)){
                    p_42924_.playSound((Player)null, tmpPos, SoundEvents.FIRE_EXTINGUISH, SoundSource.NEUTRAL, 1.0F, 1.0F);
                    p_42924_.setBlock(tmpPos, Blocks.AIR.defaultBlockState(), 3);
                }
            }
            player.playSound(SoundEvents.PUFFER_FISH_DEATH, 1.0F, 1.0F);
            if(p_42924_ instanceof ServerLevel serverLevel){
                serverLevel.sendParticles(ParticleTypes.CLOUD, player.getX(), player.getY() + player.getEyeHeight(), player.getZ(), 25, 0.0D, 0.0D, 0.0D, 0.3D);
            }
            p_42923_.hurtAndBreak(1, player, (p_40665_) -> {
                p_40665_.broadcastBreakEvent(player.getUsedItemHand());
            });
        }
        return p_42923_;
    }


    public int getUseDuration(ItemStack p_42933_) {
        return 32;
    }

    public UseAnim getUseAnimation(ItemStack p_42931_) {
        return UseAnim.BOW;
    }

    public InteractionResultHolder<ItemStack> use(Level p_42927_, Player p_42928_, InteractionHand p_42929_) {
        return ItemUtils.startUsingInstantly(p_42927_, p_42928_, p_42929_);
    }
}
