package com.starmeow.smc.items;

import com.starmeow.smc.init.ParticleRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;

public class OreDetector extends Item {
    public OreDetector(Properties p_41383_) {
        super(p_41383_);
    }


    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);
        if(level.isClientSide() && entity instanceof Player player) {

            if(level.getGameTime() % 10L != 0L) return;

            BlockPos pos = player.getOnPos().offset(0,1,0);
            CompoundTag tag = player.getItemInHand(InteractionHand.OFF_HAND).getTag();
            Block block = null;
            if(tag != null){
                block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(tag.getString("SMCDetectorBlock")));
            }
            if(player.getItemInHand(InteractionHand.OFF_HAND).is(this)) {
                int r = 32;
                int size = 0;
                for (BlockPos tmpPos : BlockPos.withinManhattan(pos, r, r, r)) {
                    if(block != null && block != Blocks.AIR && level.getBlockState(tmpPos).is(block)){
                        if(size <= 256){
                            /*
                            //弃用
                            DetectorMark mark = new DetectorMark(level, player);
                            mark.setPos(tmpPos.getCenter());
                            mark.setNoGravity(true);
                            mark.setGlowingTag(true);
                            mark.setInvisible(true);
                            level.addFreshEntity(mark);
                             */
                            level.addParticle(ParticleRegistry.DETECTOR_MARK.get(), tmpPos.getX() + 0.5, tmpPos.getY() + 0.5, tmpPos.getZ() + 0.5, 0.0, 0.0, 0.0);
                            size++;
                        }
                    }
                }
            }
        }
    }



    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand p_41130_) {
        ItemStack itemstack = player.getItemInHand(p_41130_);
        ItemStack offhandItem = player.getOffhandItem();
        if(offhandItem.getItem() instanceof BlockItem blockItem && !player.isCrouching()){
            CompoundTag tag = itemstack.getOrCreateTag();
            String id = ForgeRegistries.ITEMS.getKey(blockItem).toString();
            tag.putString("SMCDetectorBlock", id);
            player.displayClientMessage(Component.translatable("tooltip.smc.ore_detector_2", id), true);
            player.awardStat(Stats.ITEM_USED.get(this));
            level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);
            return InteractionResultHolder.success(itemstack);
        }
        return InteractionResultHolder.pass(itemstack);
    }

    @Override
    public InteractionResult useOn(UseOnContext p_41341_) {
        Level level = p_41341_.getLevel();
        BlockPos blockpos = p_41341_.getClickedPos();
        BlockState blockState = level.getBlockState(blockpos);
        ItemStack itemstack = p_41341_.getItemInHand();
        Player player = p_41341_.getPlayer();
        if(player != null && player.isCrouching()){
            CompoundTag tag = itemstack.getOrCreateTag();
            String id = ForgeRegistries.BLOCKS.getKey(blockState.getBlock()).toString();
            tag.putString("SMCDetectorBlock", id);
            player.displayClientMessage(Component.translatable("tooltip.smc.ore_detector_2", id), true);
            player.awardStat(Stats.ITEM_USED.get(this));
            level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        String string = "tooltip.smc." + stack.getItem();
        String string1 = "tooltip.smc." + stack.getItem() + "_2";
        CompoundTag tag = stack.getOrCreateTag();
        String name = tag.getString("SMCDetectorBlock");
        tooltip.add(Component.translatable(string).withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.translatable(string + "_0").withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.translatable(string + "_1").withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.translatable(string1, name).withStyle(ChatFormatting.YELLOW));
    }
}
