package com.starmeow.smc.items;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RainbowMilkshakeItem extends MilkshakeItem{
    public RainbowMilkshakeItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void applyMilkshakeEffect(Level level, LivingEntity living){
        List<MobEffectInstance> list = new ArrayList<>(living.getActiveEffects());
        List<MobEffectInstance> newEffects = new ArrayList<>();
        List<Integer> durationList = new ArrayList<>();
        List<Integer> ampifierList = new ArrayList<>();
        if(!list.isEmpty()){
            for(MobEffectInstance effectInstance : list){
                durationList.add(effectInstance.getDuration());
                ampifierList.add(effectInstance.getAmplifier());
            }
            Collections.shuffle(durationList);
            for(int i = 0; i < list.size(); ++i){
                newEffects.add(new MobEffectInstance(list.get(i).getEffect(), durationList.get(i), ampifierList.get(i)));
            }
            living.getActiveEffectsMap().clear();
            for(MobEffectInstance newEffect : newEffects){
                living.addEffect(newEffect);
            }
        }
    }
}
