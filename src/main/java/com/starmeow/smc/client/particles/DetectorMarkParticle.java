package com.starmeow.smc.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DetectorMarkParticle extends TextureSheetParticle {

    protected DetectorMarkParticle(ClientLevel level, double x, double y, double z, double vx, double vy, double vz) {
        super(level, x, y, z, 0, 0, 0);
        this.lifetime = 11;
        this.gravity = 0.0F;
        this.hasPhysics = false;
        this.quadSize = 0.5f;
    }

    @Override
    public void tick() {
        if (this.age++ >= this.lifetime) {
            this.remove();
        }
    }

    public int getLightColor(float p_106821_) {
        return 255;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ModParticleTypes.NO_DEPTH_PARTICLE;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet sprites) {
            this.sprite = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double vx, double vy, double vz) {
            DetectorMarkParticle p = new DetectorMarkParticle(level, x, y, z, vx, vy, vz);
            p.pickSprite(this.sprite);
            return p;
        }
    }
}