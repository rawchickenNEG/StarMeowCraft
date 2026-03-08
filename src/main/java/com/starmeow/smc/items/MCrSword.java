package com.starmeow.smc.items;

import com.starmeow.smc.helper.ItemHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

import java.util.Map;

public class MCrSword extends SwordItem {
    public MCrSword(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
    }

    @Override
    public boolean hurtEnemy(ItemStack p_43278_, LivingEntity p_43279_, LivingEntity p_43280_) {
        boolean result = super.hurtEnemy(p_43278_, p_43279_, p_43280_);
        if (result) {
            Level level = p_43279_.level();
            if(p_43280_ instanceof Player player){
                p_43279_.hurt(level.damageSources().playerAttack(player), 99999);
                p_43279_.invulnerableTime = 0;
            }
            if(level instanceof ServerLevel serverLevel){
                Registry<DamageType> reg = serverLevel.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE);
                for (Map.Entry<ResourceKey<DamageType>, DamageType> entry : reg.entrySet()) {
                    Holder.Reference<DamageType> holder = reg.getHolderOrThrow(entry.getKey());
                    DamageSource src = new DamageSource(holder);
                    p_43279_.invulnerableTime = 0;
                    p_43279_.hurt(src, 99999);
                }
            }
        }
        return result;



    }

    @Override
    public Component getName(ItemStack stack) {
        return ItemHelper.rainbowColor(super.getName(stack), (long)(System.currentTimeMillis() * 5), 0.5);
    }
}
