package com.starmeow.smc.events;

import com.starmeow.smc.config.Config;
import com.starmeow.smc.entities.ChickenHarvester;
import com.starmeow.smc.entities.EasterBunny;
import com.starmeow.smc.entities.SaltFish;
import com.starmeow.smc.entities.ThrownSwordEntity;
import com.starmeow.smc.helper.CuriosHelper;
import com.starmeow.smc.helper.EntityHelper;
import com.starmeow.smc.init.ItemRegistry;
import com.starmeow.smc.init.NetworkRegistry;
import com.starmeow.smc.init.PotionEffectRegistry;
import com.starmeow.smc.init.SoundRegistry;
import com.starmeow.smc.items.DollItems;
import com.starmeow.smc.items.FoxArmorItems;
import com.starmeow.smc.items.FrostiumBow;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class AttackEvents {
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event){
        LivingEntity entity = event.getEntity();
        Level level = event.getEntity().level();
        LivingEntity attacker = EntityHelper.getHurtEventSourceEntity(event);

        if (!level.isClientSide()){
            //减伤相关
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

            //凌空之冠减伤
            if(event.getEntity().getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.DIVINE_HALO.get()) || CuriosHelper.hasCharm(event.getEntity(), ItemRegistry.DIVINE_HALO.get())){
                if(event.getSource().is(DamageTypes.FLY_INTO_WALL)){
                    event.setCanceled(true);
                } else {
                    ItemStack halo = CuriosHelper.hasCharm(event.getEntity(), ItemRegistry.DIVINE_HALO.get()) ? CuriosHelper.getCharm(event.getEntity(), ItemRegistry.DIVINE_HALO.get()) : event.getEntity().getItemBySlot(EquipmentSlot.HEAD);
                    CompoundTag tag = halo.getOrCreateTag();
                    double minAbility = Config.DIVINE_HALO_MIN_REDUCE.get();
                    double reduceAbility = Config.DIVINE_HALO_DECREASE_REDUCE.get();
                    int maxTime = Config.DIVINE_HALO_COOLDOWN.get();
                    float ability = tag.getFloat("SMCHaloAbility");
                    float damage = event.getAmount();
                    float modifiedDamage = damage * (100.0F - ability) * 0.01F;
                    if(damage > 0.001F && ability > minAbility){
                        List<MobEffectInstance> list = new ArrayList<>(event.getEntity().getActiveEffects()).stream().filter(e -> e.getEffect().getCategory().equals(MobEffectCategory.HARMFUL)).toList();
                        for (MobEffectInstance ins : list) {
                            event.getEntity().removeEffect(ins.getEffect());
                            if (event.getEntity().hasEffect(ins.getEffect())) {
                                event.getEntity().getActiveEffectsMap().remove(ins.getEffect());
                            }
                        }
                        event.setAmount(modifiedDamage);
                        tag.putFloat("SMCHaloAbility", (float)Math.max(minAbility, ability - reduceAbility));
                        tag.putInt("SMCHaloTimer", maxTime);
                    }
                    if(event.getEntity() instanceof Player player && !player.getCooldowns().isOnCooldown(ItemRegistry.DIVINE_HALO.get()) && modifiedDamage >= event.getEntity().getHealth()){
                        player.setHealth(1);
                        player.setAbsorptionAmount(player.getMaxHealth() - 1);
                        player.getCooldowns().addCooldown(ItemRegistry.DIVINE_HALO.get(), Config.DIVINE_HALO_ITEM_COOLDOWN.get() * 20);
                        tag.putFloat("SMCHaloAbility",0);
                        event.setCanceled(true);
                        if (entity instanceof ServerPlayer serverPlayer) {
                            NetworkRegistry.sendTotemActivate(serverPlayer, ItemRegistry.DIVINE_HALO.get().getDefaultInstance());
                        }
                        if (level instanceof ServerLevel serverLevel) {
                            serverLevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, entity.getX(), entity.getY(), entity.getZ(), 100, 0.0D, 0.0D, 0.0D, 1.0D);
                        }
                        level.playSound(null, entity.getOnPos(), SoundEvents.TOTEM_USE, SoundSource.NEUTRAL, 1, 1);
                    }
                }
            }
            if(event.getEntity().getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.DIVINE_SHARD.get()) || CuriosHelper.hasCharm(event.getEntity(), ItemRegistry.DIVINE_SHARD.get())){
                if(event.getEntity() instanceof Player player && !player.getCooldowns().isOnCooldown(ItemRegistry.DIVINE_SHARD.get()) && event.getAmount() >= event.getEntity().getHealth()) {
                    player.setHealth(1);
                    player.getCooldowns().addCooldown(ItemRegistry.DIVINE_SHARD.get(), Config.DIVINE_HALO_ITEM_COOLDOWN.get() * 20);
                    event.setCanceled(true);
                    if (entity instanceof ServerPlayer serverPlayer) {
                        NetworkRegistry.sendTotemActivate(serverPlayer, ItemRegistry.DIVINE_SHARD.get().getDefaultInstance());
                    }
                    if (level instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, entity.getX(), entity.getY(), entity.getZ(), 100, 0.0D, 0.0D, 0.0D, 1.0D);
                    }
                    level.playSound(null, entity.getOnPos(), SoundEvents.TOTEM_USE, SoundSource.NEUTRAL, 1, 1);
                }

                ItemStack shard = CuriosHelper.hasCharm(event.getEntity(), ItemRegistry.DIVINE_SHARD.get()) ? CuriosHelper.getCharm(event.getEntity(), ItemRegistry.DIVINE_SHARD.get()) : event.getEntity().getItemBySlot(EquipmentSlot.HEAD);
                LivingEntity living = event.getEntity();
                CompoundTag tag = shard.getTag();
                if(tag != null && tag.contains("SMCShardChallenge") && tag.getInt("SMCShardChallenge") == 2 && event.getSource().getEntity() instanceof Warden){
                    tag.putInt("SMCShardAttack", tag.getInt("SMCShardAttack") + 1);
                    if(tag.getInt("SMCShardAttack") >= Config.DIVINE_SHARD_CHALLENGE_2.get()){
                        tag.remove("SMCShardAttack");
                        tag.putInt("SMCShardChallenge", 6);
                        living.level().playSound((Player)null, living.getX(), living.getY(), living.getZ(), SoundEvents.AMETHYST_CLUSTER_BREAK, SoundSource.PLAYERS, 1F, 2F);
                    }
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

            //星光增伤
            if(attacker != null){
                if(attacker.hasEffect(PotionEffectRegistry.STAR_LIGHT.get()) && event.getSource().is(DamageTypeTags.WITCH_RESISTANT_TO)){
                    float modifier = 1.0f + (attacker.getEffect(PotionEffectRegistry.STAR_LIGHT.get()).getAmplifier() + 1) * 0.2f;
                    entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100));
                    event.setAmount(event.getAmount() * modifier);
                }
            }

            //武器相关
            if (event.getSource().getDirectEntity() instanceof LivingEntity source) {
                ItemStack weapon = source.getMainHandItem();
                //冰剑特攻双倍伤害
                if (weapon.is(ItemRegistry.FROSTIUM_SWORD.get())) {
                    entity.addEffect(new MobEffectInstance(PotionEffectRegistry.FROST.get(), 100, 0));
                    if (entity.fireImmune() || entity.isSensitiveToWater()){
                        event.setAmount(event.getAmount() * 2.0f);
                    }
                }
                //咸鱼剑特攻三倍伤害
                if (weapon.is(ItemRegistry.SALT_FISH_SWORD.get())) {
                    if (attacker.isInWaterRainOrBubble()){
                        entity.addEffect(new MobEffectInstance(PotionEffectRegistry.STUNNED.get(), 20, 0));
                        event.setAmount(event.getAmount() * 3.0f);
                    }
                }
                //衣架特攻三倍伤害
                if (weapon.is(ItemRegistry.GOLDEN_HANGER.get()) || weapon.is(ItemRegistry.HANGER.get())) {
                    if(entity.isBaby()){
                        event.setAmount(event.getAmount() * 3.0f);
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
                //牢大
                if(source.hasEffect(PotionEffectRegistry.ELBOWING.get())){
                    level.playSound(null, entity.getOnPos(), SoundRegistry.ICE_TEA_DRUNK.get(), SoundSource.PLAYERS, 1, 0.9f + 0.3f * level.random.nextFloat());
                }

                //劲凉（bushi）
                if(source.hasEffect(PotionEffectRegistry.FRESH_COOL.get())){
                    if(entity.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)
                            || entity.hasEffect(PotionEffectRegistry.FROST.get())
                            || entity.hasEffect(PotionEffectRegistry.FROST_BURST.get())
                            || entity.getTicksFrozen() > 0
                    ){
                        int amp = source.getEffect(PotionEffectRegistry.FRESH_COOL.get()).getAmplifier() + 1;
                        source.heal(Math.max(amp * 0.05F * source.getMaxHealth(), amp * 2));
                    }
                }

                //热带风味（bushi）
                if(source.hasEffect(PotionEffectRegistry.FEVER_SPICY.get())){
                    if(entity.isOnFire() && (event.getSource().is(DamageTypeTags.IS_EXPLOSION) || event.getSource().is(DamageTypeTags.IS_FIRE))){
                        int amp = source.getEffect(PotionEffectRegistry.FEVER_SPICY.get()).getAmplifier() + 1;
                        event.setAmount(event.getAmount() * (1 + 0.3F * amp));
                    }
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
    }
    @SubscribeEvent
    public static void onAttackEntityForShootSword(AttackEntityEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        ItemStack stack = player.getMainHandItem();
        if (!com.starmeow.smc.helper.WeaponHelper.isValidWeapon(stack)) return;
        EntityHelper.shootSwordProjectileByPlayer(player);
    }

    @SubscribeEvent
    public static void onTotemActive(LivingUseTotemEvent event) {
        ItemStack shard = CuriosHelper.hasCharm(event.getEntity(), ItemRegistry.DIVINE_SHARD.get()) ? CuriosHelper.getCharm(event.getEntity(), ItemRegistry.DIVINE_SHARD.get()) : event.getEntity().getItemBySlot(EquipmentSlot.HEAD);
        LivingEntity living = event.getEntity();
        if(shard != null && !shard.isEmpty()){
            CompoundTag tag = shard.getTag();
            if(tag != null && tag.contains("SMCShardChallenge") && tag.getInt("SMCShardChallenge") == 4){
                tag.putInt("SMCShardTotem", tag.getInt("SMCShardTotem") + 1);
                if(tag.getInt("SMCShardTotem") >= Config.DIVINE_SHARD_CHALLENGE_4.get()){
                    tag.remove("SMCShardTotem");
                    tag.putInt("SMCShardChallenge", 8);
                    living.level().playSound((Player)null, living.getX(), living.getY(), living.getZ(), SoundEvents.AMETHYST_CLUSTER_BREAK, SoundSource.PLAYERS, 1F, 2F);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingAttacked(LivingAttackEvent event){
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        if(entity instanceof Player player && player.getMainHandItem().is(ItemRegistry.MINI_BEDROCK.get()) && Config.ENABLE_BEDROCK.get()){
            level.playSound((Player)null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ZOMBIE_ATTACK_IRON_DOOR, entity.getSoundSource(), 0.3F, entity.getVoicePitch() * 0.1f);
            event.setCanceled(true);
        }

        if(event.getSource().getEntity() instanceof Player player && player.hasEffect(PotionEffectRegistry.PEACE.get())){
            event.setCanceled(true);
        }
        if(event.getSource().getDirectEntity() instanceof Player player && player.hasEffect(PotionEffectRegistry.PEACE.get())){
            event.setCanceled(true);
        }

        //凌空之冠免疫碰撞
        if(event.getEntity().getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.DIVINE_HALO.get()) || CuriosHelper.hasCharm(event.getEntity(), ItemRegistry.DIVINE_HALO.get())){
            if(event.getSource().is(DamageTypes.FLY_INTO_WALL)){
                event.setCanceled(true);
            }
        }
        //狐尾吸收伤害
        if(entity instanceof Player player && CuriosHelper.hasCharm(player, ItemRegistry.FOX_TAIL.get()) && !player.getCooldowns().isOnCooldown(ItemRegistry.FOX_TAIL.get())){
            ItemStack tail = CuriosHelper.getCharm(player, ItemRegistry.FOX_TAIL.get());
            CompoundTag tag = tail.getOrCreateTag();
            tag.putFloat("SMCTailAtkDamage", event.getAmount());
            player.getCooldowns().addCooldown(ItemRegistry.FOX_TAIL.get(), 600);
            event.setCanceled(true);
        }
        //狐尾释放伤害
        if(event.getSource().getEntity() instanceof Player player && CuriosHelper.hasCharm(player, ItemRegistry.FOX_TAIL.get())){
            ItemStack tail = CuriosHelper.getCharm(player, ItemRegistry.FOX_TAIL.get());
            if(tail != null){
                CompoundTag tag = tail.getOrCreateTag();
                tag.putFloat("SMCTailAtkDamage", 0);
            }
        }
        //狐狸套免疫甜浆果和摔落
        boolean fox_flag = entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof FoxArmorItems
                && entity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof FoxArmorItems
                && entity.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof FoxArmorItems
                && entity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof FoxArmorItems;
        if((fox_flag || CuriosHelper.hasCharm(entity, ItemRegistry.FOX_TAIL.get())) && (event.getSource().is(DamageTypes.SWEET_BERRY_BUSH) || event.getSource().is(DamageTypes.FALL))) {
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
            if (item.getItem() instanceof DollItems) {
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
        if(entity instanceof Player player && player.getMainHandItem().is(ItemRegistry.MINI_BEDROCK.get()) && Config.ENABLE_BEDROCK.get()){
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
            //1颗心以下的玩家的狗击杀幻翼获取基恩玩偶
            if (source instanceof Wolf wolf && entity instanceof Phantom && wolf.getOwnerUUID() != null && wolf.level().getPlayerByUUID(wolf.getOwnerUUID()) != null) {
                if(wolf.level().getPlayerByUUID(wolf.getOwnerUUID()).getHealth() <= 2){
                    EntityHelper.addEntityDrops(event, ItemRegistry.DOLL_5.get());
                }
            }
            //彩蛋兔变回彩蛋（不用loottable以免双倍掉落和德格米发力）
            if (entity instanceof EasterBunny bunny) {
                if(bunny.isOnFire()){
                    EntityHelper.addEntityDrops(event, ItemRegistry.FRIED_EASTER_BUNNY_EGG.get());
                }else{
                    EntityHelper.addEntityDrops(event, ItemRegistry.EASTER_BUNNY_EGG.get());
                }
            }
            //咸鱼（同理）
            if (entity instanceof SaltFish fish) {
                if(fish.isOnFire()){
                    EntityHelper.addEntityDrops(event, ItemRegistry.COOKED_SALT_FISH.get());
                }else{
                    EntityHelper.addEntityDrops(event, ItemRegistry.SALT_FISH.get());
                }
            }
            //路牌斩首
            if (event.getSource().getEntity() instanceof LivingEntity living && living.getMainHandItem().is(ItemRegistry.ROAD_SIGN.get())) {
                ItemStack head = null;
                boolean absoluteDrop = false;
                if (entity instanceof Zombie) {
                    head = Items.ZOMBIE_HEAD.getDefaultInstance();
                }
                if (entity instanceof Skeleton) {
                    head = Items.SKELETON_SKULL.getDefaultInstance();
                }
                if (entity instanceof WitherSkeleton) {
                    head = Items.WITHER_SKELETON_SKULL.getDefaultInstance();
                }
                if (entity instanceof WitherBoss) {
                    head = Items.WITHER_SKELETON_SKULL.getDefaultInstance();
                    absoluteDrop = true;
                }
                if (entity instanceof Creeper) {
                    head = Items.CREEPER_HEAD.getDefaultInstance();
                }
                if (entity instanceof EnderDragon) {
                    head = Items.DRAGON_HEAD.getDefaultInstance();
                    absoluteDrop = true;
                }
                if (entity instanceof Piglin) {
                    head = Items.PIGLIN_HEAD.getDefaultInstance();
                }
                if (entity instanceof Player killedPlayer) {
                    head = Items.PLAYER_HEAD.getDefaultInstance();
                    head.getOrCreateTag().putString("SkullOwner", killedPlayer.getGameProfile().getName());
                }
                if(head != null){
                    if(absoluteDrop || entity.getRandom().nextInt(100) < 30){
                        entity.spawnAtLocation(head);
                    }
                }
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

    @SubscribeEvent
    public static void onLivingHurtByLightning(EntityStruckByLightningEvent event){
        if(event.getEntity() instanceof LivingEntity living && !living.level().isClientSide()){
            if(living.hasEffect(PotionEffectRegistry.FUZED.get())){
                living.addEffect(new MobEffectInstance(PotionEffectRegistry.FUZED.get(), 100, 2));
            }
        }
    }
}

