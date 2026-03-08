package com.starmeow.smc.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.starmeow.smc.config.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class TemplateShroud extends SMCArmorItems{
    public TemplateShroud(ArmorMaterial p_40386_, Type p_266831_, Properties p_40388_) {
        super(p_40386_, p_266831_, p_40388_);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack)
    {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(this.getDefaultAttributeModifiers(slot));
        CompoundTag tag = stack.getOrCreateTag();
        ListTag list = tag.getList("SMCTemplateStored", Tag.TAG_STRING);
        if (slot == EquipmentSlot.CHEST) {
            builder.put(Attributes.ARMOR, new AttributeModifier(UUID.fromString("53246214-7052-8753-8234-856724890437"), "armor", list.size() * Config.TEMPLATE_SHROUD_ADD.get(), AttributeModifier.Operation.ADDITION));
        }
        return builder.build();
    }

    public boolean overrideStackedOnOther(ItemStack itemStack, Slot p_150734_, ClickAction p_150735_, Player p_150736_) {
        if (itemStack.getCount() == 1 && p_150735_ == ClickAction.SECONDARY) {
            ItemStack selected = p_150734_.getItem();
            CompoundTag tag = itemStack.getOrCreateTag();
            ListTag list = tag.getList("SMCTemplateStored", Tag.TAG_STRING);
            ResourceLocation res = ForgeRegistries.ITEMS.getKey(selected.getItem());
            StringTag newID = StringTag.valueOf(res.toString());
            if(list.contains(newID) || !(selected.getItem() instanceof SmithingTemplateItem)){
                p_150736_.level().playSound(p_150736_, p_150736_.getOnPos(), SoundEvents.ZOMBIE_ATTACK_IRON_DOOR, SoundSource.PLAYERS);
            }else{
                p_150734_.safeTake(selected.getCount(), 1, p_150736_);
                list.add(newID);
                tag.put("SMCTemplateStored", list);
                p_150736_.level().playSound(p_150736_, p_150736_.getOnPos(), SoundEvents.SMITHING_TABLE_USE, SoundSource.PLAYERS);
                return true;
            }
        }
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if(!level.isClientSide() && entity instanceof LivingEntity living) {
            if(level.getGameTime() % (20L * Config.TEMPLATE_SHROUD_ABSORB_DURATION.get()) != 0L) return;
            if(living.getItemBySlot(this.getEquipmentSlot())==stack) {
                CompoundTag tag = stack.getOrCreateTag();
                ListTag list = tag.getList("SMCTemplateStored", Tag.TAG_STRING);
                living.setAbsorptionAmount(list.size() * 2);
            }
        }
    }


    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        CompoundTag tag = stack.getOrCreateTag();
        ListTag list = tag.getList("SMCTemplateStored", Tag.TAG_STRING);
        tooltip.add(Component.translatable("tooltip.smc.template_shroud").withStyle(ChatFormatting.BLUE));
        if(Config.TEMPLATE_SHROUD_ABSORB.get()){
            tooltip.add(Component.translatable("tooltip.smc.template_shroud_1", Config.TEMPLATE_SHROUD_ABSORB_DURATION.get()).withStyle(ChatFormatting.BLUE));
        }
        tooltip.add(Component.translatable("tooltip.smc.template_shroud_2", list.size()).withStyle(ChatFormatting.YELLOW));
    }
}
