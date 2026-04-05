package com.starmeow.smc.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.starmeow.smc.StarMeowCraft;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChickenHarvesterModel<T extends Entity> extends AgeableListModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(StarMeowCraft.MODID, "chicken_harvester"), "main");
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart right_leg;
    private final ModelPart left_leg;
    private final ModelPart right_wing;
    private final ModelPart left_wing;

    public ChickenHarvesterModel(ModelPart root) {
        this.root = root.getChild("root");
        this.head = this.root.getChild("head");
        this.body = this.root.getChild("body");
        this.right_leg = this.root.getChild("right_leg");
        this.left_leg = this.root.getChild("left_leg");
        this.right_wing = this.root.getChild("right_wing");
        this.left_wing = this.root.getChild("left_wing");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -5.0F, -2.0F, 4.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.0F, -4.0F));

        PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 9).mirror().addBox(0.317F, -14.0F, -4.4609F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 10.0F, 4.0F, 0.0F, -0.3927F, 0.0F));

        PartDefinition head_r2 = head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(0, 9).addBox(-2.317F, -14.0F, -4.4609F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, 4.0F, 0.0F, 0.3927F, 0.0F));

        PartDefinition head_r3 = head.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(0, 23).addBox(-2.5F, -17.5579F, -9.7773F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, 4.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition beak = head.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(14, 0).addBox(-2.0F, 2.0F, -4.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 0.0F));

        PartDefinition red_thing = head.addOrReplaceChild("red_thing", CubeListBuilder.create().texOffs(14, 4).addBox(-1.0F, 4.0F, -3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 9).addBox(-3.0F, -4.0F, 5.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(12, 23).addBox(-2.0F, -4.0F, 0.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 10.0F, 2.3562F, 0.0F, 0.0F));

        PartDefinition right_leg = root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -5.0F, 1.0F));

        PartDefinition left_leg = root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -5.0F, 1.0F));

        PartDefinition right_wing = root.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(24, 13).addBox(0.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -11.0F, 0.0F));

        PartDefinition left_wing = root.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(24, 13).mirror().addBox(-1.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(4.0F, -11.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    public void setupAnim(T p_102392_, float p_102393_, float p_102394_, float p_102395_, float p_102396_, float p_102397_) {
        this.head.xRot = p_102397_ * 0.017453292F;
        this.head.yRot = p_102396_ * 0.017453292F;
        this.right_leg.xRot = Mth.cos(p_102393_ * 0.6662F) * 1.4F * p_102394_;
        this.left_leg.xRot = Mth.cos(p_102393_ * 0.6662F + 3.1415927F) * 1.4F * p_102394_;
        this.right_wing.zRot = p_102395_;
        this.left_wing.zRot = -p_102395_;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.head);
    }

    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body, this.right_leg, this.left_leg, this.right_wing, this.left_wing);
    }

}
