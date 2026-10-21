package com.starmeow.smc.items.foods;

import com.starmeow.smc.items.TippedItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class WangqingBeefNoodles extends TippedItems {
    public WangqingBeefNoodles(Properties p_41383_) {
        super(p_41383_);
    }

    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity living) {
        if(living instanceof ServerPlayer player){
            PlayerAdvancements playerAdvancements = player.getAdvancements();
            for (Advancement advancement : player.server.getAdvancements().getAllAdvancements()) {
                DisplayInfo display = advancement.getDisplay();

                if (display == null) {
                    continue;
                }
                if (display.getFrame() != FrameType.CHALLENGE) {
                    continue;
                }

                AdvancementProgress progress = playerAdvancements.getOrStartProgress(advancement);

                List<String> completedCriteria = new ArrayList<>();
                progress.getCompletedCriteria().forEach(completedCriteria::add);

                for (String criterion : completedCriteria) {
                    playerAdvancements.revoke(advancement, criterion);
                }
            }
        }

        return living.eat(level, stack);
    }
}
