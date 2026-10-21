package com.starmeow.smc.items.curiosequipable;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.starmeow.smc.init.ItemRegistry;
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
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class WantedList extends Item implements ICurioItem {
    public WantedList(Properties p_41383_) {
        super(p_41383_);
    }

    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        CompoundTag stackData = stack.getOrCreateTag();
        stackData.putBoolean("SMCMenuIsCorrect", checkMenuIsCorrect(slotContext.entity(), stack));
        if(getTodayWantedMobs(slotContext.entity()).isEmpty()){
            ListTag newList = new ListTag();
            stackData.put("SMCWantedMobsConsumed", newList);
            this.setTodayWantedMobs(slotContext.entity(), stack);
            System.out.println(getTodayWantedMobs(slotContext.entity()));
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if(slotContext.entity() instanceof ServerPlayer player){
            CompoundTag data = player.getPersistentData();

            long currentDay = player.level().getDayTime() / 24000L;
            long lastRefreshDay = data.getLong("SMCMenuDay");

            if (currentDay != lastRefreshDay) {
                if(finishedAllWantedMobs(stack)){
                    ItemStack reward = new ItemStack(ItemRegistry.REWARD_BAG.get());
                    if (!player.getInventory().add(reward)) {
                        player.drop(reward, false);
                    }
                    player.sendSystemMessage(Component.translatable("message.smc.wanted_list_1").withStyle(ChatFormatting.GREEN));
                }else{
                    player.sendSystemMessage(Component.translatable("message.smc.wanted_list").withStyle(ChatFormatting.GREEN));
                }

                setTodayWantedMobs(player, stack);
                data.putLong("SMCMenuDay", currentDay);
                CompoundTag stackData = stack.getOrCreateTag();
                stackData.putBoolean("SMCMenuIsCorrect", checkMenuIsCorrect(slotContext.entity(), stack));
                player.playNotifySound(SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 1.0F, 1.0F);
            }

            if(finishedAllWantedMobs(stack)){
            }
        }
    }

    public static boolean finishedAllWantedMobs(ItemStack stack){
        List<EntityType<?>> wantedMobs = getTodayWantedMobsFromItem(stack);
        List<EntityType<?>> finishedMobs = getFinishedWantedMobsFromItem(stack);
        for(EntityType<?> entityType : wantedMobs){
            if(!finishedMobs.contains(entityType)) return false;
        }
        return true;
    }

    public static boolean wantedMobFinished(ItemStack stack, EntityType<?> entityType){
        List<EntityType<?>> finishedMobs = getFinishedWantedMobsFromItem(stack);
        return finishedMobs.contains(entityType);
    }

    public static void setWantedMobFinished(ItemStack stack, EntityType<?> entityType){
        CompoundTag data = stack.getOrCreateTag();
        ListTag wantedMobsTag = data.getList("SMCWantedMobsConsumed", 8);
        StringTag tag = StringTag.valueOf(ForgeRegistries.ENTITY_TYPES.getKey(entityType).toString());
        if(!wantedMobsTag.contains(tag)){
            wantedMobsTag.add(tag);
            data.put("SMCWantedMobsConsumed", wantedMobsTag);
        }
    }

    public List<EntityType<?>> generateTodayWantedList(LivingEntity living){
        int maxWanted = 6;
        HashSet<EntityType<?>> wantedMobs = new HashSet<>();
        List<EntityType<?>> allMobs = BuiltInRegistries.ENTITY_TYPE.stream().filter(entity ->
                entity.getCategory() == MobCategory.MONSTER
        ).toList();
        while(wantedMobs.size() < maxWanted){
            wantedMobs.add(allMobs.get(living.getRandom().nextInt(allMobs.size())));
        }
        return wantedMobs.stream().toList();
    }

    public static List<EntityType<?>> getTodayWantedMobs(LivingEntity living){
        ArrayList<EntityType<?>> mobs = new ArrayList<>();
        if(living instanceof ServerPlayer player){
            CompoundTag data = player.getPersistentData();
            if(data.contains("SMCWantedMobs")){
                ListTag wantedMobsTag = data.getList("SMCWantedMobs", 8);
                for(Tag id : wantedMobsTag){
                    EntityType<?> entityType = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(id.getAsString()));
                    mobs.add(entityType);
                }
            }
        }
        return mobs;
    }

    public static List<EntityType<?>> getTodayWantedMobsFromItem(ItemStack stack){
        ArrayList<EntityType<?>> mobs = new ArrayList<>();
        CompoundTag data = stack.getOrCreateTag();
        if(data.contains("SMCWantedMobs")){
            ListTag wantedMobsTag = data.getList("SMCWantedMobs", 8);
            for(Tag id : wantedMobsTag){
                EntityType<?> entityType = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(id.getAsString()));
                mobs.add(entityType);
            }
        }
        return mobs;
    }

    public static List<EntityType<?>> getFinishedWantedMobsFromItem(ItemStack stack){
        ArrayList<EntityType<?>> mobs = new ArrayList<>();
        CompoundTag data = stack.getOrCreateTag();
        if(data.contains("SMCWantedMobsConsumed")){
            ListTag wantedMobsTag = data.getList("SMCWantedMobsConsumed", 8);
            for(Tag id : wantedMobsTag){
                EntityType<?> entityType = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(id.getAsString()));
                mobs.add(entityType);
            }
        }
        return mobs;
    }

    public void setTodayWantedMobs(LivingEntity living, ItemStack stack){
        if(living instanceof ServerPlayer player){
            List<EntityType<?>> allWantedMobs = generateTodayWantedList(living);
            ListTag newList = new ListTag();
            for(EntityType<?> entityType : allWantedMobs){
                String id = ForgeRegistries.ENTITY_TYPES.getKey(entityType).toString();
                newList.add(StringTag.valueOf(id));
            }
            CompoundTag data = player.getPersistentData();
            data.put("SMCWantedMobs", newList);
            CompoundTag stackData = stack.getOrCreateTag();
            stackData.put("SMCWantedMobs", newList);
            stackData.put("SMCWantedMobsConsumed", new ListTag());
        }
    }

    public boolean checkMenuIsCorrect(LivingEntity living, ItemStack stack){
        for(EntityType<?> entityType : getTodayWantedMobsFromItem(stack)){
            if(!getTodayWantedMobs(living).contains(entityType)){
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
            if (finishedAllWantedMobs(stack)) {
                tooltip.add(Component.translatable(string + "_5").withStyle(ChatFormatting.GREEN));
            }
            for(EntityType<?> entityType :getTodayWantedMobsFromItem(stack)){
                String text = entityType.getDescription().getString();
                if(wantedMobFinished(stack, entityType)){
                    tooltip.add(Component.literal(text).withStyle(ChatFormatting.DARK_RED).withStyle(ChatFormatting.STRIKETHROUGH));
                }else{
                    tooltip.add(Component.literal(text).withStyle(ChatFormatting.RED));
                }
            }
        }
        else {
            tooltip.add(Component.translatable(string + "_4").withStyle(ChatFormatting.RED));
        }
    }
}
