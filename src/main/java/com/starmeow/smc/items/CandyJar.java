package com.starmeow.smc.items;

import com.starmeow.smc.helper.PlayerHelper;
import com.starmeow.smc.init.EnchantmentRegistry;
import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class CandyJar extends Item {

    public CandyJar(Properties p_41383_) {
        super(p_41383_);
    }

    public static Item getRandomFood(RandomSource random){
        List<Item> foods = List.of(ItemRegistry.FROST_CANDY.get(),ItemRegistry.STAR_CANDY.get(),ItemRegistry.BROCCOLI_CANDY.get());
        return foods.get(random.nextInt(foods.size()));
    }

    public boolean overrideStackedOnOther(ItemStack itemStack, Slot p_150734_, ClickAction p_150735_, Player p_150736_) {
        if (itemStack.getCount() == 1 && p_150735_ == ClickAction.SECONDARY) {
            ItemStack food = p_150734_.getItem();
            CompoundTag tag = itemStack.getOrCreateTag();
            int stored = tag.getInt("SMCFoodBagStored");
            if (food.getItem().isEdible() && food.getFoodProperties(p_150736_).getNutrition() > 0) {
                int nutrition = food.getFoodProperties(p_150736_).getNutrition() * food.getCount();
                p_150734_.safeTake(food.getCount(), 64, p_150736_);
                p_150736_.level().playSound(p_150736_, p_150736_.getOnPos(), SoundEvents.BOTTLE_FILL_DRAGONBREATH, SoundSource.PLAYERS);
                tag.putInt("SMCFoodBagStored", stored + nutrition);
            } else if(food.isEmpty()){
                Item exportFood = getRandomFood(p_150736_.getRandom());
                int n = 0;
                int baseCost = exportFood.getDefaultInstance().getFoodProperties(p_150736_).getNutrition();
                int totalCost = 0;
                for(int i = 0; i < 64; i++){
                    if(stored >= totalCost + baseCost){
                        totalCost += baseCost;
                        n++;
                    } else {
                        break;
                    }
                }
                p_150734_.safeInsert(new ItemStack(exportFood, n));
                p_150736_.level().playSound(p_150736_, p_150736_.getOnPos(), SoundEvents.BOTTLE_EMPTY, SoundSource.PLAYERS);
                if(n >= itemStack.getMaxDamage() - itemStack.getDamageValue()){
                    p_150736_.level().playSound(p_150736_, p_150736_.getOnPos(), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS);
                }
                itemStack.hurtAndBreak(n, p_150736_, (p_40665_) -> {
                });
                tag.putInt("SMCFoodBagStored", stored - totalCost);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);
        if(level.getGameTime() % 20L != 0L) return;
        if (entity instanceof Player player && !level.isClientSide) {
            CompoundTag tag = stack.getOrCreateTag();
            int stored = tag.getInt("SMCFoodBagStored");
            if(PlayerHelper.unModifiedNeedsFood(player) && stored > 0){
                player.getFoodData().eat(1, 0.5f);
                if(stack.getMaxDamage() - stack.getDamageValue() <= 0){
                    level.playSound(player, player.getOnPos(), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS);
                }
                stack.hurtAndBreak(1, player, (p_40665_) -> {
                });
                tag.putInt("SMCFoodBagStored", stored - 1);
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_150760_, Player player, InteractionHand p_150762_) {
        ItemStack stack = player.getItemInHand(p_150762_);
        CompoundTag tag = stack.getOrCreateTag();
        int stored = tag.getInt("SMCFoodBagStored");
        ItemStack exportFood = new ItemStack(getRandomFood(player.getRandom()));
        int cost = exportFood.getFoodProperties(player).getNutrition();
        if (stored >= cost) {
            player.level().playSound(player, player.getOnPos(), SoundEvents.BOTTLE_EMPTY, SoundSource.PLAYERS);
            if(stack.getMaxDamage() - stack.getDamageValue() <= 0){
                player.level().playSound(player, player.getOnPos(), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS);
            }
            player.drop(exportFood, true);
            stack.hurtAndBreak(1, player, (p_40665_) -> {
            });
            tag.putInt("SMCFoodBagStored", stored - cost);
            return InteractionResultHolder.sidedSuccess(stack, p_150760_.isClientSide());
        } else {
            return InteractionResultHolder.fail(stack);
        }
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return this.canApplyEnchantment(EnchantmentHelper.getEnchantments(stack).keySet().toArray(new Enchantment[0])) || super.isBookEnchantable(stack, book);
    }

    private boolean canApplyEnchantment(Enchantment... enchantments) {
        for (Enchantment enchantment : enchantments) {
            if (enchantment.category == EnchantmentCategory.BREAKABLE || enchantment.category == EnchantmentCategory.VANISHABLE)
                return true;
        }
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        CompoundTag tag = stack.getOrCreateTag();
        int stored = tag.getInt("SMCFoodBagStored");
        tooltip.add(Component.translatable("tooltip.smc.candy_jar").withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.translatable("tooltip.smc.candy_jar_1").withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.translatable("tooltip.smc.candy_jar_2").withStyle(ChatFormatting.RED));
        tooltip.add(Component.literal("🍬 x " + stored).withStyle(ChatFormatting.GOLD));
    }
}
