package com.starmeow.smc.entities.projectiles;

import com.starmeow.smc.init.EntityTypeRegistry;
import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class DetectorMark extends ThrowableItemProjectile {
    public DetectorMark(EntityType<? extends DetectorMark> entityType, Level level) {
        super(entityType, level);
    }
    public DetectorMark(Level level, LivingEntity entity) {
        super(EntityTypeRegistry.DETECTOR_MARK.get(), entity, level);
    }
    public DetectorMark(Level level, double x, double y, double z) {
        super(EntityTypeRegistry.DETECTOR_MARK.get(), x, y, z, level);
    }

    public void tick() {
        super.tick();
        boolean flag = false;
        Player player = this.level().getNearestPlayer(this, 32);
        if(player != null && player.getItemInHand(InteractionHand.OFF_HAND).is(ItemRegistry.ORE_DETECTOR.get())){
            flag = true;
        }
        if(this.getDeltaMovement().x > 0.01 || this.getDeltaMovement().y > 0.01 || this.getDeltaMovement().z > 0.01 || this.level().getBlockState(this.blockPosition()).is(Blocks.AIR)){
            this.discard();
        }
        if(!flag){
            this.discard();
        }
    }

    protected Item getDefaultItem() {
        return Items.STRUCTURE_VOID;
    }
}
