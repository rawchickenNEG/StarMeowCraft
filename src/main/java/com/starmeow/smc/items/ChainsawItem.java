package com.starmeow.smc.items;

import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ChainsawItem extends AxeItem {
    public ChainsawItem(Tier p_40521_, float p_40522_, float p_40523_, Properties p_40524_) {
        super(p_40521_, p_40522_, p_40523_, p_40524_);
    }

    @Override
    public void inventoryTick(ItemStack itemstack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, level, entity, slot, selected);
        if(!level.isClientSide() && entity instanceof Player player) {
            for(int i = 0; i < player.getInventory().getContainerSize(); i++){
                if(getPower(itemstack) < 1024){
                    ItemStack scanItem = player.getInventory().getItem(i);
                    if(scanItem.is(ItemRegistry.BATTERY.get())){
                        scanItem.setDamageValue(scanItem.getDamageValue() + 1);
                        if(scanItem.getDamageValue() >= scanItem.getMaxDamage()){
                            scanItem.shrink(1);
                        }
                        itemstack.getOrCreateTag().putInt("SMCZincPower", getPower(itemstack) + 1);
                    }
                }
            }
        }
    }

    public boolean hurtEnemy(ItemStack p_40994_, LivingEntity p_40995_, LivingEntity p_40996_) {
        CompoundTag tag = p_40994_.getOrCreateTag();
        int power = tag.getInt("SMCZincPower");
        if(power > 0){
            tag.putInt("SMCZincPower", Math.max(power - 1, 0));
        }else{
            p_40994_.hurtAndBreak(1, p_40996_, (p_40992_) -> {
                p_40992_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }
        return true;
    }

    public boolean mineBlock(ItemStack p_40998_, Level p_40999_, BlockState p_41000_, BlockPos p_41001_, LivingEntity p_41002_) {
        if (!p_40999_.isClientSide && p_41000_.getDestroySpeed(p_40999_, p_41001_) != 0.0F) {
            CompoundTag tag = p_40998_.getOrCreateTag();
            int power = tag.getInt("SMCZincPower");
            if(power > 0){
                tag.putInt("SMCZincPower", Math.max(power - 1, 0));
            }else{
                p_40998_.hurtAndBreak(1, p_41002_, (p_40992_) -> {
                    p_40992_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                });
            }
        }
        return true;
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack item, int count) {
        CompoundTag tag = item.getOrCreateTag();
        int power = tag.getInt("SMCZincPower");
        if (entity instanceof Player player) {
            if(power > 0){
                tag.putBoolean("SMCChainsawUsing", true);
                if(count % 2 == 0) {
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.AXE_WAX_OFF, SoundSource.PLAYERS, 1F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
                }
                if(count % 20 == 0) {
                    tag.putInt("SMCZincPower", Math.max(power - 2, 0));
                }
                double sx = 2 * entity.getLookAngle().x + entity.getX();
                double sy = 2 * entity.getLookAngle().y + entity.getY() + entity.getEyeHeight();
                double sz = 2 * entity.getLookAngle().z + entity.getZ();
                Vec3 svec3 = new Vec3(sx,sy,sz);
                AABB aabb = new AABB(svec3, svec3).inflate(1.2);
                List<LivingEntity> entities = entity.level().getEntitiesOfClass(LivingEntity.class, aabb);
                for (Entity target : entities) {
                    if (target != entity){
                        target.hurt(level.damageSources().explosion(null, player), 1);
                        target.invulnerableTime = 2;
                    }
                }
            }else{
                player.stopUsingItem();
                tag.putBoolean("SMCChainsawUsing", false);
            }
        }
    }

    public InteractionResultHolder<ItemStack> use(Level p_40672_, Player player, InteractionHand p_40674_) {
        ItemStack itemstack = player.getItemInHand(p_40674_);
        if(getPower(itemstack) > 0){
            player.startUsingItem(p_40674_);
            return InteractionResultHolder.success(itemstack);
        }
        return InteractionResultHolder.pass(itemstack);
    }

    public void releaseUsing(ItemStack itemstack, Level level, LivingEntity living, int p_40670_) {
        CompoundTag tag = itemstack.getOrCreateTag();
        tag.putBoolean("SMCChainsawUsing", false);
    }

    public int getPower(ItemStack item){
        CompoundTag tag = item.getOrCreateTag();
        return tag.getInt("SMCZincPower");
    }

    @Override
    public boolean isBarVisible(ItemStack item) {
        return getPower(item) > 0 ||  super.isBarVisible(item);
    }

    @Override
    public int getBarWidth(ItemStack item) {
        return getPower(item) > 0 ? Math.round(getPower(item) * 13.0F / 1024) : super.getBarWidth(item);
    }

    public int getBarColor(ItemStack item) {
        return getPower(item) > 0 ? Mth.hsvToRgb(0.0F, 0.0F, 1.0F) : super.getBarColor(item);
    }

    public int getUseDuration(ItemStack p_40680_) {
        return 72000;
    }

    public UseAnim getUseAnimation(ItemStack p_40678_) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResult useOn(UseOnContext p_40529_) {
        return InteractionResult.PASS;
    }

    @Override
    public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack) {
        return oldStack.getItem() == newStack.getItem();
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged || newStack.getItem() != oldStack.getItem();
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        String string = "tooltip.smc." + stack.getItem();
        tooltip.add(Component.translatable(string).withStyle(ChatFormatting.BLUE));
        if(getPower(stack) > 0){
            tooltip.add(Component.translatable("tooltip.smc.zinc_tool_1", getPower(stack)).withStyle(ChatFormatting.AQUA));
        }
    }
}
