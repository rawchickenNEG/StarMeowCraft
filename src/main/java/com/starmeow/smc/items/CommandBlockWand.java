package com.starmeow.smc.items;

import com.starmeow.smc.entities.projectiles.MagicArrow;
import com.starmeow.smc.helper.ItemHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class CommandBlockWand extends Item {
    public CommandBlockWand(Properties p_40660_) {
        super(p_40660_);
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack item, int count) {
        if (!level.isClientSide) {
            AbstractArrow abstractarrow = new MagicArrow(level, entity);
            abstractarrow.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 1 * 3.0F, 1.0F);

            int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, item);
            if (j > 0) {
                abstractarrow.setBaseDamage(abstractarrow.getBaseDamage() + (double)j * 0.5 + 0.5);
            }

            int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, item);
            if (k > 0) {
                abstractarrow.setKnockback(k);
            }

            if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, item) > 0) {
                abstractarrow.setSecondsOnFire(100);
            }
            level.addFreshEntity(abstractarrow);

            final Vec3 center = new Vec3(entity.getX(), entity.getY(), entity.getZ());
            AABB aabb = new AABB(center, center).inflate(16);
            List<PrimedTnt> tnt = level.getEntitiesOfClass(PrimedTnt.class, aabb);
            for (PrimedTnt tnt0 : tnt) {
                tnt0.discard();
            }
        }
        level.playSound((Player)null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (entity.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
        if(entity instanceof Player player) player.awardStat(Stats.ITEM_USED.get(this));
    }

    @Override
    public void releaseUsing(ItemStack p_40667_, Level p_40668_, LivingEntity p_40669_, int p_40670_) {
        if (p_40669_ instanceof Player player) {
            player.awardStat(Stats.ITEM_USED.get(this));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_40672_, Player p_40673_, InteractionHand p_40674_) {
        ItemStack itemstack = p_40673_.getItemInHand(p_40674_);
        if(p_40673_.isCrouching() && p_40673_.getAbilities().instabuild){
            return InteractionResultHolder.pass(itemstack);
        }else{
            p_40673_.startUsingItem(p_40674_);
        }
        return InteractionResultHolder.success(itemstack);
    }

    @Override
    public InteractionResult useOn(UseOnContext p_41341_) {
        Level level = p_41341_.getLevel();
        BlockPos blockpos = p_41341_.getClickedPos();
        ItemStack itemstack = p_41341_.getItemInHand();
        String loottableRL = itemstack.getHoverName().getString();
        ResourceLocation res = null;
        if(ItemHelper.isValidResourceLocation(loottableRL)){
            res = new ResourceLocation(loottableRL);
        }
        Player player = p_41341_.getPlayer();
        if(player != null && player.isCrouching()){
            BlockState state = level.getBlockState(blockpos);
            if((state.is(Blocks.SAND) || state.is(Blocks.GRAVEL)) && !player.getOffhandItem().isEmpty()){
                BlockState sandBlock = state.is(Blocks.SAND) ? Blocks.SUSPICIOUS_SAND.defaultBlockState() : Blocks.SUSPICIOUS_GRAVEL.defaultBlockState();
                if(level.setBlock(blockpos, sandBlock, 3)){
                    BlockEntity entity = level.getBlockEntity(blockpos);
                    if(entity instanceof BrushableBlockEntity brushable){
                        brushable.item = player.getOffhandItem().copy();
                        if(!player.getAbilities().instabuild){
                            player.getOffhandItem().shrink(1);
                        }
                        brushable.setChanged();
                        level.sendBlockUpdated(blockpos, sandBlock, sandBlock, Block.UPDATE_CLIENTS);
                        level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.GRAVEL_PLACE, SoundSource.PLAYERS, 1.0F, 2.0F);
                    }
                }
                InteractionResult.sidedSuccess(level.isClientSide);
            }
            else if(res != null && player.getAbilities().instabuild){
                boolean flag = false;
                if(level.getBlockEntity(blockpos) instanceof BrushableBlockEntity brushable){
                    brushable.setLootTable(res, 0);
                    flag = true;
                }
                if(level.getBlockEntity(blockpos) instanceof RandomizableContainerBlockEntity chestEntity){
                    chestEntity.setLootTable(res, 0);
                    flag = true;
                }
                if(flag){
                    level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 0.8F, 2.0F);
                }
            }else{
                player.displayClientMessage(Component.translatable("message.smc.not_loottable").withStyle(ChatFormatting.RED), true);
                level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.VILLAGER_NO, SoundSource.PLAYERS, 0.8F, 1.0F);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    @Override
    public int getUseDuration(ItemStack p_40680_) {
        return 72000;
    }

    @Override
    public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack) {
        return oldStack.getItem() == newStack.getItem();
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged || newStack.getItem() != oldStack.getItem();
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_40678_) {
        return UseAnim.BOW;
    }

    @Override
    public Component getName(ItemStack stack) {
        return ItemHelper.rainbowColor(super.getName(stack), 20, true);
    }


    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        String string = "tooltip.smc." + stack.getItem();
        tooltip.add(Component.translatable(string).withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.translatable(string + "_0").withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.translatable(string + "_1").withStyle(ChatFormatting.DARK_PURPLE));
    }
}
