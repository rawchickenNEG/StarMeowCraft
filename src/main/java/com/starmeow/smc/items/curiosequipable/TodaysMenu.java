package com.starmeow.smc.items.curiosequipable;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.RandomSequence;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import org.joml.Vector3f;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class TodaysMenu extends Item implements ICurioItem {
    public TodaysMenu(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attributeMap = ArrayListMultimap.create();
        if(finishedAllWantedFoods(stack)){
            attributeMap.put(Attributes.MAX_HEALTH, new AttributeModifier(UUID.fromString("5ef235fa-fa3a-4724-ad82-773d6b6c7f1b"), "menu_health_add", 20, AttributeModifier.Operation.ADDITION));
        }
        return attributeMap;
    }

    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        CompoundTag stackData = stack.getOrCreateTag();
        stackData.putBoolean("SMCMenuIsCorrect", checkMenuIsCorrect(slotContext.entity(), stack));
        if(getTodayWantedFoods(slotContext.entity()).isEmpty()){
            ListTag newList = new ListTag();
            stackData.put("SMCWantedFoodsConsumed", newList);
            this.setTodayWantedFoods(slotContext.entity(), stack);
            System.out.println(getTodayWantedFoods(slotContext.entity()));
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if(slotContext.entity() instanceof ServerPlayer player){
            CompoundTag data = player.getPersistentData();

            long currentDay = player.level().getDayTime() / 24000L;
            long lastRefreshDay = data.getLong("SMCMenuDay");

            if (currentDay != lastRefreshDay) {
                setTodayWantedFoods(player, stack);
                data.putLong("SMCMenuDay", currentDay);
                CompoundTag stackData = stack.getOrCreateTag();
                stackData.putBoolean("SMCMenuIsCorrect", checkMenuIsCorrect(slotContext.entity(), stack));
                player.sendSystemMessage(Component.translatable("message.smc.todays_menu").withStyle(ChatFormatting.GREEN));
                player.playNotifySound(SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 1.0F, 1.0F);
            }

            if(finishedAllWantedFoods(stack)){
                player.getFoodData().setFoodLevel(20);
                player.getFoodData().setSaturation(20);
                List<MobEffectInstance> list = new ArrayList<>(player.getActiveEffects()).stream().filter(e -> e.getEffect().getCategory().equals(MobEffectCategory.HARMFUL)).toList();
                for (MobEffectInstance ins : list) {
                    player.removeEffect(ins.getEffect());
                    if (player.hasEffect(ins.getEffect())) {
                        player.getActiveEffectsMap().remove(ins.getEffect());
                    }
                }
            }
        }
    }

    public static boolean finishedAllWantedFoods(ItemStack stack){
        List<Item> wantedFoods = getTodayWantedFoodsFromItem(stack);
        List<Item> finishedFoods = getFinishedWantedFoodsFromItem(stack);
        for(Item food : wantedFoods){
            if(!finishedFoods.contains(food)) return false;
        }
        return true;
    }

    public static boolean wantedFoodFinished(ItemStack stack, Item food){
        List<Item> finishedFoods = getFinishedWantedFoodsFromItem(stack);
        return finishedFoods.contains(food);
    }

    public static void setWantedFoodFinished(ItemStack stack, Item food){
        CompoundTag data = stack.getOrCreateTag();
        ListTag wantedFoodsTag = data.getList("SMCWantedFoodsConsumed", 8);
        StringTag tag = StringTag.valueOf(ForgeRegistries.ITEMS.getKey(food).toString());
        if(!wantedFoodsTag.contains(tag)){
            wantedFoodsTag.add(tag);
            data.put("SMCWantedFoodsConsumed", wantedFoodsTag);
        }
    }

    public static void addWantedFoodReward(Item food, LivingEntity living){
        ArrayList<MobEffectInstance> list = new ArrayList<>(living.getActiveEffects());
        list.removeIf(mei -> !mei.getEffect().isBeneficial());
        if(!list.isEmpty()){
            if(food.getFoodProperties().getNutrition() >= 12){
                for(MobEffectInstance mei : list){
                    living.addEffect(new MobEffectInstance(mei.getEffect(), mei.getDuration() + 600, mei.getAmplifier(), mei.isAmbient(), mei.isVisible(), mei.showIcon()));
                }
            }else if(food.getFoodProperties().getNutrition() >= 8){
                for(MobEffectInstance mei : list){
                    living.addEffect(new MobEffectInstance(mei.getEffect(), mei.getDuration() + 200, mei.getAmplifier(), mei.isAmbient(), mei.isVisible(), mei.showIcon()));
                }
            }else{
                MobEffectInstance effect = list.get(living.getRandom().nextInt(list.size()));
                living.addEffect(new MobEffectInstance(effect.getEffect(), effect.getDuration() + 200, effect.getAmplifier(), effect.isAmbient(), effect.isVisible(), effect.showIcon()));
            }
        }
        if(living instanceof Player player){
            player.playNotifySound(SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }

    public List<Item> generateTodayWantedFoods(LivingEntity living){
        int bestFoodLevel = 12;
        int maxWanted = 6;
        int minBestFood = 2;
        HashSet<Item> wantedFoods = new HashSet<>();
        List<Item> allFoods = BuiltInRegistries.ITEM.stream().filter(item -> item.getFoodProperties() != null && item.getFoodProperties().getNutrition() >= 4).toList();
        while(wantedFoods.size() < minBestFood){
            Item bestFood = allFoods.get(living.getRandom().nextInt(allFoods.size()));
            if(bestFood.getFoodProperties().getNutrition() >= bestFoodLevel){
                wantedFoods.add(bestFood);
            }
        }
        while(wantedFoods.size() < maxWanted){
            wantedFoods.add(allFoods.get(living.getRandom().nextInt(allFoods.size())));
        }
        return wantedFoods.stream().toList();
    }

    public static List<Item> getTodayWantedFoods(LivingEntity living){
        ArrayList<Item> foods = new ArrayList<>();
        if(living instanceof ServerPlayer player){
            CompoundTag data = player.getPersistentData();
            if(data.contains("SMCWantedFoods")){
                ListTag wantedFoodsTag = data.getList("SMCWantedFoods", 8);
                for(Tag id : wantedFoodsTag){
                    Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(id.getAsString()));
                    foods.add(item);
                }
            }
        }
        return foods;
    }

    public static List<Item> getTodayWantedFoodsFromItem(ItemStack stack){
        ArrayList<Item> foods = new ArrayList<>();
        CompoundTag data = stack.getOrCreateTag();
        if(data.contains("SMCWantedFoods")){
            ListTag wantedFoodsTag = data.getList("SMCWantedFoods", 8);
            for(Tag id : wantedFoodsTag){
                Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(id.getAsString()));
                foods.add(item);
            }
        }
        return foods;
    }

    public static List<Item> getFinishedWantedFoodsFromItem(ItemStack stack){
        ArrayList<Item> foods = new ArrayList<>();
        CompoundTag data = stack.getOrCreateTag();
        if(data.contains("SMCWantedFoodsConsumed")){
            ListTag wantedFoodsTag = data.getList("SMCWantedFoodsConsumed", 8);
            for(Tag id : wantedFoodsTag){
                Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(id.getAsString()));
                foods.add(item);
            }
        }
        return foods;
    }

    public void setTodayWantedFoods(LivingEntity living, ItemStack stack){
        if(living instanceof ServerPlayer player){
            List<Item> allWantedFoods = generateTodayWantedFoods(living);
            ListTag newList = new ListTag();
            for(Item item : allWantedFoods){
                String id = ForgeRegistries.ITEMS.getKey(item).toString();
                newList.add(StringTag.valueOf(id));
            }
            CompoundTag data = player.getPersistentData();
            data.put("SMCWantedFoods", newList);
            CompoundTag stackData = stack.getOrCreateTag();
            stackData.put("SMCWantedFoods", newList);
            stackData.put("SMCWantedFoodsConsumed", new ListTag());
        }
    }

    public boolean checkMenuIsCorrect(LivingEntity living, ItemStack stack){
        for(Item item : getTodayWantedFoodsFromItem(stack)){
            if(!getTodayWantedFoods(living).contains(item)){
                return false;
            }
        }
        return true;
    }

    public boolean getMenuIsCorrect(ItemStack stack){
        CompoundTag stackData = stack.getOrCreateTag();
        return stackData.getBoolean("SMCMenuIsCorrect");
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        String string = "tooltip.smc." + stack.getItem();
        tooltip.add(Component.translatable(string).withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.translatable(string + "_0").withStyle(ChatFormatting.BLUE));
        if (getMenuIsCorrect(stack)) {
            if (finishedAllWantedFoods(stack)) {
                tooltip.add(Component.translatable(string + "_5").withStyle(ChatFormatting.GREEN));
            }else{
                for(Item foodItem :getTodayWantedFoodsFromItem(stack)){
                    String text = foodItem.getDescription().getString();
                    ChatFormatting formatting;
                    if(foodItem.getFoodProperties().getNutrition() >= 12){
                        formatting = ChatFormatting.GOLD;
                    } else if(foodItem.getFoodProperties().getNutrition() >= 8){
                        formatting = ChatFormatting.DARK_GREEN;
                    }else{
                        formatting = ChatFormatting.GRAY;
                    }
                    if(wantedFoodFinished(stack, foodItem)){
                        tooltip.add(Component.literal(text).withStyle(formatting).withStyle(ChatFormatting.STRIKETHROUGH));
                    }else{
                        tooltip.add(Component.literal(text).withStyle(formatting));
                    }
                }
            }
        }
        else {
            tooltip.add(Component.translatable(string + "_4").withStyle(ChatFormatting.RED));
        }
    }
}
