package com.starmeow.smc.entities;

import com.starmeow.smc.helper.BlockHelper;
import com.starmeow.smc.init.EntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3f;

import javax.annotation.Nullable;

public class PrimedBroccoliBomb extends PrimedTnt {

    public PrimedBroccoliBomb(EntityType<PrimedBroccoliBomb> p_32076_, Level p_32077_) {
        super(p_32076_, p_32077_);
        this.blocksBuilding = true;
    }

    public PrimedBroccoliBomb(Level p_32079_, double p_32080_, double p_32081_, double p_32082_, @Nullable LivingEntity p_32083_) {
        this(EntityTypeRegistry.BROCCOLI_NUKE.get(), p_32079_);
        this.setPos(p_32080_, p_32081_, p_32082_);
        double $$5 = p_32079_.random.nextDouble() * 6.2831854820251465;
        this.setDeltaMovement(-Math.sin($$5) * 0.02, 0.20000000298023224, -Math.cos($$5) * 0.02);
        this.setFuse(200);
        this.xo = p_32080_;
        this.yo = p_32081_;
        this.zo = p_32082_;
        this.owner = p_32083_;
    }

    protected void explode() {
        if (!this.level().isClientSide) {
            BlockPos center = new BlockPos(this.getBlockX(), this.getBlockY(), this.getBlockZ());
            int r = 32;
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), r / 1.5f, Level.ExplosionInteraction.NONE);

            for (BlockPos tmpPos : BlockPos.withinManhattan(center, r * 2 + 1, r * 2 + 1, r * 2 + 1)){
                if(Math.round(BlockHelper.getBlockPosDistance(tmpPos, center)) <= r){
                    BlockState state = level().getBlockState(tmpPos);
                    boolean mayOres = (state.is(BlockTags.NEEDS_STONE_TOOL) || state.is(BlockTags.NEEDS_IRON_TOOL) || state.is(BlockTags.NEEDS_DIAMOND_TOOL)) && state.getBlock().getExplosionResistance() >= 3.0F;
                    boolean shouldAffect = state.getBlock() instanceof LiquidBlock
                            || (state.getBlock().defaultDestroyTime() >= 0 && state.getBlock().defaultDestroyTime() < 30)
                            || mayOres;
                    if(shouldAffect){
                        //激活tnt类方块
                        if(state.getBlock() instanceof TntBlock tnt){
                            tnt.onCaughtFire(state, level(), tmpPos, null, this.getOwner());
                        }
                        //矿物变成下落方块
                        if(mayOres){
                            FallingBlockEntity.fall(level(), tmpPos, state);
                        }
                        if(level().getBlockEntity(tmpPos) != null){
                            Block.dropResources(state, level(), tmpPos, level().getBlockEntity(tmpPos), this.getOwner(), new ItemStack(Items.WOODEN_PICKAXE));
                        }
                        level().setBlock(tmpPos, Blocks.AIR.defaultBlockState(), 3);
                    }
                }
            }
            DustParticleOptions options = new DustParticleOptions(new Vector3f(0, 0.7f, 0.2f), 1);
            if(this.level() instanceof ServerLevel serverLevel)serverLevel.sendParticles(options, this.getX(), this.getY(), this.getZ(), 45 * r, r, r, r, 0);
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }
}
