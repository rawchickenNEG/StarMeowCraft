package com.starmeow.smc.items.curiosequipable;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.starmeow.smc.helper.ItemHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class FoxTail extends Item implements ICurioItem {

    public FoxTail(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attributeMap = ArrayListMultimap.create();
        CompoundTag tag = stack.getOrCreateTag();
        if(tag.getInt("SMCTailAtkSpeed") != 0){
            attributeMap.put(Attributes.ATTACK_SPEED, new AttributeModifier(UUID.fromString("ee2e5bc6-8060-471b-8c90-6e22453ec41f"), "tail_atk_speed", 1, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
        if(tag.getFloat("SMCTailAtkDamage") != 0){
            attributeMap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("ee9e5bc7-7152-471b-8c90-6e22453ec75f"), "tail_atk_damage", tag.getFloat("SMCTailAtkDamage"), AttributeModifier.Operation.ADDITION));
        }
        return attributeMap;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        Level level = slotContext.entity().level();
        BlockPos pos = entity.blockPosition();
        CompoundTag tag = stack.getOrCreateTag();
        if(level.getRawBrightness(pos, 0) <= 7){
            entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 5, 0, true, true, true));
        }
        if(entity instanceof Player player){
            if(player.isCrouching()){
                tag.putInt("SMCTailJump", 10);
            }else{
                tag.putInt("SMCTailJump", Math.max(tag.getInt("SMCTailJump") - 1, 0));
            }
            if(tag.getInt("SMCTailAtkSpeed") != 0){
                tag.putInt("SMCTailAtkSpeed", Math.max(tag.getInt("SMCTailAtkSpeed") - 1, 0));
            }
        }
    }

    public static String tailTexture(ItemStack stack){
        CompoundTag tag = stack.getOrCreateTag();
        String variant = tag.getString("SMCTailVariant");
        switch (variant){
            case "fox" -> {
                return "smc:textures/models/tail/fox.png";
            }
            case "snow_fox" -> {
                return "smc:textures/models/tail/snow_fox.png";
            }
            case "huacai" -> {
                return "smc:textures/models/tail/huacai.png";
            }
            case "bf_meow" -> {
                return "smc:textures/models/tail/bf_meow.png";
            }
            case "starjump" -> {
                return "smc:textures/models/tail/starjump.png";
            }
            case "zinc" -> {
                return "smc:textures/models/tail/zinc.png";
            }
            default -> {
                return "smc:textures/models/tail/default.png";
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        CompoundTag tag = stack.getOrCreateTag();
        String variant = tag.getString("SMCTailVariant");
        String variantStr = "tooltip.smc.fox_tail_type.";
        tooltip.add(Component.translatable("tooltip.smc.fox_tail").withStyle(ChatFormatting.AQUA));
        tooltip.add(Component.translatable("tooltip.smc.fox_tail_0").withStyle(ChatFormatting.AQUA));
        tooltip.add(Component.translatable("tooltip.smc.fox_tail_1").withStyle(ChatFormatting.AQUA));
        switch (variant){
            case "fox" -> tooltip.add(Component.translatable(variantStr + "fox").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xE17B24))));
            case "snow_fox" -> tooltip.add(Component.translatable(variantStr + "snow_fox").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xD7E2D9))));
            case "huacai" -> tooltip.add(Component.translatable(variantStr + "huacai").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x587A2F))));
            case "bf_meow" -> tooltip.add(Component.translatable(variantStr + "bf_meow").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFC08E))));
            case "starjump" -> tooltip.add(Component.translatable(variantStr + "starjump").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x84A6E9))));
            case "zinc" -> tooltip.add(Component.translatable(variantStr + "zinc").withStyle(Style.EMPTY.withColor(ItemHelper.colorToInt(114, 114, 158))));
            default -> tooltip.add(Component.translatable(variantStr + "default").withStyle(Style.EMPTY.withColor(ChatFormatting.WHITE)));
        }
    }

}
