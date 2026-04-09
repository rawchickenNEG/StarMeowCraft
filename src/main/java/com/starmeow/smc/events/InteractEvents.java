package com.starmeow.smc.events;

import com.starmeow.smc.init.BlockRegistry;
import com.starmeow.smc.init.EnchantmentRegistry;
import com.starmeow.smc.init.ItemRegistry;
import com.starmeow.smc.init.PotionEffectRegistry;
import com.starmeow.smc.items.SwissArmyKnife;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.EntityMobGriefingEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
        Level level = event.getPlayer().level();
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
    }

    @SubscribeEvent
    public static void onExplosionEvent(ExplosionEvent event) {
        int r = 3;
        for (BlockPos tmpPos : BlockPos.withinManhattan(BlockPos.containing(event.getExplosion().getPosition()), r, r, r)) {
            if(event.getLevel().getBlockState(tmpPos).is(Blocks.BEDROCK)){
                if(event.getLevel().getRandom().nextInt(128000) == 0){
                    ItemEntity entityToSpawn = new ItemEntity(event.getLevel(), tmpPos.getX() + 0.5D,tmpPos.getY() + 0.5D,tmpPos.getZ() + 0.5D, new ItemStack(ItemRegistry.MINI_BEDROCK.get()));
                    entityToSpawn.setPickUpDelay(10);
                    event.getLevel().addFreshEntity(entityToSpawn);
                }
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
    public static void onMount(EntityMountEvent event) {
        if (!event.isDismounting()) return;
        if (!(event.getEntityMounting() instanceof ServerPlayer player)) return;

        Entity vehicle = event.getEntityBeingMounted();

        if (!vehicle.isAlive() || vehicle.isRemoved()) return;

        if(vehicle instanceof Boat boat && boat.getVariant() == Boat.Type.byName("end")){
            if (!boat.onGround() && !boat.isInWater()){
                event.setCanceled(true);
            }
        }
        player.connection.send(new ClientboundSetPassengersPacket(vehicle));
    }
}
