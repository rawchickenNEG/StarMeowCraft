package com.starmeow.smc.items;

import com.starmeow.smc.helper.BlockHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;

public class SuperBonemeal extends TippedItems{
    public SuperBonemeal(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Level level = ctx.getLevel();
        if(level instanceof ServerLevel serverLevel){
            boolean used = false;
            BlockPos pos = ctx.getClickedPos();
            int r = 6;
            for (BlockPos tmpPos : BlockPos.withinManhattan(pos, r * 2 + 1, r * 2 + 1, r * 2 + 1)){
                if(Math.round(BlockHelper.getBlockPosDistance(tmpPos, pos)) <= r){
                    BlockState state = serverLevel.getBlockState(tmpPos);
                    if(BlockHelper.isGrowingAgedBlock(state)){
                        serverLevel.setBlock(tmpPos, BlockHelper.setBlockAgeToMax(state), 3);
                        serverLevel.levelEvent(3009, tmpPos, 0);
                        used = true;
                    }
                    if(state.getBlock() instanceof SaplingBlock sapling){
                        if(sapling.treeGrower.growTree(serverLevel, serverLevel.getChunkSource().getGenerator(), tmpPos, state, serverLevel.getRandom())){
                            serverLevel.levelEvent(3009, tmpPos, 0);
                            used = true;
                        }
                    }
                }
            }
            if(used){
                if (ctx.getPlayer() != null && !ctx.getPlayer().getAbilities().instabuild) {
                    ctx.getItemInHand().shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
}
