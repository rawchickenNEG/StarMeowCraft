package com.starmeow.smc.helper;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemHelper {

    public static boolean hasRepairableEquipment(Player player) {
        List<ItemStack> equipments = new ArrayList<>();
        equipments.add(player.getMainHandItem());
        equipments.add(player.getOffhandItem());
        for (ItemStack armor : player.getArmorSlots()) {
            equipments.add(armor);
        }
        for(ItemStack equipment : equipments){
            if(unModifiedIsDamaged(equipment) && EnchantmentHelper.getEnchantmentLevel(Enchantments.MENDING, player) > 0){
                return true;
            }
        }
        return false;
    }

    public static boolean unModifiedIsDamaged(ItemStack itemStack){
        return itemStack.getDamageValue() > 0;
    }

    public static int extractNumberFromItemName(ItemStack stack) {
        String name = stack.getHoverName().getString();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(name);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        }
        return -1;
    }

    public static Component customColor(Component name, int r, int g, int b){
        String text = name.getString();
        int rgb = (r << 16) | (g << 8) | b;
        return Component.literal(text).withStyle(style -> style.withColor(rgb));
    }

    public static Component rainbowColor(Component name, Long time, double boldRate){
        String text = name.getString();
        float baseHue = ((time % 2000L) / 2000.0F);

        MutableComponent result = Component.literal("");
        java.util.Random random = new java.util.Random(time);
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            boolean isBold = boldRate > 0 && random.nextDouble() < boldRate;
            float hue = (baseHue + (text.length() - 1 - i) * 0.05F) % 1.0F;
            int rgb = Mth.hsvToRgb(hue, 1.0F, 1.0F) & 0xFFFFFF;
            result.append(Component.literal(String.valueOf(ch)).withStyle(style -> style.withColor(rgb).withBold(isBold)));
        }
        return result;
    }
}
