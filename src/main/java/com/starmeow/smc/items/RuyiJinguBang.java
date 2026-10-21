package com.starmeow.smc.items;

import com.starmeow.smc.config.Config;
import com.starmeow.smc.entities.ThrownSwordEntity;
import com.starmeow.smc.entities.projectiles.GiantRuyiJinguBang;
import com.starmeow.smc.entities.projectiles.ThrownBroccoliBoom;
import com.starmeow.smc.helper.ItemHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class RuyiJinguBang extends SwordItem {
    public RuyiJinguBang(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
    }

    public void releaseUsing(ItemStack itemstack, Level level, LivingEntity living, int p_40670_) {
        int i = this.getUseDuration(itemstack) - p_40670_;
        if (i >= 20) {
            LivingEntity selectedTarget = null;

            for (int s = 1; s <= 64; ++s){
                double sx = s * living.getLookAngle().x + living.getX();
                double sy = s * living.getLookAngle().y + living.getY() + living.getEyeHeight();
                double sz = s * living.getLookAngle().z + living.getZ();
                Vec3 svec3 = new Vec3(sx,sy,sz);
                AABB aabb = new AABB(svec3, svec3).inflate(1.5);
                List<Entity> entities = living.level().getEntitiesOfClass(Entity.class, aabb);
                for (Entity target : entities) {
                    if (target != living && target instanceof LivingEntity living1 && living1.isAlive() && !living1.isRemoved()){
                        selectedTarget = living1;
                        break;
                    }
                }
            }
            if(selectedTarget != null){
                float yawRadians = - living.getYRot() * Mth.DEG_TO_RAD;
                level.playSound((Player)null, living, SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.0F);
                GiantRuyiJinguBang bang = new GiantRuyiJinguBang(level, selectedTarget.getX(), selectedTarget.getY(), selectedTarget.getZ(), yawRadians, 0, living);
                level.addFreshEntity(bang);
                if(living instanceof Player player){
                    player.getCooldowns().addCooldown(this, Config.BANG_CD.get());
                }
            }
        }
    }

    public InteractionResultHolder<ItemStack> use(Level p_40672_, Player player, InteractionHand p_40674_) {
        ItemStack itemstack = player.getItemInHand(p_40674_);
        if (!player.getCooldowns().isOnCooldown(this)){
            player.startUsingItem(p_40674_);
            p_40672_.playSound((Player)null, player, SoundEvents.EVOKER_PREPARE_SUMMON, SoundSource.PLAYERS, 1.0F, 1.0F);
            return InteractionResultHolder.success(itemstack);
        }
        return InteractionResultHolder.pass(itemstack);
    }

    public int getUseDuration(ItemStack p_40680_) {
        return 72000;
    }

    public UseAnim getUseAnimation(ItemStack p_40678_) {
        return UseAnim.BOW;
    }
    
    @Override
    public Component getName(ItemStack stack) {
        int[] colors = {
                ItemHelper.colorToInt(255, 25, 25),
                ItemHelper.colorToInt(255, 182, 25),
                ItemHelper.colorToInt(255, 255, 188),
                ItemHelper.colorToInt(255, 255, 255),
                ItemHelper.colorToInt(255, 182, 188),
                ItemHelper.colorToInt(255, 182, 25)
        };
        return ItemHelper.customRainbowColor(super.getName(stack), 100, true, 0.16f, colors);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        String string = "tooltip.smc." + stack.getItem();
        tooltip.add(Component.translatable(string).withStyle(ChatFormatting.BLUE));
    }
}
