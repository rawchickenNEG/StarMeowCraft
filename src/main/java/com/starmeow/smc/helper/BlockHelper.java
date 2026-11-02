package com.starmeow.smc.helper;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class BlockHelper {
    public static float getBlockPosDistance(BlockPos pos1, BlockPos pos2){
        return (float) (Math.sqrt(Math.pow(Math.sqrt(Math.pow(pos1.getX() - pos2.getX(), 2) + Math.pow(pos1.getY() - pos2.getY(), 2)), 2) + Math.pow(pos1.getZ() - pos2.getZ(), 2)));
    }

    public static boolean isMaxAgedBlock(BlockState state){
        if (isAgedBlock(state)) {
            IntegerProperty intProp = getStateAgeProperty(state);
            if(intProp != null){
                int ageValue = state.getValue(intProp);
                int maxAgeValue = intProp.max;
                if(ageValue == maxAgeValue){
                    return !(state.getBlock() instanceof StemBlock);
                }
            }
        }
        return false;
    }

    public static boolean isGrowingAgedBlock(BlockState state){
        if (isAgedBlock(state)) {
            IntegerProperty intProp = getStateAgeProperty(state);
            if(intProp != null){
                int ageValue = state.getValue(intProp);
                int maxAgeValue = intProp.max;
                if(ageValue != maxAgeValue){
                    return !(state.getBlock() instanceof StemBlock);
                }
            }
        }
        return false;
    }

    public static boolean isAgedBlock(BlockState state){
        return state.getProperties().stream().anyMatch(prop -> prop instanceof IntegerProperty && "age".equals(prop.getName().toLowerCase()));
    }

    public static BlockState setBlockAge(BlockState state, int age){
        if (isAgedBlock(state)) {
            IntegerProperty intProp = getStateAgeProperty(state);
            if(intProp != null){
                return state.setValue(intProp, age);
            }
        }
        return state;
    }

    public static BlockState setBlockAgeToMax(BlockState state){
        if (isAgedBlock(state)) {
            IntegerProperty intProp = getStateAgeProperty(state);
            if(intProp != null){
                return state.setValue(intProp, intProp.max);
            }
        }
        return state;
    }

    public static IntegerProperty getStateAgeProperty(BlockState state){
        if (isAgedBlock(state)) {
            for (var prop : state.getProperties()) {
                if (prop instanceof IntegerProperty intProp && "age".equals(intProp.getName().toLowerCase())) {
                    return intProp;
                }
            }
        }
        return null;
    }
}
