package com.starmeow.smc.client.particles;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;

public class ModParticleTypes {

    public static final ParticleRenderType NO_DEPTH_PARTICLE = new ParticleRenderType() {

        @Override
        public void begin(BufferBuilder buf, TextureManager tex) {
            RenderSystem.depthMask(false);
            RenderSystem.disableDepthTest();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
            buf.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
        }

        @Override
        public void end(Tesselator tess) {
            tess.end();
            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
        }

        @Override
        public String toString() {
            return "smc:no_depth_particle";
        }
    };
}