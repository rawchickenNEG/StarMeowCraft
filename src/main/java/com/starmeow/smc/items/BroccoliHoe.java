package com.starmeow.smc.items;

import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class BroccoliHoe extends HoeItem {
    public BroccoliHoe(Tier p_41336_, int p_41337_, float p_41338_, Properties p_41339_) {
        super(p_41336_, p_41337_, p_41338_, p_41339_);
    }

    @Override
    public InteractionResult useOn(UseOnContext p_41341_) {
        Level level = p_41341_.getLevel();
        BlockPos blockpos = p_41341_.getClickedPos();
        BlockState toolModifiedState = level.getBlockState(blockpos).getToolModifiedState(p_41341_, net.minecraftforge.common.ToolActions.HOE_TILL, false);
        Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> pair = toolModifiedState == null ? null : Pair.of(ctx -> true, changeIntoState(toolModifiedState));
        if (pair == null) {
            return InteractionResult.PASS;
        } else {
            Predicate<UseOnContext> predicate = pair.getFirst();
            Consumer<UseOnContext> consumer = pair.getSecond();
            if (predicate.test(p_41341_)) {
                Player player = p_41341_.getPlayer();
                level.playSound(player, blockpos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!level.isClientSide) {
                    consumer.accept(p_41341_);
                    //西兰花锄获取掉落物
                    ItemStack item = p_41341_.getItemInHand();
                    int ench = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.BLOCK_FORTUNE, item);
                    float result = (float) ((20.0 / 100) + ench * (float)(20.0 / 100));
                    while (Math.random() <= result){
                        ItemEntity entityToSpawn = new ItemEntity(level, blockpos.getX() + 0.5D,blockpos.getY() + 1.0D,blockpos.getZ() + 0.5D, new ItemStack(Items.BONE_MEAL));
                        entityToSpawn.setPickUpDelay(10);
                        level.addFreshEntity(entityToSpawn);
                        result += -1;
                    }

                    if (player != null) {
                        p_41341_.getItemInHand().hurtAndBreak(1, player, (p_150845_) -> {
                            p_150845_.broadcastBreakEvent(p_41341_.getHand());
                        });
                    }
                }

                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        String string = "tooltip.smc." + stack.getItem();
        tooltip.add(Component.translatable(string).withStyle(ChatFormatting.BLUE));
    }
}
