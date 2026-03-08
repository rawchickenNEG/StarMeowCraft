package com.starmeow.smc.init;

import com.starmeow.smc.StarMeowCraft;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.time.LocalDate;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class PropertyRegistry {
    @SubscribeEvent
    public static void propertyOverrideRegistry(FMLClientSetupEvent event) {
        ItemProperties.register(ItemRegistry.BROCCOLI_FISHING_ROD.get(), new ResourceLocation("cast"), (itemstack, level, entity, number) ->
        {if (!(entity instanceof Player)) return 0.0F;
            return ((Player) entity).fishing != null && (entity.getMainHandItem() == itemstack || entity.getOffhandItem() == itemstack) ? 1.0F : 0.0F;});
        ItemProperties.register(ItemRegistry.RAINBOW_FISHING_ROD.get(), new ResourceLocation("cast"), (itemstack, level, entity, number) ->
        {if (!(entity instanceof Player)) return 0.0F;
            return ((Player) entity).fishing != null && (entity.getMainHandItem() == itemstack || entity.getOffhandItem() == itemstack) ? 1.0F : 0.0F;});
        ItemProperties.register(ItemRegistry.SLINGSHOT.get(), new ResourceLocation("pull"), (p_174635_, p_174636_, p_174637_, p_174638_) -> {
            if (p_174637_ == null) {
                return 0.0F;
            } else {
                return p_174637_.getUseItem() != p_174635_ ? 0.0F : (float) (p_174635_.getUseDuration() - p_174637_.getUseItemRemainingTicks()) / 10.0F;
            }
        });
        ItemProperties.register(ItemRegistry.SMC_ICON.get(), new ResourceLocation("aprilfoolsday"), (itemstack, level, entity, number) ->
                LocalDate.now().toString().substring(5).equals("04-01") ? 1.0F : 0.0F);
        ItemProperties.register(ItemRegistry.CANDY_JAR.get(), new ResourceLocation("foodlevel"), (itemstack, level, entity, number) -> {
            CompoundTag tag = itemstack.getTag();
            return tag != null ? tag.getInt("SMCFoodBagStored") : 0.0F;
        });
        ItemProperties.register(ItemRegistry.FROSTIUM_BOW.get(), new ResourceLocation("pull"), (p_174635_, p_174636_, p_174637_, p_174638_) -> {
            if (p_174637_ == null) {
                return 0.0F;
            } else {
                return p_174637_.getUseItem() != p_174635_ ? 0.0F : (float) (p_174635_.getUseDuration() - p_174637_.getUseItemRemainingTicks()) / 20.0F;
            }
        });
        ItemProperties.register(ItemRegistry.FROSTIUM_BOW.get(), new ResourceLocation("pulling"), (itemstack, level, entity, number) ->
                entity != null && entity.isUsingItem() && entity.getUseItem() == itemstack ? 1.0F : 0.0F);
        ItemProperties.register(ItemRegistry.PERFROSTITE_BOW.get(), new ResourceLocation("pull"), (p_174635_, p_174636_, p_174637_, p_174638_) -> {
            if (p_174637_ == null) {
                return 0.0F;
            } else {
                return p_174637_.getUseItem() != p_174635_ ? 0.0F : (float) (p_174635_.getUseDuration() - p_174637_.getUseItemRemainingTicks()) / 20.0F;
            }
        });
        ItemProperties.register(ItemRegistry.PERFROSTITE_BOW.get(), new ResourceLocation("pulling"), (itemstack, level, entity, number) ->
                entity != null && entity.isUsingItem() && entity.getUseItem() == itemstack ? 1.0F : 0.0F);
        ItemProperties.register(ItemRegistry.RAINBOW_BOW.get(), new ResourceLocation("pull"), (p_174635_, p_174636_, p_174637_, p_174638_) -> {
            if (p_174637_ == null) {
                return 0.0F;
            } else {
                return p_174637_.getUseItem() != p_174635_ ? 0.0F : (float) (p_174635_.getUseDuration() - p_174637_.getUseItemRemainingTicks()) / 20.0F;
            }
        });
        ItemProperties.register(ItemRegistry.RAINBOW_BOW.get(), new ResourceLocation("pulling"), (itemstack, level, entity, number) ->
                entity != null && entity.isUsingItem() && entity.getUseItem() == itemstack ? 1.0F : 0.0F);
        ItemProperties.register(ItemRegistry.CHOP_SHIELD.get(), new ResourceLocation("blocking"), (itemstack, level, entity, number) ->
                entity != null && entity.isUsingItem() && entity.getUseItem() == itemstack ? 1.0F : 0.0F);
        ItemProperties.register(ItemRegistry.SWISS_ARMY_KNIFE.get(), new ResourceLocation("mode"), (itemstack, level, entity, number) ->
                itemstack.getOrCreateTag().getInt("SMCSwissKnife"));
        ItemProperties.register(ItemRegistry.DIAMOND_SWISS_ARMY_KNIFE.get(), new ResourceLocation("mode"), (itemstack, level, entity, number) ->
                itemstack.getOrCreateTag().getInt("SMCSwissKnife"));
        ItemProperties.register(ItemRegistry.NETHERITE_SWISS_ARMY_KNIFE.get(), new ResourceLocation("mode"), (itemstack, level, entity, number) ->
                itemstack.getOrCreateTag().getInt("SMCSwissKnife"));
        ItemProperties.register(ItemRegistry.DEVOUR_SWORD.get(), new ResourceLocation(StarMeowCraft.MODID, "disguised"), (itemstack, level, entity, number) ->
                itemstack.getTag() != null && itemstack.getTag().contains("SMCWeaponSkin") ? 1.0F : 0.0F
        );
    }

    @SubscribeEvent
    public static void addComposterItems(FMLCommonSetupEvent event) {
        ComposterBlock.COMPOSTABLES.put(ItemRegistry.BROCCOLI.get(), 0.65f);
        ComposterBlock.COMPOSTABLES.put(ItemRegistry.BROCCOLI_SEED.get(), 0.3f);
        ComposterBlock.COMPOSTABLES.put(ItemRegistry.PEA.get(), 0.3f);
        ComposterBlock.COMPOSTABLES.put(ItemRegistry.PEA_POD.get(), 0.65f);
    }

}
