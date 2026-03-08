package com.starmeow.smc.events;

import com.starmeow.smc.client.events.ClientForgeEvents;
import com.starmeow.smc.helper.EntityHelper;
import com.starmeow.smc.init.ItemRegistry;
import com.starmeow.smc.init.NetworkRegistry;
import com.starmeow.smc.init.PotionEffectRegistry;
import com.starmeow.smc.init.SoundRegistry;
import com.starmeow.smc.items.FrostiumBow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundEventRegistration;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Comparator;
import java.util.List;

import static com.starmeow.smc.client.events.ClientForgeEvents.sendOncePerTick;

@Mod.EventBusSubscriber
public class AttackEvents {
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event){
        LivingEntity entity = event.getEntity();
        Level level = event.getEntity().level();
        if (!level.isClientSide() && event.getSource().getDirectEntity() instanceof LivingEntity source) {
            ItemStack weapon = source.getMainHandItem();
            //冰剑特攻双倍伤害
            if (weapon.is(ItemRegistry.FROSTIUM_SWORD.get())) {
                entity.addEffect(new MobEffectInstance(PotionEffectRegistry.FROST.get(), 100, 0));
                if (entity.fireImmune() || entity.isSensitiveToWater()){
                    event.setAmount(event.getAmount() * 2.0f);
                }
            }

            //星光增伤
            if(event.getSource().getEntity() instanceof LivingEntity living && living.hasEffect(PotionEffectRegistry.STAR_LIGHT.get()) && event.getSource().is(DamageTypeTags.WITCH_RESISTANT_TO)){
                float modifier = 1.0f + (living.getEffect(PotionEffectRegistry.STAR_LIGHT.get()).getAmplifier() + 1) * 0.5f;
                entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100));
                event.setAmount(event.getAmount() * modifier);
            }

            //姬排保护减伤
            if(event.getEntity() instanceof Player player && player.hasEffect(PotionEffectRegistry.CHOP_PROTECTION.get()) && !event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)){
                int foodLevel = player.getFoodData().getFoodLevel();
                float damage = event.getAmount();
                if(damage >= foodLevel){
                    damage -= foodLevel;
                    player.getFoodData().setFoodLevel(0);
                    event.setAmount(damage);
                } else {
                    player.getFoodData().setFoodLevel(foodLevel - (int)damage);
                    event.setCanceled(true);
                }
            }

            //法伤棒棒糖
            if ((weapon.is(ItemRegistry.PERKIN_LOLLIPOP.get())||weapon.is(ItemRegistry.COLORFUL_ICE_CREAM.get())) && !event.getSource().is(DamageTypes.INDIRECT_MAGIC)) {
                float originalDamage = event.getAmount();
                event.setAmount(0);
                entity.invulnerableTime = 0;
                entity.hurt(source.damageSources().indirectMagic(source, source), originalDamage);
            }
            //永冻工具加buff
            if (weapon.is(ItemRegistry.PERFROSTITE_AXE.get()) || weapon.is(ItemRegistry.PERFROSTITE_PICKAXE.get()) || weapon.is(ItemRegistry.PERFROSTITE_SHOVEL.get()) || weapon.is(ItemRegistry.PERFROSTITE_SWORD.get()) || weapon.is(ItemRegistry.PERFROSTITE_HOE.get())) {
                if (entity.hasEffect(PotionEffectRegistry.FROST_BURST.get())){
                    int amp = entity.getEffect(PotionEffectRegistry.FROST_BURST.get()).getAmplifier();
                    int dur = entity.getEffect(PotionEffectRegistry.FROST_BURST.get()).getDuration();
                    entity.addEffect(new MobEffectInstance(PotionEffectRegistry.FROST_BURST.get(), dur, amp + 1));
                } else {
                    entity.addEffect(new MobEffectInstance(PotionEffectRegistry.FROST_BURST.get(), 200, 0));
                }
            }
            //冰斧特攻双倍伤害
            if (weapon.is(ItemRegistry.FROSTIUM_AXE.get())) {
                if (entity.getTicksFrozen() >= 100){
                    event.setAmount(event.getAmount() * (float)(400 / 100));
                    entity.setTicksFrozen(0);
                }
            }
            //姬排串减攻
            if (weapon.is(ItemRegistry.CHOP_KEBAB.get())) {
                if(source instanceof Player player){
                    event.setAmount(event.getAmount() * (player.getFoodData().getFoodLevel() / 20.0F));
                    player.getFoodData().addExhaustion(2.0F);
                }
            }
            //巧克力剑
            if (weapon.is(ItemRegistry.CHOCOLATE_SWORD.get())) {
                if(entity instanceof Animal animal){
                    event.setAmount(event.getAmount() * 3);
                    animal.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 0));
                }
            }
            //坚果减伤
            Item nut = ItemRegistry.WALLNUT_POT.get();
            if((entity.getItemBySlot(EquipmentSlot.MAINHAND).is(nut)
                    || entity.getItemBySlot(EquipmentSlot.OFFHAND).is(nut))
                    && entity.isUsingItem()
                    && entity.getUseItem().is(nut)){
                event.setAmount(event.getAmount() * 0.2F);
            }

            //牢大
            if(source.hasEffect(PotionEffectRegistry.ELBOWING.get())){
                level.playSound(null, entity.getOnPos(), SoundRegistry.ICE_TEA_DRUNK.get(), SoundSource.PLAYERS, 1, 0.9f + 0.3f * level.random.nextFloat());
            }

            //测试用伤害
            /*
            if(weapon.is(ItemRegistry.DELUXE_CAKE.get())){
                double r0 = 5.0D;
                List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, new AABB(entity.getX(), entity.getY(), entity.getZ(), entity.getX(), entity.getY(), entity.getZ()).inflate(r0 * 2), e -> true).stream()
                        .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(entity.getX(), entity.getY(), entity.getZ()))).toList();
                for (LivingEntity target : entities) {
                    double dx = target.getX() - entity.getX();
                    double dy = target.getY() - entity.getY();
                    double dz = target.getZ() - entity.getZ();
                    if (Math.pow(dx,2) + Math.pow(dy,2) + Math.pow(dz,2) <= Math.pow(r0,2)){
                        if(target instanceof Mob mob && target != entity){
                            mob.setTarget(entity);
                        }
                    }
                }
            }
             */

            /*
            //受到攻击解除安康
            if(source.hasEffect(PotionEffectRegistry.PEACE.get())){
                entity.addEffect(new MobEffectInstance(PotionEffectRegistry.NO_PEACE.get(), 1200));
            }
            */
        }
    }
    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        ItemStack stack = player.getMainHandItem();
        if (!ClientForgeEvents.isValidWeapon(stack)) return;
        EntityHelper.shootSwordProjectileByPlayer(player);
    }

    @SubscribeEvent
    public static void onLivingAttacked(LivingAttackEvent event){
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        if(entity instanceof Player player && player.getMainHandItem().is(ItemRegistry.MINI_BEDROCK.get())){
            level.playSound((Player)null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ZOMBIE_ATTACK_IRON_DOOR, entity.getSoundSource(), 0.3F, entity.getVoicePitch() * 0.1f);
            event.setCanceled(true);
        }

        if(event.getSource().getEntity() instanceof Player player && player.hasEffect(PotionEffectRegistry.PEACE.get())){
            event.setCanceled(true);
        }
        if(event.getSource().getDirectEntity() instanceof Player player && player.hasEffect(PotionEffectRegistry.PEACE.get())){
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void dollsRevive(LivingHurtEvent event){
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        if (level.isClientSide()) return;

        float damage = event.getAmount();
        if (entity.getHealth() > damage) return;

        ItemStack totemStack = ItemStack.EMPTY;
        ItemStack displayItem = ItemStack.EMPTY;
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack item = entity.getItemInHand(hand);
            if (item.is(ItemRegistry.DOLL_1.get()) || item.is(ItemRegistry.DOLL_2.get()) || item.is(ItemRegistry.DOLL_3.get()) || item.is(ItemRegistry.DOLL_4.get())) {
                totemStack = item;
                displayItem = item.getItem().getDefaultInstance();
                break;
            }
        }
        if (totemStack.isEmpty()) return;

        if (entity instanceof Player player) {
            if (player.getCooldowns().isOnCooldown(totemStack.getItem())) {
                return;
            }
            player.getCooldowns().addCooldown(totemStack.getItem(), 600);
            player.awardStat(Stats.ITEM_USED.get(totemStack.getItem()), 1);
            if (!player.getAbilities().instabuild) {
                totemStack.shrink(1);
            }
        }

        if (entity instanceof ServerPlayer sp) {
            NetworkRegistry.sendTotemActivate(sp, displayItem);
        }

        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.TOTEM_OF_UNDYING,
                    entity.getX(), entity.getY(), entity.getZ(),
                    100, 0.0D, 0.0D, 0.0D, 1.0D
            );
        }
        level.playSound(null, entity.getOnPos(), SoundEvents.TOTEM_USE, SoundSource.NEUTRAL, 1, 1);

        entity.setHealth(entity.getMaxHealth());
        entity.removeAllEffects();
        entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
        entity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
        entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
        event.setCanceled(true);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingAttackedIns(LivingDeathEvent event){
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        if(entity instanceof Player player && player.getMainHandItem().is(ItemRegistry.MINI_BEDROCK.get())){
            level.playSound((Player)null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ZOMBIE_ATTACK_IRON_DOOR, entity.getSoundSource(), 0.3F, entity.getVoicePitch() * 0.5f);
            player.displayClientMessage(Component.translatable("message.smc.you_cheated_first"), true);
            event.setCanceled(true);
            player.setHealth(player.getMaxHealth());
        }
    }

    @SubscribeEvent
    public static void onPlayerLeftClickEntity(AttackEntityEvent event) {
        //绿棒棒糖中毒
        if(event.getEntity().getMainHandItem().is(ItemRegistry.BROCCOLI_LOLLIPOP.get())){
            if (event.getTarget() instanceof LivingEntity entity) {
                entity.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 0, false, true, true));
            }
        }
        //蓝棒棒糖缓慢
        if(event.getEntity().getMainHandItem().is(ItemRegistry.FROST_LOLLIPOP.get())){
            if (event.getTarget() instanceof LivingEntity entity) {
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 0, false, true, true));
            }
        }
        //冰激凌ALL！
        if(event.getEntity().getMainHandItem().is(ItemRegistry.COLORFUL_ICE_CREAM.get())){
            if (event.getTarget() instanceof LivingEntity entity) {
                entity.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 0, false, true, true));
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 0, false, true, true));
            }
        }
    }

    //参考暮色末影弓
    @SubscribeEvent
    public static void onArrowHit(ProjectileImpactEvent event) {
        Projectile arrow = event.getProjectile();
        if (arrow.getOwner() instanceof Player player
                && event.getRayTraceResult() instanceof EntityHitResult result
                && result.getEntity() instanceof LivingEntity living
                && arrow.getOwner() != result.getEntity()) {
            //无视无敌帧
            if (arrow.getPersistentData().contains(FrostiumBow.KEY)) {
                if(living.invulnerableTime >= 0){
                    living.invulnerableTime = 0;
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityLoot(LivingDropsEvent event) {
        if (!event.getEntity().level().isClientSide()) {
            LivingEntity entity = event.getEntity();
            Entity source = event.getSource().getEntity();
            DamageSource damage = event.getSource();
            //高压苦力怕炸死鸡获得姬排
            if (entity instanceof Chicken && source instanceof Creeper creeper && creeper.isPowered() && damage.is(DamageTypeTags.IS_EXPLOSION)) {
                EntityHelper.addEntityDrops(event, ItemRegistry.CHICKEN_CHOP.get());
            }
            //雪狐杀死蜘蛛获得星跃玩偶
            if (entity instanceof Spider && source instanceof Fox fox && fox.getVariant() == Fox.Type.SNOW && fox.isHolding(ItemRegistry.FROSTIUM_SWORD.get())) {
                EntityHelper.addEntityDrops(event, ItemRegistry.DOLL_4.get());
            }
            //鸡或者鸡骑士杀死高压苦力怕获得鸡姬玩偶
            if ((source instanceof Chicken || (source != null && source.getVehicle() != null && source.getVehicle() instanceof Chicken))&& entity instanceof Creeper creeper && creeper.isPowered()) {
                EntityHelper.addEntityDrops(event, ItemRegistry.DOLL_3.get());
            }
        }
    }

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        Level level = event.getEntity().level();
        if(!level.isClientSide()){
            if(event.getEntity().hasEffect(PotionEffectRegistry.ELBOWING.get())){
                level.playSound(null, event.getEntity().getOnPos(), SoundRegistry.ICE_TEA_DEATH.get(), SoundSource.NEUTRAL, 1, 0.9f + 0.3f * level.random.nextFloat());

            }
            else if (event.getSource().getEntity() instanceof LivingEntity living && living.hasEffect(PotionEffectRegistry.ELBOWING.get())){
                level.playSound(null, living.getOnPos(), SoundRegistry.ICE_TEA_KILL.get(), SoundSource.NEUTRAL, 1, 0.9f + 0.3f * level.random.nextFloat());
            }
        }

    }
}

