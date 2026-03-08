package com.starmeow.smc.mixin;

import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ChestBoat.class)
public abstract class MixinChestBoat extends Boat
{
    //From fireproofboats
    public MixinChestBoat(EntityType<? extends Boat> type, Level level) {
        super(type, level);
    }

    @Inject(at = {@At(value = "RETURN")}, method = {"getDropItem"}, cancellable = true)
    public void getDropItem(CallbackInfoReturnable<Item> cir) {
        if (this.getVariant()==Boat.Type.byName("golden")) {
            cir.setReturnValue(ItemRegistry.GOLDEN_CHEST_BOAT.get());
        }
        if (this.getVariant()==Boat.Type.byName("end")) {
            cir.setReturnValue(ItemRegistry.END_CHEST_BOAT.get());
        }
    }
}