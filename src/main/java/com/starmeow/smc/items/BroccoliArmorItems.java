package com.starmeow.smc.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class BroccoliArmorItems extends SMCArmorItems{
    public BroccoliArmorItems(ArmorMaterial p_40386_, Type p_266831_, Properties p_40388_) {
        super(p_40386_, p_266831_, p_40388_);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack)
    {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(this.getDefaultAttributeModifiers(slot));
        if (slot == EquipmentSlot.FEET && stack.is(ItemRegistry.BROCCOLI_BOOTS.get())) {
            builder.put(Attributes.LUCK, new AttributeModifier(UUID.fromString("96892431-6357-8723-2423-300617977940"), "broccoli_armor_add_health", 1D, AttributeModifier.Operation.ADDITION));
        }
        return builder.build();
    }

    @Override
    public void onArmorTick(ItemStack itemstack, Level level, Player player) {
        if(!level.isClientSide()) {
            if(player.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.BROCCOLI_BOOTS.get())) {
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 310, 1, true, true, true));
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.translatable("tooltip.smc.broccoli_boots").withStyle(ChatFormatting.BLUE));
    }

}
