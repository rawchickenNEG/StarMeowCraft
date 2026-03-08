package com.starmeow.smc.events;

import com.starmeow.smc.init.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber
public class LevelEvents {


    // 用来记录每个世界上一次的天气状态
    private static final Map<ResourceKey<Level>, Boolean> LAST_RAIN_STATE = new HashMap<>();
    private static final Map<ResourceKey<Level>, Boolean> LAST_THUNDER_STATE = new HashMap<>();

    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        Level level = event.level;

        // 只在服务端 + END 阶段处理
        if (level.isClientSide() || event.phase != TickEvent.Phase.END) {
            return;
        }

        ResourceKey<Level> key = level.dimension();

        boolean isRainingNow = level.isRaining();
        boolean isThunderingNow = level.isThundering();

        boolean wasRaining = LAST_RAIN_STATE.getOrDefault(key, isRainingNow);
        boolean wasThundering = LAST_THUNDER_STATE.getOrDefault(key, isThunderingNow);

        // 从“在下雨”变成“不下雨” → 雨刚刚结束
        if (wasRaining && !isRainingNow) {
            onRainStopped(level);
        }

        // 从“雷雨”变成“非雷雨” → 雷雨刚刚结束
        if (wasThundering && !isThunderingNow) {
            onThunderStopped(level);
        }

        // 更新记录
        LAST_RAIN_STATE.put(key, isRainingNow);
        LAST_THUNDER_STATE.put(key, isThunderingNow);
    }

    private static void onRainStopped(Level level) {
        for (Player player : level.players()) {
            for(int i = 0; i < 3; i++){
                if(player.getRandom().nextInt(8) == 0){
                    spawnItemEntities(level, player);
                }
            }
        }
    }

    private static void onThunderStopped(Level level) {
        for (Player player : level.players()) {
            for(int i = 0; i < 4; i++){
                if(player.getRandom().nextInt(4) == 0){
                    spawnItemEntities(level, player);
                }
            }
        }
    }

    private static void spawnItemEntities(Level level, Player player){
        BlockPos pos = new BlockPos(player.getOnPos().getX() + player.getRandom().nextInt(-16, 16),player.getRandom().nextInt(190, 200), player.getOnPos().getZ() + player.getRandom().nextInt(-16, 16));
        for (BlockPos tmpPos : BlockPos.withinManhattan(pos, 16, 16, 16)){
            if(level.getBlockState(tmpPos).getBlock() != Blocks.AIR) return;
        }
        ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, new ItemStack(ItemRegistry.RAINBOW_CHIP.get()));
        itemEntity.setPickUpDelay(10);
        itemEntity.setGlowingTag(true);
        itemEntity.setNoGravity(true);
        itemEntity.setDeltaMovement(new Vec3(0, 0, 0));
        level.addFreshEntity(itemEntity);
    }
}
