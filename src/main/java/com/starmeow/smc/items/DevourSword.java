package com.starmeow.smc.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.starmeow.smc.client.renderer.SpecialItemRenderers;
import com.starmeow.smc.config.Config;
import com.starmeow.smc.helper.ItemHelper;
import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
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
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class DevourSword extends SwordItem {
    public DevourSword(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack)
    {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(this.getDefaultAttributeModifiers(slot));
        CompoundTag tag = stack.getOrCreateTag();
        ListTag list = tag.getList("SMCWeaponStored", Tag.TAG_STRING);
        if (slot == EquipmentSlot.MAINHAND) {
            boolean isKnife = stack.is(ItemRegistry.DEVOUR_KNIFE.get());
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("68024817-4632-8753-1084-367954234584"), "atk_damage", list.size() * (isKnife ? Config.DEVOUR_KNIFE_ADD.get() : Config.DEVOUR_SWORD_ADD.get()), AttributeModifier.Operation.ADDITION));
        }
        return builder.build();
    }

    public boolean overrideStackedOnOther(ItemStack itemStack, Slot p_150734_, ClickAction p_150735_, Player p_150736_) {
        if (itemStack.getCount() == 1 && p_150735_ == ClickAction.SECONDARY) {
            ItemStack selected = p_150734_.getItem();
            CompoundTag tag = itemStack.getOrCreateTag();
            ListTag list = tag.getList("SMCWeaponStored", Tag.TAG_STRING);
            ResourceLocation res = ForgeRegistries.ITEMS.getKey(selected.getItem());
            StringTag newID = StringTag.valueOf(res.toString());
            if(!list.contains(newID)){
                if((selected.getItem() instanceof SwordItem && itemStack.is(ItemRegistry.DEVOUR_SWORD.get())) || (selected.is(TagKey.create(Registries.ITEM,new ResourceLocation("farmersdelight:tools/knives"))) && itemStack.is(ItemRegistry.DEVOUR_KNIFE.get()))){
                    if(selected.is(ItemRegistry.ZENISH.get())){
                        p_150736_.displayClientMessage(Component.translatable("message.smc.devour_zenish"), true);
                        p_150736_.level().playSound(p_150736_, p_150736_.getOnPos(), SoundEvents.COD_DEATH, SoundSource.PLAYERS);
                    }
                    p_150734_.safeTake(selected.getCount(), 1, p_150736_);
                    list.add(newID);
                    tag.put("SMCWeaponStored", list);
                    p_150736_.level().playSound(p_150736_, p_150736_.getOnPos(), SoundEvents.BUCKET_FILL_LAVA, SoundSource.PLAYERS);
                    return true;
                }
                //sword.getDefaultAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE);
            }else{
                p_150736_.level().playSound(p_150736_, p_150736_.getOnPos(), SoundEvents.ZOMBIE_ATTACK_IRON_DOOR, SoundSource.PLAYERS);

            }
        }
        return false;
    }

    /*
    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        this.shootSword(stack, entity);
        return super.onEntitySwing(stack, entity);
    }
    */

    /*
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity source) {
        this.shootSword(stack, source);
        return super.hurtEnemy(stack, entity, source);
    }
    */

    public int getAddedAmount(Integer number){
        int result = 0;
        int n = 1;
        int t = 0;
        while(number > 0){
            t++;
            result += n;
            if(t > 10){
                n++;
            }
            number--;
        }
        return result;
    }

    @Override
    public Component getName(ItemStack stack) {
        int[] colors = {
                ItemHelper.colorToInt(255, 192, 142 ),
                ItemHelper.colorToInt(252, 234, 167),
                ItemHelper.colorToInt(223, 231, 123),
                ItemHelper.colorToInt(144, 199, 60),
                ItemHelper.colorToInt(146, 163, 164),
                ItemHelper.colorToInt(160, 169, 208),
                ItemHelper.colorToInt(255, 255, 255)
        };
        return ItemHelper.customRainbowColor(super.getName(stack), 100, true, 0.16f, colors);
    }

    @Override
    public void initializeClient(java.util.function.Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return SpecialItemRenderers.BEWLR;
            }
        });
    }

    public static void setSkinItem(ItemStack stack, ItemStack skinStack) {
        stack.getOrCreateTag().putString("SMCWeaponSkin", ForgeRegistries.ITEMS.getKey(skinStack.getItem()).toString());
    }

    public static void clearSkinItem(ItemStack stack) {
        if (stack.hasTag()) stack.getTag().remove("SMCWeaponSkin");
    }

    public static boolean hasShootAbility(ItemStack stack){
        CompoundTag tag = stack.getOrCreateTag();
        ListTag list = tag.getList("SMCWeaponStored", Tag.TAG_STRING).copy();
        StringTag exId = StringTag.valueOf(ForgeRegistries.ITEMS.getKey(ItemRegistry.EXCALIBUR.get()).toString());
        return list.contains(exId);
    }

    public static boolean hasZenishAbility(ItemStack stack){
        CompoundTag tag = stack.getOrCreateTag();
        ListTag list = tag.getList("SMCWeaponStored", Tag.TAG_STRING).copy();
        StringTag zenId = StringTag.valueOf(ForgeRegistries.ITEMS.getKey(ItemRegistry.ZENISH.get()).toString());
        return list.contains(zenId);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        CompoundTag tag = stack.getOrCreateTag();
        ListTag list = tag.getList("SMCWeaponStored", Tag.TAG_STRING);
        int size = list.size();
        tooltip.add(Component.translatable("tooltip.smc." + stack.getItem()).withStyle(ChatFormatting.BLUE));
        if(Config.DEVOUR_SWORD_SHOOT.get() && stack.is(ItemRegistry.DEVOUR_SWORD.get())){
            Style style = Style.EMPTY.withColor(ItemHelper.colorToInt(125, 89, 225));
            if(hasZenishAbility(stack)){
                tooltip.add(Component.translatable("tooltip.smc.devour_sword_6").withStyle(style));
            }else{
                if(hasShootAbility(stack)){
                    tooltip.add(Component.translatable("tooltip.smc.devour_sword_1", Config.DEVOUR_SWORD_UPGRADE.get(), Config.DEVOUR_SWORD_MAX.get()).withStyle(ChatFormatting.DARK_PURPLE));
                }else{
                    tooltip.add(Component.translatable("tooltip.smc.devour_sword_4").withStyle(ChatFormatting.DARK_GRAY));
                }
                tooltip.add(Component.translatable("tooltip.smc.devour_sword_5").withStyle(ChatFormatting.DARK_GRAY));
            }
        }
        tooltip.add(Component.translatable("tooltip.smc.devour_sword_2", size).withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable("tooltip.smc.devour_sword_3").withStyle(ChatFormatting.BLUE));
    }
}
