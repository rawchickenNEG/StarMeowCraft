package com.starmeow.smc.mixin;

import com.starmeow.smc.helper.EntityHelper;
import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.extensions.IForgeBoat;
import net.minecraftforge.fluids.FluidType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Boat.class)
public abstract class MixinBoat extends Entity implements IForgeBoat
{
    //From fireproofboats
    @Unique
    private static final Boat.Type GOLDEN = Boat.Type.byName("golden");
    @Unique
    private static final Boat.Type GLASS_GOLDEN = Boat.Type.byName("glass_golden");
    @Unique
    private static final Boat.Type END = Boat.Type.byName("end");
    @Shadow
    public abstract Boat.Type getVariant();

    public MixinBoat(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Override
    public boolean fireImmune() {
        return EntityHelper.isFireProofBoat(this.getVariant()) || super.fireImmune();
    }

    @Override
    public boolean shouldUpdateFluidWhileRiding(FluidState state, Entity rider) {
        Boat boat = (Boat) (Object) this;
        return EntityHelper.isFireProofBoat(this.getVariant()) && state.shouldUpdateWhileBoating(boat, rider);
    }

    @Inject(at = {@At(value = "RETURN")}, method = {"getDropItem"}, cancellable = true)
    public void getDropItem(CallbackInfoReturnable<Item> cir) {
        if (this.getVariant() == GOLDEN) {
            cir.setReturnValue(ItemRegistry.GOLDEN_BOAT.get());
        }
        if (this.getVariant() == GLASS_GOLDEN) {
            cir.setReturnValue(ItemRegistry.GOLDEN_TRANSPARENT_BOAT.get());
        }
        if (this.getVariant() == END) {
            cir.setReturnValue(ItemRegistry.END_BOAT.get());
        }
    }

    @Override
    public boolean canBoatInFluid(FluidState state) {
        Boat boat = (Boat) (Object) this;
        return state.supportsBoating(boat) || (state.getFluidType().equals(Fluids.LAVA.getFluidType()) && EntityHelper.isFireProofBoat(this.getVariant()));
    }

    @Override
    public boolean canBoatInFluid(FluidType type) {
        Boat boat = (Boat) (Object) this;
        return type.supportsBoating(boat) || (type.equals(Fluids.LAVA.getFluidType()) && EntityHelper.isFireProofBoat(this.getVariant()));
    }

    @Inject(
            method = "tick()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/vehicle/Boat;floatBoat()V",
                    shift = At.Shift.AFTER
            )
    )
    private void smc$sinkInWaterAfterFloat(CallbackInfo ci) {
        Boat self = (Boat)(Object)this;

        if (!EntityHelper.isFireProofBoat(self.getVariant())) return;
        if (!self.isInWater() || self.isInLava()) return;

        final double sinkAccel = 0.08D;
        final double maxDown   = -0.35D;

        Vec3 v = self.getDeltaMovement();
        double newY = Math.max(v.y - sinkAccel, maxDown);
        self.setDeltaMovement(v.x, newY, v.z);
    }

    @Inject(
            method = "tick()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/vehicle/Boat;floatBoat()V",
                    shift = At.Shift.AFTER
            )
    )
    private void smc$floatingEndBoat(CallbackInfo ci) {
        Boat self = (Boat)(Object)this;
        if (self.getVariant() != END) return;

        self.setNoGravity(true);

        double yMovement = 0.0;

        if (self.isControlledByLocalInstance() && self.level().isClientSide) {
            LocalPlayer p = Minecraft.getInstance().player;
            if (p != null && self.getControllingPassenger() == p) {
                boolean jumping = Minecraft.getInstance().options.keyJump.isDown();
                boolean shift   = Minecraft.getInstance().options.keyShift.isDown();

                if (jumping && !shift) yMovement = 0.1;
                else if (shift && !jumping) yMovement = -0.1;
            }
        }

        Vec3 v = self.getDeltaMovement();
        self.setDeltaMovement(v.x, Mth.clamp(v.y + yMovement, -0.5f, 0.5f) , v.z);
        if(yMovement == 0){
            self.setDeltaMovement(v.multiply(1.0, 0.75f, 1.0));
        }
    }
}
