package com.starmeow.smc.mixin;

import com.starmeow.smc.helper.ItemHelper;
import com.starmeow.smc.init.BlockRegistry;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SmithingMenu.class)
public class SmithingMenuMixin {
    @Inject(method = "isValidBlock", at = @At("HEAD"), cancellable = true)
    public void smc$specialSmithingTable(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (state.is(BlockRegistry.ANCIENT_SMITHING_TABLE.get())) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "shrinkStackInSlot", at = @At("HEAD"), cancellable = true)
    public void smc$specialSmithingTableShrinkItem(int slot, CallbackInfo ci) {
        SmithingMenu menu = (SmithingMenu)(Object)this;
        menu.access.execute((p_150479_, p_150480_) -> {
            BlockState blockstate = p_150479_.getBlockState(p_150480_);
            if (blockstate.is(BlockRegistry.ANCIENT_SMITHING_TABLE.get())) {
                ItemStack item = menu.inputSlots.getItem(slot);
                if (!item.isEmpty() && ItemHelper.isTemplateItem(item) ) {
                    ci.cancel();
                }
            }
        });
    }
}
