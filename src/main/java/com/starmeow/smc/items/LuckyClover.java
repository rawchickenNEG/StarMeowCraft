package com.starmeow.smc.items;

import com.starmeow.smc.config.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class LuckyClover extends Item {
    private final boolean isCreative;
    public LuckyClover(Properties p_41383_, boolean isCreative) {
        super(p_41383_);
        this.isCreative = isCreative;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand p_41130_) {
        ItemStack itemstack = player.getItemInHand(p_41130_);
        if (!player.getCooldowns().isOnCooldown(this) && !Config.LUCKY_CLOVER_DISABLE.get()){
            Random random = new Random();
            List<Item> items = Config.whitelistItems;
            if(isCreative){
                items = Config.blacklistItems;
            }

            Item randomItem = items.get(random.nextInt(items.size()));
            ItemStack spawnedItem = new ItemStack(randomItem);
            if(Config.LUCKY_CLOVER_STACK.get()){
                spawnedItem.setCount(Math.max(random.nextInt(randomItem.getMaxStackSize()), 1));
            }
            level.addFreshEntity(new ItemEntity(level, player.getX(), player.getY(), player.getZ(), spawnedItem));
            player.getCooldowns().addCooldown(this, Config.LUCKY_CLOVER_CD.get());
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
        }
        return InteractionResultHolder.pass(itemstack);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        if(Config.LUCKY_CLOVER_DISABLE.get()){
            tooltip.add(Component.translatable("tooltip.smc.lucky_clover_ban").withStyle(ChatFormatting.RED));

        }else{
            tooltip.add(Component.translatable("tooltip.smc.lucky_clover").withStyle(ChatFormatting.GREEN));
        }
        tooltip.add(Component.translatable("tooltip.smc.lucky_clover_1").withStyle(ChatFormatting.BLUE));
    }
}
