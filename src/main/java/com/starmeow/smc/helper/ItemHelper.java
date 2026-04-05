package com.starmeow.smc.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.registries.ForgeRegistries;

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

    public static int colorToInt(int r, int g, int b){
        return (r << 16) | (g << 8) | b;
    }

    public static Component customColor(Component name, int r, int g, int b, boolean bold){
        String text = name.getString();
        return Component.literal(text).withStyle(style -> style.withColor(colorToInt(r,g,b)));
    }

    public static Component rainbowColor(Component name, long speed, double boldRate){
        String text = name.getString();
        long time = Minecraft.getInstance().level != null
                ? Minecraft.getInstance().level.getGameTime() * speed
                : System.currentTimeMillis() * speed;
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

    public static Component customRainbowColor(Component name, long speed, boolean bold, float factor, int... colors) {
        String text = name.getString();
        if (text.isEmpty()) return name;
        long gameTime = Minecraft.getInstance().level != null
                ? Minecraft.getInstance().level.getGameTime()
                : System.currentTimeMillis() / 50;
        long time = gameTime * speed;

        int colorCount = colors.length;
        float progress = (time % (2000L * colorCount)) / (2000.0F * colorCount);
        float segment = progress * colorCount;

        int currentIndex = (int) segment;
        float lerpFactor = segment - currentIndex;

        MutableComponent result = Component.literal("");
        for (int i = text.length() - 1; i >= 0; i--) {
            char ch = text.charAt(text.length() - 1 - i);

            int color1 = colors[currentIndex % colorCount];
            int color2 = colors[(currentIndex + 1) % colorCount];

            float finalFactor = (lerpFactor + (i)) % 1.0f;
            int finalColor = lerpColor(color1, color2, finalFactor);

            result.append(Component.literal(String.valueOf(ch))
                    .withStyle(style -> style.withColor(finalColor).withBold(bold)));
        }
        return result;
    }

    public static int lerpColor(int color1, int color2, float factor) {
        int r1 = (color1 >> 16) & 0xFF;
        int g1 = (color1 >> 8) & 0xFF;
        int b1 = color1 & 0xFF;

        int r2 = (color2 >> 16) & 0xFF;
        int g2 = (color2 >> 8) & 0xFF;
        int b2 = color2 & 0xFF;

        int r = (int) (r1 + (r2 - r1) * factor);
        int g = (int) (g1 + (g2 - g1) * factor);
        int b = (int) (b1 + (b2 - b1) * factor);
        r = Mth.clamp(r, 0, 255);
        g = Mth.clamp(g, 0, 255);
        b = Mth.clamp(b, 0, 255);

        return (r << 16) | (g << 8) | b;
    }

    public static boolean isTemplateItem(ItemStack itemStack){
        if(itemStack == null) return false;
        return itemStack.getItem() instanceof SmithingTemplateItem
                || ForgeRegistries.ITEMS.getKey(itemStack.getItem()).toString().matches(".*smithing_template.*");
    }
}
