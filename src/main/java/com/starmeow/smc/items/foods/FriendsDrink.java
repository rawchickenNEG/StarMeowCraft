package com.starmeow.smc.items.foods;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;

public class FriendsDrink extends Item {
    public FriendsDrink(Item.Properties p_42979_) {
        super(p_42979_);
    }

    public UseAnim getUseAnimation(ItemStack p_41358_) {
        return UseAnim.DRINK;
    }
}