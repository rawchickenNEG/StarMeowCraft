package com.starmeow.smc.client.model;

import com.starmeow.smc.StarMeowCraft;
import com.starmeow.smc.entities.Cloudian;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CloudianModel extends HierarchicalModel<Cloudian> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(StarMeowCraft.MODID, "cloudian"), "main");
    private static final float[] SPIKE_X_ROT = new float[]{1.75F, 0.25F, 0.0F, 0.0F, 0.5F, 0.5F, 0.5F, 0.5F, 1.25F, 0.75F, 0.0F, 0.0F};
    private static final float[] SPIKE_Y_ROT = new float[]{0.0F, 0.0F, 0.0F, 0.0F, 0.25F, 1.75F, 1.25F, 0.75F, 0.0F, 0.0F, 0.0F, 0.0F};
    private static final float[] SPIKE_Z_ROT = new float[]{0.0F, 0.0F, 0.25F, 1.75F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.75F, 1.25F};
    private static final float[] SPIKE_X = new float[]{0.0F, 0.0F, 8.0F, -8.0F, -8.0F, 8.0F, 8.0F, -8.0F, 0.0F, 0.0F, 8.0F, -8.0F};
    private static final float[] SPIKE_Y = new float[]{-8.0F, -8.0F, -8.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 8.0F, 8.0F};
    private static final float[] SPIKE_Z = new float[]{8.0F, -8.0F, 0.0F, 0.0F, -8.0F, -8.0F, 8.0F, 8.0F, 8.0F, -8.0F, 0.0F, 0.0F};
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart eye;
    private final ModelPart[] spikeParts;
    private final ModelPart[] tailParts;

    public CloudianModel(ModelPart p_170600_) {
        this.root = p_170600_;
        this.spikeParts = new ModelPart[12];
        this.head = p_170600_.getChild("head");

        for(int $$1 = 0; $$1 < this.spikeParts.length; ++$$1) {
            this.spikeParts[$$1] = this.head.getChild(createSpikeName($$1));
        }

        this.eye = this.head.getChild("eye");
        this.tailParts = new ModelPart[3];
        this.tailParts[0] = this.head.getChild("tail0");
        this.tailParts[1] = this.tailParts[0].getChild("tail1");
        this.tailParts[2] = this.tailParts[1].getChild("tail2");
    }

    private static String createSpikeName(int p_170603_) {
        return "spike" + p_170603_;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        PartDefinition $$2 = $$1.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, 10.0F, -8.0F, 12.0F, 12.0F, 16.0F).texOffs(0, 28).addBox(-8.0F, 10.0F, -6.0F, 2.0F, 12.0F, 12.0F).texOffs(0, 28).addBox(6.0F, 10.0F, -6.0F, 2.0F, 12.0F, 12.0F, true).texOffs(16, 40).addBox(-6.0F, 8.0F, -6.0F, 12.0F, 2.0F, 12.0F).texOffs(16, 40).addBox(-6.0F, 22.0F, -6.0F, 12.0F, 2.0F, 12.0F), PartPose.ZERO);
        CubeListBuilder $$3 = CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -4.5F, -1.0F, 2.0F, 9.0F, 2.0F);

        for(int $$4 = 0; $$4 < 12; ++$$4) {
            float $$5 = getSpikeX($$4, 0.0F, 0.0F);
            float $$6 = getSpikeY($$4, 0.0F, 0.0F);
            float $$7 = getSpikeZ($$4, 0.0F, 0.0F);
            float $$8 = 3.1415927F * SPIKE_X_ROT[$$4];
            float $$9 = 3.1415927F * SPIKE_Y_ROT[$$4];
            float $$10 = 3.1415927F * SPIKE_Z_ROT[$$4];
            $$2.addOrReplaceChild(createSpikeName($$4), $$3, PartPose.offsetAndRotation($$5, $$6, $$7, $$8, $$9, $$10));
        }

        $$2.addOrReplaceChild("eye", CubeListBuilder.create().texOffs(8, 0).addBox(-1.0F, 15.0F, 0.0F, 2.0F, 2.0F, 1.0F), PartPose.offset(0.0F, 0.0F, -8.25F));
        PartDefinition $$11 = $$2.addOrReplaceChild("tail0", CubeListBuilder.create().texOffs(40, 0).addBox(-2.0F, 14.0F, 7.0F, 4.0F, 4.0F, 8.0F), PartPose.ZERO);
        PartDefinition $$12 = $$11.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 54).addBox(0.0F, 14.0F, 0.0F, 3.0F, 3.0F, 7.0F), PartPose.offset(-1.5F, 0.5F, 14.0F));
        $$12.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(41, 32).addBox(0.0F, 14.0F, 0.0F, 2.0F, 2.0F, 6.0F).texOffs(25, 19).addBox(1.0F, 10.5F, 3.0F, 1.0F, 9.0F, 9.0F), PartPose.offset(0.5F, 0.5F, 6.0F));
        return LayerDefinition.create($$0, 64, 64);
    }

    public ModelPart root() {
        return this.root;
    }

    public void setupAnim(Cloudian p_102719_, float p_102720_, float p_102721_, float p_102722_, float p_102723_, float p_102724_) {
        float $$6 = p_102722_ - (float)p_102719_.tickCount;
        this.head.yRot = p_102723_ * 0.017453292F;
        this.head.xRot = p_102724_ * 0.017453292F;
        float $$7 = (1.0F - p_102719_.getSpikesAnimation($$6)) * 0.55F;
        this.setupSpikes(p_102722_, $$7);
        Entity $$8 = Minecraft.getInstance().getCameraEntity();
        if (p_102719_.hasActiveAttackTarget()) {
            $$8 = p_102719_.getActiveAttackTarget();
        }

        if ($$8 != null) {
            Vec3 $$9 = ((Entity)$$8).getEyePosition(0.0F);
            Vec3 $$10 = p_102719_.getEyePosition(0.0F);
            double $$11 = $$9.y - $$10.y;
            if ($$11 > 0.0) {
                this.eye.y = 0.0F;
            } else {
                this.eye.y = 1.0F;
            }

            Vec3 $$12 = p_102719_.getViewVector(0.0F);
            $$12 = new Vec3($$12.x, 0.0, $$12.z);
            Vec3 $$13 = (new Vec3($$10.x - $$9.x, 0.0, $$10.z - $$9.z)).normalize().yRot(1.5707964F);
            double $$14 = $$12.dot($$13);
            this.eye.x = Mth.sqrt((float)Math.abs($$14)) * 2.0F * (float)Math.signum($$14);
        }

        this.eye.visible = true;
        float $$15 = p_102719_.getTailAnimation($$6);
        this.tailParts[0].yRot = Mth.sin($$15) * 3.1415927F * 0.05F;
        this.tailParts[1].yRot = Mth.sin($$15) * 3.1415927F * 0.1F;
        this.tailParts[2].yRot = Mth.sin($$15) * 3.1415927F * 0.15F;
    }

    private void setupSpikes(float p_102709_, float p_102710_) {
        for(int $$2 = 0; $$2 < 12; ++$$2) {
            this.spikeParts[$$2].x = getSpikeX($$2, p_102709_, p_102710_);
            this.spikeParts[$$2].y = getSpikeY($$2, p_102709_, p_102710_);
            this.spikeParts[$$2].z = getSpikeZ($$2, p_102709_, p_102710_);
        }

    }

    private static float getSpikeOffset(int p_170605_, float p_170606_, float p_170607_) {
        return 1.0F + Mth.cos(p_170606_ * 1.5F + (float)p_170605_) * 0.01F - p_170607_;
    }

    private static float getSpikeX(int p_170610_, float p_170611_, float p_170612_) {
        return SPIKE_X[p_170610_] * getSpikeOffset(p_170610_, p_170611_, p_170612_);
    }

    private static float getSpikeY(int p_170614_, float p_170615_, float p_170616_) {
        return 16.0F + SPIKE_Y[p_170614_] * getSpikeOffset(p_170614_, p_170615_, p_170616_);
    }

    private static float getSpikeZ(int p_170618_, float p_170619_, float p_170620_) {
        return SPIKE_Z[p_170618_] * getSpikeOffset(p_170618_, p_170619_, p_170620_);
    }
}
