package com.starmeow.smc.client.renderer.item;

import com.starmeow.smc.client.model.DivineHaloModel;
import com.starmeow.smc.client.model.DivineWingModel;
import com.starmeow.smc.client.model.FoxTailModel;
import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class CustomArmorRenderProperties implements IClientItemExtensions {
    private static boolean init;
    public static DivineWingModel WING_MODEL;
    public static DivineHaloModel HALO_MODEL;
    public static FoxTailModel TAIL_MODEL;

    public CustomArmorRenderProperties() {
    }

    public static void initializeModels() {
        init = true;
        WING_MODEL = new DivineWingModel(Minecraft.getInstance().getEntityModels().bakeLayer(DivineWingModel.LAYER_LOCATION));
        HALO_MODEL = new DivineHaloModel(Minecraft.getInstance().getEntityModels().bakeLayer(DivineHaloModel.LAYER_LOCATION));
        TAIL_MODEL = new FoxTailModel(Minecraft.getInstance().getEntityModels().bakeLayer(FoxTailModel.LAYER_LOCATION));

    }

    public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
        if (!init) {
            initializeModels();
        }
        WING_MODEL.setupAnim(livingEntity, livingEntity.walkAnimation.position(), livingEntity.walkAnimation.speed(), livingEntity.tickCount + Minecraft.getInstance().getPartialTick(), 0, 0);
        if(itemStack.getTag() != null){
            String variant = itemStack.getTag().getString("SMCHaloVariant");
            if(variant.equals("frost") || variant.equals("meow") ||  variant.equals("broccoli") || variant.equals("apostle") || variant.equals("zinc")){
                HALO_MODEL.setupAnim(livingEntity, 1, 0, (livingEntity.tickCount + Minecraft.getInstance().getPartialTick()) / 80.0F, 0, 0);

            } else if(variant.equals("helicopter")){
                HALO_MODEL.setupAnim(livingEntity, 1, 0, livingEntity.tickCount + Minecraft.getInstance().getPartialTick(), 0, 0);
            }else{
                HALO_MODEL.setupAnim(livingEntity, 0, 0, 0, 0, 0);
            }
        }
        TAIL_MODEL.setupAnim(livingEntity, livingEntity.walkAnimation.position(), livingEntity.walkAnimation.speed(), livingEntity.tickCount + Minecraft.getInstance().getPartialTick(), 0, 0);

        Item item = itemStack.getItem();
        if (item == ItemRegistry.DIVINE_WING.get()) {
            return WING_MODEL;
        } else if (item == ItemRegistry.DIVINE_HALO.get() || item == ItemRegistry.DIVINE_SHARD.get() || item == ItemRegistry.CREATIVE_HALO.get()) {
            return HALO_MODEL;
        } else if (item == ItemRegistry.FOX_TAIL.get()) {
            return TAIL_MODEL;
        } else {
            return _default;
        }
    }
}
