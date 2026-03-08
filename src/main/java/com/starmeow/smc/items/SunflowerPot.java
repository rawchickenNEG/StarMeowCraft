package com.starmeow.smc.items;

import com.starmeow.smc.init.BlockRegistry;
import com.starmeow.smc.init.ParticleRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class SunflowerPot extends Item implements Equipable {
    public SunflowerPot(Properties p_41383_) {
        super(p_41383_);
    }

    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity) {
        level.getLevelData().setRaining(false);
        if(entity instanceof Player player){
            player.getCooldowns().addCooldown(this, 1200);
            itemStack.hurtAndBreak(10, player, (p_40665_) -> {
                p_40665_.broadcastBreakEvent(player.getUsedItemHand());
            });
        }
        return itemStack;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (level.getLevelData().isRaining() || level.getLevelData().isThundering() && !player.isCrouching()){
            return ItemUtils.startUsingInstantly(level, player, hand);
        }
        if(player.isCrouching()){
            if(level.getBlockState(player.getOnPos().above()).is(Blocks.AIR)){
                level.setBlockAndUpdate(player.getOnPos().above(), BlockRegistry.SUNFLOWER_LIGHT_BLOCK.get().defaultBlockState());
                itemstack.hurtAndBreak(1, player, (p_40665_) -> {
                    p_40665_.broadcastBreakEvent(player.getUsedItemHand());
                });
            }
        }
        return InteractionResultHolder.pass(itemstack);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);
        if(level.isClientSide() && entity instanceof Player player) {

            if(level.getGameTime() % 10L != 0L) return;

            //是的孩子们，我复用了OreDetector的代码
            BlockPos pos = player.getOnPos().offset(0,1,0);
            if(player.getItemInHand(InteractionHand.MAIN_HAND).is(this)) {
                int r = 32;
                int size = 0;
                for (BlockPos tmpPos : BlockPos.withinManhattan(pos, r, r, r)) {
                    if(level.getBlockState(tmpPos).is(BlockRegistry.SUNFLOWER_LIGHT_BLOCK.get())){
                        if(size <= 256){
                            level.addParticle(ParticleRegistry.SUNLIGHT.get(), tmpPos.getX() + 0.5, tmpPos.getY() + 0.5, tmpPos.getZ() + 0.5, 0.0, 0.0, 0.0);
                            size++;
                        }
                    }
                }
            }
        }
    }

    public int getUseDuration(ItemStack p_42933_) {
        return 32;
    }

    public UseAnim getUseAnimation(ItemStack p_42931_) {
        return UseAnim.BOW;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        String string = "tooltip.smc." + stack.getItem();
        tooltip.add(Component.translatable(string).withStyle(ChatFormatting.BLUE));
    }

    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }
}
