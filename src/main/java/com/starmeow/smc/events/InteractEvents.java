package com.starmeow.smc.events;

import com.starmeow.smc.cache.MiningFaceCache;
import com.starmeow.smc.config.Config;
import com.starmeow.smc.entities.EasterBunny;
import com.starmeow.smc.helper.BlockHelper;
import com.starmeow.smc.helper.CuriosHelper;
import com.starmeow.smc.helper.ItemHelper;
import com.starmeow.smc.init.BlockRegistry;
import com.starmeow.smc.init.EnchantmentRegistry;
import com.starmeow.smc.init.ItemRegistry;
import com.starmeow.smc.init.PotionEffectRegistry;
import com.starmeow.smc.items.SwissArmyKnife;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.enchanting.EnchantmentLevelSetEvent;
import net.minecraftforge.event.entity.EntityMobGriefingEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.event.ItemEvent;
import java.util.*;

@Mod.EventBusSubscriber
public class InteractEvents {

    @SubscribeEvent
    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        //改变花盆方块
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        LivingEntity entity = event.getEntity();
        BlockState state = level.getBlockState(pos);
        if(state.is(Blocks.FLOWER_POT)){
            boolean flag = false;
            Block block = null;
            Vec3 eyePos = entity.getEyePosition();
            Direction direction = entity.getDirection().getOpposite();
            if(entity.getMainHandItem().is(ItemRegistry.PEA_SHOOTER_POT.get())){
                block = BlockRegistry.PEA_SHOOTER_POT_BLOCK.get();
                flag = true;
            }
            if(entity.getMainHandItem().is(ItemRegistry.SUNFLOWER_POT.get())){
                block = BlockRegistry.SUNFLOWER_POT_BLOCK.get();
                flag = true;
            }
            if(entity.getMainHandItem().is(ItemRegistry.WALLNUT_POT.get())){
                block = BlockRegistry.WALLNUT_POT_BLOCK.get();
                flag = true;
            }
            if(flag){
                level.setBlock(pos, block.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, direction), 3);
                level.levelEvent(2001, pos,
                        Block.getId(level.getBlockState(pos)));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        ItemStack itemstack = event.getItemStack();
        if(itemstack.getItem() instanceof SwissArmyKnife){
            CompoundTag tag = itemstack.getOrCreateTag();
            int mode = tag.getInt("SMCSwissKnife");
            boolean hasShears = tag.getBoolean("SMCSwissKnifeShears");
            boolean hasBrush = tag.getBoolean("SMCSwissKnifeBrush");
            if(EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.ADAPTION.get(), itemstack) > 0){
                if(state.getBlock() instanceof BrushableBlock && hasBrush){
                    mode = 6;
                    tag.putInt("SMCSwissKnife", mode);
                    return;
                }

                if(state.is(BlockTags.SWORD_EFFICIENT)){
                    if(hasShears){
                        mode = 5;
                    }else{
                        mode = 0;
                    }
                } else if (state.is(BlockTags.MINEABLE_WITH_AXE)){
                    mode = 1;
                } else if (state.is(BlockTags.MINEABLE_WITH_PICKAXE)){
                    mode = 2;
                } else if (state.is(BlockTags.MINEABLE_WITH_SHOVEL)){
                    mode = 3;
                } else if (state.is(BlockTags.MINEABLE_WITH_HOE)){
                    mode = 4;
                }
                tag.putInt("SMCSwissKnife", mode);
            }
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        BlockPos pos = event.getPos();
        ItemStack item = event.getPlayer().getMainHandItem();
        Player player = event.getPlayer();
        Level level = player.level();
        if (item.is(ItemRegistry.FROSTIUM_PICKAXE.get())) {
            Direction[] directions = Direction.values();
            for (Direction direction1 : directions){
                BlockPos nearPos = pos.relative(direction1);
                BlockState block = level.getBlockState(nearPos);
                if (block.getBlock() instanceof LiquidBlock){
                    if (block.is(Blocks.LAVA)){
                        level.setBlock(nearPos, Blocks.OBSIDIAN.defaultBlockState(), 3);
                        level.playSound((Player)null, nearPos, SoundEvents.LAVA_EXTINGUISH, SoundSource.NEUTRAL, 0.2F, 1.0F);
                    } else if (block.is(Blocks.WATER)){
                        level.setBlock(nearPos, Blocks.ICE.defaultBlockState(), 3);
                        level.playSound((Player)null, nearPos, SoundEvents.GLASS_PLACE, SoundSource.NEUTRAL, 0.2F, 1.0F);
                    } else {
                        level.setBlock(nearPos, Blocks.MUD.defaultBlockState(), 3);
                        level.playSound((Player)null, nearPos, SoundEvents.LAVA_EXTINGUISH, SoundSource.NEUTRAL, 0.2F, 1.0F);
                    }
                }
            }
        }
        if(item.is(ItemRegistry.ZINC_AXE.get()) || item.is(ItemRegistry.ZINC_PICKAXE.get()) || item.is(ItemRegistry.ZINC_SHOVEL.get()) || item.is(ItemRegistry.ZINC_HOE.get())){
            if(item.getTag()!=null && item.getTag().contains("SMCZincPower")){
                if(item.getTag().getInt("SMCZincPower") > 0){
                    int r = 1;
                    float hardness0 = level.getBlockState(pos).getDestroySpeed(level, pos);
                    Direction face = MiningFaceCache.get(player.getUUID());
                    for (BlockPos tmpPos : BlockPos.withinManhattan(pos,
                            face.getStepX() == 0 ? r : 0,
                            face.getStepY() == 0 ? r : 0,
                            face.getStepZ() == 0 ? r : 0)){
                        if(tmpPos != pos){
                            float hardness = level.getBlockState(tmpPos).getDestroySpeed(level, tmpPos);
                            if(hardness >= 0 && hardness0 >= hardness){
                                if (!event.getPlayer().getAbilities().instabuild) {
                                    Block.dropResources(level.getBlockState(tmpPos), level, tmpPos, level.getBlockEntity(tmpPos), event.getPlayer(), item.copy());
                                }
                                level.destroyBlock(tmpPos, false, player);
                            }
                        }
                    }
                }
            }
        }
        if (item.getEnchantmentLevel(EnchantmentRegistry.VIOLENT_ARCHAEOLOGY.get()) > 0) {
            if(level.getBlockEntity(pos) instanceof BrushableBlockEntity brushable){
                brushable.unpackLootTable(event.getPlayer());
                ItemStack drop = brushable.getItem();
                ItemEntity entityToSpawn = new ItemEntity(level, pos.getX() + 0.5D,pos.getY() + 0.5D,pos.getZ() + 0.5D, drop);
                entityToSpawn.setPickUpDelay(10);
                event.getLevel().addFreshEntity(entityToSpawn);
            }
        }
        if(item.is(ItemRegistry.GOD_PICKAXE.get()) && level instanceof ServerLevel serverLevel){
            ItemStack modifiedTool = item.copy();
            if(modifiedTool.getEnchantmentLevel(Enchantments.BLOCK_FORTUNE) < 3){
                Map<Enchantment, Integer> modifiedEnchantments = EnchantmentHelper.getEnchantments(modifiedTool);
                modifiedEnchantments.put(Enchantments.BLOCK_FORTUNE, 3);
                EnchantmentHelper.setEnchantments(modifiedEnchantments, modifiedTool);
            }
            List<ItemStack> drops = ItemHelper.getBlockDrops(serverLevel, pos, event.getPlayer(), modifiedTool);
            List<ItemStack> newDrops = new ArrayList<>();
            for (ItemStack in : drops) {
                if (ItemHelper.itemHasSmeltResult(in, level)) {
                    ItemStack out = ItemHelper.getItemSmeltResult(in, level);
                    if (!out.isEmpty()) {
                        out = out.copy();
                        out.setCount(out.getCount() * in.getCount());
                        newDrops.add(out);
                        continue;
                    }
                }
                newDrops.add(in);
            }
            if(event.getState().getBlock() == Blocks.ANCIENT_DEBRIS){
                ItemStack extraScrap = new ItemStack(Items.NETHERITE_SCRAP);
                newDrops.add(extraScrap);
            }
            level.removeBlock(pos, false);

            for (ItemStack stack : newDrops) {
                if(!event.getPlayer().getAbilities().instabuild){
                    Containers.dropItemStack(level, player.getX(), player.getY(), player.getZ(), stack);
                }
            }
            if(event.getExpToDrop() != 0){
                ExperienceOrb xpOrb = new ExperienceOrb(level, player.getX(), player.getY(), player.getZ(), event.getExpToDrop() * 3);
                level.addFreshEntity(xpOrb);
            }
        }
        if (item.is(ItemRegistry.CHAINSAW.get())) {
            CompoundTag tag = item.getTag();
            if (tag != null && tag.contains("SMCZincPower")) {
                int lim = Math.min(256, tag.getInt("SMCZincPower"));

                if (lim <= 0) return;

                BlockPos startPos = event.getPos();
                Block targetBlock = event.getState().getBlock();

                List<BlockPos> selected = new ArrayList<>();
                Set<BlockPos> visited = new HashSet<>();
                Queue<BlockPos> queue = new ArrayDeque<>();

                selected.add(startPos);
                visited.add(startPos);
                queue.add(startPos);

                while (!queue.isEmpty() && selected.size() < lim) {
                    BlockPos current = queue.poll();
                    for (int x = -1; x <= 1; x++) {
                        for (int y = -1; y <= 1; y++) {
                            for (int z = -1; z <= 1; z++) {
                                if (x == 0 && y == 0 && z == 0) {
                                    continue;
                                }
                                BlockPos next = current.offset(x, y, z);
                                if (!visited.add(next)) {
                                    continue;
                                }
                                BlockState state = level.getBlockState(next);
                                if (state.getBlock() != targetBlock) {
                                    continue;
                                }
                                selected.add(next);
                                queue.add(next);
                                if (selected.size() >= lim) {
                                    break;
                                }
                            }
                            if (selected.size() >= lim) {
                                break;
                            }
                        }
                        if (selected.size() >= lim) {
                            break;
                        }
                    }
                }

                for (BlockPos blockPos : selected) {
                    BlockState state = level.getBlockState(blockPos);
                    if (state.isAir()) {
                        continue;
                    }
                    if (!event.getPlayer().getAbilities().instabuild) {
                        Block.dropResources(state, level, blockPos, level.getBlockEntity(blockPos), event.getPlayer(), item);
                        item.mineBlock(level, state, blockPos, event.getPlayer());
                    }

                    level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onExplosionEvent(ExplosionEvent event) {
        int r = 3;
        for (BlockPos tmpPos : BlockPos.withinManhattan(BlockPos.containing(event.getExplosion().getPosition()), r, r, r)) {
            if(event.getLevel().getBlockState(tmpPos).is(Blocks.BEDROCK)){
                if(event.getLevel().getRandom().nextInt(Config.BEDROCK_RATE.get()) == 0 && Config.ENABLE_BEDROCK.get()){
                    ItemEntity entityToSpawn = new ItemEntity(event.getLevel(), tmpPos.getX() + 0.5D,tmpPos.getY() + 0.5D,tmpPos.getZ() + 0.5D, new ItemStack(ItemRegistry.MINI_BEDROCK.get()));
                    entityToSpawn.setPickUpDelay(10);
                    event.getLevel().addFreshEntity(entityToSpawn);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingJumpEvent(LivingEvent.LivingJumpEvent event) {
        LivingEntity living = event.getEntity();
        if(CuriosHelper.hasCharm(living, ItemRegistry.FOX_TAIL.get())){
            ItemStack tailItem = CuriosHelper.getCharm(living, ItemRegistry.FOX_TAIL.get());
            CompoundTag tag = tailItem.getOrCreateTag();
            if(tag.getInt("SMCTailJump") > 0 && tag.getInt("SMCTailJump") < 10){
                tag.putInt("SMCTailAtkSpeed", 100);
                living.setDeltaMovement(living.getDeltaMovement().add(0, 1, 0));
            }
        }
    }

    @SubscribeEvent
    public static void onItemUseEvent(LivingEntityUseItemEvent.Tick event) {
        LivingEntity living = event.getEntity();
        Level level = living.level();
        if(living.hasEffect(PotionEffectRegistry.OVER_SPEED.get())){
            int amp = Math.min(living.getEffect(PotionEffectRegistry.OVER_SPEED.get()).getAmplifier() + 1, 5);
            if(level.getGameTime() % 2 == 0L){
                event.setDuration(event.getDuration() - amp);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityMobGriefingEvent(EntityMobGriefingEvent event) {
        if (event.getEntity() instanceof Rabbit rabbit){
            Level level = rabbit.level();
            BlockPos pos = rabbit.getOnPos().above();
            if(level.getBlockState(pos).is(Blocks.CARROTS) && level.getBlockState(pos).getBlock() instanceof CropBlock crop){
                if(Math.random() < 0.1f && crop.isMaxAge(level.getBlockState(pos))){
                    ItemEntity entityToSpawn = new ItemEntity(level , rabbit.getX(),rabbit.getY(),rabbit.getZ(), new ItemStack(ItemRegistry.CARROT_PICKAXE.get()));
                    entityToSpawn.setPickUpDelay(10);
                    level.addFreshEntity(entityToSpawn);
                }
            }

        }
    }

    @SubscribeEvent
    public static void onPlayerRightClickBlockForKnife(PlayerInteractEvent.RightClickBlock event) {
        if (event.getHand() != InteractionHand.MAIN_HAND) return;

        Level level = event.getLevel();
        if (!level.isClientSide()){
            Player player = event.getEntity();
            ItemStack stack = event.getItemStack();
            Direction face = event.getFace();
            BlockPos clickedPos = event.getPos();
            BlockPos placePos = clickedPos.relative(face);
            BlockState target = level.getBlockState(placePos);
            boolean placed = false;
            if (!level.mayInteract(player, placePos) || !player.mayUseItemAt(placePos, face, stack) || !target.canBeReplaced()) return;
            if (stack.is(ItemRegistry.KNIFE.get())) {
                BlockState placeState = BlockRegistry.KNIFE.get().defaultBlockState();
                int flags = Block.UPDATE_ALL;
                placed = level.setBlock(placePos, placeState.setValue(HorizontalDirectionalBlock.FACING, player.getDirection().getClockWise()), flags);
            }
            if (stack.is(ItemRegistry.CLEAVER.get())) {
                BlockState placeState = BlockRegistry.CLEAVER.get().defaultBlockState();
                int flags = Block.UPDATE_ALL;
                placed = level.setBlock(placePos, placeState.setValue(HorizontalDirectionalBlock.FACING, player.getDirection().getClockWise()), flags);
            }
            if (placed){
                level.playSound(null, placePos, SoundEvents.ANVIL_PLACE, SoundSource.BLOCKS, 1.0F, 2.0F);
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                event.setCancellationResult(InteractionResult.SUCCESS);
                event.setCanceled(true);
            }
        }

    }

    @SubscribeEvent
    public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
        if(event.getEntity() instanceof Player player){
            if (!player.isCreative() && !player.isSpectator() && event.getFrom().is(ItemRegistry.DIVINE_WING.get()) && !event.getTo().is(ItemRegistry.DIVINE_WING.get())) {
                player.getAbilities().mayfly = false;
                player.getAbilities().flying = false;
                player.onUpdateAbilities();
            }
        }
    }

    @SubscribeEvent
    public static void onFoodConsume(LivingEntityUseItemEvent.Finish event) {
        if(!event.getEntity().level().isClientSide()){
            if(event.getItem().is(Items.APPLE) && event.getEntity() instanceof Player player){
                boolean flag = false;
                boolean badOmen = player.hasEffect(MobEffects.BAD_OMEN);
                if(player.getRandom().nextInt(100) < Config.BAD_APPLE_RATE.get() && !badOmen){
                    flag = true;
                } else if (player.getRandom().nextInt(100) < Config.BAD_APPLE_RATE_WITH_EFFECT.get() && badOmen){
                    flag = true;
                    int time = player.getEffect(MobEffects.BAD_OMEN).getDuration();
                    int amp = player.getEffect(MobEffects.BAD_OMEN).getAmplifier();
                    player.removeEffect(MobEffects.BAD_OMEN);
                    if(amp != 0){
                        player.addEffect(new MobEffectInstance(MobEffects.BAD_OMEN, time, amp - 1));
                    }
                }
                if(flag){
                    ItemStack itemStack = new ItemStack(ItemRegistry.BAD_APPLE.get());
                    if (!player.getInventory().add(itemStack)) {
                        player.drop(itemStack, false);
                    }
                }
            }
        }
    }

    /*
    @SubscribeEvent
    public static void onPlayerOpenContainerEvent(PlayerContainerEvent.Open event) {
        event.getEntity().level().playSound((Player)null, event.getEntity().getOnPos(), SoundEvents.ANVIL_USE, SoundSource.NEUTRAL, 0.2F, 1.0F);
    }
     */
    /*
    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickItem event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) return;

        ItemStack held = event.getItemStack();

        List<ItemStack> inputs = SmeltingReverseLookup.getAllSmeltingInputsForResult(serverLevel, held);

        if (inputs.isEmpty()) {
            event.getEntity().sendSystemMessage(Component.literal("该物品没有找到对应的熔炼原料。"));
        } else {
            event.getEntity().sendSystemMessage(Component.literal("可能的原料包括："));
            for (ItemStack input : inputs) {
                event.getEntity().sendSystemMessage(Component.literal("- " + input.getDisplayName().getString()));
            }
        }
    }
     */
    @SubscribeEvent
    public static void onChangeTarget(LivingChangeTargetEvent event) {
        if (!event.getEntity().level().isClientSide && event.getEntity() instanceof Mob mob){
            //if(mob.hasEffect(PotionEffectRegistry.NO_PEACE.get())) return;
            LivingEntity living = event.getNewTarget();
            if(living != null && living.hasEffect(PotionEffectRegistry.PEACE.get())){
                event.setCanceled(true);
                mob.setTarget(null);
                if (mob instanceof NeutralMob neutral) {
                    neutral.stopBeingAngry();
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerRightClickEntity(PlayerInteractEvent.EntityInteract event) {
        if(!event.getLevel().isClientSide){
            Player player = event.getEntity();
            if(event.getEntity().getMainHandItem().is(Items.BOWL) && event.getTarget() instanceof SnowGolem snowGolem){
                if(event.getLevel() instanceof ServerLevel serverLevel){
                    serverLevel.sendParticles(ParticleTypes.CLOUD, snowGolem.getX(), snowGolem.getY(), snowGolem.getZ(), 200, 1.0D, 1.0D, 1.0D, 0.0D);
                }
                snowGolem.playSound(SoundEvents.BUCKET_FILL_POWDER_SNOW, 1, 1);
                snowGolem.discard();
                ItemStack serving = new ItemStack(ItemRegistry.SNOW_GOLEM_ICE_CREAM.get());
                if (!player.getInventory().add(serving)) {
                    player.drop(serving, false);
                }
                if (!event.getEntity().getAbilities().instabuild) {
                    event.getEntity().getMainHandItem().shrink(1);
                }
            }
            if (event.getItemStack().is(ItemRegistry.GIANT_CARROT.get())){
                boolean flag = false;
                if(event.getTarget() instanceof Rabbit rabbit){
                    RandomSource random = rabbit.getRandom();
                    int count = random.nextInt(32) + 32;
                    ArrayList<ItemStack> drops = new ArrayList<>();
                    if(rabbit.getVariant() == Rabbit.Variant.EVIL){
                        flag = true;
                        List<Item> items = Config.whitelistSwordItems.stream().filter(item -> item instanceof SwordItem sword && sword.getDamage() >= Config.KNIFE_MIN_ATK.get() && sword.getDamage() < Config.KNIFE_MAX_ATK.get() && item != ItemRegistry.KNIFE.get()).toList();
                        for(int i = 0; i < count / 4; i++){
                            ItemStack randomItem = new ItemStack(items.get(random.nextInt(items.size())));
                            drops.add(randomItem);
                        }
                    } else if (rabbit instanceof EasterBunny) {
                        flag = true;
                        List<Item> items = Config.easterBunnyEggItems;
                        for(int i = 0; i < count; i++){
                            ItemStack randomItem = new ItemStack(items.get(random.nextInt(items.size())));
                            drops.add(randomItem);
                        }
                    } else {
                        flag = true;
                        List<Item> items = List.of(Items.RABBIT, Items.RABBIT, Items.RABBIT_HIDE, Items.RABBIT_HIDE, Items.RABBIT_HIDE, Items.RABBIT_FOOT);
                        for(int i = 0; i < count; i++){
                            ItemStack randomItem = new ItemStack(items.get(random.nextInt(items.size())));
                            drops.add(randomItem);
                        }
                    }
                    if(event.getLevel() instanceof ServerLevel serverLevel){
                        serverLevel.sendParticles(ParticleTypes.EXPLOSION, rabbit.getX(), rabbit.getY() + rabbit.getBbHeight(), rabbit.getZ(), 25, 3, 3, 3, 0.0D);
                    }
                    event.getLevel().playSound(null, event.getPos(), SoundEvents.DRAGON_FIREBALL_EXPLODE, SoundSource.PLAYERS, 1.0F, 2.0F);
                    for(ItemStack itemStack : drops){
                        ItemEntity itemEntity = new ItemEntity(event.getLevel(), rabbit.getX(), rabbit.getY(), rabbit.getZ(), itemStack);
                        itemEntity.setDeltaMovement(random.nextFloat() * 2.0F - 1.0F, random.nextFloat() * 1.5F, random.nextFloat() * 2.0F - 1.0F);
                        event.getLevel().addFreshEntity(itemEntity);
                    }
                    rabbit.setHealth(0);
                }
                if(flag && !player.getAbilities().instabuild){
                    event.getItemStack().shrink(1);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onMount(EntityMountEvent event) {
        if (event.isDismounting() && event.getEntityBeingMounted() instanceof Boat boat){
            if (!(event.getEntityMounting() instanceof ServerPlayer player)) return;
            if(boat.isAlive() && !boat.isRemoved() && boat.getVariant() == Boat.Type.byName("end")){
                if (!boat.onGround() && !boat.isInWater()){
                    event.setCanceled(true);
                    player.connection.send(new ClientboundSetPassengersPacket(boat));
                }
            }
        }
    }
}
