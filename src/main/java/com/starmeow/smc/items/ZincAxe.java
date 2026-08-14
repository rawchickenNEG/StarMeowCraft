package com.starmeow.smc.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class ZincAxe extends AxeItem {
    public ZincAxe(Tier p_40521_, float p_40522_, float p_40523_, Properties p_40524_) {
        super(p_40521_, p_40522_, p_40523_, p_40524_);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack)
    {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(this.getDefaultAttributeModifiers(slot));
        CompoundTag tag = stack.getOrCreateTag();
        int power = tag.getInt("SMCZincPower");
        if (slot == EquipmentSlot.MAINHAND && power > 0) {
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("1bec158a-a125-499b-9e43-e2a25bfbb809"), "atk_damage", 0.5, AttributeModifier.Operation.MULTIPLY_BASE));
        }
        return builder.build();
    }

    public boolean hurtEnemy(ItemStack p_40994_, LivingEntity p_40995_, LivingEntity p_40996_) {
        CompoundTag tag = p_40994_.getOrCreateTag();
        int power = tag.getInt("SMCZincPower");
        if(power == 1){
            p_40994_.hurtAndBreak(1, p_40996_, (p_41007_) -> {
                p_41007_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
            tag.putInt("SMCZincPower", 0);
        }else if (power <= 0){
            p_40994_.hurtAndBreak(2, p_40996_, (p_41007_) -> {
                p_41007_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }else{
            tag.putInt("SMCZincPower", Math.max(power - 2, 0));
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
    public void inventoryTick(ItemStack itemstack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, level, entity, slot, selected);
        if(!level.isClientSide() && entity instanceof Player player) {
            for(int i = 0; i < player.getInventory().getContainerSize(); i++){
                if(getPower(itemstack) < 64){
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
        return getPower(item) > 0 ? Math.round(getPower(item) * 13.0F / 64) : super.getBarWidth(item);
    }

    public int getBarColor(ItemStack item) {
        return getPower(item) > 0 ? Mth.hsvToRgb(0.0F, 0.0F, 1.0F) : super.getBarColor(item);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        tooltip.add(Component.translatable("tooltip.smc.zinc_tool").withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.translatable("tooltip.smc.zinc_tool_0").withStyle(ChatFormatting.BLUE));
        if(getPower(stack) > 0){
            tooltip.add(Component.translatable("tooltip.smc.zinc_tool_1", getPower(stack)).withStyle(ChatFormatting.AQUA));
        }
    }
}
