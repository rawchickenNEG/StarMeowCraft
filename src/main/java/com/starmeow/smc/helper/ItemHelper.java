package com.starmeow.smc.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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
            if(unModifiedIsDamaged(equipment) && equipment.getEnchantmentLevel(Enchantments.MENDING) > 0){
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
            try {
                long parsedValue = Long.parseLong(matcher.group());
                return Math.toIntExact(parsedValue);
            } catch (NumberFormatException | ArithmeticException e) {
                return -1;
            }
        }

        return -1;
    }

    public static int colorToInt(int r, int g, int b){
        return (r << 16) | (g << 8) | b;
    }

    public static Component customColor(Component name, int r, int g, int b, boolean bold){
        String text = name.getString();
        return Component.literal(text).withStyle(style -> style.withColor(colorToInt(r,g,b)).withBold(bold));
    }

    public static Component rainbowColor(Component name, long speed, boolean bold){
        String text = name.getString();
        long time = System.currentTimeMillis() / 20 * speed;
        float baseHue = ((time % 2000L) / 2000.0F);

        MutableComponent result = Component.literal("");
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            float hue = (baseHue + (text.length() - 1 - i) * 0.05F) % 1.0F;
            int rgb = Mth.hsvToRgb(hue, 1.0F, 1.0F) & 0xFFFFFF;
            result.append(Component.literal(String.valueOf(ch)).withStyle(style -> style.withColor(rgb).withBold(bold)));
        }
        return result;
    }

    public static Component customRainbowColor(Component name, long speed, boolean bold, float factor, int... colors) {
        String text = name.getString();
        if (text.isEmpty()) return name;
        long gameTime = System.currentTimeMillis() / 20;
        long time = gameTime * speed;

        int colorCount = colors.length;
        float progress = (time % (2000L * colorCount)) / (2000.0F * colorCount);
        float segment = progress * colorCount;

        int currentIndex = (int) segment;
        float lerpFactor = segment - currentIndex;

        MutableComponent result = Component.literal("");
        for (int i = text.length() - 1; i >= 0; i--) {
            char ch = text.charAt(text.length() - 1 - i);
            float shifted = lerpFactor + i * factor;

            int indexOffset = (int) Math.floor(shifted);
            float finalFactor = shifted - indexOffset;

            int index = (currentIndex + indexOffset) % colorCount;
            if (index < 0) index += colorCount;

            int color1 = colors[index];
            int color2 = colors[(index + 1) % colorCount];

            int finalColor = lerpColor(color1, color2, finalFactor);
            result.append(Component.literal(String.valueOf(ch)).withStyle(style -> style.withColor(finalColor).withBold(bold)));
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

    public static boolean isValidResourceLocation(String str) {
        if (str == null || str.isEmpty()) return false;

        try {
            ResourceLocation loc = ResourceLocation.tryParse(str);
            return loc != null;
        } catch (Exception e) {
            return false;
        }
    }

    public static List<ItemStack> getBlockDrops(ServerLevel level, BlockPos pos, Player player, ItemStack tool) {
        BlockState state = level.getBlockState(pos);
        BlockEntity blockEntity = level.getBlockEntity(pos);
        LootParams.Builder builder = new LootParams.Builder(level)
                .withParameter(LootContextParams.ORIGIN, pos.getCenter())
                .withParameter(LootContextParams.BLOCK_STATE, state)
                .withParameter(LootContextParams.TOOL, tool)
                .withOptionalParameter(LootContextParams.THIS_ENTITY, player)
                .withOptionalParameter(LootContextParams.BLOCK_ENTITY, blockEntity);;

        if (player != null) {
            builder.withParameter(LootContextParams.THIS_ENTITY, player);
        }
        return state.getDrops(builder);
    }

    public static boolean itemHasSmeltResult(ItemStack itemStack, Level level){
        return level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SimpleContainer(itemStack), level).isPresent();
    }

    public static ItemStack getItemSmeltResult(ItemStack itemStack, Level level){
        if (itemHasSmeltResult(itemStack, level)) {
            return level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SimpleContainer(itemStack), level).map(recipe -> recipe.getResultItem(level.registryAccess()).copy()).orElse(ItemStack.EMPTY);
        }
        return ItemStack.EMPTY;
    }
}
