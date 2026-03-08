package com.starmeow.smc.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

@OnlyIn(Dist.CLIENT)
public class SpecialItemRenderers {
    public static final BlockEntityWithoutLevelRenderer BEWLR =
            new DisguiseBEWLR(Minecraft.getInstance().getBlockEntityRenderDispatcher(),
                    Minecraft.getInstance().getEntityModels());

    private static class DisguiseBEWLR extends BlockEntityWithoutLevelRenderer {
        public DisguiseBEWLR(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet) {
            super(dispatcher, modelSet);
        }

        @Override
        public void renderByItem(ItemStack stack, ItemDisplayContext ctx, PoseStack poseStack,
                                 MultiBufferSource buffer, int light, int overlay) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.level == null) return;

            ItemStack skin = getSkinStack(stack);

            boolean leftHand =
                    (ctx == ItemDisplayContext.FIRST_PERSON_LEFT_HAND || ctx == ItemDisplayContext.THIRD_PERSON_LEFT_HAND);

            BakedModel model = mc.getItemRenderer().getModel(skin, mc.level, null, 0);

            poseStack.pushPose();

            poseStack.translate(0.5F, 0.5F, 0.5F);
            mc.getItemRenderer().render(skin, ctx, leftHand, poseStack, buffer, light, overlay, model);

            poseStack.popPose();
        }

        private ItemStack getSkinStack(ItemStack original) {
            if (original.hasTag()) {
                String s = original.getTag().getString("SMCWeaponSkin");
                ResourceLocation id = ResourceLocation.tryParse(s);
                if (id != null) {
                    Item item = ForgeRegistries.ITEMS.getValue(id);
                    if (item != null && item != Items.AIR) {
                        return new ItemStack(item);
                    }
                }
            }
            return new ItemStack(Items.BARRIER);
        }
    }
}
