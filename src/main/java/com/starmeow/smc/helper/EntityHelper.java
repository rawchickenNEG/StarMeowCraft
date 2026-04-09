package com.starmeow.smc.helper;

import com.starmeow.smc.config.Config;
import com.starmeow.smc.entities.ThrownSwordEntity;
import com.starmeow.smc.entities.projectiles.SwordAura;
import com.starmeow.smc.init.EnchantmentRegistry;
import com.starmeow.smc.init.EntityTypeRegistry;
import com.starmeow.smc.init.ItemRegistry;
import com.starmeow.smc.items.DevourSword;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class EntityHelper {

    private static final Map<UUID, Long> LAST_TICK = new HashMap<>();

    public static void addEntityDrops(LivingDropsEvent event, Item item) {
        ItemStack itemStack = new ItemStack(item);
        ItemEntity itemEntity = new ItemEntity(event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), itemStack);
        itemEntity.setPickUpDelay(10);
        event.getDrops().add(itemEntity);
    }

    public static Vec3 getVec3(Entity entity){
        return new Vec3(entity.getX(), entity.getY() + entity.getBbHeight() / 2.0, entity.getZ());
    }


    public static void shootSwordProjectileByPlayer(ServerPlayer player){
        long tick = player.level().getGameTime();
        UUID id = player.getUUID();

        if (LAST_TICK.getOrDefault(id, -1L) == tick) return;
        LAST_TICK.put(id, tick);

        ItemStack stack = player.getMainHandItem();
        Level level = player.level();
        if (stack.isEmpty()) return;
        if(!level.isClientSide) {
            float scale = player.getAttackStrengthScale(0.5f);
            if(scale > 0.9){
                if (stack.getItem() instanceof DevourSword){
                    shootDevouredSwordProjectile(stack, player);
                }
                if (stack.is(ItemRegistry.EXCALIBUR.get())){
                    shootExcaliburSwordProjectile(stack, player);
                }
            }
        }
    }

    public static void shootDevouredSwordProjectile(ItemStack stack, LivingEntity entity){
        if(!Config.DEVOUR_SWORD_SHOOT.get()) return;

        Level level = entity.level();
        CompoundTag tag = stack.getOrCreateTag();
        ListTag list = tag.getList("SMCWeaponStored", Tag.TAG_STRING).copy();
        ResourceLocation res = ForgeRegistries.ITEMS.getKey(ItemRegistry.EXCALIBUR.get());
        StringTag exId = StringTag.valueOf(res.toString());
        if(list.contains(exId)){
            list.remove(exId);
            if(list.isEmpty())return;

            int count = Config.DEVOUR_SWORD_UPGRADE.get() != 0 ? 1 + (list.size() / Config.DEVOUR_SWORD_UPGRADE.get()) : 1;

            for(int i = 0; i < count; i++){
                ThrownSwordEntity sword = EntityTypeRegistry.THROWN_SWORD.get().create(level);

                ItemStack item = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(list.get(entity.getRandom().nextInt(list.size())).getAsString())));
                EnchantmentHelper.setEnchantments(stack.getAllEnchantments(), item);
                sword.setItemSlot(EquipmentSlot.MAINHAND, item);
                sword.setPos(entity.getX(), entity.getEyeY() - 0.1, entity.getZ());
                sword.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 1.0F, 25.0F);
                sword.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(list.size() * Config.DEVOUR_SWORD_SHOOT_DAMAGE.get() * Config.DEVOUR_SWORD_ADD.get());
                sword.setOwner(entity);
                level.addFreshEntity(sword);
            }

            level.playSound((Player)null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1F, 0.4F / level.getRandom().nextFloat() + 1F);
            stack.hurtAndBreak(1, entity, (p_40665_) -> {
                p_40665_.broadcastBreakEvent(entity.getUsedItemHand());
            });
        }

    }

    public static void shootExcaliburSwordProjectile(ItemStack stack, LivingEntity entity){
        //if(!Config.DEVOUR_SWORD_SHOOT.get()) return;

        Level level = entity.level();
        SwordAura sword = EntityTypeRegistry.SWORD_AURA.get().create(level);
        sword.setPos(entity.getX(), entity.getEyeY() - 0.3, entity.getZ());
        sword.setYRot(entity.getYHeadRot());
        sword.setXRot(entity.getXRot());
        sword.setOwner(entity);
        if(stack.getEnchantmentLevel(EnchantmentRegistry.DIVINE_JUDGEMENT.get()) != 0){
            sword.setDivineLevel(stack.getEnchantmentLevel(EnchantmentRegistry.DIVINE_JUDGEMENT.get()));
        }
        sword.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 2.0F, 0.0F);
        level.addFreshEntity(sword);
        level.playSound((Player)null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.AMETHYST_CLUSTER_BREAK, SoundSource.PLAYERS, 1F, 2F);
        stack.hurtAndBreak(1, entity, (p_40665_) -> {
            p_40665_.broadcastBreakEvent(entity.getUsedItemHand());
        });
    }

    public static boolean isFireProofBoat(Boat.Type type){
        return type == Boat.Type.byName("golden") || type == Boat.Type.byName("glass_golden");
    }
}
