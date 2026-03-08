package com.starmeow.smc.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.starmeow.smc.entities.projectiles.ThrownSpear;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;

public class PerkinSpear extends Item implements Vanishable {
    public static final int THROW_THRESHOLD_TIME = 10;
    public static final float BASE_DAMAGE = 7.0F;
    public static final float SHOOT_POWER = 2.5F;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public PerkinSpear(Item.Properties p_43381_) {
        super(p_43381_);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> $$1 = ImmutableMultimap.builder();
        $$1.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 7.0, AttributeModifier.Operation.ADDITION));
        $$1.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", -3.0, AttributeModifier.Operation.ADDITION));
        $$1.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(UUID.fromString("12345678-1234-1244-1234-176545624534"), "Tool modifier", 3.0, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = $$1.build();
    }

    public boolean canAttackBlock(BlockState p_43409_, Level p_43410_, BlockPos p_43411_, Player p_43412_) {
        return !p_43412_.isCreative();
    }

    public UseAnim getUseAnimation(ItemStack p_43417_) {
        return UseAnim.SPEAR;
    }

    public int getUseDuration(ItemStack p_43419_) {
        return 72000;
    }

    public void releaseUsing(ItemStack p_43394_, Level p_43395_, LivingEntity p_43396_, int p_43397_) {
        if (p_43396_ instanceof Player $$4) {
            int $$5 = this.getUseDuration(p_43394_) - p_43397_;
            if ($$5 >= 10) {
                int $$6 = EnchantmentHelper.getRiptide(p_43394_);
                if ($$6 <= 0 || $$4.isInWaterOrRain()) {
                    if (!p_43395_.isClientSide) {
                        p_43394_.hurtAndBreak(1, $$4, (p_43388_) -> {
                            p_43388_.broadcastBreakEvent(p_43396_.getUsedItemHand());
                        });
                        if ($$6 == 0) {
                            ThrownSpear $$7 = new ThrownSpear(p_43395_, $$4, p_43394_);
                            $$7.shootFromRotation($$4, $$4.getXRot(), $$4.getYRot(), 0.0F, 2.5F + (float)$$6 * 0.5F, 1.0F);
                            if ($$4.getAbilities().instabuild) {
                                $$7.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                            }

                            p_43395_.addFreshEntity($$7);
                            p_43395_.playSound((Player)null, $$7, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
                            if (!$$4.getAbilities().instabuild) {
                                $$4.getInventory().removeItem(p_43394_);
                            }
                        }
                    }

                    $$4.awardStat(Stats.ITEM_USED.get(this));
                    if ($$6 > 0) {
                        float $$8 = $$4.getYRot();
                        float $$9 = $$4.getXRot();
                        float $$10 = -Mth.sin($$8 * 0.017453292F) * Mth.cos($$9 * 0.017453292F);
                        float $$11 = -Mth.sin($$9 * 0.017453292F);
                        float $$12 = Mth.cos($$8 * 0.017453292F) * Mth.cos($$9 * 0.017453292F);
                        float $$13 = Mth.sqrt($$10 * $$10 + $$11 * $$11 + $$12 * $$12);
                        float $$14 = 3.0F * ((1.0F + (float)$$6) / 4.0F);
                        $$10 *= $$14 / $$13;
                        $$11 *= $$14 / $$13;
                        $$12 *= $$14 / $$13;
                        $$4.push((double)$$10, (double)$$11, (double)$$12);
                        $$4.startAutoSpinAttack(20);
                        if ($$4.onGround()) {
                            float $$15 = 1.1999999F;
                            $$4.move(MoverType.SELF, new Vec3(0.0, 1.1999999284744263, 0.0));
                        }

                        SoundEvent $$18;
                        if ($$6 >= 3) {
                            $$18 = SoundEvents.TRIDENT_RIPTIDE_3;
                        } else if ($$6 == 2) {
                            $$18 = SoundEvents.TRIDENT_RIPTIDE_2;
                        } else {
                            $$18 = SoundEvents.TRIDENT_RIPTIDE_1;
                        }

                        p_43395_.playSound((Player)null, $$4, $$18, SoundSource.PLAYERS, 1.0F, 1.0F);
                    }

                }
            }
        }
    }

    public InteractionResultHolder<ItemStack> use(Level p_43405_, Player p_43406_, InteractionHand p_43407_) {
        ItemStack $$3 = p_43406_.getItemInHand(p_43407_);
        if ($$3.getDamageValue() >= $$3.getMaxDamage() - 1) {
            return InteractionResultHolder.fail($$3);
        } else if (EnchantmentHelper.getRiptide($$3) > 0 && !p_43406_.isInWaterOrRain()) {
            return InteractionResultHolder.fail($$3);
        } else {
            p_43406_.startUsingItem(p_43407_);
            return InteractionResultHolder.consume($$3);
        }
    }

    public boolean hurtEnemy(ItemStack p_43390_, LivingEntity p_43391_, LivingEntity p_43392_) {
        p_43390_.hurtAndBreak(1, p_43392_, (p_43414_) -> {
            p_43414_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }

    public boolean mineBlock(ItemStack p_43399_, Level p_43400_, BlockState p_43401_, BlockPos p_43402_, LivingEntity p_43403_) {
        if ((double)p_43401_.getDestroySpeed(p_43400_, p_43402_) != 0.0) {
            p_43399_.hurtAndBreak(2, p_43403_, (p_43385_) -> {
                p_43385_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }

        return true;
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot p_43383_) {
        return p_43383_ == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(p_43383_);
    }

    public int getEnchantmentValue() {
        return 22;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.category == EnchantmentCategory.TRIDENT;
    }
}
