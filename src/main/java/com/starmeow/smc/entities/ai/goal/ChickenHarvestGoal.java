package com.starmeow.smc.entities.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

import java.util.List;

public class ChickenHarvestGoal extends MoveToBlockGoal {
    private final Mob removerMob;
    private BlockPos lastPos;
    public ChickenHarvestGoal(PathfinderMob p_25841_, double p_25842_, int p_25843_) {
        super(p_25841_, p_25842_, 24, p_25843_);
        this.removerMob = p_25841_;
    }

    public boolean canUse() {
        if (this.nextStartTick > 0) {
            --this.nextStartTick;
            return false;
        } else if (this.findNearestBlock()) {
            this.nextStartTick = reducedTickDelay(20);
            return true;
        } else {
            this.nextStartTick = this.nextStartTick(this.mob);
            return false;
        }
    }

    public void stop() {
        super.stop();
    }

    public void start() {
        super.start();
    }

    public void tick() {
        super.tick();
        if(this.removerMob.blockPosition() != lastPos){
            lastPos = this.removerMob.blockPosition();
            for (BlockPos tmpPos : BlockPos.withinManhattan(lastPos, 1, 2, 1)){
                harvestTarget(tmpPos);
            }
        }
    }

    protected void harvestTarget(BlockPos pos) {
        BlockState blockstate = this.removerMob.level().getBlockState(pos);
        Block block = blockstate.getBlock();
        if (isMaxAgedBlock(blockstate)){
            if(!(block instanceof StemBlock)){
                this.harvestCrops(pos);
            }
        }else if(block instanceof StemGrownBlock){
            this.harvestMelons(pos);
        }else if(CaveVines.hasGlowBerries(blockstate)){
            this.harvestCaveVines(pos);
        }
    }

    private void harvestCrops(BlockPos pos){
        Level level = this.removerMob.level();
        BlockState state = level.getBlockState(pos);
        BlockHitResult hitResult = new BlockHitResult(Vec3.atCenterOf(pos), Direction.UP, pos, false);

        if(level instanceof ServerLevel serverLevel) {
            FakePlayer fake = FakePlayerFactory.getMinecraft(serverLevel);
            fake.moveTo(this.removerMob.position());
            if(state.use(level, fake, InteractionHand.MAIN_HAND, hitResult).consumesAction()) return;
            List<ItemStack> drops = Block.getDrops(state, serverLevel, pos, null);
            boolean flag = false;
            int i = 0;
            for(ItemStack itemstack : drops){
                if(!flag && state.getBlock().getCloneItemStack(serverLevel, pos, state).is(itemstack.getItem())){
                    flag = true;
                    continue;
                }
                this.removerMob.spawnAtLocation(itemstack);
                i++;
            }
            if(i == 0){
                Block.dropResources(state, level, pos, null);
            }
            level.levelEvent(2001, pos, Block.getId(level.getBlockState(pos)));
            BlockState seeds = setBlockAge(state, 0);
            level.setBlockAndUpdate(pos, seeds);
        }
    }

    private void harvestMelons(BlockPos pos){
        Level level = this.removerMob.level();
        BlockState state = level.getBlockState(pos);
        if(state.getBlock() instanceof StemGrownBlock) {
            Block.dropResources(state, level, pos, null);
            level.levelEvent(2001, pos, Block.getId(level.getBlockState(pos)));
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        }
    }

    private void harvestCaveVines(BlockPos pos){
        Level level = this.removerMob.level();
        BlockState state = level.getBlockState(pos);
        BlockHitResult hitResult = new BlockHitResult(Vec3.atCenterOf(pos), Direction.UP, pos, false);
        if(level instanceof ServerLevel serverLevel) {
            FakePlayer fake = FakePlayerFactory.getMinecraft(serverLevel);
            fake.moveTo(this.removerMob.position());
            state.use(level, fake, InteractionHand.MAIN_HAND, hitResult);
        }
    }

    protected boolean isValidTarget(LevelReader p_25850_, BlockPos p_25851_) {
        ChunkAccess chunkaccess = p_25850_.getChunk(SectionPos.blockToSectionCoord(p_25851_.getX()), SectionPos.blockToSectionCoord(p_25851_.getZ()), ChunkStatus.FULL, false);
        if (chunkaccess == null) {
            return false;
        } else {
            return isMaxAgedBlock(chunkaccess.getBlockState(p_25851_))
                    || chunkaccess.getBlockState(p_25851_).getBlock() instanceof StemGrownBlock
                    || CaveVines.hasGlowBerries(chunkaccess.getBlockState(p_25851_));
        }
    }

    public boolean isMaxAgedBlock(BlockState state){
        boolean hasAge = state.getProperties().stream().anyMatch(prop -> prop instanceof IntegerProperty && "age".equals(prop.getName().toLowerCase()));
        if (hasAge) {
            for (var prop : state.getProperties()) {
                if (prop instanceof IntegerProperty intProp && "age".equals(intProp.getName().toLowerCase())) {
                    int ageValue = state.getValue(intProp);
                    int maxAgeValue = intProp.max;
                    if(ageValue == maxAgeValue){
                        if(!(state.getBlock() instanceof StemBlock)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public BlockState setBlockAge(BlockState state, int age){
        boolean hasAge = state.getProperties().stream().anyMatch(prop -> prop instanceof IntegerProperty && "age".equals(prop.getName().toLowerCase()));
        if (hasAge) {
            for (var prop : state.getProperties()) {
                if (prop instanceof IntegerProperty intProp && "age".equals(intProp.getName().toLowerCase())) {
                    return state.setValue(intProp, age);
                }
            }
        }
        return state;
    }
}
