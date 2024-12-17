package me.loving11ish.redlightgreenlight.tasks;

import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import org.bukkit.Bukkit;

public class OnlinePlayerTasks implements Runnable {

    @Override
    public void run() {
        synchronized (RedLightGreenLight.getPlugin().onlinePlayers) {
            RedLightGreenLight.getPlugin().onlinePlayers.clear();
        }

        RedLightGreenLight.getPlugin().getFoliaLib().getScheduler().runNextTick((task) -> {
            synchronized (RedLightGreenLight.getPlugin().onlinePlayers) {
                RedLightGreenLight.getPlugin().onlinePlayers.addAll(Bukkit.getOnlinePlayers());
            }
        });
    }
}
