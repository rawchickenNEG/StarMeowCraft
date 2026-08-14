package com.starmeow.smc.client.model;// Made with Blockbench 4.12.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class DivineHaloModel extends HumanoidModel<LivingEntity> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("smc", "divine_halo"), "main");
	private final ModelPart fixed;
	private final ModelPart head;
	private final ModelPart halo_part;
	private final ModelPart rot_down;
	private final ModelPart rot_up;

	public DivineHaloModel(ModelPart root) {
		super(root);
		this.head = root.getChild("head");
		this.fixed = this.head.getChild("halo");
		this.halo_part = this.fixed.getChild("halo_part");
		this.rot_down = this.halo_part.getChild("rot_down");
		this.rot_up = this.halo_part.getChild("rot_up");
		this.head.skipDraw = true;
	}

	public static LayerDefinition createArmorLayer() {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(new CubeDeformation(0.5F), 0.0F);
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition head = partdefinition.getChild("head");

		PartDefinition halo = head.addOrReplaceChild("halo", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition halo_part = halo.addOrReplaceChild("halo_part", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -37.6037F, 2.7083F, -0.2618F, 0.0F, 0.0F));

		PartDefinition rot_down = halo_part.addOrReplaceChild("rot_down", CubeListBuilder.create().texOffs(-32, 32).addBox(-16.0F, 0.0F, -16.0F, 32.0F, 0.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.9659F, -0.2588F));

		PartDefinition rot_up = halo_part.addOrReplaceChild("rot_up", CubeListBuilder.create().texOffs(-32, 0).addBox(-16.0F, 0.0F, -16.0F, 32.0F, 0.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setupAnim(entity, 0, 0, ageInTicks, netHeadYaw, headPitch);
		if(limbSwing != 0){
			this.rot_up.yRot = ageInTicks;
		}else{
			this.rot_up.yRot = 0;
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		head.render(poseStack, vertexConsumer, 0xF000F0, packedOverlay, red, green, blue, alpha);
	}
}