package com.starmeow.smc.mixin;

import com.starmeow.smc.init.BlockRegistry;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;

@Mixin(value = Boat.Type.class)
public class MixinBoatType
{
    //From fireproofboats
    @Shadow
    @Final
    @Mutable
    private static Boat.Type[] $VALUES;

    @Shadow
    @Final
    @Mutable
    public static StringRepresentable.EnumCodec<Boat.Type> CODEC;

    @Shadow
    @Final
    @Mutable
    private static IntFunction<Boat.Type> BY_ID;

    @Invoker("<init>")
    public static Boat.Type invokeInit(String enumName, int internalId, Block wood, String name) {
        throw new AssertionError();
    }

    private static final Boat.Type GOLDEN = addType("GOLDEN", Blocks.GOLD_BLOCK, "golden");
    private static final Boat.Type GLASS_GOLDEN = addType("GLASS_GOLDEN", Blocks.GOLD_BLOCK, "glass_golden");
    private static final Boat.Type END = addType("END", Blocks.PURPUR_BLOCK, "end");


    private static Boat.Type addType(String enumName, Block wood, String name) {
        List<Boat.Type> variants = new ArrayList<>(Arrays.asList(MixinBoatType.$VALUES));
        var boatType =  MixinBoatType.invokeInit(enumName, variants.get(variants.size() - 1).ordinal() + 1, wood, name);
        variants.add(boatType);
        MixinBoatType.$VALUES = variants.toArray(new Boat.Type[0]);
        MixinBoatType.CODEC = StringRepresentable.fromEnum(Boat.Type::values);
        MixinBoatType.BY_ID = ByIdMap.continuous(Enum::ordinal, MixinBoatType.$VALUES, ByIdMap.OutOfBoundsStrategy.ZERO);
        return boatType;
    }
}