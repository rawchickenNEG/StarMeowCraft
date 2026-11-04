package com.starmeow.smc.items.curiosequipable;

import com.starmeow.smc.client.renderer.item.CustomArmorRenderProperties;
import com.starmeow.smc.config.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class DivineShard extends ArmorItem implements BaseCurioItem {

    public DivineShard(ArmorMaterial p_40386_, Type p_266831_, Properties p_40388_) {
        super(p_40386_, p_266831_, p_40388_);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        onActiveTick(stack, level, player);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        Level level = slotContext.entity().level();
        if(entity instanceof Player player){
            onActiveTick(stack, level, player);
        }
    }

    private void onActiveTick(ItemStack stack, Level level, Player player){
        if(!level.isClientSide()) {
            CompoundTag tag = stack.getOrCreateTag();
            int challenge = tag.getInt("SMCShardChallenge");
            int lastEffect = tag.getInt("SMCShardEffect");
            if(challenge == 3){
                if(player.position().y() >= Config.DIVINE_SHARD_CHALLENGE_3.get()){
                    tag.putInt("SMCShardChallenge", 7);
                    level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.AMETHYST_CLUSTER_BREAK, SoundSource.PLAYERS, 1F, 2F);
                }
            }
            if(challenge == 1){
                boolean flag = false;
                int effect = player.getActiveEffects().size();
                if(lastEffect >= Config.DIVINE_SHARD_CHALLENGE_1.get() && effect == 0){
                    flag = true;
                }else{
                    tag.putInt("SMCShardEffect", effect);
                }
                if(flag){
                    tag.putInt("SMCShardChallenge", 5);
                    tag.remove("SMCShardEffect");
                    level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.AMETHYST_CLUSTER_BREAK, SoundSource.PLAYERS, 1F, 2F);
                }
            }
        }
    }

    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept((IClientItemExtensions)getArmorRenderProperties());
    }

    public Object getArmorRenderProperties() {
        return new CustomArmorRenderProperties();
    }

    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "smc:textures/models/halo/shard.png";
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        CompoundTag tag = stack.getOrCreateTag();
        String string = "tooltip.smc." + stack.getItem();
        int challenge = tag.getInt("SMCShardChallenge");
        tooltip.add(Component.translatable(string).withStyle(ChatFormatting.BLUE));
        if(challenge > 4){
            tooltip.add(Component.translatable(string + "_" + challenge).withStyle(ChatFormatting.GOLD));
        }else{
            switch (challenge){
                case 1 -> tooltip.add(Component.translatable(string + "_" + challenge, Config.DIVINE_SHARD_CHALLENGE_1.get()).withStyle(ChatFormatting.LIGHT_PURPLE));
                case 2 -> tooltip.add(Component.translatable(string + "_" + challenge, Config.DIVINE_SHARD_CHALLENGE_2.get()).withStyle(ChatFormatting.LIGHT_PURPLE));
                case 3 -> tooltip.add(Component.translatable(string + "_" + challenge, Config.DIVINE_SHARD_CHALLENGE_3.get()).withStyle(ChatFormatting.LIGHT_PURPLE));
                case 4 -> tooltip.add(Component.translatable(string + "_" + challenge, Config.DIVINE_SHARD_CHALLENGE_4.get()).withStyle(ChatFormatting.LIGHT_PURPLE));
                default -> tooltip.add(Component.translatable(string + "_" + challenge).withStyle(ChatFormatting.DARK_AQUA));
            }
        }
    }
}
