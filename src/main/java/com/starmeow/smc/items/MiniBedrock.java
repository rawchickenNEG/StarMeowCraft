package com.starmeow.smc.items;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class MiniBedrock extends Item {
    public MiniBedrock(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);
        if(entity instanceof Player player) {
            CompoundTag tag = stack.getOrCreateTag();
            if(player.getMainHandItem() == stack){
                if(Math.sqrt(Math.pow(player.getX() - tag.getDouble("BedrockPosXLock"), 2) + Math.pow(player.getZ() - tag.getDouble("BedrockPosZLock"), 2)) < 1.0){
                    player.setPos(tag.getDouble("BedrockPosXLock"),player.getY(),tag.getDouble("BedrockPosZLock"));
                } else {
                    tag.putDouble("BedrockPosXLock", player.getX());
                    tag.putDouble("BedrockPosZLock", player.getZ());
                }
            } else {
                tag.putDouble("BedrockPosXLock", player.getX());
                tag.putDouble("BedrockPosZLock", player.getZ());
            }
        }
    }

    @Override
    public float getDestroySpeed(ItemStack p_41004_, BlockState p_41005_) {
        return p_41005_.is(Blocks.BEDROCK) ? -1.0F : 1.0F;
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        entity.setInvulnerable(true);
        return false;
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
        tooltip.add(Component.translatable(string + "_1").withStyle(ChatFormatting.BLUE));
    }
}
