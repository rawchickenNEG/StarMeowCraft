package com.starmeow.smc.entities.projectiles;

import com.starmeow.smc.init.EntityTypeRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.minecraftforge.common.ToolActions;

import java.util.Collections;

public class SpearHook extends FishingHook {
    public boolean stuck;
    private Vec3 stuckPos;
    public SpearHook(EntityType<? extends SpearHook> entityType, Level level) {
        super(EntityTypeRegistry.SPEAR_HOOK.get(), level);
    }

    public SpearHook(Player player, Level level) {
        this(EntityTypeRegistry.SPEAR_HOOK.get(), level);
        this.setOwner(player);
        this.moveTo(player.getX(), player.getEyeY() - 0.1D, player.getZ(), player.getYRot(), player.getXRot());
        this.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 4.0F, 1.0F);
        player.fishing = this;
    }

    public int retrieve(ItemStack p_37157_) {
        super.retrieve(p_37157_);
        Player player = this.getPlayerOwner();
        if (!this.level().isClientSide && player != null) {
            if (this.hookedIn != null) {
                this.pullEntity(this.hookedIn);
                CriteriaTriggers.FISHING_ROD_HOOKED.trigger((ServerPlayer)player, p_37157_, this, Collections.emptyList());
                this.level().broadcastEntityEvent(this, (byte)31);
            }

            if (this.stuck && !player.isCrouching()) {

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

    @Override
    protected void pullEntity(Entity p_150156_) {
        Entity entity = this.getOwner();
        if (entity != null) {
            Vec3 vec3 = (new Vec3(entity.getX() - this.getX(), (entity.getY() - this.getY()) * 0.5, entity.getZ() - this.getZ())).scale(0.2);
            p_150156_.setDeltaMovement(p_150156_.getDeltaMovement().add(vec3));
        }

    }

    public void tick(){
        super.tick();
        if (this.stuck && this.stuckPos != null) {
            this.setDeltaMovement(Vec3.ZERO);
            this.setPos(this.stuckPos.x, this.stuckPos.y, this.stuckPos.z);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);

        if (!this.stuck) {
            this.stuck = true;
            this.stuckPos = result.getLocation();

            this.setDeltaMovement(Vec3.ZERO);
            this.setPos(this.stuckPos.x, this.stuckPos.y, this.stuckPos.z);
        }
    }
}
