package com.starmeow.smc.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

import static net.minecraft.world.level.storage.loot.LootTable.createStackSplitter;

public class SMCAddLootModifier extends LootModifier {
    public static final Supplier<Codec<SMCAddLootModifier>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.create(inst -> codecStart(inst)
                    .and(ResourceLocation.CODEC.fieldOf("lootTable").forGetter((m) -> m.lootTable))
                    .apply(inst, SMCAddLootModifier::new)));
    private final ResourceLocation lootTable;

    protected SMCAddLootModifier(LootItemCondition[] conditionsIn, ResourceLocation lootTable) {
        super(conditionsIn);
        this.lootTable = lootTable;
    }

    @Nonnull
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        LootTable extraTable = context.getResolver().getLootTable(this.lootTable);
        extraTable.getRandomItemsRaw(context, createStackSplitter(context.getLevel(), generatedLoot::add));
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
