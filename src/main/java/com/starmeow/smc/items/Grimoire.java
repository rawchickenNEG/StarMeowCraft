package com.starmeow.smc.items;

import com.starmeow.smc.entities.projectiles.GrimoireBullet;
import com.starmeow.smc.init.EnchantmentRegistry;
import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;

public class Grimoire extends Item {
    public Grimoire(Properties p_41383_) {
        super(p_41383_);
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
            if (enchantment.category == EnchantmentRegistry.GRIMOIRE ||  enchantment.category == EnchantmentCategory.BREAKABLE || enchantment.category == EnchantmentCategory.VANISHABLE)
                return true;
        }
        return false;
    }


    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand p_41130_) {
        ItemStack itemstack = player.getItemInHand(p_41130_);
        int rc = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.RAPID_COOLING.get(), itemstack);
        int ss = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.STRENGTH_SPELL.get(), itemstack);
        int ps = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.PROTECTION_SPELL.get(), itemstack);
        int hs = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.HEAL_SPELL.get(), itemstack);
        int ls = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.LAUNCH_SPELL.get(), itemstack);
        int es = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.EXTENSION_SPELL.get(), itemstack);
        if (level instanceof ServerLevel serverLevel && (player.getInventory().contains(ItemRegistry.STAR_DUST.get().getDefaultInstance()) || player.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.INFINITE_SPELL.get(), itemstack) != 0)) {
            if (!player.isCrouching()){
                GrimoireBullet bullet = new GrimoireBullet(level, player);
                if (EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.LIGHTNING_SPELL.get(), itemstack) != 0){
                    bullet.isLightning(true);
                }
                bullet.setDamage(EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.DAMAGE_SPELL.get(), itemstack));
                bullet.setExplode(EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.EXPLODE_SPELL.get(), itemstack));
                bullet.setFire(EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.FIRE_SPELL.get(), itemstack));
                bullet.setFreeze(EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.FREEZE_SPELL.get(), itemstack));
                bullet.setLaunch(ls);
                bullet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, ls == 0 ? 1.5f : 1.5f * (float)(ls / 2), 1.0F);
                level.addFreshEntity(bullet);
                level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_EYE_LAUNCH, SoundSource.PLAYERS, 1F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
                player.getCooldowns().addCooldown(this, rc == 0 ? 20 : 20 - 4 * rc);
                itemstack.hurtAndBreak(1, player, (p_40665_) -> {
                    p_40665_.broadcastBreakEvent(player.getUsedItemHand());
                });
            } else {
                double r0 = 5.0D + 0.5 * es;
                double n0 = 128.0D;
                for(int i = 0; i < n0; ++i) {
                    double sin = r0 * Math.sin((0.5 * i / n0) * 180D * Math.PI);
                    double cos = Math.cos((0.5 * i / n0) * 180D * Math.PI);
                    serverLevel.sendParticles(ParticleTypes.WAX_OFF, player.getX() + sin, player.getY(), player.getZ() + r0 * cos, 1, 0.1D, 0.1D, 0.1D, 0.0D);
                }
                List<Entity> entities = level.getEntitiesOfClass(Entity.class, new AABB(player.getX(), player.getY(), player.getZ(), player.getX(), player.getY(), player.getZ()).inflate(r0*2), e -> true).stream()
                        .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(player.getX(), player.getY(), player.getZ()))).toList();
                for (Entity target : entities) {
                    double dx = target.getX() - player.getX();
                    double dy = target.getY() - player.getY();
                    double dz = target.getZ() - player.getZ();
                    if (Math.pow(dx,2) + Math.pow(dy,2) + Math.pow(dz,2) <= Math.pow(r0,2)){
                        if(target instanceof LivingEntity living){
                            if (living instanceof ServerPlayer || (living instanceof TamableAnimal animal && animal.isTame())){
                                if(ss != 0){
                                    living.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300, ss - 1, true, true, true));
                                }
                                if(ps != 0){
                                    living.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300, ps - 1, true, true, true));
                                }
                                living.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 300, 1, true, true, true));
                                living.heal(hs == 0 ? 8.0F : 8.0F + 3.0F * hs);
                                living.level().broadcastEntityEvent(living, (byte)18);
                            }
                        }
                    }
                }
                level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 1F, 1F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
                player.getCooldowns().addCooldown(this, rc == 0 ? 400 : 400 - 20 * rc);
                itemstack.hurtAndBreak(3, player, (p_40665_) -> {
                    p_40665_.broadcastBreakEvent(player.getUsedItemHand());
                });
            }
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild && EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.INFINITE_SPELL.get(), itemstack) == 0) {
            player.getInventory().clearOrCountMatchingItems(p -> new ItemStack(ItemRegistry.STAR_DUST.get()).getItem() == p.getItem(), 1, player.inventoryMenu.getCraftSlots());
        }
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    @Override
    public boolean isEnchantable(ItemStack p_41456_) {
        return true;
    }

    @Override
    public int getEnchantmentValue(ItemStack itemStack){
        return 15;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.translatable("tooltip.smc.perkin_grimoire").withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.translatable("tooltip.smc.perkin_grimoire_1").withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.translatable("tooltip.smc.perkin_grimoire_2").withStyle(ChatFormatting.BLUE));
    }
}
