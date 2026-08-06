package com.starmeow.smc.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.starmeow.smc.client.model.DivineHaloModel;
import com.starmeow.smc.client.model.DivineWingModel;
import com.starmeow.smc.items.curiosequipable.DivineHalo;
import com.starmeow.smc.items.curiosequipable.DivineWing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

@OnlyIn(Dist.CLIENT)
public class DivineHaloCuriosRenderer implements ICurioRenderer {

    private final DivineHaloModel model;

    public DivineHaloCuriosRenderer() {
        this.model = new DivineHaloModel(Minecraft.getInstance().getEntityModels().bakeLayer(DivineHaloModel.LAYER_LOCATION));
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack,
                                                                          SlotContext slotContext,
                                                                          PoseStack matrixStack,
                                                                          RenderLayerParent<T, M> renderLayerParent,
                                                                          MultiBufferSource renderTypeBuffer,
                                                                          int light, float limbSwing,
                                                                          float limbSwingAmount,
                                                                          float partialTicks,
                                                                          float ageInTicks,
                                                                          float netHeadYaw,
                                                                          float headPitch) {

        LivingEntity entity = slotContext.entity();

        ICurioRenderer.followBodyRotations(entity, model);
        //model.copyPropertiesTo((HumanoidModel<LivingEntity>) renderLayerParent.getModel());
        matrixStack.pushPose();
        if(stack.getTag() != null){
            String variant = stack.getTag().getString("SMCHaloVariant");
            if(variant.equals("frost") || variant.equals("meow") ||  variant.equals("broccoli") || variant.equals("apostle") || variant.equals("zinc")){
                model.setupAnim(entity, 1, 0, (entity.tickCount + Minecraft.getInstance().getPartialTick()) / 80.0F, netHeadYaw, headPitch);

            } else if(variant.equals("helicopter")){
                model.setupAnim(entity, 1, 0, entity.tickCount + Minecraft.getInstance().getPartialTick(), netHeadYaw, headPitch);
            }else{
                model.setupAnim(entity, 0, 0, 0, netHeadYaw, headPitch);
            }
        }
        //model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        String haloTexture = DivineHalo.haloTexture(stack);
        VertexConsumer vertexConsumer = renderTypeBuffer.getBuffer(
                RenderType.entityCutoutNoCull(new ResourceLocation(haloTexture))
        );
        model.renderToBuffer(matrixStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.popPose();
    }
}