package com.starmeow.smc.items;

import com.mojang.datafixers.util.Pair;
import com.starmeow.smc.init.EnchantmentRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class SwissArmyKnife extends SwordItem {
    protected final float speed;

    public SwissArmyKnife(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
        this.speed = p_43269_.getSpeed();
    }

    @Override
    public boolean canAttackBlock(BlockState p_43291_, Level p_43292_, BlockPos p_43293_, Player p_43294_) {
        return true;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand p_41130_) {
        ItemStack itemstack = player.getItemInHand(p_41130_);
        if(player.isCrouching()){
            CompoundTag tag = itemstack.getOrCreateTag();
            int mode = tag.getInt("SMCSwissKnife");
            boolean hasShears = tag.getBoolean("SMCSwissKnifeShears");
            boolean hasBrush = tag.getBoolean("SMCSwissKnifeBrush");
            mode++;
            if(!hasShears && mode == 5){
                mode++;
            }
            if(!hasBrush && mode == 6){
                mode++;
            }
            tag.putInt("SMCSwissKnife", mode > 6 ? 0 : mode);

            player.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.success(itemstack);
        }
        return InteractionResultHolder.pass(itemstack);
    }

    @Override
    public boolean hurtEnemy(ItemStack p_43278_, LivingEntity p_43279_, LivingEntity p_43280_) {
        if(EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.ADAPTION.get(), p_43278_) > 0){
            CompoundTag tag = p_43278_.getOrCreateTag();
            tag.putInt("SMCSwissKnife", 0);
        }
        p_43278_.hurtAndBreak(1, p_43280_, (p_43296_) -> {
            p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }

    @Override
    public void inventoryTick(ItemStack itemstack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, level, entity, slot, selected);
        if(!level.isClientSide() && entity instanceof Player player) {
            CompoundTag tag = itemstack.getOrCreateTag();
            int mode = tag.getInt("SMCSwissKnife");
            int oldMode = tag.getInt("SMCSwissKnifeOld");
            if(mode != oldMode){
                level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 1.0F, 1.0F);
                oldMode = mode;
                tag.putInt("SMCSwissKnifeOld", oldMode);
            }
        }
    }

    public UseAnim getUseAnimation(ItemStack p_273490_) {
        return UseAnim.BRUSH;
    }

    public int getUseDuration(ItemStack p_272765_) {
        return 200;
    }

    @Override
    public boolean mineBlock(ItemStack p_40998_, Level p_40999_, BlockState p_41000_, BlockPos p_41001_, LivingEntity p_41002_) {
        if(p_40998_.getOrCreateTag().getInt("SMCSwissKnife") == 5){
            if (!p_40999_.isClientSide && !p_41000_.is(BlockTags.FIRE)) {
                p_40998_.hurtAndBreak(1, p_41002_, (p_43076_) -> {
                    p_43076_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                });
            }

            return p_41000_.is(BlockTags.LEAVES) || p_41000_.is(Blocks.COBWEB) || p_41000_.is(Blocks.GRASS) || p_41000_.is(Blocks.FERN) || p_41000_.is(Blocks.DEAD_BUSH) || p_41000_.is(Blocks.HANGING_ROOTS) || p_41000_.is(Blocks.VINE) || p_41000_.is(Blocks.TRIPWIRE) || p_41000_.is(BlockTags.WOOL) || super.mineBlock(p_40998_, p_40999_, p_41000_, p_41001_, p_41002_);
        }
        if (!p_40999_.isClientSide && p_41000_.getDestroySpeed(p_40999_, p_41001_) != 0.0F) {
            p_40998_.hurtAndBreak(1, p_41002_, (p_40992_) -> {
                p_40992_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }
        return true;
    }

    @Override
    public float getDestroySpeed(ItemStack itemstack, BlockState p_43289_) {
        CompoundTag tag = itemstack.getOrCreateTag();
        int mode = tag.getInt("SMCSwissKnife");
        switch(mode){
            case 1 -> {
                return p_43289_.is(BlockTags.MINEABLE_WITH_AXE) ? this.speed : 1.0F;
            }
            case 2 -> {
                return p_43289_.is(BlockTags.MINEABLE_WITH_PICKAXE) ? this.speed : 1.0F;
            }
            case 3 -> {
                return p_43289_.is(BlockTags.MINEABLE_WITH_SHOVEL) ? this.speed : 1.0F;
            }
            case 4 -> {
                return p_43289_.is(BlockTags.MINEABLE_WITH_HOE) ? this.speed : 1.0F;
            }
            case 5 -> {
                return getDestroySpeedInShearsMode(itemstack, p_43289_);
            }
            case 6 -> {
                return p_43289_.getBlock() instanceof BrushableBlock ? 0.1F : 1.0F;
            }
            default -> {
                return p_43289_.is(BlockTags.SWORD_EFFICIENT) ? this.speed : 1.0F;
            }
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext p_41341_) {
        CompoundTag tag = p_41341_.getItemInHand().getOrCreateTag();
        int mode = tag.getInt("SMCSwissKnife");
        if(!p_41341_.getPlayer().isCrouching()){
            if(mode == 1){
                this.useOnInAxeMode(p_41341_);
            } else if(mode == 4){
                this.useOnInHoeMode(p_41341_);
            } else if(mode == 3){
                this.useOnInShovelMode(p_41341_);
            } else if(mode == 5){
                this.useOnInShearsMode(p_41341_);
            } else if(mode == 6){
                this.useOnInBrushMode(p_41341_);
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean canPerformAction(ItemStack itemstack, ToolAction toolAction) {
        CompoundTag tag = itemstack.getOrCreateTag();
        int mode = tag.getInt("SMCSwissKnife");
        switch(mode){
            case 0 -> {
                return ToolActions.DEFAULT_SWORD_ACTIONS.contains(toolAction);
            }
            case 1 -> {
                return ToolActions.DEFAULT_AXE_ACTIONS.contains(toolAction);
            }
            case 2 -> {
                return ToolActions.DEFAULT_PICKAXE_ACTIONS.contains(toolAction);
            }
            case 3 -> {
                return ToolActions.DEFAULT_SHOVEL_ACTIONS.contains(toolAction);
            }
            case 4 -> {
                return ToolActions.DEFAULT_HOE_ACTIONS.contains(toolAction);
            }
            case 5 -> {
                return ToolActions.DEFAULT_SHEARS_ACTIONS.contains(toolAction);
            }
        }
        return ToolActions.DEFAULT_SWORD_ACTIONS.contains(toolAction);
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return this.canApplyEnchantment(EnchantmentHelper.getEnchantments(stack).keySet().toArray(new Enchantment[0])) || super.isBookEnchantable(stack, book);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return this.canApplyEnchantment(enchantment) || super.canApplyAtEnchantingTable(stack, enchantment);
    }

    private boolean canApplyEnchantment(Enchantment... enchantments) {
        for (Enchantment enchantment : enchantments) {
            if (enchantment.category == EnchantmentCategory.WEAPON
                    || enchantment.category == EnchantmentCategory.DIGGER
                    || enchantment.category == EnchantmentRegistry.SWISS_ARMY_KNIFE
                    || enchantment.category == EnchantmentCategory.BREAKABLE
                    || enchantment.category == EnchantmentCategory.VANISHABLE)
                return true;
        }
        return false;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack itemstack, BlockState state) {
        CompoundTag tag = itemstack.getOrCreateTag();
        int mode = tag.getInt("SMCSwissKnife");
        boolean tier = TierSortingRegistry.isCorrectTierForDrops(this.getTier(), state);
        switch(mode){
            case 1 -> {
                return state.is(BlockTags.MINEABLE_WITH_AXE) && tier;
            }
            case 2 -> {
                return state.is(BlockTags.MINEABLE_WITH_PICKAXE) && tier;
            }
            case 3 -> {
                return state.is(BlockTags.MINEABLE_WITH_SHOVEL) && tier;
            }
            case 4 -> {
                return state.is(BlockTags.MINEABLE_WITH_HOE) && tier;
            }
            default -> {
                return state.is(BlockTags.SWORD_EFFICIENT) && tier;
            }
        }
    }


    //From HoeItem

    public InteractionResult useOnInHoeMode(UseOnContext p_41341_) {
        Level level = p_41341_.getLevel();
        BlockPos blockpos = p_41341_.getClickedPos();
        BlockState toolModifiedState = level.getBlockState(blockpos).getToolModifiedState(p_41341_, ToolActions.HOE_TILL, false);
        Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> pair = toolModifiedState == null ? null : Pair.of((ctx) -> {
            return true;
        }, changeIntoState(toolModifiedState));
        if (pair == null) {
            return InteractionResult.PASS;
        } else {
            Predicate<UseOnContext> predicate = (Predicate)pair.getFirst();
            Consumer<UseOnContext> consumer = (Consumer)pair.getSecond();
            if (predicate.test(p_41341_)) {
                Player player = p_41341_.getPlayer();
                level.playSound(player, blockpos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!level.isClientSide) {
                    consumer.accept(p_41341_);
                    if (player != null) {
                        p_41341_.getItemInHand().hurtAndBreak(1, player, (p_150845_) -> {
                            p_150845_.broadcastBreakEvent(p_41341_.getHand());
                        });
                    }
                }

                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    public static Consumer<UseOnContext> changeIntoState(BlockState p_150859_) {
        return (p_238241_) -> {
            p_238241_.getLevel().setBlock(p_238241_.getClickedPos(), p_150859_, 11);
            p_238241_.getLevel().gameEvent(GameEvent.BLOCK_CHANGE, p_238241_.getClickedPos(), GameEvent.Context.of(p_238241_.getPlayer(), p_150859_));
        };
    }

    //From Axe Item
    public InteractionResult useOnInAxeMode(UseOnContext p_40529_) {
        Level level = p_40529_.getLevel();
        BlockPos blockpos = p_40529_.getClickedPos();
        Player player = p_40529_.getPlayer();
        BlockState blockstate = level.getBlockState(blockpos);
        Optional<BlockState> optional = Optional.ofNullable(blockstate.getToolModifiedState(p_40529_, ToolActions.AXE_STRIP, false));
        Optional<BlockState> optional1 = optional.isPresent() ? Optional.empty() : Optional.ofNullable(blockstate.getToolModifiedState(p_40529_, ToolActions.AXE_SCRAPE, false));
        Optional<BlockState> optional2 = !optional.isPresent() && !optional1.isPresent() ? Optional.ofNullable(blockstate.getToolModifiedState(p_40529_, ToolActions.AXE_WAX_OFF, false)) : Optional.empty();
        ItemStack itemstack = p_40529_.getItemInHand();
        Optional<BlockState> optional3 = Optional.empty();
        if (optional.isPresent()) {
            level.playSound(player, blockpos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
            optional3 = optional;
        } else if (optional1.isPresent()) {
            level.playSound(player, blockpos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.levelEvent(player, 3005, blockpos, 0);
            optional3 = optional1;
        } else if (optional2.isPresent()) {
            level.playSound(player, blockpos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.levelEvent(player, 3004, blockpos, 0);
            optional3 = optional2;
        }

        if (optional3.isPresent()) {
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockpos, itemstack);
            }

            level.setBlock(blockpos, (BlockState)optional3.get(), 11);
            level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(player, (BlockState)optional3.get()));
            if (player != null) {
                itemstack.hurtAndBreak(1, player, (p_150686_) -> {
                    p_150686_.broadcastBreakEvent(p_40529_.getHand());
                });
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }

    //From Shovel Item
    public InteractionResult useOnInShovelMode(UseOnContext p_43119_) {
        Level level = p_43119_.getLevel();
        BlockPos blockpos = p_43119_.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        if (p_43119_.getClickedFace() == Direction.DOWN) {
            return InteractionResult.PASS;
        } else {
            Player player = p_43119_.getPlayer();
            BlockState blockstate1 = blockstate.getToolModifiedState(p_43119_, ToolActions.SHOVEL_FLATTEN, false);
            BlockState blockstate2 = null;
            if (blockstate1 != null && level.isEmptyBlock(blockpos.above())) {
                level.playSound(player, blockpos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
                blockstate2 = blockstate1;
            } else if (blockstate.getBlock() instanceof CampfireBlock && (Boolean)blockstate.getValue(CampfireBlock.LIT)) {
                if (!level.isClientSide()) {
                    level.levelEvent((Player)null, 1009, blockpos, 0);
                }

                CampfireBlock.dowse(p_43119_.getPlayer(), level, blockpos, blockstate);
                blockstate2 = (BlockState)blockstate.setValue(CampfireBlock.LIT, false);
            }

            if (blockstate2 != null) {
                if (!level.isClientSide) {
                    level.setBlock(blockpos, blockstate2, 11);
                    level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(player, blockstate2));
                    if (player != null) {
                        p_43119_.getItemInHand().hurtAndBreak(1, player, (p_43122_) -> {
                            p_43122_.broadcastBreakEvent(p_43119_.getHand());
                        });
                    }
                }

                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    //From brush item
    private static final double MAX_BRUSH_DISTANCE;

    public InteractionResult useOnInBrushMode(UseOnContext p_272607_) {
        Player $$1 = p_272607_.getPlayer();
        if ($$1 != null && this.calculateHitResult($$1).getType() == HitResult.Type.BLOCK) {
            $$1.startUsingItem(p_272607_.getHand());
        }

        return InteractionResult.CONSUME;
    }

    public void onUseTick(Level p_273467_, LivingEntity p_273619_, ItemStack p_273316_, int p_273101_) {
        if (p_273101_ >= 0 && p_273619_ instanceof Player $$5) {
            HitResult $$6 = this.calculateHitResult(p_273619_);
            if ($$6 instanceof BlockHitResult $$8) {
                if ($$6.getType() == HitResult.Type.BLOCK) {
                    int $$9 = this.getUseDuration(p_273316_) - p_273101_ + 1;
                    boolean $$10 = $$9 % 10 == 5;
                    if ($$10) {
                        BlockPos $$11 = $$8.getBlockPos();
                        BlockState $$12 = p_273467_.getBlockState($$11);
                        HumanoidArm $$13 = p_273619_.getUsedItemHand() == InteractionHand.MAIN_HAND ? $$5.getMainArm() : $$5.getMainArm().getOpposite();
                        this.spawnDustParticles(p_273467_, $$8, $$12, p_273619_.getViewVector(0.0F), $$13);
                        Block var15 = $$12.getBlock();
                        SoundEvent $$16;
                        if (var15 instanceof BrushableBlock) {
                            BrushableBlock $$14 = (BrushableBlock)var15;
                            $$16 = $$14.getBrushSound();
                        } else {
                            $$16 = SoundEvents.BRUSH_GENERIC;
                        }

                        p_273467_.playSound($$5, $$11, $$16, SoundSource.BLOCKS);
                        if (!p_273467_.isClientSide()) {
                            BlockEntity var18 = p_273467_.getBlockEntity($$11);
                            if (var18 instanceof BrushableBlockEntity) {
                                BrushableBlockEntity $$17 = (BrushableBlockEntity)var18;
                                boolean $$18 = $$17.brush(p_273467_.getGameTime(), $$5, $$8.getDirection());
                                if ($$18) {
                                    EquipmentSlot $$19 = p_273316_.equals($$5.getItemBySlot(EquipmentSlot.OFFHAND)) ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
                                    p_273316_.hurtAndBreak(1, p_273619_, (p_279044_) -> {
                                        p_279044_.broadcastBreakEvent($$19);
                                    });
                                }
                            }
                        }
                    }

                    return;
                }
            }

            p_273619_.releaseUsingItem();
        } else {
            p_273619_.releaseUsingItem();
        }
    }

    private HitResult calculateHitResult(LivingEntity p_281264_) {
        return ProjectileUtil.getHitResultOnViewVector(p_281264_, (p_281111_) -> {
            return !p_281111_.isSpectator() && p_281111_.isPickable();
        }, MAX_BRUSH_DISTANCE);
    }

    public void spawnDustParticles(Level p_278327_, BlockHitResult p_278272_, BlockState p_278235_, Vec3 p_278337_, HumanoidArm p_285071_) {
        double $$5 = 3.0;
        int $$6 = p_285071_ == HumanoidArm.RIGHT ? 1 : -1;
        int $$7 = p_278327_.getRandom().nextInt(7, 12);
        BlockParticleOption $$8 = new BlockParticleOption(ParticleTypes.BLOCK, p_278235_);
        Direction $$9 = p_278272_.getDirection();
        SwissArmyKnife.DustParticlesDelta $$10 = SwissArmyKnife.DustParticlesDelta.fromDirection(p_278337_, $$9);
        Vec3 $$11 = p_278272_.getLocation();

        for(int $$12 = 0; $$12 < $$7; ++$$12) {
            p_278327_.addParticle($$8, $$11.x - (double)($$9 == Direction.WEST ? 1.0E-6F : 0.0F), $$11.y, $$11.z - (double)($$9 == Direction.NORTH ? 1.0E-6F : 0.0F), $$10.xd() * (double)$$6 * 3.0 * p_278327_.getRandom().nextDouble(), 0.0, $$10.zd() * (double)$$6 * 3.0 * p_278327_.getRandom().nextDouble());
        }

    }

    static {
        MAX_BRUSH_DISTANCE = Math.sqrt(ServerGamePacketListenerImpl.MAX_INTERACTION_DISTANCE) - 1.0;
    }

    static record DustParticlesDelta(double xd, double yd, double zd) {
        private static final double ALONG_SIDE_DELTA = 1.0;
        private static final double OUT_FROM_SIDE_DELTA = 0.1;

        public static SwissArmyKnife.DustParticlesDelta fromDirection(Vec3 p_273421_, Direction p_272987_) {
            double $$2 = 0.0;
            SwissArmyKnife.DustParticlesDelta var10000;
            switch (p_272987_) {
                case DOWN:
                case UP:
                    var10000 = new SwissArmyKnife.DustParticlesDelta(p_273421_.z(), 0.0, -p_273421_.x());
                    break;
                case NORTH:
                    var10000 = new SwissArmyKnife.DustParticlesDelta(1.0, 0.0, -0.1);
                    break;
                case SOUTH:
                    var10000 = new SwissArmyKnife.DustParticlesDelta(-1.0, 0.0, 0.1);
                    break;
                case WEST:
                    var10000 = new SwissArmyKnife.DustParticlesDelta(-0.1, 0.0, -1.0);
                    break;
                case EAST:
                    var10000 = new SwissArmyKnife.DustParticlesDelta(0.1, 0.0, 1.0);
                    break;
                default:
                    throw new IncompatibleClassChangeError();
            }

            return var10000;
        }

        public double xd() {
            return this.xd;
        }

        public double yd() {
            return this.yd;
        }

        public double zd() {
            return this.zd;
        }
    }

    //From Shears
    public boolean isCorrectToolForDrops(BlockState p_43087_) {
        return p_43087_.is(Blocks.COBWEB) || p_43087_.is(Blocks.REDSTONE_WIRE) || p_43087_.is(Blocks.TRIPWIRE);
    }

    public float getDestroySpeedInShearsMode(ItemStack p_43084_, BlockState p_43085_) {
        if (!p_43085_.is(Blocks.COBWEB) && !p_43085_.is(BlockTags.LEAVES)) {
            if (p_43085_.is(BlockTags.WOOL)) {
                return 5.0F;
            } else {
                return !p_43085_.is(Blocks.VINE) && !p_43085_.is(Blocks.GLOW_LICHEN) ? super.getDestroySpeed(p_43084_, p_43085_) : 2.0F;
            }
        } else {
            return 15.0F;
        }
    }

    public InteractionResult interactLivingEntity(ItemStack stack, Player playerIn, LivingEntity entity, InteractionHand hand) {
        CompoundTag tag = stack.getOrCreateTag();
        int mode = tag.getInt("SMCSwissKnife");
        if (entity instanceof IForgeShearable target && mode == 5) {
            if (entity.level().isClientSide) {
                return InteractionResult.SUCCESS;
            } else {
                BlockPos pos = BlockPos.containing(entity.position());
                if (target.isShearable(stack, entity.level(), pos)) {
                    List<ItemStack> drops = target.onSheared(playerIn, stack, entity.level(), pos, EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, stack));
                    Random rand = new Random();
                    drops.forEach((d) -> {
                        ItemEntity ent = entity.spawnAtLocation(d, 1.0F);
                        ent.setDeltaMovement(ent.getDeltaMovement().add((double)((rand.nextFloat() - rand.nextFloat()) * 0.1F), (double)(rand.nextFloat() * 0.05F), (double)((rand.nextFloat() - rand.nextFloat()) * 0.1F)));
                    });
                    stack.hurtAndBreak(1, playerIn, (e) -> {
                        e.broadcastBreakEvent(hand);
                    });
                }

                return InteractionResult.SUCCESS;
            }
        } else {
            return InteractionResult.PASS;
        }
    }

    public InteractionResult useOnInShearsMode(UseOnContext p_186371_) {
        Level level = p_186371_.getLevel();
        BlockPos blockpos = p_186371_.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        Block block = blockstate.getBlock();
        if (block instanceof GrowingPlantHeadBlock growingplantheadblock) {
            if (!growingplantheadblock.isMaxAge(blockstate)) {
                Player player = p_186371_.getPlayer();
                ItemStack itemstack = p_186371_.getItemInHand();
                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockpos, itemstack);
                }

                level.playSound(player, blockpos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0F, 1.0F);
                BlockState blockstate1 = growingplantheadblock.getMaxAgeState(blockstate);
                level.setBlockAndUpdate(blockpos, blockstate1);
                level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(p_186371_.getPlayer(), blockstate1));
                if (player != null) {
                    itemstack.hurtAndBreak(1, player, (p_186374_) -> {
                        p_186374_.broadcastBreakEvent(p_186371_.getHand());
                    });
                }

                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }

        return super.useOn(p_186371_);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        CompoundTag tag = stack.getOrCreateTag();
        boolean hasShears = tag.getBoolean("SMCSwissKnifeShears");
        boolean hasBrush = tag.getBoolean("SMCSwissKnifeBrush");
        boolean hasFlint = tag.getBoolean("SMCSwissKnifeFlint");
        if(hasShears)tooltip.add(Component.translatable("tooltip.smc.swiss_army_knife_shears").withStyle(ChatFormatting.BLUE));
        if(hasBrush)tooltip.add(Component.translatable("tooltip.smc.swiss_army_knife_brush").withStyle(ChatFormatting.BLUE));
        if(hasFlint)tooltip.add(Component.translatable("tooltip.smc.swiss_army_knife_flint").withStyle(ChatFormatting.BLUE));
    }
}
