package me.loving11ish.redlightgreenlight.updatesystem;

import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import me.loving11ish.redlightgreenlight.utils.ColorUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    RedLightGreenLight plugin;

    public JoinEvent(RedLightGreenLight plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("playergui.update")) {
            new UpdateChecker(96866).getVersion(version -> {
                try {
                    if (!(plugin.getDescription().getVersion().equalsIgnoreCase(version))) {
                        player.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Update-1")));
                        player.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Update-2")));
                        player.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Update-3")));
                    }
                }catch (NullPointerException e){
                    player.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Update-check-failure")));
                }
            });
        }
    }
}
