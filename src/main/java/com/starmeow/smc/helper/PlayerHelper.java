package com.starmeow.smc.helper;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class PlayerHelper {
    //From Enigmatic Legacy, ExperienceHelper.
    public static int getPlayerXP(Player player) {
        return (int) (getExperienceForLevel(player.experienceLevel) + player.experienceProgress * player.getXpNeededForNextLevel());
    }

    public static int getExperienceForLevel(int level) {
        if (level <= 0)
            return 0;
        if (level > 0 && level < 17)
            return (level * level + 6 * level);
        else if (level > 16 && level < 32)
            return (int) (2.5 * level * level - 40.5 * level + 360);
        else
            return (int) (4.5 * level * level - 162.5 * level + 2220);
    }

    public static int getLevelForExperience(int experience) {
        if (experience <= 0)
            return 0;

        int i = 0;
        while (getExperienceForLevel(i) <= experience) {
            i++;
        }
        return i - 1;
    }

    public static double getMovementSpeed(Vec3 motion){
        double speed = Math.sqrt(motion.x * motion.x + motion.z * motion.z);
        return speed * 20.0;
    }

    public static boolean unModifiedNeedsFood(Player player){
        return player.getFoodData().getFoodLevel() < 20;
    }
}
