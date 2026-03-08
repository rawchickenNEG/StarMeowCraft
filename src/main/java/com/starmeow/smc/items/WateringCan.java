package com.starmeow.smc.items;

import com.starmeow.smc.config.Config;
import com.starmeow.smc.init.EnchantmentRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class WateringCan extends Item {
    private final int range;
    private final int maxWater;
    private final int enchantLevel;
    public WateringCan(Properties p_41383_, int range, int maxWater, int enchantLevel) {
        super(p_41383_);
        this.range = range;
        this.maxWater = maxWater;
        this.enchantLevel = enchantLevel;
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack item, int count) {
        if (entity instanceof Player player){
            CompoundTag tag = item.getOrCreateTag();
            boolean infinite = EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY_ARROWS, player) > 0 && Config.WATERING_CAN_INFINITY.get();
            if(tag.getInt("SMCWateringCanEmptied") < this.maxWater || infinite){
                if(infinite){
                    tag.putInt("SMCWateringCanEmptied", 0);
                } else {
                    tag.putInt("SMCWateringCanEmptied", tag.getInt("SMCWateringCanEmptied") + 1);
                }

                BlockPos pos = player.getOnPos().offset(0,1,0);
                int r = this.range + EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.WIDE_RANGE.get(), player);
                for (BlockPos tmpPos : BlockPos.withinManhattan(pos, r, 0, r)){
                    level.addParticle(ParticleTypes.SPLASH , (float)tmpPos.getX() + Math.random(), (float)tmpPos.getY() + Math.random(), (float)tmpPos.getZ() + Math.random(), 0,0,0);
                    BlockState blockstate = level.getBlockState(tmpPos);
                    if (level instanceof ServerLevel serverLevel && ((blockstate.getBlock() instanceof BushBlock && EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.COMPOSITE_FERTILIZER.get(), player) > 0) || blockstate.getBlock() instanceof CropBlock)) {
                        //int rand = Math.max(16 - 2 * EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.STRONG_FERTILIZER.get(), player), 1);
                        //if (player.getRandom().nextInt(rand) == 0){
                        //    blockstate.randomTick((ServerLevel)level, tmpPos, level.random);
                        //}
                        for(int i = 0; i <= EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.STRONG_FERTILIZER.get(), player); ++i) {
                            if(player.getRandom().nextInt(4)==0){
                                blockstate.randomTick((ServerLevel) level, tmpPos, level.random);
                            }
                        }
                        if(EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.LIFE_CYCLE.get(), player) > 0 && blockstate.getBlock() instanceof CropBlock cropBlock && cropBlock.isMaxAge(blockstate)){
                            List<ItemStack> drops = Block.getDrops(blockstate, serverLevel, tmpPos, null);
                            boolean flag = false;
                            for(ItemStack itemstack : drops){
                                if(!flag && cropBlock.getCloneItemStack(serverLevel, tmpPos, blockstate).is(itemstack.getItem())){
                                    flag = true;
                                    continue;
                                }
                                popResource(serverLevel, tmpPos, itemstack);
                            }
                            level.levelEvent(2001, tmpPos, Block.getId(level.getBlockState(tmpPos)));
                            BlockState seeds = cropBlock.getStateForAge(0);
                            level.setBlockAndUpdate(tmpPos, seeds);
                        }
                    }
                }
            }
        }
    }

    public static void popResource(Level level, BlockPos pos, ItemStack stack) {
        double d0 = (double) EntityType.ITEM.getHeight() / 2.0;
        double d1 = (double)pos.getX() + 0.5 + Mth.nextDouble(level.random, -0.25, 0.25);
        double d2 = (double)pos.getY() + 0.5 + Mth.nextDouble(level.random, -0.25, 0.25) - d0;
        double d3 = (double)pos.getZ() + 0.5 + Mth.nextDouble(level.random, -0.25, 0.25);
        ItemEntity item = new ItemEntity(level, d1, d2, d3, stack);
        item.setDefaultPickUpDelay();
        level.addFreshEntity(item);
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
        CompoundTag tag = itemstack.getOrCreateTag();
        if(tag.getInt("SMCWateringCanEmptied") < this.maxWater){
            p_42928_.startUsingItem(p_42929_);
            return InteractionResultHolder.success(itemstack);
        }
        return InteractionResultHolder.pass(itemstack);
    }

    @Override
    public InteractionResult useOn(UseOnContext p_41341_) {
        Level level = p_41341_.getLevel();
        BlockPos blockpos = p_41341_.getClickedPos().relative(p_41341_.getClickedFace());
        BlockState blockState = level.getBlockState(blockpos);
        ItemStack itemstack = p_41341_.getItemInHand();
        CompoundTag tag = itemstack.getOrCreateTag();
        Player player = p_41341_.getPlayer();
        if(player != null && blockState.is(Blocks.WATER) && tag.getInt("SMCWateringCanEmptied") != 0){
            tag.putInt("SMCWateringCanEmptied", 0);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return this.canApplyEnchantment(EnchantmentHelper.getEnchantments(stack).keySet().toArray(new Enchantment[0])) || super.isBookEnchantable(stack, book);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return this.canApplyEnchantment(enchantment) || super.canApplyAtEnchantingTable(stack, enchantment);
    }

    private boolean canApplyEnchantment(Enchantment... enchantments) {
        for (Enchantment enchantment : enchantments) {
            if (enchantment.category == EnchantmentRegistry.WATERING_CAN || (enchantment == Enchantments.INFINITY_ARROWS && Config.WATERING_CAN_INFINITY.get()))
                return true;
        }
        return false;
    }

    @Override
    public boolean isBarVisible(ItemStack item) {
        CompoundTag tag = item.getOrCreateTag();
        return (tag.getInt("SMCWateringCanEmptied") != 0);
    }

    @Override
    public int getBarWidth(ItemStack item) {
        CompoundTag tag = item.getOrCreateTag();
        return Math.round(13.0F - (float)tag.getInt("SMCWateringCanEmptied") * 13.0F / this.maxWater);
    }

    public int getBarColor(ItemStack item) {
        return Mth.hsvToRgb(0.0F, 0.0F, 1.0F);
    }

    @Override
    public boolean isEnchantable(ItemStack p_41456_) {
        return true;
    }

    @Override
    public int getEnchantmentValue(ItemStack itemStack){
        return this.enchantLevel;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        String string = "tooltip.smc." + stack.getItem();
        tooltip.add(Component.translatable(string).withStyle(ChatFormatting.BLUE));
    }

}
