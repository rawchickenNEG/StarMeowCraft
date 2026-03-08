package com.starmeow.smc.blocks;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;

import java.util.Collections;
import java.util.List;

public class CommonBlocks extends Block {
    public CommonBlocks(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        return Collections.singletonList(new ItemStack(this, 1));
    }

}
