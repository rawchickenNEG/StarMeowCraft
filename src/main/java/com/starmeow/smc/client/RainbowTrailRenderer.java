package com.starmeow.smc.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;

import com.starmeow.smc.StarMeowCraft;
import com.starmeow.smc.entities.projectiles.MeowBall;
import com.starmeow.smc.init.PotionEffectRegistry;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import org.joml.Matrix4f;

import java.awt.Color;
import java.util.*;

@Mod.EventBusSubscriber(modid = StarMeowCraft.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RainbowTrailRenderer {

    private static final Map<UUID, Deque<TrailPoint>> TRAILS = new HashMap<>();
    private static final int LIFE_TIME = 20;
    private static final int MAX_POINTS = 30;
    private static final double WIDTH = 0.28D;

    private record TrailPoint(Vec3 position, float height, long tick) {
    }
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getInstance();

        if (mc.level == null) {
            TRAILS.clear();
            return;
        }

        long gameTime = mc.level.getGameTime();

        for (Entity entity : mc.level.entitiesForRendering()) {
            if (!shouldRenderTrail(entity)) {
                continue;
            }
            UUID uuid = entity.getUUID();

            Deque<TrailPoint> trail = TRAILS.computeIfAbsent(uuid, id -> new ArrayDeque<>());

            Vec3 position = new Vec3(entity.xOld, entity.yOld, entity.zOld);

            TrailPoint last = trail.peekLast();

            if (last == null || last.position().distanceToSqr(position) > 0.0025D) {
                if (last != null && last.position().distanceToSqr(position) > 64.0D) {
                    trail.clear();
                }
                trail.addLast(new TrailPoint(position, entity.getBbHeight(), gameTime));
            }

            while (!trail.isEmpty() && gameTime - trail.peekFirst().tick() > LIFE_TIME) {
                trail.removeFirst();
            }

            while (trail.size() > MAX_POINTS) {
                trail.removeFirst();
            }
        }

        for (Iterator<Map.Entry<UUID, Deque<TrailPoint>>> iterator = TRAILS.entrySet().iterator(); iterator.hasNext();) {

            Map.Entry<UUID, Deque<TrailPoint>> entry = iterator.next();
            Deque<TrailPoint> trail = entry.getValue();
            while (!trail.isEmpty() && gameTime - trail.peekFirst().tick() > LIFE_TIME) {
                trail.removeFirst();
            }
            if (trail.isEmpty()) {
                iterator.remove();
            }
        }
    }

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES) return;
        Minecraft mc = Minecraft.getInstance();

        if (mc.level == null) return;

        Camera camera = event.getCamera();
        Vec3 cameraPos = camera.getPosition();
        PoseStack poseStack = event.getPoseStack();
        poseStack.pushPose();

        poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        Matrix4f matrix = poseStack.last().pose();

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        RenderSystem.disableCull();

        RenderSystem.depthMask(false);

        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        for (Map.Entry<UUID, Deque<TrailPoint>> entry : TRAILS.entrySet()) {
            List<TrailPoint> points = new ArrayList<>(entry.getValue());

            if (points.size() < 2) continue;

            renderTrailRainbow(matrix, points, mc.level.getGameTime());
        }

        RenderSystem.depthMask(true);

        RenderSystem.enableCull();
        RenderSystem.disableBlend();

        poseStack.popPose();
    }

    private static void renderTrailRainbow(Matrix4f matrix, List<TrailPoint> points, long gameTime) {
        if (points.size() < 2) return;

        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder buffer = tessellator.getBuilder();

        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

        final int VERTICAL_SEGMENTS = 12;

        for (int i = 0; i < points.size() - 1; i++) {

            TrailPoint point1 = points.get(i);
            TrailPoint point2 = points.get(i + 1);

            Vec3 pos1 = point1.position();
            Vec3 pos2 = point2.position();

            float height = Math.min(point1.height(), point2.height());
            long age1 = gameTime - point1.tick();
            long age2 = gameTime - point2.tick();
            float alpha1 = 1.0F - age1 / (float) LIFE_TIME;
            float alpha2 = 1.0F - age2 / (float) LIFE_TIME;

            alpha1 = Math.max(0.0F, Math.min(1.0F, alpha1));
            alpha2 = Math.max(0.0F, Math.min(1.0F, alpha2));
            alpha1 *= 0.75F;
            alpha2 *= 0.75F;

            for (int segment = 0; segment < VERTICAL_SEGMENTS; segment++) {
                float bottomProgress = segment / (float) VERTICAL_SEGMENTS;
                float topProgress = (segment + 1) / (float) VERTICAL_SEGMENTS;

                double yBottom1 = pos1.y + height * bottomProgress;
                double yTop1 = pos1.y + height * topProgress;
                double yBottom2 = pos2.y + height * bottomProgress;
                double yTop2 = pos2.y + height * topProgress;

                float bottomHue = getRainbowHue(bottomProgress);
                float topHue = getRainbowHue(topProgress);

                int bottomRGB = Color.HSBtoRGB(bottomHue, 1.0F, 1.0F);
                int topRGB = Color.HSBtoRGB(topHue, 1.0F, 1.0F);

                float bottomR = ((bottomRGB >> 16) & 255) / 255.0F;
                float bottomG = ((bottomRGB >> 8) & 255) / 255.0F;
                float bottomB = (bottomRGB & 255) / 255.0F;
                float topR = ((topRGB >> 16) & 255) / 255.0F;
                float topG = ((topRGB >> 8) & 255) / 255.0F;
                float topB = (topRGB & 255) / 255.0F;

                buffer.vertex(matrix, (float) pos1.x, (float) yBottom1, (float) pos1.z).color(bottomR, bottomG, bottomB, alpha1).endVertex();
                buffer.vertex(matrix, (float) pos2.x, (float) yBottom2, (float) pos2.z).color(bottomR, bottomG, bottomB, alpha2).endVertex();
                buffer.vertex(matrix, (float) pos2.x, (float) yTop2, (float) pos2.z).color(topR, topG, topB, alpha2).endVertex();
                buffer.vertex(matrix, (float) pos1.x, (float) yTop1, (float) pos1.z).color(topR, topG, topB, alpha1).endVertex();
            }
        }

        BufferUploader.drawWithShader(
                buffer.end()
        );
    }

    private static float getRainbowHue(float verticalProgress) {
        return (1.0F - verticalProgress) * 0.78F;
    }

    private static void renderTrailLerp(Matrix4f matrix, List<TrailPoint> points, Vec3 cameraPos, long gameTime) {

        Tesselator tessellator = Tesselator.getInstance();

        BufferBuilder buffer = tessellator.getBuilder();

        buffer.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);

        int size = points.size();

        for (int i = 0; i < size; i++) {

            Vec3 current = points.get(i).position();
            Vec3 previous = points.get(Math.max(0, i - 1)).position();
            Vec3 next = points.get(Math.min(size - 1, i + 1)).position();
            Vec3 tangent = next.subtract(previous);

            if (tangent.lengthSqr() < 0.0001D) {
                tangent = new Vec3(0, 0, 1);
            }

            tangent = tangent.normalize();

            Vec3 toCamera = cameraPos.subtract(current);

            if (toCamera.lengthSqr() < 0.0001D) {
                toCamera = new Vec3(0, 1, 0);
            }

            toCamera = toCamera.normalize();

            Vec3 side = tangent.cross(toCamera);

            if (side.lengthSqr() < 0.0001D) {
                side = tangent.cross(new Vec3(0, 1, 0));
            }

            if (side.lengthSqr() < 0.0001D) {
                side = new Vec3(1, 0, 0);
            }

            side = side.normalize().scale(WIDTH);

            float progress = i / (float) (size - 1);
            float hue = (progress * 0.8F + gameTime * 0.015F) % 1.0F;
            int rgb = Color.HSBtoRGB(hue, 1.0F, 1.0F);
            float red = ((rgb >> 16) & 255) / 255.0F;
            float green = ((rgb >> 8) & 255) / 255.0F;
            float blue = (rgb & 255) / 255.0F;
            long age = gameTime - points.get(i).tick();
            float lifeAlpha = 1.0F - age / (float) LIFE_TIME;
            lifeAlpha = Math.max(0.0F, Math.min(1.0F, lifeAlpha));
            float alpha = lifeAlpha * progress * 0.75F;

            Vec3 left = current.add(side);
            Vec3 right = current.subtract(side);


            buffer.vertex(matrix, (float) left.x, (float) left.y, (float) left.z).color(red, green, blue, alpha).endVertex();
            buffer.vertex(matrix, (float) right.x, (float) right.y, (float) right.z).color(red, green, blue, alpha).endVertex();
        }
        BufferUploader.drawWithShader(buffer.end());
    }

    private static boolean shouldRenderTrail(Entity entity) {
        if(entity instanceof LivingEntity livingEntity && livingEntity.hasEffect(PotionEffectRegistry.RAINBOW.get())){
            return true;
        }
        return entity instanceof MeowBall;
    }
}