package com.starmeow.smc.items.curiosequipable;

import com.starmeow.smc.client.renderer.item.CustomArmorRenderProperties;
import com.starmeow.smc.config.Config;
import com.starmeow.smc.helper.ItemHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class CreativeHalo extends ArmorItem implements ICurioItem {

    public CreativeHalo(ArmorMaterial p_40386_, Type p_266831_, Properties p_40388_) {
        super(p_40386_, p_266831_, p_40388_);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        onActiveTick(stack, level, player);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        Level level = slotContext.entity().level();
        if(entity instanceof Player player){
            onActiveTick(stack, level, player);
        }
    }

    private void onActiveTick(ItemStack stack, Level level, Player player){
        if (level instanceof ServerLevel && player instanceof ServerPlayer serverPlayer) {
            if(serverPlayer.gameMode.getGameModeForPlayer().isSurvival() && !player.getAbilities().instabuild){
                player.getAbilities().instabuild = true;
                player.getAbilities().invulnerable = true;
                player.getAbilities().mayfly = true;
                player.onUpdateAbilities();
            }
        }
    }

    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept((IClientItemExtensions)getArmorRenderProperties());
    }

    public Object getArmorRenderProperties() {
        return new CustomArmorRenderProperties();
    }

    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return haloTexture(stack);
    }

    public static String haloTexture(ItemStack stack){
        CompoundTag tag = stack.getOrCreateTag();
        String variant = tag.getString("SMCHaloVariant");
        if(variant.isBlank()){
            return "smc:textures/models/halo/creative.png";
        } else {
            return "smc:textures/models/halo/" + variant + ".png";
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        CompoundTag tag = stack.getOrCreateTag();
        String variant = tag.getString("SMCHaloVariant");
        String string = "tooltip.smc." + stack.getItem();
        String variantStr = "tooltip.smc.divine_halo_type.";
        tooltip.add(Component.translatable(string).withStyle(ChatFormatting.BLUE));
        if(Screen.hasShiftDown()){
            tooltip.add(Component.translatable(string + "_1").withStyle(ChatFormatting.LIGHT_PURPLE));
            tooltip.add(Component.translatable(string + "_2").withStyle(ChatFormatting.LIGHT_PURPLE));
            tooltip.add(Component.translatable(string + "_3").withStyle(ChatFormatting.LIGHT_PURPLE));
            tooltip.add(Component.translatable(string + "_4").withStyle(ChatFormatting.LIGHT_PURPLE));
        }else{
            tooltip.add(Component.translatable(string + "_0").withStyle(ChatFormatting.LIGHT_PURPLE));
        }
        Style format;
        switch (variant){
            case "perfrostite" -> format = Style.EMPTY.withColor(TextColor.fromRgb(0x7878B6));
            case "meow" -> format = Style.EMPTY.withColor(TextColor.fromRgb(0xFFC08E));
            case "frost" -> format = Style.EMPTY.withColor(TextColor.fromRgb(0x84A6E9));
            case "arisu" -> format = Style.EMPTY.withColor(ChatFormatting.AQUA);
            case "koharu" -> format = Style.EMPTY.withColor(ItemHelper.colorToInt(236, 71, 123));
            case "kokona" -> format = Style.EMPTY.withColor(ItemHelper.colorToInt(207, 194, 255));
            case "hikari" -> format = Style.EMPTY.withColor(ItemHelper.colorToInt(230, 240, 156));
            case "broccoli" -> format = Style.EMPTY.withColor(TextColor.fromRgb(0x587A2F));
            case "apostle" -> format = Style.EMPTY.withColor(ChatFormatting.DARK_RED);
            case "helicopter" -> format = Style.EMPTY.withColor(ItemHelper.colorToInt(98, 28, 156));
            case "professor" -> format = Style.EMPTY.withColor(ItemHelper.colorToInt(244, 240, 207));
            case "d" -> format = Style.EMPTY.withColor(ChatFormatting.DARK_GRAY);
            case "zinc" -> format = Style.EMPTY.withColor(ItemHelper.colorToInt(114, 114, 158));
            default -> format = Style.EMPTY.withColor(ChatFormatting.GRAY);
        }
        if(variant.isBlank()){
            tooltip.add(Component.translatable(variantStr + "default").withStyle(format));
        }else{
            tooltip.add(Component.translatable(variantStr + variant).withStyle(format));
        }
    }
}
