package com.starmeow.smc.config;
import com.starmeow.smc.StarMeowCraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(modid = StarMeowCraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    public static ForgeConfigSpec COMMON_CONFIG;
    // COMMON
    public static final String LUCKY_CLOVER = "LuckyClover";
    public static ForgeConfigSpec.BooleanValue LUCKY_CLOVER_CREATIVE;
    public static ForgeConfigSpec.BooleanValue LUCKY_CLOVER_STACK;
    public static ForgeConfigSpec.IntValue LUCKY_CLOVER_CD;
    public static ForgeConfigSpec.BooleanValue LUCKY_CLOVER_DISABLE;
    public static final String LUCKY_NUGGET = "LuckyNugget";
    public static ForgeConfigSpec.IntValue LUCKY_NUGGET_DURATION;
    public static ForgeConfigSpec.BooleanValue LUCKY_NUGGET_INFINITE;
    public static final String DEVOUR_SWORD = "DevourSword";
    public static ForgeConfigSpec.BooleanValue DEVOUR_SWORD_SHOOT;
    public static ForgeConfigSpec.DoubleValue DEVOUR_SWORD_ADD;
    public static ForgeConfigSpec.DoubleValue DEVOUR_SWORD_SHOOT_DAMAGE;
    public static ForgeConfigSpec.IntValue DEVOUR_SWORD_UPDATE;
    public static final String WATERING_CAN = "WateringCan";
    public static ForgeConfigSpec.BooleanValue WATERING_CAN_INFINITY;
    public static final String TEMPLATE_SHROUD = "TemplateShroud";
    public static ForgeConfigSpec.BooleanValue TEMPLATE_SHROUD_ABSORB;
    public static ForgeConfigSpec.DoubleValue TEMPLATE_SHROUD_ADD;
    public static ForgeConfigSpec.IntValue TEMPLATE_SHROUD_ABSORB_DURATION;
    public static final String HEIRLOOM_KNIFE = "HeirloomKnife";
    public static ForgeConfigSpec.DoubleValue KNIFE_MIN_ATK;
    public static ForgeConfigSpec.DoubleValue KNIFE_MAX_ATK;

    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> LUCKY_CLOVER_BLACKLIST_ITEM_STRINGS;
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> LUCKY_CLOVER_BLACKLIST_KEYWORDS_STRINGS;
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> LUCKY_CLOVER_WHITELIST_ITEM_STRINGS;
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> LUCKY_CLOVER_WHITELIST_KEYWORDS_STRINGS;
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> KNIFE_BLACKLIST_ITEM_STRINGS;
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> KNIFE_WHITELIST_ITEM_STRINGS;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

        COMMON_BUILDER.push(LUCKY_CLOVER);
        LUCKY_CLOVER_CREATIVE = COMMON_BUILDER.comment("If it's true, Lucky Clover will drop banned items (like Debug and Creative items)")
                .define("LuckyCloverCreative", false);
        LUCKY_CLOVER_STACK = COMMON_BUILDER.comment("If it's true, Lucky Clover will get random stack size of item (depends on the max stack size of this item)")
                .define("LuckyCloverStack", false);
        LUCKY_CLOVER_CD = COMMON_BUILDER.comment("Define the cooldown of Lucky Clover (in MC tick)")
                .defineInRange("LuckyCloverCD", 20, 0, Integer.MAX_VALUE);
        LUCKY_CLOVER_BLACKLIST_ITEM_STRINGS = COMMON_BUILDER
                .comment("A list of items which can never be obtained from Lucky Clover.")
                .defineListAllowEmpty("LuckyCloverBlacklistItems",
                        List.of("minecraft:bedrock",
                                "minecraft:command_block",
                                "minecraft:repeating_command_block",
                                "minecraft:chain_command_block",
                                "minecraft:command_block_minecart",
                                "minecraft:jigsaw",
                                "minecraft:structure_block",
                                "minecraft:structure_void",
                                "minecraft:barrier",
                                "minecraft:light",
                                "minecraft:potion",
                                "minecraft:knowledge_book",
                                "minecraft:end_portal_frame",
                                "create:handheld_worldshaper",
                                "enigmaticlegacy:enigmatic_item",
                                "enigmaticlegacy:the_judgement",
                                "slashblade:slashblade",
                                "tacz:ammo",
                                "tacz:modern_kinetic_gun",
                                "tacz:attachment"
                        ), Config::validateItemName);

        LUCKY_CLOVER_BLACKLIST_KEYWORDS_STRINGS = COMMON_BUILDER
                .comment("A list of keywords. Items ID with those keywords can never be obtained from Lucky Clover.")
                .comment("If you want to ban all items from one mod, write \"mod_id:\", example: \"avaritia:\"")
                .defineListAllowEmpty("LuckyCloverBlacklistKeywords",
                        List.of("creative",
                                "debug"
                        ), Config::validateString);
        LUCKY_CLOVER_WHITELIST_ITEM_STRINGS = COMMON_BUILDER
                .comment("(Not recommended!) A list of items which Lucky Clover can only contain. Empty means no whitelist.")
                .defineListAllowEmpty("LuckyCloverWhitelistItems",
                        List.of(), Config::validateItemName);

        LUCKY_CLOVER_WHITELIST_KEYWORDS_STRINGS = COMMON_BUILDER
                .comment("A list of keywords. Lucky Clover can only contain Items which their ID with those keywords. Empty means no whitelist.")
                .comment("If you want to add all items from one mod or only from minecraft, write \"mod_id:\", example: \"minecraft:\", \"farmersdelight:\"")
                .defineListAllowEmpty("LuckyCloverWhitelistKeywords",
                        List.of(), Config::validateString);
        LUCKY_CLOVER_DISABLE = COMMON_BUILDER.comment("If it's true, Lucky Clover will lose the ability of dropping items.")
                .define("LuckyCloverDisable", false);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.push(LUCKY_NUGGET);
        LUCKY_NUGGET_DURATION = COMMON_BUILDER.comment("Define the duration of effects which gives by Lucky Nugget (in tick)")
                .defineInRange("LuckyNuggetDuration", 6000, 0, Integer.MAX_VALUE);
        LUCKY_NUGGET_INFINITE = COMMON_BUILDER.comment("If it's true, the duration of effects gives by Lucky Nugget will be infinite.")
                .define("LuckyNuggetInfinite", false);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.push(DEVOUR_SWORD);
        DEVOUR_SWORD_SHOOT = COMMON_BUILDER.comment("Whether to enable the capability of shooting sword.")
                .define("ShootingSwords", true);
        DEVOUR_SWORD_ADD = COMMON_BUILDER.comment("Define the amount of damage increased by each sword.")
                .defineInRange("DamageAdd", 0.5, 0, Double.MAX_VALUE);
        DEVOUR_SWORD_SHOOT_DAMAGE = COMMON_BUILDER.comment("Define the percentage of damage inherited from Devour Sword (Devoured sword number * damage inherit * damage add).")
                .defineInRange("DamageInherit", 0.5, 0, Double.MAX_VALUE);
        DEVOUR_SWORD_UPDATE = COMMON_BUILDER.comment("Define the sword amount requirement for increase the shooting sword amount.")
                .defineInRange("UpdateAmount", 9, 0, Integer.MAX_VALUE);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.push(TEMPLATE_SHROUD);
        TEMPLATE_SHROUD_ABSORB = COMMON_BUILDER.comment("Whether to enable the capability of providing absorption hearts.")
                .define("AbsorbAbility", true);
        TEMPLATE_SHROUD_ADD = COMMON_BUILDER.comment("Define the amount of armor increased by each template.")
                .defineInRange("ArmorAdd", 1, 0, Double.MAX_VALUE);
        TEMPLATE_SHROUD_ABSORB_DURATION = COMMON_BUILDER.comment("Define the cooldown of the capability of providing absorption hearts.")
                .defineInRange("AbsorbCooldown", 15, 0, Integer.MAX_VALUE);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.push(WATERING_CAN);
        WATERING_CAN_INFINITY = COMMON_BUILDER.comment("Whether to enable the infinite enchantment on watering can.")
                .define("WateringCanInfinite", true);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.push(HEIRLOOM_KNIFE);
        KNIFE_MIN_ATK = COMMON_BUILDER.comment("Define the min damage provided by sword from Heirloom Knife.")
                .defineInRange("MinDamageRage", 5, 0, Double.MAX_VALUE);
        KNIFE_MAX_ATK = COMMON_BUILDER.comment("Define the max damage provided by sword from Heirloom Knife.")
                .defineInRange("MaxDamageRage", 8, 0, Double.MAX_VALUE);
        KNIFE_WHITELIST_ITEM_STRINGS = COMMON_BUILDER
                .comment("A list of sword items which Heirloom Knife can only contain. Empty means no whitelist.")
                .comment("If anything in this list, it will not be limited by the blacklist from Lucky Clover.")
                .defineListAllowEmpty("HeirloomKnifeWhitelistItems",
                        List.of(), Config::validateItemName);
        KNIFE_BLACKLIST_ITEM_STRINGS = COMMON_BUILDER
                .comment("A list of sword items which can never be obtained from Heirloom Knife.")
                .comment("This list is based on the Lucky Clover blacklist.")
                .defineListAllowEmpty("LuckyCloverBlacklistItems",
                        List.of("smc:devour_sword"
                        ), Config::validateItemName);
        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    public static List<Item> blacklistItems, whitelistItems, easterBunnyEggItems, whitelistSwordItems;

    private static boolean validateItemName(final Object obj)
    {
        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
    }

    private static boolean validateString(final Object obj)
    {
        return obj instanceof String;
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        Set<Item> blacklistItemsByID = LUCKY_CLOVER_BLACKLIST_ITEM_STRINGS.get().stream()
                .map(itemName -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)))
                .collect(Collectors.toSet());

        Set<Item> whitelistItemsByID = LUCKY_CLOVER_WHITELIST_ITEM_STRINGS.get().stream()
                .map(itemName -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)))
                .collect(Collectors.toSet());

        Set<Item> whitelistSwordItemsByID = KNIFE_WHITELIST_ITEM_STRINGS.get().stream()
                .map(itemName -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)))
                .collect(Collectors.toSet());

        Set<Item> blacklistSwordItemsByID = KNIFE_BLACKLIST_ITEM_STRINGS.get().stream()
                .map(itemName -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)))
                .collect(Collectors.toSet());

        Set<Item> blacklistBlocksUnbreakable = BuiltInRegistries.ITEM.stream().filter(item -> {
            if(item instanceof BlockItem blockItem){
                Block block = blockItem.getBlock();
                return block.defaultDestroyTime() < 0;
            }
            return false;
        }).collect(Collectors.toSet());

        Set<Item> blacklistItemsByKeyword = ForgeRegistries.ITEMS.getEntries().stream()
                .filter(entry -> {
                    ResourceLocation rl = entry.getKey().location();
                    String id = rl.toString();
                    return LUCKY_CLOVER_BLACKLIST_KEYWORDS_STRINGS.get().stream().anyMatch(id::contains);
                })
                .map(Map.Entry::getValue)
                .collect(Collectors.toSet());

        Set<Item> whitelistItemsByKeyword = ForgeRegistries.ITEMS.getEntries().stream()
                .filter(entry -> {
                    ResourceLocation rl = entry.getKey().location();
                    String id = rl.toString();
                    return LUCKY_CLOVER_WHITELIST_KEYWORDS_STRINGS.get().stream().anyMatch(id::contains);
                })
                .map(Map.Entry::getValue)
                .collect(Collectors.toSet());

        Set<Item> blacklist = Stream.of(blacklistItemsByID, blacklistItemsByKeyword, blacklistBlocksUnbreakable)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());

        Set<Item> whitelist0 = Stream.of(whitelistItemsByID, whitelistItemsByKeyword)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());

        Set<Item> whitelist = whitelist0.isEmpty() ? BuiltInRegistries.ITEM.stream().collect(Collectors.toSet()) : BuiltInRegistries.ITEM.stream().filter(whitelist0::contains).collect(Collectors.toSet());

        whitelistItems = whitelist.stream().filter(item -> !blacklist.contains(item)).toList();

        blacklistItems = BuiltInRegistries.ITEM.stream().filter(item -> !whitelistItems.contains(item)).toList();

        easterBunnyEggItems = BuiltInRegistries.ITEM.stream().filter(item -> item instanceof SpawnEggItem).toList();

        if(!whitelistSwordItemsByID.isEmpty()){
            whitelistSwordItems = whitelistSwordItemsByID.stream().toList();
        }else{
            whitelistSwordItems = whitelist.stream().filter(item -> !blacklistSwordItemsByID.contains(item)).toList();
        }
    }
}