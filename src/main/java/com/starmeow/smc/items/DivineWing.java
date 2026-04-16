package com.starmeow.smc.items;

import com.starmeow.smc.client.renderer.item.CustomArmorRenderProperties;
import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class DivineWing extends ArmorItem {
    public DivineWing(ArmorMaterial p_40386_, Type p_266831_, Properties p_40388_) {
        super(p_40386_, p_266831_, p_40388_);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if(!level.isClientSide()) {
            CompoundTag tag = stack.getOrCreateTag();
            boolean fly = tag.getBoolean("SMCWingAbility");
            if(player.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.DIVINE_WING.get())){
                if(player.getAbilities().flying && !player.getAbilities().instabuild){
                    if(level.getGameTime() % 20L == 0L){
                        player.getFoodData().addExhaustion(5.0f);
                    }
                }
                if(player.getFoodData().getFoodLevel() > 6 && !fly){
                    tag.putBoolean("SMCWingAbility", true);
                }
                if(player.getFoodData().getFoodLevel() <= 6 && fly){
                    tag.putBoolean("SMCWingAbility", false);
                    player.getAbilities().flying = false;
                }
                player.getAbilities().mayfly = fly;
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
        CompoundTag tag = stack.getOrCreateTag();
        String variant = tag.getString("SMCWingVariant");
        switch (variant){
            case "black" -> {
                return "smc:textures/models/wing/divine_wing_black.png";
            }
            case "meow" -> {
                return "smc:textures/models/wing/divine_wing_meow.png";
            }
            case "gold" -> {
                return "smc:textures/models/wing/divine_wing_gold.png";
            }
            default -> {
                return "smc:textures/models/wing/divine_wing.png";
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        CompoundTag tag = stack.getOrCreateTag();
        String variant = tag.getString("SMCWingVariant");
        String string = "tooltip.smc." + stack.getItem();
        String variantStr = "tooltip.smc.divine_wing_type.";
        tooltip.add(Component.translatable(string).withStyle(ChatFormatting.BLUE));
        switch (variant){
            case "black" -> tooltip.add(Component.translatable(variantStr + "black").withStyle(ChatFormatting.DARK_RED));
            case "meow" -> tooltip.add(Component.translatable(variantStr + "meow").withStyle(ChatFormatting.GOLD));
            case "gold" -> tooltip.add(Component.translatable(variantStr + "gold").withStyle(ChatFormatting.YELLOW));
            default -> tooltip.add(Component.translatable(variantStr + "white").withStyle(ChatFormatting.GRAY));
        }
    }
}
