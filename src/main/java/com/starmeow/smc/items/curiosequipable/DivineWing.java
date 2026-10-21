package com.starmeow.smc.items.curiosequipable;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.starmeow.smc.client.renderer.item.CustomArmorRenderProperties;
import com.starmeow.smc.helper.CuriosHelper;
import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class DivineWing extends ArmorItem implements ICurioItem {
    public DivineWing(ArmorMaterial p_40386_, Type p_266831_, Properties p_40388_) {
        super(p_40386_, p_266831_, p_40388_);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attributeMap = ArrayListMultimap.create();
        //attributeMap.put(CaelusApi.getInstance().getFlightAttribute(), new AttributeModifier(UUID.fromString("53016504-1341-6422-2424-032531187451"), "elytra", 1, AttributeModifier.Operation.ADDITION));
        return attributeMap;
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
        if(!level.isClientSide()) {
            if(player.isSpectator()) return;
            CompoundTag tag = stack.getOrCreateTag();
            boolean fly = tag.getBoolean("SMCWingAbility");
            if(player.getAbilities().flying && !player.getAbilities().instabuild && !CuriosHelper.hasCharm(player, ItemRegistry.DIVINE_HALO.get()) && !player.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.DIVINE_HALO.get())){
                if(level.getGameTime() % 4L == 0L){
                    player.getFoodData().addExhaustion(1.0f);
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

    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept((IClientItemExtensions)getArmorRenderProperties());
    }

    public Object getArmorRenderProperties() {
        return new CustomArmorRenderProperties();
    }

    @Override
    public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.CHEST).is(this);
    }

    @Override
    public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
        if (!entity.level().isClientSide) {
            int nextFlightTick = flightTicks + 1;
            if (nextFlightTick % 10 == 0) {
                entity.gameEvent(GameEvent.ELYTRA_GLIDE);
                if(entity instanceof Player player && !player.getAbilities().instabuild && !CuriosHelper.hasCharm(entity, ItemRegistry.DIVINE_HALO.get()) && !player.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.DIVINE_HALO.get())){
                    player.getFoodData().addExhaustion(1.0f);
                }
            }
        }
        return true;
    }


    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return wingTexture(stack);
    }

    public static String wingTexture(ItemStack stack){
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
            case "frost" -> {
                return "smc:textures/models/wing/divine_wing_frost.png";
            }
            case "rainbow" -> {
                return "smc:textures/models/wing/divine_wing_rainbow.png";
            }
            case "zinc" -> {
                return "smc:textures/models/wing/divine_wing_zinc.png";
            }
            case "titanium" -> {
                return "smc:textures/models/wing/divine_wing_titanium.png";
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
        tooltip.add(Component.translatable(string + "_1").withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.translatable(string + "_2").withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.translatable(string + "_3").withStyle(ChatFormatting.BLUE));
        switch (variant){
            case "black" -> tooltip.add(Component.translatable(variantStr + "black").withStyle(ChatFormatting.DARK_RED));
            case "meow" -> tooltip.add(Component.translatable(variantStr + "meow").withStyle(ChatFormatting.GOLD));
            case "gold" -> tooltip.add(Component.translatable(variantStr + "gold").withStyle(ChatFormatting.YELLOW));
            case "frost" -> tooltip.add(Component.translatable(variantStr + "frost").withStyle(ChatFormatting.AQUA));
            case "rainbow" -> tooltip.add(Component.translatable(variantStr + "rainbow").withStyle(ChatFormatting.LIGHT_PURPLE));
            case "zinc" -> tooltip.add(Component.translatable(variantStr + "zinc").withStyle(ChatFormatting.BLUE));
            case "titanium" -> tooltip.add(Component.translatable(variantStr + "titanium").withStyle(ChatFormatting.RED));
            default -> tooltip.add(Component.translatable(variantStr + "white").withStyle(ChatFormatting.GRAY));
        }
    }
}
