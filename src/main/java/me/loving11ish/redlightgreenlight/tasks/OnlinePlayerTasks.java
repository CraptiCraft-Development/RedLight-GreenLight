package me.loving11ish.redlightgreenlight.tasks;

import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import org.bukkit.Bukkit;

public class OnlinePlayerTasks implements Runnable {

    @Override
    public void run() {
        RedLightGreenLight.getPlugin().onlinePlayers.clear();
        RedLightGreenLight.getPlugin().onlinePlayers.addAll(Bukkit.getOnlinePlayers());
    }
}
