package com.starmeow.smc.items;

import com.starmeow.smc.entities.projectiles.DetectorMark;
import com.starmeow.smc.helper.EntityHelper;
import com.starmeow.smc.init.ParticleRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class TreasureDetector extends Item {
    public TreasureDetector(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);
        if(!level.isClientSide() && entity instanceof LivingEntity living) {
            if(entity instanceof Player player && player.getItemInHand(InteractionHand.OFF_HAND).is(this)){
                if(level.getGameTime() % 20L != 0L) return;
                BlockPos pos = entity.getOnPos().offset(0,1,0);
                if(true) {
                    int r = 32;
                    int size = 0;
                    for (BlockPos tmpPos : BlockPos.withinManhattan(pos, r, r, r)) {
                        if(isTreasureBlock(level, tmpPos)){
                            if(size <= 64){
                                DetectorMark mark = new DetectorMark(level, living);
                                mark.setPos(tmpPos.getCenter());
                                mark.setNoGravity(true);
                                level.addFreshEntity(mark);
                                size++;
                            }
                        }
                    }
                }
            }
        }
        if(level.isClientSide() && entity instanceof Player){
            final Vec3 center = EntityHelper.getVec3(entity);
            AABB aabb = new AABB(center, center).inflate(32);
            List<DetectorMark> entities = entity.level().getEntitiesOfClass(DetectorMark.class, aabb, e -> true);
            if(true){
                for (DetectorMark target : entities) {
                    level.addParticle(ParticleRegistry.DETECTOR_MARK.get(), target.getX(), target.getY(), target.getZ(), 0.0, 0.0, 0.0);
                }
            }
        }
    }
/*
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        ItemStack itemstack = p_41433_.getItemInHand(p_41434_);
        if(p_41433_.getItemInHand(InteractionHand.OFF_HAND).is(Items.ENDER_PEARL) && p_41434_.equals(InteractionHand.MAIN_HAND)){
            if(hasValidTarget(p_41433_)){
                p_41433_.startUsingItem(p_41434_);
                return InteractionResultHolder.consume(itemstack);
            }
        }
        return InteractionResultHolder.pass(itemstack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if(!level.isClientSide() && entity instanceof Player player){
            final Vec3 center = EntityHelper.getVec3(entity);
            AABB aabb = new AABB(center, center).inflate(32);
            List<DetectorMark> entities = entity.level().getEntitiesOfClass(DetectorMark.class, aabb, e -> true);
            if(hasValidTarget(entity)){
                for (DetectorMark target : entities) {
                    teleportPlayer(level, target.getOnPos(), player, stack);
                    if (!player.getAbilities().instabuild && player.getOffhandItem().is(Items.ENDER_PEARL)) {
                        ItemStack pearl = player.getOffhandItem();
                        pearl.shrink(1);
                    }
                }
            }
        }

        return result;
    }

    public static void teleportPlayer(Level level, BlockPos pos, Player player, ItemStack itemStack){
        player.getCooldowns().addCooldown(itemStack.getItem(), 200);
        if(player.getVehicle() != null){
            player.stopRiding();
        }
        player.teleportTo(pos.getX(), player.getOnPos().getY(), pos.getZ());
        level.playSound(null, pos.getX(), player.getOnPos().getY(), pos.getZ(), SoundEvents.PORTAL_TRAVEL, SoundSource.PLAYERS, 1.0F, 1.0F);
    }

    public int getUseDuration(ItemStack p_41454_) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_40678_) {
        return UseAnim.BOW;
    }

 */

    public boolean isTreasureBlock(Level level, BlockPos pos){
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if(blockEntity instanceof RandomizableContainerBlockEntity rcb){
            CompoundTag tag = rcb.saveWithFullMetadata();
            if (tag.contains("LootTable", 8) && tag.contains("LootTable")) {
                return true;
            }
        }
        return false;
    }

    public boolean hasValidTarget(LivingEntity entity){
        final Vec3 center = EntityHelper.getVec3(entity);
        AABB aabb = new AABB(center, center).inflate(32);
        List<DetectorMark> entities = entity.level().getEntitiesOfClass(DetectorMark.class, aabb, e -> true);
        return !entities.isEmpty();
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        String string = "tooltip.smc." + stack.getItem();
        tooltip.add(Component.translatable(string).withStyle(ChatFormatting.BLUE));
    }
}
