package com.starmeow.smc.items;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;

public class VacuumSniffer extends Item {
    public VacuumSniffer(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack item, int count) {
        if (entity instanceof Player player){
            BlockPos pos = player.getOnPos().offset(0,1,0);
            int r = 15;
            for (BlockPos tmpPos : BlockPos.withinManhattan(pos, r, r, r)) {
                if(level.getBlockState(tmpPos).is(Blocks.GRASS) || level.getBlockState(tmpPos).is(Blocks.TALL_GRASS) || level.getBlockState(tmpPos).is(Blocks.FERN) || level.getBlockState(tmpPos).is(Blocks.LARGE_FERN)){
                    if (player.getRandom().nextInt(16) == 0) {
                        level.addParticle(ParticleTypes.CLOUD , (float)tmpPos.getX(), (float)tmpPos.getY(), (float)tmpPos.getZ(), 0.05 * (player.getX() - tmpPos.getX()), 0.05 * (player.getY() - tmpPos.getY()), 0.05 * (player.getZ() - tmpPos.getZ()));
                        if (player.getRandom().nextInt(4) == 0){
                            level.destroyBlock(tmpPos, true, player);
                            if (player.getRandom().nextInt(4) == 0){
                                if (player.getRandom().nextInt(4) == 0){
                                    Item ore = (ForgeRegistries.ITEMS.tags().getTag(ItemTags.create(new ResourceLocation("smc:vacuum_sniffer_drop"))).getRandomElement(RandomSource.create()).orElse(Items.AIR));
                                    ItemEntity entityToSpawn = new ItemEntity(level, tmpPos.getX() + 0.5D,tmpPos.getY() + 0.5D,tmpPos.getZ() + 0.5D, new ItemStack(ore));
                                    entityToSpawn.setPickUpDelay(10);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                Item ore = (ForgeRegistries.ITEMS.tags().getTag(ItemTags.create(new ResourceLocation("forge:seeds"))).getRandomElement(RandomSource.create()).orElse(Items.AIR));
                                ItemEntity entityToSpawn = new ItemEntity(level, tmpPos.getX() + 0.5D,tmpPos.getY() + 0.5D,tmpPos.getZ() + 0.5D, new ItemStack(ore));
                                entityToSpawn.setPickUpDelay(10);
                                level.addFreshEntity(entityToSpawn);
                            }
                        }
                    }
                }
            }
            final Vec3 center = new Vec3(player.getX(), player.getY(), player.getZ());
            AABB aabb = new AABB(center, center).inflate(r);
            List<ItemEntity> itemEntities = player.level().getEntitiesOfClass(ItemEntity.class, aabb);
            for (ItemEntity target : itemEntities) {
                target.setDeltaMovement(0.05 * (player.getX() - target.getX()), 0.05 * (player.getY() - target.getY()),0.05 * (player.getZ() - target.getZ()));
            }

            level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNIFFER_SNIFFING, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F));

            if (count > 10 && count % 10 == 0) {
                item.hurtAndBreak(1, player, (p_40665_) -> {
                    p_40665_.broadcastBreakEvent(player.getUsedItemHand());
                });
            }
        }
    }


    public int getUseDuration(ItemStack p_42933_) {
        return 72000;
    }

    public UseAnim getUseAnimation(ItemStack p_42931_) {
        return UseAnim.BOW;
    }

    @Override
    public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack) {
        return oldStack.getItem() == newStack.getItem();
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged || newStack.getItem() != oldStack.getItem();
    }

    public InteractionResultHolder<ItemStack> use(Level p_42927_, Player p_42928_, InteractionHand p_42929_) {
        ItemStack itemstack = p_42928_.getItemInHand(p_42929_);
        p_42928_.startUsingItem(p_42929_);
        return InteractionResultHolder.success(itemstack);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        String string = "tooltip.smc." + stack.getItem();
        tooltip.add(Component.translatable(string).withStyle(ChatFormatting.BLUE));
    }
}
