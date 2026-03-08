package com.starmeow.smc.entities.projectiles;

import com.starmeow.smc.init.EntityTypeRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BroccoliFishingHook extends FishingHook {
    public BroccoliFishingHook(EntityType<? extends BroccoliFishingHook> entityType, Level level) {
        super(EntityTypeRegistry.BROCCOLI_FISHING_HOOK.get(), level);
    }

    public BroccoliFishingHook(Player player, Level level, int luck, int speed) {
        this(EntityTypeRegistry.BROCCOLI_FISHING_HOOK.get(), level);
        this.setOwner(player);
        this.moveTo(player.getX(), player.getEyeY() - 0.1D, player.getZ(), player.getYRot(), player.getXRot());
        this.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
        this.luck = luck;
        this.lureSpeed = speed;
        player.fishing = this;
    }

    private boolean shouldStopFishing(Player p_37137_) {
        ItemStack itemstack = p_37137_.getMainHandItem();
        ItemStack itemstack1 = p_37137_.getOffhandItem();
        boolean flag = itemstack.canPerformAction(ToolActions.FISHING_ROD_CAST);
        boolean flag1 = itemstack1.canPerformAction(ToolActions.FISHING_ROD_CAST);
        if (!p_37137_.isRemoved() && p_37137_.isAlive() && (flag || flag1) && !(this.distanceToSqr(p_37137_) > 1024.0)) {
            return false;
        } else {
            this.discard();
            return true;
        }
    }

    public int retrieve(ItemStack p_37157_) {
        Player player = this.getPlayerOwner();
        if (!this.level().isClientSide && player != null && !this.shouldStopFishing(player)) {
            int i = 0;
            ItemFishedEvent event = null;
            if (this.hookedIn != null) {
                this.pullEntity(this.hookedIn);
                CriteriaTriggers.FISHING_ROD_HOOKED.trigger((ServerPlayer)player, p_37157_, this, Collections.emptyList());
                this.level().broadcastEntityEvent(this, (byte)31);
                i = this.hookedIn instanceof ItemEntity ? 3 : 5;
            } else if (this.nibble > 0) {
                int luck = (int)(this.luck + player.getLuck());
                LootParams lootparams = (new LootParams.Builder((ServerLevel)this.level()))
                        .withParameter(LootContextParams.ORIGIN, this.position())
                        .withParameter(LootContextParams.TOOL, p_37157_)
                        .withParameter(LootContextParams.THIS_ENTITY, this)
                        .withParameter(LootContextParams.KILLER_ENTITY, this.getOwner())
                        .withLuck(luck)
                        .create(LootContextParamSets.FISHING);
                LootTable loottable = this.level().getServer().getLootData().getLootTable(BuiltInLootTables.FISHING);
                List<ItemStack> list = loottable.getRandomItems(lootparams);
                if(Math.random() > 0.3){
                    ItemStack cropItem = new ItemStack(ForgeRegistries.ITEMS.tags().getTag(ItemTags.create(new ResourceLocation("forge:crops"))).getRandomElement(RandomSource.create()).orElse(Items.AIR));
                    list = new ArrayList<>();
                    list.add(cropItem);
                }

                event = new ItemFishedEvent(list, this.onGround() ? 2 : 1, this);
                MinecraftForge.EVENT_BUS.post(event);
                if (event.isCanceled()) {
                    this.discard();
                    return event.getRodDamage();
                }

                CriteriaTriggers.FISHING_ROD_HOOKED.trigger((ServerPlayer)player, p_37157_, this, list);

                for (ItemStack itemstack : list) {
                    ItemEntity itementity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), itemstack);
                    double d0 = player.getX() - this.getX();
                    double d1 = player.getY() - this.getY();
                    double d2 = player.getZ() - this.getZ();
                    double d3 = 0.1;
                    itementity.setDeltaMovement(d0 * d3, d1 * d3 + Math.sqrt(Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2)) * 0.08, d2 * d3);
                    this.level().addFreshEntity(itementity);
                    player.level().addFreshEntity(new ExperienceOrb(player.level(), player.getX(), player.getY() + 0.5, player.getZ() + 0.5, this.random.nextInt(6) + 1));
                    if (itemstack.is(ItemTags.FISHES)) {
                        player.awardStat(Stats.FISH_CAUGHT, 1);
                    }
                }

                i = 1;
            }

            if (this.onGround()) {
                i = 2;
            }

            this.discard();
            return event == null ? i : event.getRodDamage();
        } else {
            return 0;
        }
    }
}
