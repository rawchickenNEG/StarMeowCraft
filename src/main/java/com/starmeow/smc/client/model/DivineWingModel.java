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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class DivineWingModel extends HumanoidModel<LivingEntity> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("smc", "divine_wing"), "main");
	private final ModelPart wings;
	private final ModelPart wing_r;
	private final ModelPart wing_l;

	public DivineWingModel(ModelPart root) {
		super(root);
		this.wings = root.getChild("wings");
		this.wing_r = this.wings.getChild("wing_r");
		this.wing_l = this.wings.getChild("wing_l");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.ZERO);

		PartDefinition wings = partdefinition.addOrReplaceChild("wings", CubeListBuilder.create(), PartPose.offset(-2.0F, 2.0F, 4.0F));

		PartDefinition wing_r = wings.addOrReplaceChild("wing_r", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = wing_r.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 26).addBox(-42.0F, -19.0F, -0.02F, 46.0F, 26.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-42.0F, -19.0F, 0.0F, 46.0F, 26.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(60, 67).addBox(-13.0F, -8.0F, -1.0F, 5.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 64).addBox(-13.0F, 1.0F, -1.0F, 14.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 52).addBox(-38.0F, -13.0F, -1.0F, 30.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 59).addBox(-38.0F, -16.0F, -1.0F, 30.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(32, 64).addBox(-8.0F, -3.0F, -1.0F, 12.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(64, 52).addBox(-8.0F, -16.0F, -1.0F, 4.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.3816F, 2.3375F, 0.1948F, 0.0F, 0.0F, -0.48F));

		PartDefinition wing_l = wings.addOrReplaceChild("wing_l", CubeListBuilder.create(), PartPose.offset(4.0F, 0.0F, 0.0F));

		PartDefinition cube_r2 = wing_l.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 26).mirror().addBox(-4.0F, -19.0F, -0.02F, 46.0F, 26.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 0).mirror().addBox(-4.0F, -19.0F, 0.0F, 46.0F, 26.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(60, 67).mirror().addBox(8.0F, -8.0F, -1.0F, 5.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 64).mirror().addBox(-1.0F, 1.0F, -1.0F, 14.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 52).mirror().addBox(8.0F, -13.0F, -1.0F, 30.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 59).mirror().addBox(8.0F, -16.0F, -1.0F, 30.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(32, 64).mirror().addBox(-4.0F, -3.0F, -1.0F, 12.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(64, 52).mirror().addBox(4.0F, -16.0F, -1.0F, 4.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.3816F, 2.3375F, 0.1948F, 0.0F, 0.0F, 0.48F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		boolean isFlying = entity instanceof Player p && (p.getAbilities().flying || p.fallDistance > 2);

		float time = ageInTicks * (isFlying ? 0.4F : 0.12F);
		float flap = Mth.sin(time);
		this.wing_r.yRot = (0.5F + flap * (isFlying ? 0.7F : 0.15F));
		this.wing_l.yRot = -(0.5F + flap * (isFlying ? 0.7F : 0.15F));
		if(entity instanceof Player player && player.isCrouching()){
			this.wing_r.yRot = (1.2F);
			this.wing_l.yRot = -(1.2F);
		}
	}


	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		wings.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}