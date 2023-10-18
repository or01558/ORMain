package net.ordahan.ormain.utils;

import classes.PlayerStatsProvider;
import classes.Provider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

import java.util.concurrent.atomic.AtomicReference;
import static net.ordahan.ormain.ORMain.LOGGER;

public class Providers {
    public static Provider<Double, LivingEntity> makeProvider(String statName) {
        AtomicReference<Double> valueReference = new AtomicReference<>();

        return (living) -> {
            if (living instanceof ServerPlayer player)
                player.getCapability(PlayerStatsProvider.PLAYER_STATS).ifPresent((playerStats) -> {
                    valueReference.set(playerStats.getStat(statName));
                });

            return valueReference.get();
        };
    }
}
