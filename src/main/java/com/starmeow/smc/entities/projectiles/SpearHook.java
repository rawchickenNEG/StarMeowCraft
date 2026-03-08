package com.starmeow.smc.entities.projectiles;

import com.starmeow.smc.init.EntityTypeRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.minecraftforge.common.ToolActions;

import java.util.Collections;

public class SpearHook extends FishingHook {
    public SpearHook(EntityType<? extends SpearHook> entityType, Level level) {
        super(EntityTypeRegistry.SPEAR_HOOK.get(), level);
    }

    public SpearHook(Player player, Level level) {
        this(EntityTypeRegistry.SPEAR_HOOK.get(), level);
        this.setOwner(player);
        this.moveTo(player.getX(), player.getEyeY() - 0.1D, player.getZ(), player.getYRot(), player.getXRot());
        this.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.0F, 1.0F);
        player.fishing = this;
    }

    private boolean shouldStopFishing(Player p_37137_) {
        ItemStack itemstack = p_37137_.getMainHandItem();
        ItemStack itemstack1 = p_37137_.getOffhandItem();
        boolean flag = itemstack.canPerformAction(ToolActions.FISHING_ROD_CAST);
        boolean flag1 = itemstack1.canPerformAction(ToolActions.FISHING_ROD_CAST);
        if (!p_37137_.isRemoved() && p_37137_.isAlive() && (flag || flag1) && !(this.distanceToSqr(p_37137_) > 1024.0)) {
            return false;
        } else {
            this.discard();
            return true;
        }
    }

    public int retrieve(ItemStack p_37157_) {
        Player player = this.getPlayerOwner();
        if (!this.level().isClientSide && player != null && !this.shouldStopFishing(player)) {
            if (this.hookedIn != null) {
                this.pullEntity(this.hookedIn);
                CriteriaTriggers.FISHING_ROD_HOOKED.trigger((ServerPlayer)player, p_37157_, this, Collections.emptyList());
                this.level().broadcastEntityEvent(this, (byte)31);
            }

            if (this.onGround()) {

                double d0 = this.getX() - player.getX();
                double d1 = this.getY() - player.getY();
                double d2 = this.getZ() - player.getZ();
                double d3 = 0.2;
                player.setDeltaMovement(d0 * d3, d1 * d3 * 0.8 + Math.sqrt(Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2)) * 0.08, d2 * d3);
                player.hurtMarked = true;
            }

            this.discard();
            return 1;
        } else {
            return 0;
        }
    }

    protected void onHitBlock(BlockHitResult p_37384_) {
        super.onHitBlock(p_37384_);
        if (!this.level().isClientSide) {
            this.setDeltaMovement(Vec3.ZERO);
            this.setNoGravity(true);
        }
    }
}
