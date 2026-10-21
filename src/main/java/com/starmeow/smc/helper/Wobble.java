package com.starmeow.smc.helper;

import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;

public class Wobble {
    private double rotation;
    private double rota;
    private long lastUpdateTick;

    public double wobble(Level p_117904_, double p_117905_) {
        if (p_117904_.getGameTime() != this.lastUpdateTick) {
            this.lastUpdateTick = p_117904_.getGameTime();
            double d0 = p_117905_ - this.rotation;
            d0 = Mth.positiveModulo(d0 + 0.5D, 1.0D) - 0.5D;
            this.rota += d0 * 0.1D;
            this.rota *= 0.9D;
            this.rotation = Mth.positiveModulo(this.rotation + this.rota, 1.0D);
        }
        return this.rotation;
    }
}
