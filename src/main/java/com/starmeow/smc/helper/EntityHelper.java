package com.starmeow.smc.helper;

import com.starmeow.smc.config.Config;
import com.starmeow.smc.entities.ThrownSwordEntity;
import com.starmeow.smc.entities.projectiles.MeowBall;
import com.starmeow.smc.entities.projectiles.SwordAura;
import com.starmeow.smc.init.EnchantmentRegistry;
import com.starmeow.smc.init.EntityTypeRegistry;
import com.starmeow.smc.init.ItemRegistry;
import com.starmeow.smc.items.DevourSword;
import com.starmeow.smc.items.Zenish;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

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
            if (stack.getItem() instanceof Zenish){
                shootZenishSwordProjectile(stack, player);
            }
            float scale = player.getAttackStrengthScale(0.5f);
            if(scale > 0.9){
                if (stack.getItem() instanceof DevourSword){
                    shootDevouredSwordProjectile(stack, player);
                }
                if (stack.is(ItemRegistry.EXCALIBUR.get())){
                    shootExcaliburSwordProjectile(stack, player);
                }
                if (stack.is(ItemRegistry.MEOWMERE.get())){
                    shootMeowBall(stack, player);
                }
            }
        }
    }

    public static void shootDevouredSwordProjectile(ItemStack stack, LivingEntity entity){
        if(!Config.DEVOUR_SWORD_SHOOT.get()) return;

        Level level = entity.level();
        CompoundTag tag = stack.getOrCreateTag();
        ListTag list = tag.getList("SMCWeaponStored", Tag.TAG_STRING).copy();
        StringTag exId = StringTag.valueOf(ForgeRegistries.ITEMS.getKey(ItemRegistry.EXCALIBUR.get()).toString());
        StringTag zenId = StringTag.valueOf(ForgeRegistries.ITEMS.getKey(ItemRegistry.ZENISH.get()).toString());
        LivingEntity selectedTarget = null;
        Vec3 fallbackTargetPos = null;
        //获取目标
        for (int s = 1; s <= 30; ++s){
            double sx = s * entity.getLookAngle().x + entity.getX();
            double sy = s * entity.getLookAngle().y + entity.getY() + entity.getEyeHeight();
            double sz = s * entity.getLookAngle().z + entity.getZ();
            Vec3 svec3 = new Vec3(sx,sy,sz);
            fallbackTargetPos = svec3;
            AABB aabb = new AABB(svec3, svec3).inflate(1.5);
            List<Entity> entities = entity.level().getEntitiesOfClass(Entity.class, aabb);
            for (Entity target : entities) {
                if (target != entity && target instanceof LivingEntity living && !(target instanceof ThrownSwordEntity) && living.isAlive() && !living.isRemoved()){
                    selectedTarget = living;
                    break;
                }
            }
        }
        if(list.contains(exId) || list.contains(zenId)){
            list.remove(exId);
            if(list.isEmpty())return;

            int count = Config.DEVOUR_SWORD_UPGRADE.get() != 0 ? 1 + (list.size() / Config.DEVOUR_SWORD_UPGRADE.get()) : 1;
            if(list.contains(zenId)){
                list.remove(zenId);
                count = 1;
            }
            count = Math.min(count, Config.DEVOUR_SWORD_MAX.get());
            for(int i = 0; i < count; i++){
                ItemStack item = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(list.get(entity.getRandom().nextInt(list.size())).getAsString())));
                float damage = (float) (list.size() * Config.DEVOUR_SWORD_SHOOT_DAMAGE.get() * Config.DEVOUR_SWORD_ADD.get());
                shootFlyingSword(level, stack, entity, selectedTarget, fallbackTargetPos, item, damage, false);
            }

            level.playSound((Player)null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1F, 0.4F / level.getRandom().nextFloat() + 1F);
            stack.hurtAndBreak(1, entity, (p_40665_) -> {
                p_40665_.broadcastBreakEvent(entity.getUsedItemHand());
            });
        }
    }

    public static void shootZenishSwordProjectile(ItemStack stack, LivingEntity entity){
        Level level = entity.level();
        LivingEntity selectedTarget = null;
        Vec3 fallbackTargetPos = null;
        //获取目标
        for (int s = 1; s <= 30; ++s){
            double sx = s * entity.getLookAngle().x + entity.getX();
            double sy = s * entity.getLookAngle().y + entity.getY() + entity.getEyeHeight();
            double sz = s * entity.getLookAngle().z + entity.getZ();
            Vec3 svec3 = new Vec3(sx,sy,sz);
            fallbackTargetPos = svec3;
            AABB aabb = new AABB(svec3, svec3).inflate(1.5);
            List<Entity> entities = entity.level().getEntitiesOfClass(Entity.class, aabb);
            for (Entity target : entities) {
                if (target != entity && target instanceof LivingEntity living && !(target instanceof ThrownSwordEntity) && living.isAlive() && !living.isRemoved()){
                    selectedTarget = living;
                    break;
                }
            }
        }
        List<Item> containedSwords = List.of(
                Items.WOODEN_SWORD,
                Items.STONE_SWORD,
                Items.IRON_SWORD,
                Items.DIAMOND_SWORD,
                Items.GOLDEN_SWORD,
                Items.NETHERITE_SWORD,
                ItemRegistry.PERFROSTITE_SWORD.get(),
                ItemRegistry.FROSTIUM_SWORD.get(),
                ItemRegistry.ZINC_SWORD.get(),
                ItemRegistry.DEVOUR_SWORD.get(),
                ItemRegistry.MEOWMERE.get(),
                ItemRegistry.SALT_FISH_SWORD.get());
        shootFlyingSword(level, stack, entity, selectedTarget, fallbackTargetPos, new ItemStack(containedSwords.get(entity.getRandom().nextInt(containedSwords.size()))), 0, true);
        level.playSound((Player)null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.COD_FLOP, SoundSource.PLAYERS, 1F, 0.4F / level.getRandom().nextFloat() + 1F);
    }

    private static void shootFlyingSword(Level level, ItemStack stack, LivingEntity entity, LivingEntity selectedTarget, Vec3 fallbackTargetPos, ItemStack swordStack, float damage, boolean fixed){
        ThrownSwordEntity sword = EntityTypeRegistry.THROWN_SWORD.get().create(level);
        Vec3 targetPos = selectedTarget != null ? selectedTarget.position() : fallbackTargetPos;
        sword.setTargetPos(targetPos);
        sword.setFixedDamage(fixed);
        EnchantmentHelper.setEnchantments(stack.getAllEnchantments(), swordStack);
        sword.setItemSlot(EquipmentSlot.MAINHAND, swordStack);
        sword.setOwner(entity);
        sword.launchFromRotation(entity, 1.0F, 25.0F, selectedTarget);
        sword.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(damage);
        level.addFreshEntity(sword);
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

    public static void shootMeowBall(ItemStack stack, LivingEntity entity){
        Level level = entity.level();
        MeowBall sword = EntityTypeRegistry.MEOW_BALL.get().create(level);
        RandomSource random = entity.getRandom();
        sword.setPos(entity.getX(), entity.getEyeY() - 0.3, entity.getZ());
        sword.setYRot(entity.getYHeadRot());
        sword.setXRot(entity.getXRot());
        sword.setOwner(entity);
        sword.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 1.5F, 0.0F);
        if(random.nextInt(128) == 0){
            sword.setVariant(5);
        }else{
            sword.setVariant(entity.getRandom().nextInt(0, 5));
        }
        level.addFreshEntity(sword);
        level.playSound((Player)null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.CAT_AMBIENT, SoundSource.PLAYERS, 1F, 2F);
        stack.hurtAndBreak(1, entity, (p_40665_) -> {
            p_40665_.broadcastBreakEvent(entity.getUsedItemHand());
        });
    }

    public static boolean isFireProofBoat(Boat.Type type){
        return type == Boat.Type.byName("golden") || type == Boat.Type.byName("glass_golden");
    }

    public static LivingEntity getHurtEventSourceEntity(LivingHurtEvent event){
        LivingEntity attacker = null;
        if(event.getSource().getEntity() instanceof LivingEntity entity0){
            attacker = entity0;
        }else if(event.getSource().getDirectEntity() instanceof LivingEntity entity0){
            attacker = entity0;
        }
        return attacker;
    }

    public static void spawnLingeringCloud(LivingEntity living, int amp) {
        Collection<MobEffectInstance> $$0 = living.getActiveEffects();
        if (!$$0.isEmpty()) {
            AreaEffectCloud $$1 = new AreaEffectCloud(living.level(), living.getX(), living.getY(), living.getZ());
            $$1.setRadius(2.5F + 0.5F * (amp + 1));
            $$1.setRadiusOnUse(-0.5F);
            $$1.setWaitTime(10);
            $$1.setDuration($$1.getDuration() / 2);
            $$1.setRadiusPerTick(-$$1.getRadius() / (float)$$1.getDuration());

            for (MobEffectInstance $$2 : $$0) {
                $$1.addEffect(new MobEffectInstance($$2));
            }

            living.level().addFreshEntity($$1);
        }
    }
}
