package com.starmeow.smc.entities.projectiles;

import com.starmeow.smc.helper.BlockHelper;
import com.starmeow.smc.init.EntityTypeRegistry;
import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import org.joml.Vector3f;

public class ThrownBroccoliBoom extends ThrowableItemProjectile {
    public ThrownBroccoliBoom(EntityType<? extends ThrownBroccoliBoom> entityType, Level level) {
        super(entityType, level);
    }
    public ThrownBroccoliBoom(Level level, LivingEntity entity) {
        super(EntityTypeRegistry.THROWN_BROCCOLI_BOOM.get(), entity, level);
    }
    public ThrownBroccoliBoom(Level level, double x, double y, double z) {
        super(EntityTypeRegistry.THROWN_BROCCOLI_BOOM.get(), x, y, z, level);
    }

    protected void onHit(HitResult p_37488_) {
        super.onHit(p_37488_);
        if (!this.level().isClientSide) {
            BlockPos center = new BlockPos(this.getBlockX(), this.getBlockY(), this.getBlockZ());
            int r = 6;
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
                            tnt.onCaughtFire(state, level(), tmpPos, null, this.getOwner() instanceof LivingEntity living ? living : null);
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

    protected Item getDefaultItem() {
        return ItemRegistry.BROCCOLI.get();
    }
}
