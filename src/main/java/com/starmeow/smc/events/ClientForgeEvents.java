package com.starmeow.smc.events;


import com.mojang.blaze3d.systems.RenderSystem;
import com.starmeow.smc.StarMeowCraft;
import com.starmeow.smc.cache.MiningFaceCache;
import com.starmeow.smc.helper.CuriosHelper;
import com.starmeow.smc.init.ItemRegistry;
import com.starmeow.smc.init.NetworkRegistry;
import com.starmeow.smc.init.PotionEffectRegistry;
import com.starmeow.smc.items.DevourSword;
import com.starmeow.smc.helper.WeaponHelper;
import com.starmeow.smc.packet.EndBoatControlPacket;
import com.starmeow.smc.packet.SwordSlashC2S;
import com.starmeow.smc.packet.WingBoostC2S;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = StarMeowCraft.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeEvents {

    private static int lastSentTick = -999999;
    private static boolean lastBoostPressed = false;

    private static final ResourceLocation EFFECT_HEART = new ResourceLocation(StarMeowCraft.MODID,"textures/gui/smc_effect_heart.png");
    private static int lastHealth;
    private static int displayHealth;
    private static long lastHealthTime;
    private static long healthBlinkTime;


    public static void sendOncePerTick(Player player) {
        int now = player.tickCount;
        if (now == lastSentTick) return;
        lastSentTick = now;

        NetworkRegistry.sendToServer(new SwordSlashC2S());
    }

    @SubscribeEvent
    public static void onLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
        Player player = event.getEntity();
        if (player == null) return;

        ItemStack stack = player.getMainHandItem();
        if (!WeaponHelper.isValidWeapon(stack)) return;

        if (Minecraft.getInstance().player != player) return;

        sendOncePerTick(player);
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Minecraft mc = Minecraft.getInstance();
            LocalPlayer player = mc.player;

            if (player != null){
                if(player.getMainHandItem().is(ItemRegistry.ZENISH.get()) || (player.getMainHandItem().is(ItemRegistry.DEVOUR_SWORD.get()) && DevourSword.hasZenishAbility(player.getMainHandItem()))){
                    boolean swinging = mc.options.keyAttack.isDown();
                    if(swinging){
                        NetworkRegistry.sendToServer(new SwordSlashC2S());
                    }
                }

                if (player.getVehicle() instanceof Boat boat){
                    if (boat.getVariant() != Boat.Type.byName("end")) return;

                    boolean jumping = mc.options.keyJump.isDown();
                    boolean shift   = mc.options.keyShift.isDown();

                    NetworkRegistry.CHANNEL.sendToServer(new EndBoatControlPacket(jumping, shift));
                }

                boolean boost = mc.options.keyJump.isDown();
                if(player.isFallFlying() && (player.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.DIVINE_WING.get()) || CuriosHelper.hasCharm(player, ItemRegistry.DIVINE_WING.get())) && !player.getCooldowns().isOnCooldown(ItemRegistry.DIVINE_WING.get())){
                    if(boost && !lastBoostPressed){
                        NetworkRegistry.sendToServer(new WingBoostC2S());
                    }
                }
                lastBoostPressed = boost;
            }


        }
    }

    @SubscribeEvent
    public static void clientTickSetFaceCache(TickEvent.ClientTickEvent event) {

        Minecraft mc = Minecraft.getInstance();
        if(mc.player == null) return;
        if(!(mc.hitResult instanceof BlockHitResult hit)) return;
        Direction face = hit.getDirection();
        MiningFaceCache.set(mc.player.getUUID(), face);
    }

    @SubscribeEvent
    public static void onPreRenderHUD(RenderGuiOverlayEvent.Pre event) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            Minecraft mc = Minecraft.getInstance();
            ForgeGui gui = (ForgeGui)mc.gui;
            if (event.getOverlay() == VanillaGuiOverlay.PLAYER_HEALTH.type() && !mc.options.hideGui && gui.shouldDrawSurvivalElements()) {
                if (player.hasEffect(PotionEffectRegistry.OVER_SPEED.get())) {
                    if(player.getEffect(PotionEffectRegistry.OVER_SPEED.get()) != null && player.getEffect(PotionEffectRegistry.OVER_SPEED.get()).getAmplifier() > 4) {
                        CustomHealth(event, 25);
                    }
                }
            }
        }
    }

    //From L_Ender's Cataclysm
    private static void CustomHealth(RenderGuiOverlayEvent.Pre event,int back){
        Player player = Minecraft.getInstance().player;
        RandomSource random = player.getRandom();
        Minecraft mc = Minecraft.getInstance();
        ForgeGui gui = (ForgeGui)mc.gui;
        GuiGraphics stack = event.getGuiGraphics();
        gui.setupOverlayRenderState(true, false);
        int width = event.getWindow().getGuiScaledWidth();
        int height = event.getWindow().getGuiScaledHeight();
        event.setCanceled(true);
        RenderSystem.setShaderTexture(0, EFFECT_HEART);
        RenderSystem.enableBlend();
        int health = Mth.ceil(player.getHealth());
        int tickCount = gui.getGuiTicks();
        boolean highlight = healthBlinkTime > (long) tickCount && (healthBlinkTime - (long) tickCount) / 3L % 2L == 1L;
        if (health < lastHealth && player.invulnerableTime > 0) {
            lastHealthTime = Util.getMillis();
            healthBlinkTime = (long) (tickCount + 20);
        } else if (health > lastHealth && player.invulnerableTime > 0) {
            lastHealthTime = Util.getMillis();
            healthBlinkTime = (long) (tickCount + 10);
        }

        if (Util.getMillis() - lastHealthTime > 1000L) {
            lastHealth = health;
            displayHealth = health;
            lastHealthTime = Util.getMillis();
        }

        lastHealth = health;
        int healthLast = displayHealth;
        AttributeInstance maxHealth = player.getAttribute(Attributes.MAX_HEALTH);
        float healthMax = (float) maxHealth.getValue();
        int absorbtion = Mth.ceil(player.getAbsorptionAmount());
        int healthRows = Mth.ceil((healthMax + (float) absorbtion) / 2.0F / 10.0F);
        int rowHeight = Math.max(10 - (healthRows - 2), 3);
        random.setSeed((long) (tickCount * 312871L));
        int left = width / 2 - 91;
        int top = height - gui.leftHeight;
        gui.leftHeight += healthRows * rowHeight;
        if (rowHeight != 10) {
            gui.leftHeight += 10 - rowHeight;
        }

        int regen = -1;
        if (player.hasEffect(MobEffects.REGENERATION)) {
            regen = tickCount % Mth.ceil(healthMax + 5.0F);
        }

        int TOP = player.level().getLevelData().isHardcore() ? 9 : 0;
        int BACKGROUND = highlight ? back : 16;
        int margin = 34;
        float absorbtionRemaining = (float) absorbtion;

        for (int i = Mth.ceil((healthMax + (float) absorbtion) / 2.0F) - 1; i >= 0; --i) {
            int row = Mth.ceil((float) (i + 1) / 10.0F) - 1;
            int x = left + i % 10 * 8;
            int y = top - row * rowHeight;
            if (health <= 4) {
                y += random.nextInt(2);
            }

            if (i == regen) {
                y -= 2;
            }

            stack.blit(EFFECT_HEART, x, y, BACKGROUND, TOP, 9, 9);
            if (highlight) {
                if (i * 2 + 1 < healthLast) {
                    stack.blit(EFFECT_HEART, x, y, margin, TOP, 9, 9);
                } else if (i * 2 + 1 == healthLast) {
                    stack.blit(EFFECT_HEART, x, y, margin + 9, TOP, 9, 9);
                }
            }

            if (absorbtionRemaining > 0.0F) {
                if (absorbtionRemaining == (float) absorbtion && (float) absorbtion % 2.0F == 1.0F) {
                    stack.blit(EFFECT_HEART, x, y, margin + 9, TOP, 9, 9);
                    --absorbtionRemaining;
                } else {
                    stack.blit(EFFECT_HEART, x, y, margin, TOP, 9, 9);
                    absorbtionRemaining -= 2.0F;
                }
            } else if (i * 2 + 1 < health) {
                stack.blit(EFFECT_HEART, x, y, margin, TOP, 9, 9);
            } else if (i * 2 + 1 == health) {
                stack.blit(EFFECT_HEART, x, y, margin + 9, TOP, 9, 9);
            }
        }

        RenderSystem.disableBlend();
        RenderSystem.setShaderTexture(0, EFFECT_HEART);
    }
}
