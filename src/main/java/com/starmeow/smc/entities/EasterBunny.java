package com.starmeow.smc.entities;

import com.starmeow.smc.config.Config;
import com.starmeow.smc.init.EntityTypeRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;
import java.util.List;

public class EasterBunny extends Rabbit {

    public int eggTime;

    public EasterBunny(EntityType<? extends Rabbit> p_29656_, Level p_29657_) {
        super(p_29656_, p_29657_);
        this.eggTime = this.random.nextInt(6000) + 6000;
    }

    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide && this.isAlive() && !this.isBaby() && --this.eggTime <= 0) {
            this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);

            List<Item> items = Config.easterBunnyEggItems;
            Item randomItem = items.get(this.random.nextInt(items.size()));
            this.spawnAtLocation(randomItem);

            this.gameEvent(GameEvent.ENTITY_PLACE);
            this.eggTime = this.random.nextInt(6000) + 6000;
        }
    }

    @Nullable
    public EasterBunny getBreedOffspring(ServerLevel p_149035_, AgeableMob p_149036_) {
        EasterBunny rabbit = EntityTypeRegistry.EASTER_BUNNY.get().create(p_149035_);
        if (rabbit != null) {
            rabbit.setVariant(Variant.BROWN);
        }
        return rabbit;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_29678_, DifficultyInstance p_29679_, MobSpawnType p_29680_, @Nullable SpawnGroupData p_29681_, @Nullable CompoundTag p_29682_) {
        this.setVariant(Variant.BROWN);
        return super.finalizeSpawn(p_29678_, p_29679_, p_29680_, (SpawnGroupData)p_29681_, p_29682_);
    }
}
